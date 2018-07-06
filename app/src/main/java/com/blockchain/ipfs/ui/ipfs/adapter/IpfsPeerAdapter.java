package com.blockchain.ipfs.ui.ipfs.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blockchain.ipfs.R;
import com.blockchain.ipfs.model.bean.ImageBean;
import com.blockchain.ipfs.util.ImageLoaderProxy;
import com.ipfs.api.entity.NodeCat;
import com.ipfs.api.entity.PeerEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IpfsPeerAdapter extends RecyclerView.Adapter<IpfsPeerAdapter.ViewHolder> {

    private List<PeerEntity> peerEntityList;

    private LayoutInflater inflater;
    private Context mContext;

    public IpfsPeerAdapter(Context _mContext, List<PeerEntity> mList) {
        mContext = _mContext;
        inflater = LayoutInflater.from(mContext);
        this.peerEntityList = mList;
    }


    @Override
    public IpfsPeerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new IpfsPeerAdapter.ViewHolder(inflater.inflate(R.layout.item_ipfs_peer, parent, false));
    }

    @Override
    public void onBindViewHolder(final IpfsPeerAdapter.ViewHolder holder, int position) {
        holder.tv_addr.setText(peerEntityList.get(position).getAddr());

        holder.tv_peer.setText(peerEntityList.get(position).getPeer());
        holder.tv_latency.setText(peerEntityList.get(position).getLatency());
        holder.tv_muxer.setText(peerEntityList.get(position).getMuxer());
        holder.tv_streams.setText(TextUtils.isEmpty(peerEntityList.get(position).getStreams()) ? "null" : peerEntityList.get(position).getStreams());

    }

    @Override
    public int getItemCount() {
        return peerEntityList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_addr)
        TextView tv_addr;
        @BindView(R.id.tv_peer)
        TextView tv_peer;
        @BindView(R.id.tv_latency)
        TextView tv_latency;
        @BindView(R.id.tv_muxer)
        TextView tv_muxer;
        @BindView(R.id.tv_streams)
        TextView tv_streams;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
