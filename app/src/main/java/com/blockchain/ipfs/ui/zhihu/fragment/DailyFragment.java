package com.blockchain.ipfs.ui.zhihu.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blockchain.ipfs.app.Constants;
import com.blockchain.ipfs.base.RootFragment;
import com.blockchain.ipfs.base.contract.zhihu.DailyContract;
import com.blockchain.ipfs.component.RxBus;
import com.blockchain.ipfs.model.bean.DailyBeforeListBean;
import com.blockchain.ipfs.model.bean.DailyListBean;
import com.blockchain.ipfs.util.CircularAnimUtil;
import com.blockchain.ipfs.util.DateUtil;
import com.blockchain.ipfs.R;
import com.blockchain.ipfs.app.Constants;
import com.blockchain.ipfs.base.RootFragment;
import com.blockchain.ipfs.base.contract.zhihu.DailyContract;
import com.blockchain.ipfs.component.RxBus;
import com.blockchain.ipfs.model.bean.DailyBeforeListBean;
import com.blockchain.ipfs.model.bean.DailyListBean;
import com.blockchain.ipfs.presenter.zhihu.DailyPresenter;
import com.blockchain.ipfs.ui.zhihu.activity.CalendarActivity;
import com.blockchain.ipfs.ui.zhihu.activity.ZhihuDetailActivity;
import com.blockchain.ipfs.ui.zhihu.adapter.DailyAdapter;
import com.blockchain.ipfs.util.CircularAnimUtil;
import com.blockchain.ipfs.util.DateUtil;
import com.blockchain.ipfs.app.Constants;
import com.blockchain.ipfs.base.RootFragment;
import com.blockchain.ipfs.base.contract.zhihu.DailyContract;
import com.blockchain.ipfs.component.RxBus;
import com.blockchain.ipfs.model.bean.DailyBeforeListBean;
import com.blockchain.ipfs.model.bean.DailyListBean;
import com.blockchain.ipfs.util.CircularAnimUtil;
import com.blockchain.ipfs.util.DateUtil;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by codeest on 2016/8/11.
 */
public class DailyFragment extends RootFragment<DailyPresenter> implements DailyContract.View {

    @BindView(R.id.fab_calender)
    FloatingActionButton fabCalender;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.view_main)
    RecyclerView rvDailyList;

    String currentDate;
    DailyAdapter mAdapter;
    List<DailyListBean.StoriesBean> mList = new ArrayList<>();

    boolean isDataReady = false;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_daily;
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        currentDate = DateUtil.getTomorrowDate();
        mAdapter = new DailyAdapter(mContext,mList);
        mAdapter.setOnItemClickListener(new DailyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position,View shareView) {
                mPresenter.insertReadToDB(mList.get(position).getId());
                mAdapter.setReadState(position,true);
                if(mAdapter.getIsBefore()) {
                    mAdapter.notifyItemChanged(position + 1);
                } else {
                    mAdapter.notifyItemChanged(position + 2);
                }
                Intent intent = new Intent();
                intent.setClass(mContext, ZhihuDetailActivity.class);
                intent.putExtra(Constants.IT_ZHIHU_DETAIL_ID, mList.get(position).getId());
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(mActivity, shareView, "shareView");
                mContext.startActivity(intent,options.toBundle());
            }
        });
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(currentDate.equals(DateUtil.getTomorrowDate())) {
                    mPresenter.getDailyData();
                } else {
                    int year = Integer.valueOf(currentDate.substring(0,4));
                    int month = Integer.valueOf(currentDate.substring(4,6));
                    int day = Integer.valueOf(currentDate.substring(6,8));
                    CalendarDay date = CalendarDay.from(year, month - 1, day);
                    RxBus.getDefault().post(date);
                }
            }
        });
        rvDailyList.setLayoutManager(new LinearLayoutManager(mContext));
        rvDailyList.setAdapter(mAdapter);
        stateLoading();
        mPresenter.getDailyData();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isDataReady) {
            mPresenter.startInterval();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.stopInterval();
    }

    /**
     * 当天数据
     * @param info
     */
    @Override
    public void showContent(DailyListBean info) {
        if(swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }
        stateMain();
        mList = info.getStories();
        currentDate = String.valueOf(Integer.valueOf(info.getDate()) + 1);
        mAdapter.addDailyDate(info);
        isDataReady = true;
        mPresenter.startInterval();
    }

    /**
     * 过往数据
     * @param date
     * @param info
     */
    @Override
    public void showMoreContent(String date,DailyBeforeListBean info) {
        if(swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }
        stateMain();
        isDataReady = false;
        mPresenter.stopInterval();
        mList = info.getStories();
        currentDate = String.valueOf(Integer.valueOf(info.getDate()));
        mAdapter.addDailyBeforeDate(info);
    }

    @Override
    public void doInterval(int currentCount) {
        mAdapter.changeTopPager(currentCount);
    }

    @OnClick(R.id.fab_calender)
    void startCalender() {
        Intent it = new Intent();
        it.setClass(mContext,CalendarActivity.class);
        CircularAnimUtil.startActivity(mActivity,it,fabCalender,R.color.fab_bg);
    }

    @Override
    public void stateError() {
        super.stateError();
        if(swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }
    }
}
