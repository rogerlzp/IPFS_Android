package com.blockchain.ipfs.ui.wallet.activity;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blockchain.ipfs.util.KeyStoreUtils;
import com.blockchain.ipfs.R;
import com.blockchain.ipfs.base.SimpleActivity;
import com.blockchain.ipfs.util.KeyStoreUtils;
import com.blockchain.ipfs.util.ToastUtil;
import com.blockchain.ipfs.util.KeyStoreUtils;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.WalletUtils;
import org.web3j.utils.Numeric;

import butterknife.BindView;
import butterknife.OnClick;

public class ImportPrivateKeyActivity extends SimpleActivity {

    @BindView(R.id.et_private_key)
    EditText etPrivateKey;
    @BindView(R.id.btn_import)
    Button btnImport;
    @BindView(R.id.tv_mgs)
    TextView tvMgs;

    @BindView(R.id.et_password)
    EditText et_password;


    @OnClick(R.id.btn_import)
    public void onViewClicked() {
        importPrivateKey();
    }

    private void importPrivateKey() {
        if (etPrivateKey.length() == 0) {
            etPrivateKey.setError("请输入私钥");
            return;
        }
        if (et_password.length() == 0) {
            et_password.setError("请输入钱包密码");
            return;
        }
        String privateKey = etPrivateKey.getText().toString();
        Credentials credentials = Credentials.create(privateKey);
        ECKeyPair ecKeyPair = credentials.getEcKeyPair();
        KeyStoreUtils.genKeyStore2Files(ecKeyPair);
        String msg = "address:\n" + credentials.getAddress()
                + "\nprivateKey:\n" + Numeric.encodeQuantity(ecKeyPair.getPrivateKey())
                + "\nPublicKey:\n" + Numeric.encodeQuantity(ecKeyPair.getPublicKey());
        tvMgs.setText(msg);

        KeyStoreUtils.genKeyStore2Files(et_password.getText().toString().trim(), ecKeyPair);
        ToastUtil.show("钱包文件已经导入");
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_import_private_key;
    }

    @Override
    protected void initEventAndData() {

    }
}
