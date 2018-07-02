package com.blockchain.ipfs.ui.ipfs.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.blockchain.ipfs.R;
import com.blockchain.ipfs.app.App;
import com.blockchain.ipfs.app.Constants;
import com.blockchain.ipfs.model.bean.GoldManagerItemBean;
import com.blockchain.ipfs.model.bean.ImageBean;
import com.blockchain.ipfs.ui.gold.adapter.GoldManagerAdapter;
import com.blockchain.ipfs.util.ImageLoader;
import com.blockchain.ipfs.util.ImageLoaderProxy;
import com.blockchain.ipfs.util.ToastUtil;
import com.ipfs.api.IPFSAnroid;
import com.ipfs.api.entity.FileAdd;
import com.ipfs.api.entity.FileGet;
import com.ipfs.api.entity.NodeCat;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by zhengpingli on 2018/6/14.
 */

public class ImageIpfsAdapter  extends RecyclerView.Adapter<ImageIpfsAdapter.ViewHolder> {

    private List<ImageBean> imageBeanList;
    private List<NodeCat> nodeCatList;
    private LayoutInflater inflater;
    private Context mContext;

    public ImageIpfsAdapter(Context _mContext, List<ImageBean> mList) {
        mContext = _mContext;
        inflater = LayoutInflater.from(mContext);
        this.imageBeanList = mList;
    }

//    public ImageIpfsAdapter(Context _mContext, List<NodeCat> nodeCatList) {
//        mContext = _mContext;
//        inflater = LayoutInflater.from(mContext);
//        this.nodeCatList = nodeCatList;
//    }
    @Override
    public ImageIpfsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageIpfsAdapter.ViewHolder(inflater.inflate(R.layout.item_ipfs_image, parent, false));
    }

    @Override
    public void onBindViewHolder(final ImageIpfsAdapter.ViewHolder holder, int position) {
        holder.tv_desc.setText(imageBeanList.get(position).getDesc());
        //从状态中获取到Gateway或者是自己射在Gateway
        ImageLoaderProxy.getInstance().displayImage(mContext,"http://127.0.0.1:8080/ipfs/"+imageBeanList.get(position).getHash(),
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
        return imageBeanList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_image)
        ImageView iv_image;
        @BindView(R.id.tv_desc)
        TextView tv_desc;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
