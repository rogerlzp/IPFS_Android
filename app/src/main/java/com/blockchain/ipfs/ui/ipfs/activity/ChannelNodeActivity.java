package com.blockchain.ipfs.ui.ipfs.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.blockchain.ipfs.ui.ipfs.adapter.IpfsNodeDataAdapter;
import com.blockchain.ipfs.R;
import com.blockchain.ipfs.app.App;
import com.blockchain.ipfs.app.Constants;
import com.blockchain.ipfs.base.SimpleActivity;
import com.blockchain.ipfs.model.bean.ImageBean;
import com.blockchain.ipfs.ui.ipfs.adapter.ImageIpfsAdapter;
import com.blockchain.ipfs.ui.ipfs.adapter.IpfsNodeDataAdapter;
import com.blockchain.ipfs.util.ToastUtil;
import com.blockchain.ipfs.ui.ipfs.adapter.IpfsNodeDataAdapter;
import com.ipfs.api.IPFSAnroid;
import com.ipfs.api.entity.FileGet;
import com.ipfs.api.entity.NodeCat;

import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChannelNodeActivity extends SimpleActivity {


    @BindView(R.id.rv_image_list)
    RecyclerView rv_image_list;


    List<NodeCat> mList;
    IpfsNodeDataAdapter mAdapter;
    String nodeHash = "";


    @Override
    protected int getLayout() {
        return R.layout.activity_ipfs_channel_node;
    }

    @Override
    protected void initEventAndData() {
        // 解析JSON文件，


        nodeHash = getIntent().getExtras().getString(Constants.IPFS_NODE_HASH);
        IPFSAnroid ipfsAnroid = new IPFSAnroid();
        ipfsAnroid.catNode(new Callback<List<NodeCat>>() {

            @Override
            public void onResponse(Call<List<NodeCat>> call, Response<List<NodeCat>> response) {
                if (mList != null) {
                    mList.clear();
                }
                mList = response.body();
                mAdapter = new IpfsNodeDataAdapter(mContext, mList);
                rv_image_list.setLayoutManager(new LinearLayoutManager(mContext));
                rv_image_list.setAdapter(mAdapter);
//                for (NodeCat nodeCat : response.body()) {
//                    ToastUtil.show("HASH:" + nodeCat.getHash()
//                            + "\nname:" + nodeCat.getName()
//                            + "\nstatus:" + nodeCat.getStatus()
//                            + "\ndesc" + nodeCat.getDesc()
//                    );
//                }
            }

            @Override
            public void onFailure(Call<List<NodeCat>> call, Throwable t) {
                ToastUtil.show("failed with :" + t.getMessage());
            }


        }, "/ipns/" + nodeHash);
//        mList = App.getInstance().mDaoSession.getImageBeanDao().loadAll();

//        mAdapter = new ImageIpfsAdapter(mContext, mList);
    }

}
