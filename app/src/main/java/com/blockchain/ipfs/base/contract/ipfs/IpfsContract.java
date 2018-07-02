package com.blockchain.ipfs.base.contract.ipfs;

import com.blockchain.ipfs.model.bean.WXItemBean;
import com.blockchain.ipfs.base.BasePresenter;
import com.blockchain.ipfs.base.BaseView;
import com.blockchain.ipfs.model.bean.WXItemBean;
import com.blockchain.ipfs.model.bean.WXItemBean;

import java.util.List;

/**
 * Created by zhengpingli on 2018/6/12.
 */


public interface IpfsContract {

    interface View extends BaseView {

        void showContent(List<WXItemBean> mList);

        void showMoreContent(List<WXItemBean> mList);
    }

    interface Presenter extends BasePresenter<View> {
        void getWechatData();

        void getMoreWechatData();
    }
}
