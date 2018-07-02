package com.blockchain.ipfs.base.contract.zhihu;

import com.blockchain.ipfs.model.bean.SectionListBean;
import com.blockchain.ipfs.base.BasePresenter;
import com.blockchain.ipfs.base.BaseView;
import com.blockchain.ipfs.model.bean.SectionListBean;
import com.blockchain.ipfs.model.bean.SectionListBean;

/**
 * Created by codeest on 16/8/12.
 */

public interface SectionContract {

    interface View extends BaseView {

        void showContent(SectionListBean sectionListBean);

    }

    interface Presenter extends BasePresenter<View> {

        void getSectionData();
    }
}
