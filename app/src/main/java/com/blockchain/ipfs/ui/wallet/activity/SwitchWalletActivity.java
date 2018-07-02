package com.blockchain.ipfs.ui.wallet.activity;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.blockchain.ipfs.R;
import com.blockchain.ipfs.base.SimpleActivity;
import com.blockchain.ipfs.model.bean.KeyStoreBean;
import com.blockchain.ipfs.ui.wallet.adapter.KeyStoreListAdapter;
import com.blockchain.ipfs.util.KeyStoreUtils;
import com.blockchain.ipfs.util.ToastUtil;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SwitchWalletActivity extends SimpleActivity implements KeyStoreListAdapter.ChooseKeyStoreListener {

    public static final int FROM_ADDRESS = 232;
    public static final int TO_ADDRESS = 255;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    KeyStoreListAdapter keyStoreListAdapter;


    private List<KeyStoreBean> gekeystoreList() {


        List<KeyStoreBean> keyStoreBeans = new ArrayList<>();

        File[] keyStoreFiles = KeyStoreUtils.getKeyStorePathFile().listFiles();

        for (File file : keyStoreFiles) {

            String name = file.getName();
            String address;
            if (name.endsWith(".json")) {//web3j生成的keystore
                address = name.substring(name.lastIndexOf("--") + 2, name.lastIndexOf("."));
            } else {
                //geth生成的keystore
                address = name.substring(name.lastIndexOf("--") + 2, name.length() - 1);
            }


            keyStoreBeans.add(new KeyStoreBean(address, file.getAbsolutePath()));
        }

        return keyStoreBeans;
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_switch_wallet;
    }

    @Override
    protected void initEventAndData() {
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        keyStoreListAdapter = new KeyStoreListAdapter(this, gekeystoreList());
        keyStoreListAdapter.setChooseKeyStoreListener(this);
        recyclerview.setAdapter(keyStoreListAdapter);
        recyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }

    @Override
    public void onKeyStoreChoosed(KeyStoreBean keyStoreBean) {
        Intent intent = new Intent();
        intent.putExtra("address", keyStoreBean.getAddress());
        intent.putExtra("path", keyStoreBean.getPath());
        setResult(RESULT_OK, intent);
        finish();
    }

}
