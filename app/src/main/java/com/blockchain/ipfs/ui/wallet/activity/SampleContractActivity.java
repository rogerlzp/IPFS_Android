package com.blockchain.ipfs.ui.wallet.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.blockchain.ipfs.app.Constants;
import com.blockchain.ipfs.base.SimpleActivity;
import com.blockchain.ipfs.ui.ipfs.activity.ChannelListActivity;
import com.blockchain.ipfs.ui.wallet.contract.MetaCoin;
import com.blockchain.ipfs.ui.wallet.contract.SampleContract;
import com.blockchain.ipfs.ui.wallet.contract.TutorialToken;
import com.blockchain.ipfs.util.KeyStoreUtils;
import com.blockchain.ipfs.util.ToastUtil;
import com.blockchain.ipfs.R;
import com.blockchain.ipfs.app.Constants;
import com.blockchain.ipfs.base.SimpleActivity;
import com.blockchain.ipfs.ui.ipfs.activity.ChannelListActivity;
import com.blockchain.ipfs.ui.ipfs.activity.ChannelNodeActivity;
import com.blockchain.ipfs.ui.wallet.contract.MetaCoin;
import com.blockchain.ipfs.ui.wallet.contract.SampleContract;
import com.blockchain.ipfs.ui.wallet.contract.TutorialToken;
import com.blockchain.ipfs.util.KeyStoreUtils;
import com.blockchain.ipfs.util.ToastUtil;
import com.blockchain.ipfs.app.Constants;
import com.blockchain.ipfs.base.SimpleActivity;
import com.blockchain.ipfs.ui.ipfs.activity.ChannelListActivity;
import com.blockchain.ipfs.ui.wallet.contract.MetaCoin;
import com.blockchain.ipfs.ui.wallet.contract.SampleContract;
import com.blockchain.ipfs.ui.wallet.contract.TutorialToken;
import com.blockchain.ipfs.util.KeyStoreUtils;
import com.blockchain.ipfs.util.ToastUtil;

import org.web3j.abi.datatypes.Bool;

import java.math.BigInteger;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SampleContractActivity extends SimpleActivity {
    private String contractAddress = "0xe8bb91414fae190894b02cf7ebf10b6f69b74b26";
    private String metaCoinContractAddress = "0x2dbE09d6adfeb3183c2af6fC715f07b8aEE2B973";

    private String ttCoinContractAddress = "0xc70ace8f3fa405a77b9237d8dc94e9c8a10bdec9";

    @BindView(R.id.tv_address)
    TextView tvAddress;

    @BindView(R.id.tv_receiveraddress)
    TextView tv_receiveraddress;


    @BindView(R.id.tv_contract_result)
    TextView tv_contract_result;

    @BindView(R.id.btn_select)
    Button btnSelect;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.btn_get)
    Button btnGet;
    @BindView(R.id.et_set)
    EditText etSet;
    @BindView(R.id.btn_send)
    Button btn_send;


    @BindView(R.id.pbar_small)
    ProgressBar pbar_small;

    private SampleContract sampleContract;
    private TutorialToken tutorialToken;
    private MetaCoin metaCoinContract;
    private String gasPrice = "1800000000000";
    private String gasLimit = "100000";

    private String receiverAddress = "";
    private BigInteger sendAmount = BigInteger.valueOf(100L);

    private String fromRoute = "";

    @Override
    protected int getLayout() {
        return R.layout.activity_contract;
    }


    @Override
    protected void initEventAndData() {
        if (getIntent().getExtras() != null) {
            fromRoute = getIntent().getExtras().getString(Constants.ROUTE_FROM);
        }

    }

    @OnClick({R.id.btn_select, R.id.btn_get, R.id.btn_send, R.id.tv_receiveraddress})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_select:
                Intent toInetnt = new Intent(this, SwitchWalletActivity.class);
                toInetnt.putExtra("SwitchMode", true);
                startActivityForResult(toInetnt, SwitchWalletActivity.FROM_ADDRESS);
                break;
            case R.id.btn_get:
                getContractValue();
                break;
            case R.id.btn_send:
