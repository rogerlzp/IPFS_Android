package com.blockchain.ipfs.presenter.zhihu;

import com.blockchain.ipfs.base.RxPresenter;
import com.blockchain.ipfs.model.DataManager;
import com.blockchain.ipfs.model.bean.CommentBean;
import com.blockchain.ipfs.base.contract.zhihu.CommentContract;
import com.blockchain.ipfs.util.RxUtil;
import com.blockchain.ipfs.widget.CommonSubscriber;

import javax.inject.Inject;

/**
 * Created by codeest on 16/8/18.
 */

public class CommentPresenter extends RxPresenter<CommentContract.View> implements CommentContract.Presenter{

    public static final int SHORT_COMMENT = 0;

    public static final int LONG_COMMENT = 1;

    private DataManager mDataManager;

    @Inject
    public CommentPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void getCommentData(int id, int commentKind) {
        if(commentKind == SHORT_COMMENT) {
            addSubscribe(mDataManager.fetchShortCommentInfo(id)
                .compose(RxUtil.<CommentBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<CommentBean>(mView) {
                    @Override
                    public void onNext(CommentBean commentBean) {
                        mView.stateMain();
                        mView.showContent(commentBean);
                    }
                })
            );
        } else {
            addSubscribe(mDataManager.fetchLongCommentInfo(id)
                .compose(RxUtil.<CommentBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<CommentBean>(mView) {
                    @Override
                    public void onNext(CommentBean commentBean) {
                        mView.stateMain();
                        mView.showContent(commentBean);
                    }
                })
            );
        }
    }
}
