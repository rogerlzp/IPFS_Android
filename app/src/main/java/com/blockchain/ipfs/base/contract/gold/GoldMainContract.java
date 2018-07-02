package com.blockchain.ipfs.base.contract.gold;

import com.blockchain.ipfs.model.bean.GoldManagerBean;
import com.blockchain.ipfs.model.bean.GoldManagerItemBean;
import com.blockchain.ipfs.base.BasePresenter;
import com.blockchain.ipfs.base.BaseView;
import com.blockchain.ipfs.model.bean.GoldManagerBean;
import com.blockchain.ipfs.model.bean.GoldManagerItemBean;
import com.blockchain.ipfs.model.bean.GoldManagerBean;
import com.blockchain.ipfs.model.bean.GoldManagerItemBean;

import java.util.List;

/**
 * Created by codeest on 16/11/28.
 */

public interface GoldMainContract {

    interface View extends BaseView {

        void updateTab(List<GoldManagerItemBean> mList);

        void jumpToManager(GoldManagerBean mBean);
    }

    interface Presenter extends BasePresenter<View> {

        void initManagerList();

        void setManagerList();
    }
}
