package com.blockchain.ipfs.ui.wallet.activity;

import android.content.Context;
import android.os.Environment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blockchain.ipfs.base.SimpleActivity;
import com.blockchain.ipfs.util.KeyStoreUtils;
import com.blockchain.ipfs.util.PermissionUtil;
import com.blockchain.ipfs.R;
import com.blockchain.ipfs.base.SimpleActivity;
import com.blockchain.ipfs.util.KeyStoreUtils;
import com.blockchain.ipfs.util.PermissionUtil;
import com.blockchain.ipfs.base.SimpleActivity;
import com.blockchain.ipfs.util.KeyStoreUtils;
import com.blockchain.ipfs.util.PermissionUtil;

import org.json.JSONException;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.WalletUtils;
import org.web3j.utils.Numeric;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

public class GenerateWalletActivity extends SimpleActivity {
    @BindView(R.id.btn_generate)
    Button btnGenerate;
    @BindView(R.id.tv_mgs)
    TextView tvMgs;
    @BindView(R.id.et_password)
    EditText etPassword;


    @OnClick(R.id.btn_generate)
    public void onViewClicked() {
        genWallet();
    }

    private void genWallet() {
        if (etPassword.length() == 0) {
            etPassword.setError("请输入password");
            return;
        }
        String password = etPassword.getText().toString();
        try {
            File fileDir = new File(Environment.getExternalStorageDirectory().getPath() + "/LightWallet");
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            ECKeyPair ecKeyPair = Keys.createEcKeyPair();

            //在外置卡生成
            String filename = WalletUtils.generateWalletFile(password, ecKeyPair, fileDir, false);

            KeyStoreUtils.genKeyStore2Files(ecKeyPair);

            Credentials credentials = WalletUtils.loadCredentials(password, new File(fileDir, filename));

            String msg = "fileName:\n" + filename
                    + "\nprivateKey:\n" + Numeric.encodeQuantity(ecKeyPair.getPrivateKey())
                    + "\nPublicKey:\n" + Numeric.encodeQuantity(ecKeyPair.getPublicKey())
                    + "\nWallet Address\n" + credentials.getAddress();
            tvMgs.setText(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_generate_wallet;

    }

    @Override
    protected void initEventAndData() {
        PermissionUtil.requestPermissions(this);
    }


    public Credentials getFullWallet(Context context, String password, String wallet) throws IOException, JSONException, CipherException {
        if (wallet.startsWith("0x"))
            wallet = wallet.substring(2, wallet.length());
        return WalletUtils.loadCredentials(password, new File(context.getFilesDir(), wallet));
    }

}
