package com.blockchain.ipfs.ui.vtex.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.blockchain.ipfs.app.Constants;
import com.blockchain.ipfs.base.RootFragment;
import com.blockchain.ipfs.base.contract.vtex.VtexContract;
import com.blockchain.ipfs.model.bean.TopicListBean;
import com.blockchain.ipfs.widget.CommonItemDecoration;
import com.blockchain.ipfs.R;
import com.blockchain.ipfs.app.Constants;
import com.blockchain.ipfs.base.RootFragment;
import com.blockchain.ipfs.model.bean.TopicListBean;
import com.blockchain.ipfs.presenter.vtex.VtexPresenter;
import com.blockchain.ipfs.base.contract.vtex.VtexContract;
import com.blockchain.ipfs.ui.vtex.adapter.TopicAdapter;
import com.blockchain.ipfs.widget.CommonItemDecoration;
import com.blockchain.ipfs.app.Constants;
import com.blockchain.ipfs.base.RootFragment;
import com.blockchain.ipfs.base.contract.vtex.VtexContract;
import com.blockchain.ipfs.model.bean.TopicListBean;
import com.blockchain.ipfs.widget.CommonItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by codeest on 6/12/19.
 */

public class VtexPagerFragment extends RootFragment<VtexPresenter> implements VtexContract.View {

    @BindView(R.id.view_main)
    RecyclerView rvContent;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    private TopicAdapter mAdapter;

    private String mType;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_common_list;
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        mType = getArguments().getString(Constants.IT_VTEX_TYPE);
        mAdapter = new TopicAdapter(mContext, new ArrayList<TopicListBean>());
        CommonItemDecoration mDecoration = new CommonItemDecoration(1, CommonItemDecoration.UNIT_DP);
        rvContent.setLayoutManager(new LinearLayoutManager(mContext));
        rvContent.setAdapter(mAdapter);
        rvContent.addItemDecoration(mDecoration);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getContent(mType);
            }
        });
        stateLoading();
        mPresenter.getContent(mType);
    }

    @Override
    public void stateError() {
        super.stateError();
        if(swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }
    }

    @Override
    public void showContent(List<TopicListBean> mList) {
        if(swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }
        stateMain();
        mAdapter.updateData(mList);
    }
}
