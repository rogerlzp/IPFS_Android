package com.blockchain.ipfs.base.contract.zhihu;

import com.blockchain.ipfs.model.bean.ThemeChildListBean;
import com.blockchain.ipfs.base.BasePresenter;
import com.blockchain.ipfs.base.BaseView;
import com.blockchain.ipfs.model.bean.ThemeChildListBean;
import com.blockchain.ipfs.model.bean.ThemeChildListBean;

/**
 * Created by codeest on 16/8/24.
 */

public interface ThemeChildContract {

    interface View extends BaseView {

        void showContent(ThemeChildListBean themeChildListBean);

    }

    interface Presenter extends BasePresenter<View> {

        void getThemeChildData(int id);

        void insertReadToDB(int id);
    }
}
