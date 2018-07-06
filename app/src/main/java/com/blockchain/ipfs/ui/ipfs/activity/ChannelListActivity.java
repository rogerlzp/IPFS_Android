package com.blockchain.ipfs.ui.ipfs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.blockchain.ipfs.ui.ipfs.adapter.IpfsNodeAdapter;
import com.blockchain.ipfs.R;
import com.blockchain.ipfs.app.App;
import com.blockchain.ipfs.app.Constants;
import com.blockchain.ipfs.base.SimpleActivity;
import com.blockchain.ipfs.model.bean.ImageBean;
import com.blockchain.ipfs.model.bean.IpfsChannelNodeBean;
import com.blockchain.ipfs.ui.ipfs.adapter.ImageIpfsAdapter;
import com.blockchain.ipfs.ui.ipfs.adapter.IpfsNodeAdapter;
import com.blockchain.ipfs.ui.wallet.activity.SampleContractActivity;
import com.blockchain.ipfs.ui.wallet.contract.SampleContract;
import com.blockchain.ipfs.ui.ipfs.adapter.IpfsNodeAdapter;

import java.util.List;

import butterknife.BindView;

public class ChannelListActivity extends SimpleActivity implements IpfsNodeAdapter.ChooseNodeListener {

    @BindView(R.id.rv_channel_node_list)
    RecyclerView rv_channel_node_list;


    List<IpfsChannelNodeBean> mList;
    IpfsNodeAdapter mAdapter;

    IpfsChannelNodeBean selectedChannelNodeBean;

    @Override
    protected int getLayout() {
        return R.layout.activity_ipfs_channel_list;
    }

    @Override
    protected void initEventAndData() {

        mList = App.getInstance().mDaoSession.getIpfsChannelNodeBeanDao().loadAll();
        mAdapter = new IpfsNodeAdapter(mContext, mList);
        mAdapter.setChooseKeyStoreListener(this);
        rv_channel_node_list.setLayoutManager(new LinearLayoutManager(mContext));
        rv_channel_node_list.setAdapter(mAdapter);

    }


    @Override
    public void onNodeChoosed(IpfsChannelNodeBean ipfsChannelNodeBean) {
        //跳转到Node页面
        // 首先跳转到支付界面，等Transfer TT TOKEN 以后，再往下走
        // 如果不走SampleContract, 则取消，不去显示
        selectedChannelNodeBean = ipfsChannelNodeBean;
        Intent contractIntent = new Intent(this, SampleContractActivity.class);
        Bundle b = new Bundle();
        b.putDouble(Constants.NODE_PRICE, selectedChannelNodeBean.getTotalPrice());
        b.putString(Constants.RECEIVER_ADDRESS, selectedChannelNodeBean.getReceiverAddress());
        b.putString(Constants.ROUTE_FROM, Constants.FROM_CHANANEL_LIST);
        contractIntent.putExtras(b);
        startActivityForResult(contractIntent, Constants.REQUEST_PAY4READ);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {
            case Constants.RESULT_PAYED:
                Intent intent = new Intent(this, ChannelNodeActivity.class);
                Bundle b = new Bundle();
                b.putString(Constants.IPFS_NODE_HASH, selectedChannelNodeBean.getHash());
                intent.putExtras(b);
                startActivity(intent);
                break;

        }
    }

}

