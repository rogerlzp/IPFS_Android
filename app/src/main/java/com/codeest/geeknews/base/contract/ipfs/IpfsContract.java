package com.codeest.geeknews.base.contract.ipfs;

import com.codeest.geeknews.base.BasePresenter;
import com.codeest.geeknews.base.BaseView;
import com.codeest.geeknews.model.bean.WXItemBean;

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
