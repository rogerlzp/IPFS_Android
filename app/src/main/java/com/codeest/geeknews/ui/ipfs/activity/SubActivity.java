package com.codeest.geeknews.ui.ipfs.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.codeest.geeknews.R;
import com.codeest.geeknews.base.SimpleActivity;
import com.codeest.geeknews.util.ToastUtil;
import com.ipfs.api.IPFSAnroid;
import com.ipfs.api.entity.Pub;
import com.ipfs.api.entity.Sub;

import java.util.Timer;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zhengpingli on 2018/6/15.
 */

public class SubActivity extends SimpleActivity {

    @BindView(R.id.et_topic)
    EditText et_topic;

    @BindView(R.id.tv_message)
    TextView tv_message;


    @BindView(R.id.btn_sub)
    Button btn_sub;

    @Override
    protected int getLayout() {
        return R.layout.activity_ipfs_sub;
    }


    @Override
    protected void initEventAndData() {

    }

    @OnClick(R.id.btn_sub)
    public void subMessage() {
        if (TextUtils.isEmpty(et_topic.getText().toString().trim())) {
            ToastUtil.shortShow("et_topic 不能为空");
            return;
        }
//        IpfsSubService ipfsSubService = new IpfsSubService();
//        Intent intent = new Intent(this,IpfsSubService.class );
//        startService(intent);
//        timer = new Timer();
        IPFSAnroid ipfsAnroid = new IPFSAnroid();
        ipfsAnroid.pubsub().sub(new Callback<Sub>() {
                                    @Override
                                    public void onResponse(Call<Sub> call, Response<Sub> response) {
                                        ToastUtil.show("pub succeed");
                                        if(response.body() !=null) {
                                            tv_message.setText(response.body().getData());
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Sub> call, Throwable t) {

                                    }
                                }, et_topic.getText().toString().trim()
        );

    }

}
