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
import com.blockchain.ipfs.model.bean.IpfsChannelNodeBean;
import com.blockchain.ipfs.ui.wallet.adapter.KeyStoreListAdapter;
import com.blockchain.ipfs.util.ImageLoaderProxy;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IpfsNodeAdapter extends RecyclerView.Adapter<IpfsNodeAdapter.ViewHolder> {

    private List<IpfsChannelNodeBean> channelNodeBeanList;
    private LayoutInflater inflater;
    private Context mContext;
    ChooseNodeListener chooseNodeListener;


    public IpfsNodeAdapter(Context _mContext, List<IpfsChannelNodeBean> mList) {
        mContext = _mContext;
        inflater = LayoutInflater.from(mContext);
        this.channelNodeBeanList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new IpfsNodeAdapter.ViewHolder(inflater.inflate(R.layout.item_ipfs_channel_node, parent, false));
    }

    @Override
    public void onBindViewHolder(final IpfsNodeAdapter.ViewHolder holder, int position) {
        holder.tv_node.setText(channelNodeBeanList.get(position).getName());
        holder.tv_topic.setText(channelNodeBeanList.get(position).getTopic());
        holder.tv_hash.setText(channelNodeBeanList.get(position).getHash());
        holder.itemView.setOnClickListener(new MyOnClickListener(position));

        //从状态中获取到Gateway或者是自己射在Gateway
//        ImageLoaderProxy.getInstance().displayImage(mContext, "http://127.0.0.1:8080/ipfs/" + imageBeanList.get(position).getHash(),
//                holder.iv_image, R.drawable.icon_placeholder);
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
        return channelNodeBeanList.size();
    }

    public ChooseNodeListener getChooseNodeListener() {
        return chooseNodeListener;
    }

    public void setChooseKeyStoreListener(ChooseNodeListener chooseNodeListener) {
        this.chooseNodeListener = chooseNodeListener;
    }



    private class MyOnClickListener implements View.OnClickListener {

        private int position;

        public MyOnClickListener(int position) {
            this.position = position;
            if (position < 0) {
                this.position = 0;
            }
        }

        @Override
        public void onClick(View view) {
            if (chooseNodeListener != null) {
                chooseNodeListener.onNodeChoosed(channelNodeBeanList.get(position));
            }
        }
    }

    public interface ChooseNodeListener {
        public void onNodeChoosed(IpfsChannelNodeBean ipfsChannelNodeBean);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_node)
        TextView tv_node;
        @BindView(R.id.tv_topic)
        TextView tv_topic;
        @BindView(R.id.tv_hash)
        TextView tv_hash;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
