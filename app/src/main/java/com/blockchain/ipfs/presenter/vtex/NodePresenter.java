package com.blockchain.ipfs.presenter.vtex;

import com.blockchain.ipfs.base.RxPresenter;
import com.blockchain.ipfs.model.DataManager;
import com.blockchain.ipfs.model.bean.NodeBean;
import com.blockchain.ipfs.model.bean.NodeListBean;
import com.blockchain.ipfs.base.contract.vtex.NodeContract;
import com.blockchain.ipfs.util.RxUtil;
import com.blockchain.ipfs.widget.CommonSubscriber;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by codeest on 16/12/23.
 */

public class NodePresenter extends RxPresenter<NodeContract.View> implements NodeContract.Presenter{

    private DataManager mDataManager;

    @Inject
    public NodePresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void getContent(String node_name) {
        addSubscribe(mDataManager.fetchTopicList(node_name)
                .compose(RxUtil.<List<NodeListBean>>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<List<NodeListBean>>(mView) {
                    @Override
                    public void onNext(List<NodeListBean> nodeListBeen) {
                        mView.showContent(nodeListBeen);
                    }
                })
        );
    }

    @Override
    public void getTopInfo(String node_name) {
        addSubscribe(mDataManager.fetchNodeInfo(node_name)
                .compose(RxUtil.<NodeBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<NodeBean>(mView, false) {
                    @Override
                    public void onNext(NodeBean nodeBean) {
                        mView.showTopInfo(nodeBean);
                    }
                })
        );
    }
}
