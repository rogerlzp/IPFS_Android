package com.codeest.geeknews.ui.ipfs.activity;

import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.codeest.geeknews.R;
import com.codeest.geeknews.base.SimpleActivity;
import com.codeest.geeknews.util.ToastUtil;
import com.ipfs.api.IPFSAnroid;
import com.ipfs.api.entity.Pub;
import com.ipfs.api.entity.SwarmEntity;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zhengpingli on 2018/6/15.
 */

public class SwarmActivity extends SimpleActivity {

    @BindView(R.id.et_address)
    EditText et_address;




    @BindView(R.id.btn_swarm)
    Button btn_swarm;

    @Override
    protected int getLayout() {
        return R.layout.activity_ipfs_swarm;
    }


    @Override
    protected void initEventAndData() {

    }

    @OnClick(R.id.btn_swarm)
    public void swarm() {
        if (TextUtils.isEmpty(et_address.getText().toString().trim())) {
            ToastUtil.shortShow("et_address 不能为空");
            return;
        }


        IPFSAnroid ipfsAnroid = new IPFSAnroid();
        ipfsAnroid.swarm().connect(new Callback<SwarmEntity.Connect>() {
            @Override
            public void onResponse(Call<SwarmEntity.Connect> call, Response<SwarmEntity.Connect> response) {
               if(!TextUtils.isEmpty(response.body().toString())) {
                   ToastUtil.show(response.body().getStrings().get(0));
               }
            }

            @Override
            public void onFailure(Call<SwarmEntity.Connect> call, Throwable t) {

            }
        }, et_address.getText().toString().trim());

    }

}
