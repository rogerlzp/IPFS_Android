package com.blockchain.ipfs.ui.ipfs.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blockchain.ipfs.app.App;
import com.blockchain.ipfs.app.Constants;
import com.blockchain.ipfs.base.SimpleActivity;
import com.blockchain.ipfs.model.bean.IpfsNodeBean;
import com.blockchain.ipfs.util.ToastUtil;
import com.blockchain.ipfs.R;
import com.blockchain.ipfs.app.App;
import com.blockchain.ipfs.app.Constants;
import com.blockchain.ipfs.base.SimpleActivity;
import com.blockchain.ipfs.model.bean.ImageBean;
import com.blockchain.ipfs.model.bean.IpfsNodeBean;
import com.blockchain.ipfs.util.ToastUtil;
import com.blockchain.ipfs.app.App;
import com.blockchain.ipfs.app.Constants;
import com.blockchain.ipfs.base.SimpleActivity;
import com.blockchain.ipfs.model.bean.IpfsNodeBean;
import com.blockchain.ipfs.util.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ipfs.api.IPFSAnroid;
import com.ipfs.api.entity.FileAdd;
import com.ipfs.api.entity.NameEntity;
import com.socks.library.KLog;

import org.json.JSONArray;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 发布个人 Node 节点
 * 1. 发布照片集的 json格式文件
 * 2. 将该Json文件 name 到自己的节点上
 * 3. 将自己的节点发送出去，以NodeEvent 的Topic 发出去
 * <p>
 * 对应的有订阅NodeEvent,
 */

public class PubNodeActivity extends SimpleActivity {


    @BindView(R.id.btn_pub_data)
    Button btn_pub_data;


    @BindView(R.id.btn_pub_node)
    Button btn_pub_node;

    @BindView(R.id.btn_pub_name)
    Button btn_pub_name;


    @BindView(R.id.pb_update)
    ProgressBar pb_update;


    private String mDataFilename;
    private String mNodeFilename;
    private String mNodeHash = ""; //代表节点的数据

    private String mNodeDataHash;


    @Override
    protected int getLayout() {
        return R.layout.activity_ipfs_pub_node;

    }

    @Override
    protected void initEventAndData() {

        mDataFilename = ".data";
        mNodeFilename = this.getRepoPath() + "/.node";
    }

    private File getRepoPath() {
        return new File(mContext.getFilesDir() + "/.ipfs_repo");
    }


    /**
     * 读取数据库文件，生成临时的文件
     * 将该文件对应的Hash Name到节点上
     */
    @OnClick(R.id.btn_pub_data)
    public void pubData() {

//        JsonArray jsonArray = new JsonParser().parse(new Gson().toJson(App.getInstance().mDaoSession.getImageBeanDao().loadAll())).getAsJsonArray();
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.add("imageBeanList", jsonArray);
//        jsonObject.addProperty("totalPrice", String.valueOf(totalPrice));

        String dataJson = new Gson().toJson(App.getInstance().mDaoSession.getImageBeanDao().loadAll());
        Writer writer = null;
        try {
            OutputStream out = mContext.openFileOutput(mDataFilename,
                    Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(dataJson);
        } catch (FileNotFoundException ffe) {
            KLog.d(ffe.getMessage());
        } catch (IOException ie) {
            KLog.d(ie.getMessage());
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ie) {
                    KLog.d(ie.getMessage());
                }
            }
        }
        IPFSAnroid ipfsAnroid = new IPFSAnroid();
        ipfsAnroid.add(new Callback<FileAdd>() {
            @Override
            public void onResponse(Call<FileAdd> call, Response<FileAdd> response) {
                // 更新到数据库里面
                IpfsNodeBean ipfsNodeBean = new IpfsNodeBean();
                mNodeDataHash = response.body().getHash();
                ipfsNodeBean.setHash(response.body().getHash());
                ipfsNodeBean.setName(response.body().getName());
                App.getInstance().mDaoSession.getIpfsNodeBeanDao().insert(ipfsNodeBean);
                btn_pub_name.setEnabled(true);
                ToastUtil.show("发布数据成功");
            }

            @Override
            public void onFailure(Call<FileAdd> call, Throwable t) {
                ToastUtil.show(t.getMessage());
//                tv_ipfs_status.setText(t.getMessage());
            }
        }, new File(mContext.getFilesDir() + "/" + mDataFilename), mDataFilename);

    }

    @OnClick(R.id.btn_pub_node)
    public void pubNode() {
        List<ImageBean> imageBeanList = App.getInstance().mDaoSession.getImageBeanDao().loadAll();
        double totalPrice = 0L;
        for (ImageBean imageBean : imageBeanList) {
            totalPrice += imageBean.getPrice();
        }
        Intent intent = new Intent(this, PubActivity.class);
        Bundle b = new Bundle();
        b.putString(Constants.IPFS_NODE_HASH, "NodeHash:" + mNodeHash + ";TotalPrice:" +
                String.valueOf(totalPrice) +";RECEIVER_ADDRESS:"+App.getMainWalletAddress());
        intent.putExtras(b);
        startActivity(intent);
    }


    @OnClick(R.id.btn_pub_name)
    public void namePublsih() {
        if (TextUtils.isEmpty(mNodeDataHash)) {
            ToastUtil.show("Node Data Hash 为空");
            return;
        }
        pb_update.setVisibility(View.VISIBLE);
        IPFSAnroid ipfsAnroid = new IPFSAnroid();
        ipfsAnroid.name().publish(new Callback<NameEntity>() {

            @Override
            public void onResponse(Call<NameEntity> call, Response<NameEntity> response) {
                ToastUtil.show("the node has published to " + response.body().getName());
                KLog.d("IPFS" + response.body().getName());
                mNodeHash = response.body().getName();
                btn_pub_node.setEnabled(true);
                pb_update.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<NameEntity> call, Throwable t) {
                pb_update.setVisibility(View.GONE);
                KLog.e("IPFS" + t.getMessage());
            }

        }, mNodeDataHash);
    }
}
