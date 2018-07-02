package com.blockchain.ipfs.ui.wallet.fragment;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.blockchain.ipfs.base.contract.wallet.WalletContract;
import com.blockchain.ipfs.presenter.wallet.WalletPresenter;
import com.blockchain.ipfs.ui.wallet.activity.ImportPrivateKeyActivity;
import com.blockchain.ipfs.ui.wallet.activity.SendActivity;
import com.blockchain.ipfs.ui.wallet.activity.SwitchWalletActivity;
import com.blockchain.ipfs.R;
import com.blockchain.ipfs.app.Constants;
import com.blockchain.ipfs.base.RootFragment;

import com.blockchain.ipfs.base.contract.wallet.WalletContract;
import com.blockchain.ipfs.model.bean.WXItemBean;

import com.blockchain.ipfs.presenter.wallet.WalletPresenter;
import com.blockchain.ipfs.ui.ipfs.activity.SwarmActivity;
import com.blockchain.ipfs.ui.wallet.activity.EthWalletActivity;
import com.blockchain.ipfs.ui.wallet.activity.GenerateWalletActivity;
import com.blockchain.ipfs.ui.wallet.activity.ImportKeystoreActivity;
import com.blockchain.ipfs.ui.wallet.activity.ImportPrivateKeyActivity;
import com.blockchain.ipfs.ui.wallet.activity.SampleContractActivity;
import com.blockchain.ipfs.ui.wallet.activity.SendActivity;
import com.blockchain.ipfs.ui.wallet.activity.SwitchWalletActivity;
import com.blockchain.ipfs.ui.wallet.activity.Web3JService;
import com.blockchain.ipfs.util.LogUtil;
import com.blockchain.ipfs.util.ToastUtil;
import com.blockchain.ipfs.base.contract.wallet.WalletContract;
import com.blockchain.ipfs.presenter.wallet.WalletPresenter;
import com.blockchain.ipfs.ui.wallet.activity.ImportPrivateKeyActivity;
import com.blockchain.ipfs.ui.wallet.activity.SendActivity;
import com.blockchain.ipfs.ui.wallet.activity.SwitchWalletActivity;

import org.web3j.protocol.core.methods.response.Web3ClientVersion;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WalletFragment extends RootFragment<WalletPresenter> implements WalletContract.View {


    @BindView(R.id.view_main)
    RecyclerView rvWechatList;

    @BindView(R.id.btn_import_keystore)
    Button btn_import_keystore;


    @BindView(R.id.btn_conn_ropsten)
    Button btn_conn_ropsten;

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
        startActivity(new Intent(this.mContext, ImportKeystoreActivity.class));

    }

    @OnClick(R.id.btn_import_privatekey)
    public void importPrivateKey(View view) {
        startActivity(new Intent(this.mContext, ImportPrivateKeyActivity.class));
    }

    @OnClick(R.id.btn_transation)
    public void transation(View view) {
        startActivity(new Intent(this.mContext, SendActivity.class));
    }

    @OnClick(R.id.btn_generateWallet)
    public void generateWallet(View view) {

        startActivity(new Intent(this.mContext, GenerateWalletActivity.class));

    }

    @OnClick(R.id.btn_switchWallet)
    public void switchWallet(View view) {

        startActivity(new Intent(this.mContext, SwitchWalletActivity.class));
    }

    @OnClick(R.id.btn_sampleContract)
    public void sampleContract(View view) {

        startActivity(new Intent(this.mContext, SampleContractActivity.class));
    }

    @OnClick(R.id.btn_conn_ropsten)
    public void testNetwork() {
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
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wallet;
    }


    private void registerReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.IPFS_ACTION_START);
        filter.addAction(Constants.IPFS_ACTION_STOP);
//        registerReceiver(ipfsDaemonReceiver, filter);
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        registerReceiver();


    }

    @Override
    public void showContent(List<WXItemBean> list) {

    }

    @Override
    public void showMoreContent(List<WXItemBean> list) {

    }

    @Override
    public void stateError() {
        super.stateError();

    }


}
