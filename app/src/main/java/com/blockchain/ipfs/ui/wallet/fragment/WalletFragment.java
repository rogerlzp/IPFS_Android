package com.blockchain.ipfs.ui.wallet.fragment;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blockchain.ipfs.app.App;
import com.blockchain.ipfs.base.contract.wallet.WalletContract;
import com.blockchain.ipfs.presenter.wallet.WalletPresenter;
import com.blockchain.ipfs.ui.ipfs.activity.ChannelListActivity;
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
import com.blockchain.ipfs.util.TokenUtil;
import com.blockchain.ipfs.widget.DialogUI;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.utils.Convert;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
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

    @BindView(R.id.btn_set_wallet)
    Button btn_set_wallet;

    @BindView(R.id.tv_wallet_value_tt)
    TextView tv_wallet_value_tt;

    @BindView(R.id.tv_wallet_address)
    TextView tv_wallet_address;

    @BindView(R.id.tv_wallet_value_eth)
    TextView tv_wallet_value_eth;

    @BindView(R.id.btn_change_wallet)
    Button btn_change_wallet;

    String mailWalletAddress = "";
    BigDecimal ethValue;
    BigInteger ttValue;

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


    @OnClick(R.id.btn_set_wallet)
    public void setMainWallet(View view) {
        startActivityForResult(new Intent(this.mContext, SwitchWalletActivity.class), Constants.REQUEST_FROM_SET_WALLET);
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

        // 检查是否设置了主钱包

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_FROM_SET_WALLET) {
            mailWalletAddress = data.getStringExtra("address");
            if (!mailWalletAddress.startsWith("0x")) {
                mailWalletAddress = "0x" + mailWalletAddress;
            }
            App.setMainWalletAddress(mailWalletAddress);
            tv_wallet_address.setText(mailWalletAddress);
            updateToken();
        }
    }

    private void updateToken() {

        Observable.create(new ObservableOnSubscribe<Object>() {

            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                ethValue = TokenUtil.getTokenBalance(mailWalletAddress);
                ttValue = (TokenUtil.getTokenBalance(mailWalletAddress, Constants.TT_COIN_CONTRACT_ADDRESS).divide(new BigInteger("100")));
                e.onNext(new Object());
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Observer<Object>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(Object value) {
                            }

                            @Override
                            public void onError(Throwable e) {
                            }

                            @Override
                            public void onComplete() {

                                tv_wallet_value_eth.setText("" + ethValue + "ETH");
                                tv_wallet_value_tt.setText("" + ttValue + "TT");
                            }
                        }
                );

    }


}
