package com.blockchain.ipfs.ui.ipfs.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.blockchain.ipfs.R;
import com.blockchain.ipfs.app.Constants;
import com.blockchain.ipfs.base.SimpleActivity;
import com.blockchain.ipfs.ui.ipfs.adapter.IpfsPeerAdapter;
import com.blockchain.ipfs.util.PermissionUtil;
import com.blockchain.ipfs.util.ToastUtil;
import com.ipfs.api.IPFSAnroid;
import com.ipfs.api.entity.BootStrapEntity;
import com.ipfs.api.entity.PeerEntity;
import com.ipfs.api.entity.SwarmEntity;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IpfsBootstrapAddActivity extends SimpleActivity {
//
//    @BindView(R.id.rv_peer_list)
//    RecyclerView rv_peer_list;

    @BindView(R.id.btn_bootstrap_add)
    Button btn_bootstrap_add;

    @BindView(R.id.et_address)
    EditText et_address;

    @BindView(R.id.btn_save_by_img)
    Button btn_save_by_img;

    List<PeerEntity> mList;
    IpfsPeerAdapter mAdapter;


    @Override
    protected int getLayout() {
        return R.layout.activity_ipfs_bootstrap_add;
    }

    @Override
    protected void initEventAndData() {

        PermissionUtil.requestPermissions(this);

        ZXingLibrary.initDisplayOpinion(this);

    }

    @OnClick(R.id.btn_save_by_img)
    public void scan() {
        if (ContextCompat.checkSelfPermission(IpfsBootstrapAddActivity.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            //权限还没有授予，需要在这里写申请权限的代码
            ActivityCompat.requestPermissions(IpfsBootstrapAddActivity.this,
                    new String[]{Manifest.permission.CAMERA}, 60);
        } else {
            Intent intent = new Intent(this, CaptureActivity.class);
            startActivityForResult(intent, Constants.REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    et_address.setText(result);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }

    }

    @OnClick(R.id.btn_bootstrap_add)
    public void swarm() {
        if (TextUtils.isEmpty(et_address.getText().toString().trim())) {
            ToastUtil.shortShow("et_address 不能为空");
            return;
        }

        IPFSAnroid ipfsAnroid = new IPFSAnroid();
        ipfsAnroid.bootstrap().add(new Callback<BootStrapEntity>() {
            @Override
            public void onResponse(Call<BootStrapEntity> call, Response<BootStrapEntity> response) {
                if (response.body() != null) {
                    if (!TextUtils.isEmpty(response.body().toString())) {
                        if (response.body().getPeers().size() != 0) {
                            ToastUtil.show(response.body().getPeers().get(0));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BootStrapEntity> call, Throwable t) {
                ToastUtil.show(t.getMessage());
            }

        }, et_address.getText().toString().trim());
    }

}
