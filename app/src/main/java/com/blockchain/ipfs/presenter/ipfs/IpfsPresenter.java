package com.blockchain.ipfs.presenter.ipfs;

import com.blockchain.ipfs.app.Constants;
import com.blockchain.ipfs.base.RxPresenter;
import com.blockchain.ipfs.base.contract.ipfs.IpfsContract;
import com.blockchain.ipfs.base.contract.wechat.WechatContract;
import com.blockchain.ipfs.component.RxBus;
import com.blockchain.ipfs.model.DataManager;
import com.blockchain.ipfs.model.bean.WXItemBean;
import com.blockchain.ipfs.model.event.SearchEvent;
import com.blockchain.ipfs.model.http.response.WXHttpResponse;
import com.blockchain.ipfs.util.RxUtil;
import com.blockchain.ipfs.widget.CommonSubscriber;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * Created by zhengpingli on 2018/6/12.
 */

public class IpfsPresenter extends RxPresenter<IpfsContract.View>
        implements IpfsContract.Presenter {

    private static final int NUM_OF_PAGE = 20;

    private int currentPage = 1;
    private String queryStr = null;

    private DataManager mDataManager;

    @Inject
    public IpfsPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void attachView(IpfsContract.View view) {
        super.attachView(view);
        registerEvent();
    }

    private void registerEvent() {
        addSubscribe(RxBus.getDefault().toFlowable(SearchEvent.class)
                .compose(RxUtil.<SearchEvent>rxSchedulerHelper())
                .filter(new Predicate<SearchEvent>() {
                    @Override
                    public boolean test(@NonNull SearchEvent searchEvent) throws Exception {
                        return searchEvent.getType() == Constants.TYPE_WECHAT;
                    }
                })
                .map(new Function<SearchEvent, String>() {
                    @Override
                    public String apply(SearchEvent searchEvent) {
                        return searchEvent.getQuery();
                    }
                })
                .subscribeWith(new CommonSubscriber<String>(mView, "搜索失败ヽ(≧Д≦)ノ") {
                    @Override
                    public void onNext(String s) {
                        queryStr = s;
                        getSearchWechatData(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        registerEvent();
                    }
                })
        );
    }

    @Override
    public void getWechatData() {
        queryStr = null;
        currentPage = 1;
        addSubscribe(mDataManager.fetchWechatListInfo(NUM_OF_PAGE,currentPage)
                .compose(RxUtil.<WXHttpResponse<List<WXItemBean>>>rxSchedulerHelper())
                .compose(RxUtil.<List<WXItemBean>>handleWXResult())
                .subscribeWith(new CommonSubscriber<List<WXItemBean>>(mView) {
                    @Override
                    public void onNext(List<WXItemBean> wxItemBeen) {
                        mView.showContent(wxItemBeen);
                    }
                })
        );
    }

    @Override
    public void getMoreWechatData() {
        Flowable<WXHttpResponse<List<WXItemBean>>> observable;
        if (queryStr != null) {
            observable = mDataManager.fetchWechatSearchListInfo(NUM_OF_PAGE,++currentPage,queryStr);
        } else {
            observable = mDataManager.fetchWechatListInfo(NUM_OF_PAGE,++currentPage);
        }
        addSubscribe(observable
                .compose(RxUtil.<WXHttpResponse<List<WXItemBean>>>rxSchedulerHelper())
                .compose(RxUtil.<List<WXItemBean>>handleWXResult())
                .subscribeWith(new CommonSubscriber<List<WXItemBean>>(mView, "没有更多了ヽ(≧Д≦)ノ") {
                    @Override
                    public void onNext(List<WXItemBean> wxItemBeen) {
                        mView.showMoreContent(wxItemBeen);
                    }
                })
        );
    }

    private void getSearchWechatData(String query) {
        currentPage = 1;
        addSubscribe(mDataManager.fetchWechatSearchListInfo(NUM_OF_PAGE,currentPage,query)
                .compose(RxUtil.<WXHttpResponse<List<WXItemBean>>>rxSchedulerHelper())
                .compose(RxUtil.<List<WXItemBean>>handleWXResult())
                .subscribeWith(new CommonSubscriber<List<WXItemBean>>(mView) {
                    @Override
                    public void onNext(List<WXItemBean> wxItemBeen) {
                        mView.showContent(wxItemBeen);
                    }
                })
        );
    }

}
