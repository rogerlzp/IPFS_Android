package com.codeest.geeknews.ui.ipfs.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.codeest.geeknews.R;
import com.codeest.geeknews.app.Constants;
import com.codeest.geeknews.base.SimpleActivity;
import com.codeest.geeknews.model.bean.GoldManagerBean;
import com.codeest.geeknews.ui.gold.adapter.GoldManagerAdapter;
import com.codeest.geeknews.widget.DefaultItemTouchHelpCallback;

import java.util.Collections;

import butterknife.BindView;

/**
 * Created by zhengpingli on 2018/6/12.
 */

public class AddIPFSContentActivity extends SimpleActivity {

    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.rv_gold_manager_list)
    RecyclerView rvGoldManagerList;


    @Override
    protected int getLayout() {
        return R.layout.activity_add_ipfs_content;
    }

    @Override
    protected void initEventAndData() {
//        mList = ((GoldManagerBean) getIntent().getParcelableExtra(Constants.IT_GOLD_MANAGER)).getManagerList();
//        mAdapter = new GoldManagerAdapter(mContext, mList);
//        rvGoldManagerList.setLayoutManager(new LinearLayoutManager(mContext));
//        rvGoldManagerList.setAdapter(mAdapter);
//        mCallback = new DefaultItemTouchHelpCallback(new DefaultItemTouchHelpCallback.OnItemTouchCallbackListener() {
//            @Override
//            public void onSwiped(int adapterPosition) {
//            }
//
//            @Override
//            public boolean onMove(int srcPosition, int targetPosition) {
//                if (mList != null) {
//                    Collections.swap(mList, srcPosition, targetPosition);
//                    mAdapter.notifyItemMoved(srcPosition, targetPosition);
//                    return true;
//                }
//                return false;
//            }
//        });
//        mCallback.setDragEnable(true);
//        mCallback.setSwipeEnable(false);
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mCallback);
//        itemTouchHelper.attachToRecyclerView(rvGoldManagerList);
    }
}

