package com.blockchain.ipfs.base.contract.gold;

import com.blockchain.ipfs.model.bean.GoldListBean;
import com.blockchain.ipfs.base.BasePresenter;
import com.blockchain.ipfs.base.BaseView;
import com.blockchain.ipfs.model.bean.GoldListBean;
import com.blockchain.ipfs.model.bean.GoldListBean;

import java.util.List;

/**
 * Created by codeest on 16/11/27.
 */

public interface GoldContract {

    interface View extends BaseView {

        void showContent(List<GoldListBean> goldListBean);

        void showMoreContent(List<GoldListBean> goldMoreListBean, int start, int end);
    }

    interface Presenter extends BasePresenter<View> {

        void getGoldData(String type);

        void getMoreGoldData();
    }
}
