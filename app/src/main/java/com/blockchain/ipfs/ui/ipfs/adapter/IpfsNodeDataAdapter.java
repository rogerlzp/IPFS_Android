package com.blockchain.ipfs.ui.ipfs.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blockchain.ipfs.R;
import com.blockchain.ipfs.model.bean.ImageBean;
import com.blockchain.ipfs.util.ImageLoaderProxy;
import com.ipfs.api.entity.NodeCat;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IpfsNodeDataAdapter extends RecyclerView.Adapter<IpfsNodeDataAdapter.ViewHolder> {

    private List<NodeCat> nodeCatList = new ArrayList<>();
    private LayoutInflater inflater;
    private Context mContext;

    public IpfsNodeDataAdapter(Context _mContext, List<NodeCat> mList) {
        mContext = _mContext;
        inflater = LayoutInflater.from(mContext);
        this.nodeCatList = mList;
    }

    //    public ImageIpfsAdapter(Context _mContext, List<NodeCat> nodeCatList) {
//        mContext = _mContext;
//        inflater = LayoutInflater.from(mContext);
//        this.nodeCatList = nodeCatList;
//    }
    @Override
    public IpfsNodeDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new IpfsNodeDataAdapter.ViewHolder(inflater.inflate(R.layout.item_ipfs_image, parent, false));
    }

    @Override
    public void onBindViewHolder(final IpfsNodeDataAdapter.ViewHolder holder, int position) {
        holder.tv_desc.setText(nodeCatList.get(position).getDesc());
        //从状态中获取到Gateway或者是自己射在Gateway
        ImageLoaderProxy.getInstance().displayImage(mContext, "http://127.0.0.1:8080/ipfs/" + nodeCatList.get(position).getHash(),
                holder.iv_image, R.drawable.icon_placeholder);
//        IPFSAnroid ipfsAnroid = new IPFSAnroid();
//        ipfsAnroid.get(new Callback<FileGet>() {
//            @Override
//            public void onResponse(Call<FileGet> call, Response<FileGet> response) {
//                ToastUtil.shortShow(mContext.getFilesDir() + "/.ipfs_repo/" + response.body().getHash());
//                ImageLoaderProxy.getInstance().displayImage(mContext,mContext.getFilesDir() + "/.ipfs_repo/" + response.body().getHash(),
//                        holder.iv_image, R.drawable.icon_placeholder);
//            }
//
//            @Override
//            public void onFailure(Call<FileGet> call, Throwable t) {
////                tv_ipfs_status.setText(t.getMessage());
//            }
//        }, imageBeanList.get(position).getHash());
    }

    @Override
    public int getItemCount() {
        return nodeCatList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_image)
        ImageView iv_image;
        @BindView(R.id.tv_desc)
        TextView tv_desc;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
