package com.blockchain.ipfs.base.contract.gank;

import com.blockchain.ipfs.model.bean.GankItemBean;
import com.blockchain.ipfs.base.BasePresenter;
import com.blockchain.ipfs.base.BaseView;
import com.blockchain.ipfs.model.bean.GankItemBean;
import com.blockchain.ipfs.model.bean.GankItemBean;

import java.util.List;

/**
 * Created by codeest on 16/8/19.
 */

public interface GirlContract {

    interface View extends BaseView {

        void showContent(List<GankItemBean> list);

        void showMoreContent(List<GankItemBean> list);
    }

    interface Presenter extends BasePresenter<View> {

        void getGirlData();

        void getMoreGirlData();

    }
}
