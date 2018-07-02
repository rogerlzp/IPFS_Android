package com.blockchain.ipfs.base.contract.zhihu;

import com.blockchain.ipfs.model.bean.SectionChildListBean;
import com.blockchain.ipfs.base.BasePresenter;
import com.blockchain.ipfs.base.BaseView;
import com.blockchain.ipfs.model.bean.SectionChildListBean;
import com.blockchain.ipfs.model.bean.SectionChildListBean;

/**
 * Created by codeest on 16/8/28.
 */

public interface SectionChildContract {

    interface View extends BaseView {

        void showContent(SectionChildListBean sectionChildListBean);
    }

    interface Presenter extends BasePresenter<View> {

        void getThemeChildData(int id);

        void insertReadToDB(int id);
    }
}
