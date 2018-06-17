package com.codeest.geeknews.ui.ipfs.activity;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.codeest.geeknews.R;
import com.codeest.geeknews.app.App;
import com.codeest.geeknews.base.SimpleActivity;
import com.codeest.geeknews.model.bean.ImageBean;
import com.codeest.geeknews.util.ToastUtil;
import com.ipfs.api.IPFSAnroid;
import com.ipfs.api.entity.FileAdd;
import com.ipfs.api.entity.Pub;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zhengpingli on 2018/6/15.
 */

public class PubActivity extends SimpleActivity {

    @BindView(R.id.et_topic)
    EditText et_topic;

    @BindView(R.id.et_message)
    EditText et_message;


    @BindView(R.id.btn_pub)
    Button btn_pub;

    @Override
    protected int getLayout() {
        return R.layout.activity_ipfs_pub;
    }


    @Override
    protected void initEventAndData() {

    }

    @OnClick(R.id.btn_pub)
    public void pubMessage() {
        if (TextUtils.isEmpty(et_message.getText().toString().trim())) {
            ToastUtil.shortShow("et_message 不能为空");
            return;
        }
        if (TextUtils.isEmpty(et_topic.getText().toString().trim())) {
            ToastUtil.shortShow("et_topic 不能为空");
            return;
        }

        IPFSAnroid ipfsAnroid = new IPFSAnroid();
        ipfsAnroid.pubsub().pub(new Callback<Pub>() {
                                    @Override
                                    public void onResponse(Call<Pub> call, Response<Pub> response) {
                                        ToastUtil.show("pub succeed");
                                    }

                                    @Override
                                    public void onFailure(Call<Pub> call, Throwable t) {

                                    }
                                }, et_topic.getText().toString().trim(), et_message.getText().toString().trim()
        );

    }


}
