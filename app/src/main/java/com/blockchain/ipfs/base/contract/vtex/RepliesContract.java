package com.blockchain.ipfs.base.contract.vtex;

import com.blockchain.ipfs.model.bean.NodeListBean;
import com.blockchain.ipfs.model.bean.RealmLikeBean;
import com.blockchain.ipfs.model.bean.RepliesListBean;
import com.blockchain.ipfs.base.BasePresenter;
import com.blockchain.ipfs.base.BaseView;
import com.blockchain.ipfs.model.bean.NodeListBean;
import com.blockchain.ipfs.model.bean.RealmLikeBean;
import com.blockchain.ipfs.model.bean.RepliesListBean;
import com.blockchain.ipfs.model.bean.NodeListBean;
import com.blockchain.ipfs.model.bean.RealmLikeBean;
import com.blockchain.ipfs.model.bean.RepliesListBean;

import java.util.List;

/**
 * Created by codeest on 16/12/23.
 */

public interface RepliesContract {

    interface View extends BaseView {

        void showContent(List<RepliesListBean> mList);

        void showTopInfo(NodeListBean mTopInfo);
    }

    interface Presenter extends BasePresenter<View> {

        void getContent(String topic_id);

        void getTopInfo(String topic_id);

        void insert(RealmLikeBean bean);

        void delete(String id);

        boolean query(String id);
    }
}
