package com.blockchain.ipfs.ui.wallet.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.blockchain.ipfs.base.SimpleActivity;
import com.blockchain.ipfs.util.LogUtil;
import com.blockchain.ipfs.util.ToastUtil;
import com.blockchain.ipfs.R;
import com.blockchain.ipfs.base.SimpleActivity;
import com.blockchain.ipfs.util.LogUtil;
import com.blockchain.ipfs.util.ToastUtil;
import com.blockchain.ipfs.base.SimpleActivity;
import com.blockchain.ipfs.util.LogUtil;
import com.blockchain.ipfs.util.ToastUtil;

import org.web3j.protocol.core.methods.response.Web3ClientVersion;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class EthWalletActivity extends SimpleActivity {

    @BindView(R.id.btn_import_keystore)
    Button btn_import_keystore;

    @BindView(R.id.btn_import_privatekey)
    Button btn_import_privatekey;

    @BindView(R.id.btn_transation)
    Button btn_transation;

    @BindView(R.id.btn_generateWallet)
    Button btn_generateWallet;

    @BindView(R.id.btn_switchWallet)
    Button btn_switchWallet;

    @BindView(R.id.btn_sampleContract)
    Button btn_sampleContract;


    @OnClick(R.id.btn_import_keystore)
    public void importKeystore(View view) {
        startActivity(new Intent(this, ImportKeystoreActivity.class));

    }

    @OnClick(R.id.btn_import_privatekey)
    public void importPrivateKey(View view) {
        startActivity(new Intent(this, ImportPrivateKeyActivity.class));
    }

    @OnClick(R.id.btn_transation)
    public void transation(View view) {
        startActivity(new Intent(this, SendActivity.class));
    }

    @OnClick(R.id.btn_generateWallet)
    public void generateWallet(View view) {

        startActivity(new Intent(this, GenerateWalletActivity.class));

    }

    @OnClick(R.id.btn_switchWallet)
    public void switchWallet(View view) {

        startActivity(new Intent(this, SwitchWalletActivity.class));
    }

    @OnClick(R.id.btn_sampleContract)
    public void sampleContract(View view) {

        startActivity(new Intent(this, SampleContractActivity.class));
    }

    public void testNetwork(View view) {
        Observable.create((ObservableOnSubscribe<Web3ClientVersion>) e -> {
            Web3ClientVersion send = Web3JService.getInstance().web3ClientVersion().send();
            e.onNext(send);
            e.onComplete();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(web3ClientVersion -> {
                    String version = web3ClientVersion.getWeb3ClientVersion();
                    ToastUtil.show("wallet : " + version);
                    LogUtil.d("web3j" + version);
                }, throwable -> throwable.printStackTrace());
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_eth_wallet;
    }

    @Override
    protected void initEventAndData() {

    }
}
