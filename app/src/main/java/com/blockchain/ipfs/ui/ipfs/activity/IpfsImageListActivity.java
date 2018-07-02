package com.blockchain.ipfs.ui.ipfs.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.blockchain.ipfs.app.App;
import com.blockchain.ipfs.base.SimpleActivity;
import com.blockchain.ipfs.model.bean.ImageBean;
import com.blockchain.ipfs.ui.ipfs.adapter.ImageIpfsAdapter;
import com.blockchain.ipfs.widget.DefaultItemTouchHelpCallback;
import com.blockchain.ipfs.R;
import com.blockchain.ipfs.app.App;
import com.blockchain.ipfs.app.Constants;
import com.blockchain.ipfs.base.SimpleActivity;
import com.blockchain.ipfs.component.RxBus;
import com.blockchain.ipfs.model.bean.GoldManagerBean;
import com.blockchain.ipfs.model.bean.GoldManagerItemBean;
import com.blockchain.ipfs.model.bean.ImageBean;
import com.blockchain.ipfs.ui.gold.adapter.GoldManagerAdapter;
import com.blockchain.ipfs.ui.ipfs.adapter.ImageIpfsAdapter;
import com.blockchain.ipfs.widget.DefaultItemTouchHelpCallback;
import com.blockchain.ipfs.app.App;
import com.blockchain.ipfs.base.SimpleActivity;
import com.blockchain.ipfs.model.bean.ImageBean;
import com.blockchain.ipfs.ui.ipfs.adapter.ImageIpfsAdapter;
import com.blockchain.ipfs.widget.DefaultItemTouchHelpCallback;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import io.realm.RealmList;

/**
 * Created by zhengpingli on 2018/6/14.
 */

public class IpfsImageListActivity extends SimpleActivity {


    @BindView(R.id.rv_image_list)
    RecyclerView rv_image_list;


    List<ImageBean> mList;
    ImageIpfsAdapter mAdapter;
    DefaultItemTouchHelpCallback mCallback;

    @Override
    protected int getLayout() {
        return R.layout.activity_ipfs_image;
    }



    @Override
    protected void initEventAndData() {
//        setToolBar(toolBar, "首页特别展示");
        mList = App.getInstance().mDaoSession.getImageBeanDao().loadAll();

//        mList = ((GoldManagerBean) getIntent().getParcelableExtra(Constants.IT_GOLD_MANAGER)).getManagerList();
        mAdapter = new ImageIpfsAdapter(mContext, mList);
        rv_image_list.setLayoutManager(new LinearLayoutManager(mContext));
        rv_image_list.setAdapter(mAdapter);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        RxBus.getDefault().post(new GoldManagerBean(mList));
    }


}
