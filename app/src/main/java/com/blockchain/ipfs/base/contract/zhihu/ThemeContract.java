package com.blockchain.ipfs.base.contract.zhihu;

import com.blockchain.ipfs.model.bean.ThemeListBean;
import com.blockchain.ipfs.base.BasePresenter;
import com.blockchain.ipfs.base.BaseView;
import com.blockchain.ipfs.model.bean.ThemeListBean;
import com.blockchain.ipfs.model.bean.ThemeListBean;

/**
 * Created by codeest on 16/8/12.
 */

public interface ThemeContract {

    interface View extends BaseView {

        void showContent(ThemeListBean themeListBean);
    }

    interface Presenter extends BasePresenter<View> {

        void getThemeData();
    }
}
