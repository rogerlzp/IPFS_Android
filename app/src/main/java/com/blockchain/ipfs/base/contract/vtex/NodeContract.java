package com.blockchain.ipfs.base.contract.vtex;

import com.blockchain.ipfs.model.bean.NodeBean;
import com.blockchain.ipfs.model.bean.NodeListBean;
import com.blockchain.ipfs.base.BasePresenter;
import com.blockchain.ipfs.base.BaseView;
import com.blockchain.ipfs.model.bean.NodeBean;
import com.blockchain.ipfs.model.bean.NodeListBean;
import com.blockchain.ipfs.model.bean.NodeBean;
import com.blockchain.ipfs.model.bean.NodeListBean;

import java.util.List;

/**
 * Created by codeest on 16/12/23.
 */

public interface NodeContract {

    interface View extends BaseView {

        void showContent(List<NodeListBean> mList);

        void showTopInfo(NodeBean mTopInfo);
    }

    interface Presenter extends BasePresenter<View> {

        void getContent(String node_name);

        void getTopInfo(String node_name);
    }
}
