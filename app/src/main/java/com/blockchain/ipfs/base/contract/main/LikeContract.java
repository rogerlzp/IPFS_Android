package com.blockchain.ipfs.base.contract.main;

import com.blockchain.ipfs.model.bean.RealmLikeBean;
import com.blockchain.ipfs.base.BasePresenter;
import com.blockchain.ipfs.base.BaseView;
import com.blockchain.ipfs.model.bean.RealmLikeBean;
import com.blockchain.ipfs.model.bean.RealmLikeBean;

import java.util.List;

/**
 * Created by codeest on 2016/8/23.
 */
public interface LikeContract {

    interface View extends BaseView {

        void showContent(List<RealmLikeBean> mList);
    }

    interface Presenter extends BasePresenter<View> {

        void getLikeData();

        void deleteLikeData(String id);

        void changeLikeTime(String id,long time,boolean isPlus);

        boolean getLikePoint();

        void setLikePoint(boolean b);
    }
}