//                sendCoin();
                transferTTCoin();
                break;
            case R.id.tv_receiveraddress:
                toInetnt = new Intent(this, SwitchWalletActivity.class);
                toInetnt.putExtra("SwitchMode", true);
                startActivityForResult(toInetnt, SwitchWalletActivity.TO_ADDRESS);
                break;
        }
    }

    private void setContractValue() {
        if (etSet.length() == 0) {
            return;
        }
        Observable.create(e -> {
            sampleContract.set(new BigInteger(etSet.getText().toString())).send();
            e.onNext(new Object());
            e.onComplete();
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        o -> Toast.makeText(SampleContractActivity.this, "设置成功", Toast.LENGTH_SHORT).show(),
                        throwable -> throwable.printStackTrace());
    }

    public void transferTTCoin() {

        pbar_small.setVisibility(View.VISIBLE);
        Observable.create(new ObservableOnSubscribe<Object>() {

            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                tutorialToken.transfer(receiverAddress, sendAmount).send();
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
//                                if ((Boolean) value) {
                                tv_contract_result.setText("发送成功: True");
//                                }

                            }

                            @Override
                            public void onError(Throwable e) {
                                pbar_small.setVisibility(View.INVISIBLE);
                                tv_contract_result.setText(e.getMessage());
                            }

                            @Override
                            public void onComplete() {
                                pbar_small.setVisibility(View.INVISIBLE);
                                tv_contract_result.setText("发送成功");
                                if (fromRoute.equals(Constants.FROM_CHANANEL_LIST)) {
                                    Intent intent = new Intent(SampleContractActivity.this, ChannelListActivity.class);
                                    setResult(Constants.RESULT_PAYED, intent);
                                    finish();
                                }

                            }
                        }
                );

    }


    public void sendCoin() {
//        Observable.create(
////===============这里是第一部分A===============
//                new ObservableOnSubscribe<String>() {
//                    @Override
//                    public void subscribe(ObservableEmitter<String> e) throws Exception {
//                        e.onNext("你好世界");
//                        e.onComplete();
//                    }
//                }
////===============这里是第一部分A-结束===============
//        ).subscribe(
////===============这里是第二部分B===============
//                new Observer<String>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                    }
//                    @Override
//                    public void onNext(String value) {
//                    }
//                    @Override
//                    public void onError(Throwable e) {
//                    }
//                    @Override
//                    public void onComplete() {
//                    }
//                }
////===============这里是第二部分B===============
//        );
//
        Observable.create(e -> {
            metaCoinContract.sendCoin(receiverAddress, sendAmount).send();
            e.onNext(new Object());
            e.onComplete();
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        o -> tv_contract_result.setText("发送成功")
//                        o -> Toast.makeText(SampleContractActivity.this, "发送成功", Toast.LENGTH_SHORT).show()
                        , throwable -> tv_contract_result.setText(throwable.getMessage()));
    }

    public void getContractValue() {
        Observable.create((ObservableOnSubscribe<BigInteger>) e -> {
            BigInteger send = sampleContract.get().send();
            e.onNext(send);
            e.onComplete();
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(value -> {
                    tvContent.setText(value.toString());

                }, throwable -> {
                    throwable.printStackTrace();

                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case SwitchWalletActivity.FROM_ADDRESS:
                String address = data.getStringExtra("address");
                tvAddress.setText(address);
                Observable.create((ObservableOnSubscribe<TutorialToken>) emitter -> {
                    // TODO: 2018/3/3 合约调用前要先部署 ,已经部署过直接load
//                    MetaCoin metaCoinContract = MetaCoin.load(metaCoinContractAddress,
//                            Web3JService.getInstance(), KeyStoreUtils.getCredentials(address),
//                            new BigInteger(gasPrice),
//                            new BigInteger(gasLimit));

                    TutorialToken tutorialToken = TutorialToken.load(ttCoinContractAddress,
                            Web3JService.getInstance(), KeyStoreUtils.getCredentials(address),
                            new BigInteger(gasPrice),
                            new BigInteger(gasLimit));


//                    SampleContract sampleContract = new SampleContract(contractAddress,
//                            Web3JService.getInstance(),
//                            KeyStoreUtils.getCredentials(address),
//                            new BigInteger(gasPrice),
//                            new BigInteger(gasLimit));
                    emitter.onNext(tutorialToken);
//                    emitter.onNext(metaCoinContract);
                    emitter.onComplete();

                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(tutorialToken -> {
                            this.tutorialToken = tutorialToken;
//                            this.metaCoinContract = metaCoinContract;
                            ToastUtil.show("合约加载完成");
                        }, throwable -> throwable.printStackTrace())
                ;

                break;
            case SwitchWalletActivity.TO_ADDRESS:
                receiverAddress = data.getStringExtra("address");
                tv_receiveraddress.setText(receiverAddress);

                break;

        }

    }
}
