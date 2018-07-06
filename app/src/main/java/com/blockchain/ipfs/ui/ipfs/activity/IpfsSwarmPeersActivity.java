package com.blockchain.ipfs.ui.ipfs.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.blockchain.ipfs.R;
import com.blockchain.ipfs.app.App;
import com.blockchain.ipfs.base.SimpleActivity;
import com.blockchain.ipfs.model.bean.ImageBean;
import com.blockchain.ipfs.ui.ipfs.adapter.ImageIpfsAdapter;
import com.blockchain.ipfs.ui.ipfs.adapter.IpfsPeerAdapter;
import com.blockchain.ipfs.util.ToastUtil;
import com.ipfs.api.IPFSAnroid;
import com.ipfs.api.entity.PeerEntity;
import com.ipfs.api.entity.PeersEntity;
import com.socks.library.KLog;

import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IpfsSwarmPeersActivity extends SimpleActivity {

//
//    @BindView(R.id.et_address)
//    EditText et_address;


    @BindView(R.id.rv_peer_list)
    RecyclerView rv_peer_list;


    List<PeerEntity> mList;
    IpfsPeerAdapter mAdapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_ipfs_swarm_peers;
    }


    @Override
    protected void initEventAndData() {
        IPFSAnroid ipfsAnroid = new IPFSAnroid();
        ipfsAnroid.swarm().peers(new Callback<PeersEntity>() {
            @Override
            public void onResponse(Call<PeersEntity> call, Response<PeersEntity> response) {
                ToastUtil.show("PeersEntity succeed");
                mList = response.body().getPeers();


//        mList = ((GoldManagerBean) getIntent().getParcelableExtra(Constants.IT_GOLD_MANAGER)).getManagerList();
                mAdapter = new IpfsPeerAdapter(mContext, mList);
                rv_peer_list.setLayoutManager(new LinearLayoutManager(mContext));
                rv_peer_list.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<PeersEntity> call, Throwable t) {
                ToastUtil.show("pub failed");
                KLog.e(t.getMessage());
            }
        });

    }

}
