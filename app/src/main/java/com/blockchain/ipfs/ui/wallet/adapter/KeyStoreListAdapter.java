package com.blockchain.ipfs.ui.wallet.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blockchain.ipfs.model.bean.KeyStoreBean;
import com.blockchain.ipfs.R;
import com.blockchain.ipfs.app.Constants;
import com.blockchain.ipfs.component.ImageLoader;
import com.blockchain.ipfs.model.bean.GoldListBean;
import com.blockchain.ipfs.model.bean.KeyStoreBean;
import com.blockchain.ipfs.ui.gank.activity.TechDetailActivity;
import com.blockchain.ipfs.ui.gold.adapter.GoldListAdapter;
import com.blockchain.ipfs.util.DateUtil;
import com.blockchain.ipfs.widget.SquareImageView;
import com.blockchain.ipfs.model.bean.KeyStoreBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KeyStoreListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<KeyStoreBean> mList;
    private Context mContext;
    private LayoutInflater inflater;

    public ChooseKeyStoreListener getChooseKeyStoreListener() {
        return chooseKeyStoreListener;
    }

    public void setChooseKeyStoreListener(ChooseKeyStoreListener chooseKeyStoreListener) {
        this.chooseKeyStoreListener = chooseKeyStoreListener;
    }

    ChooseKeyStoreListener chooseKeyStoreListener;

    public KeyStoreListAdapter(Context mContext, List<KeyStoreBean> mList) {
        this.mList = mList;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new KeyStoreListAdapter.ContentViewHolder(inflater.inflate(R.layout.item_keystore, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        KeyStoreBean bean = mList.get(0);
        if (position > 0) {
            bean = mList.get(position - 1);
        }
        if (holder instanceof KeyStoreListAdapter.ContentViewHolder) {

            ((ContentViewHolder) holder).tv_address.setText(bean.getAddress());
            ((ContentViewHolder) holder).tv_filename.setText(bean.getPath());

            holder.itemView.setOnClickListener(new KeyStoreListAdapter.MyOnClickListener(--position));
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ContentViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_address)
        TextView tv_address;

        @BindView(R.id.tv_filename)
        TextView tv_filename;

        public ContentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
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
            if (chooseKeyStoreListener != null) {
                chooseKeyStoreListener.onKeyStoreChoosed(mList.get(position));
            }
        }
    }


    public void updateData(List<KeyStoreBean> list) {
        mList = list;
    }

    public interface ChooseKeyStoreListener {
        public void onKeyStoreChoosed(KeyStoreBean keyStoreBean);
    }

}
