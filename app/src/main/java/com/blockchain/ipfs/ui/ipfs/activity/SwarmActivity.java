package com.blockchain.ipfs.ui.ipfs.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blockchain.ipfs.R;
import com.blockchain.ipfs.app.Constants;
import com.blockchain.ipfs.base.SimpleActivity;
import com.blockchain.ipfs.ui.main.activity.MainActivity;
import com.blockchain.ipfs.util.PermissionUtil;
import com.blockchain.ipfs.util.ToastUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.ipfs.api.IPFSAnroid;
import com.ipfs.api.entity.Id;
import com.ipfs.api.entity.SwarmEntity;

import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

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

    @BindView(R.id.btn_save_img)
    Button btn_save_img;

    @BindView(R.id.btn_save_by_img)
    Button btn_save_by_img;


    @BindView(R.id.btn_change)
    Button btn_change;


    @BindView(R.id.iv_qr_img)
    ImageView iv_qr_img;


    @BindView(R.id.tv_ip_address)
    TextView tv_ip_address;

    int ipAddressCounter = 1;

    @Override
    protected int getLayout() {
        return R.layout.activity_ipfs_swarm;
    }

    String firstIp = "";

    @Override
    protected void initEventAndData() {
        PermissionUtil.requestPermissions(this);

        ZXingLibrary.initDisplayOpinion(this);


    }

    @OnClick(R.id.btn_save_img)
    public void saveNodeImg() {
        IPFSAnroid ipfsAnroid = new IPFSAnroid();
        ipfsAnroid.id(new Callback<Id>() {
            @Override
            public void onResponse(Call<Id> call, Response<Id> response) {
                if (response.body().getAddresses().size() != 0) {
                    firstIp = response.body().getAddresses().get(0);

                    Bitmap mBitmap = CodeUtils.createImage(firstIp, 400, 400, BitmapFactory.decodeResource(getResources(), R.drawable.ipfs_logo_440));
                    iv_qr_img.setImageBitmap(mBitmap);
                    tv_ip_address.setText(firstIp);
                }
            }

            @Override
            public void onFailure(Call<Id> call, Throwable t) {
            }
        });
    }

    @OnClick(R.id.btn_change)
    public void btn_change() {
        IPFSAnroid ipfsAnroid = new IPFSAnroid();
        ipfsAnroid.id(new Callback<Id>() {
            @Override
            public void onResponse(Call<Id> call, Response<Id> response) {
                if (response.body().getAddresses().size() != 0) {

                    firstIp = response.body().getAddresses().get(ipAddressCounter++);

                    Bitmap mBitmap = CodeUtils.createImage(firstIp, 400, 400, BitmapFactory.decodeResource(getResources(), R.drawable.ipfs_logo_440));
                    iv_qr_img.setImageBitmap(mBitmap);
                    tv_ip_address.setText(firstIp);
                }
            }

            @Override
            public void onFailure(Call<Id> call, Throwable t) {
            }
        });
    }


    @OnClick(R.id.btn_save_by_img)
    public void scan() {
        if (ContextCompat.checkSelfPermission(SwarmActivity.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            //权限还没有授予，需要在这里写申请权限的代码
            ActivityCompat.requestPermissions(SwarmActivity.this,
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
                if (response.body() != null) {
                    if (!TextUtils.isEmpty(response.body().toString())) {
                        ToastUtil.show(response.body().getStrings().get(0));
                    }
                }
            }

            @Override
            public void onFailure(Call<SwarmEntity.Connect> call, Throwable t) {
                ToastUtil.show(t.getMessage());
            }
        }, et_address.getText().toString().trim());
    }

}
