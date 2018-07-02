package com.blockchain.ipfs.base.contract.vtex;

import com.blockchain.ipfs.model.bean.TopicListBean;
import com.blockchain.ipfs.base.BasePresenter;
import com.blockchain.ipfs.base.BaseView;
import com.blockchain.ipfs.model.bean.TopicListBean;
import com.blockchain.ipfs.model.bean.TopicListBean;

import java.util.List;

/**
 * Created by codeest on 16/12/23.
 */

public interface VtexContract {

    interface View extends BaseView {

        void showContent(List<TopicListBean> mList);

    }

    interface Presenter extends BasePresenter<View> {

        void getContent(String type);

    }
}
