package com.blockchain.ipfs.presenter.gold;

import com.blockchain.ipfs.base.RxPresenter;
import com.blockchain.ipfs.base.contract.gold.GoldMainContract;
import com.blockchain.ipfs.component.RxBus;
import com.blockchain.ipfs.model.DataManager;
import com.blockchain.ipfs.model.bean.GoldManagerBean;
import com.blockchain.ipfs.model.bean.GoldManagerItemBean;
import com.blockchain.ipfs.util.RxUtil;
import com.blockchain.ipfs.widget.CommonSubscriber;

import javax.inject.Inject;

//import io.realm.RealmList;

/**
 * Created by codeest on 16/11/28.
 */

public class GoldMainPresenter extends RxPresenter<GoldMainContract.View> implements GoldMainContract.Presenter{

    private DataManager mDataManager;
//    private RealmList<GoldManagerItemBean> mList;

    @Inject
    public GoldMainPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void attachView(GoldMainContract.View view) {
        super.attachView(view);
        registerEvent();
    }

    private void registerEvent() {
        addSubscribe(RxBus.getDefault().toFlowable(GoldManagerBean.class)
                .compose(RxUtil.<GoldManagerBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<GoldManagerBean>(mView, "设置失败", false) {
                    @Override
                    public void onNext(GoldManagerBean goldManagerBean) {
                        mDataManager.updateGoldManagerList(goldManagerBean);
//                        mView.updateTab(goldManagerBean.getManagerList());
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
    public void initManagerList() {
        if (mDataManager.getManagerPoint()) {
            //第一次使用，生成默认ManagerList
            initList();
//            mDataManager.updateGoldManagerList(new GoldManagerBean(mList));
//            mView.updateTab(mList);
        } else {
            if (mDataManager.getGoldManagerList() == null) {
                initList();
//                mDataManager.updateGoldManagerList(new GoldManagerBean(mList));
            } else {
//                mList = mDataManager.getGoldManagerList().getManagerList();
            }
//            mView.updateTab(mList);
        }
    }

    @Override
    public void setManagerList() {
        mView.jumpToManager(mDataManager.getGoldManagerList());
    }

    private void initList() {
//        mList = new RealmList<>();
//        mList.add(new GoldManagerItemBean(0, true));
//        mList.add(new GoldManagerItemBean(1, true));
//        mList.add(new GoldManagerItemBean(2, true));
//        mList.add(new GoldManagerItemBean(3, true));
//        mList.add(new GoldManagerItemBean(4, false));
//        mList.add(new GoldManagerItemBean(5, false));
//        mList.add(new GoldManagerItemBean(6, false));
//        mList.add(new GoldManagerItemBean(7, false));
    }
}
