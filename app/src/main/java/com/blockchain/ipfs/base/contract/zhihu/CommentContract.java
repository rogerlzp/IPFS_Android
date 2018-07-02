package com.blockchain.ipfs.base.contract.zhihu;

import com.blockchain.ipfs.model.bean.CommentBean;
import com.blockchain.ipfs.base.BasePresenter;
import com.blockchain.ipfs.base.BaseView;
import com.blockchain.ipfs.model.bean.CommentBean;
import com.blockchain.ipfs.model.bean.CommentBean;

/**
 * Created by codeest on 16/8/18.
 */

public interface CommentContract {

    interface View extends BaseView {

        void showContent(CommentBean commentBean);

    }

    interface Presenter extends BasePresenter<View> {

        void getCommentData(int id,int commentKind);

    }
}
