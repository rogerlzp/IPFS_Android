package com.blockchain.ipfs.base.contract.wallet;

import com.blockchain.ipfs.base.BasePresenter;
import com.blockchain.ipfs.base.BaseView;
import com.blockchain.ipfs.model.bean.WXItemBean;

import java.util.List;


public interface WalletContract {

    interface View extends BaseView {

        void showContent(List<WXItemBean> mList);

        void showMoreContent(List<WXItemBean> mList);
    }

    interface Presenter extends BasePresenter<View> {
        void getWechatData();

        void getMoreWechatData();
    }
}