package com.blockchain.ipfs.ui.ipfs.activity;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blockchain.ipfs.R;
import com.blockchain.ipfs.app.App;
import com.blockchain.ipfs.base.SimpleActivity;
import com.blockchain.ipfs.util.FileReaderUtil;
import com.blockchain.ipfs.util.LogUtil;
import com.blockchain.ipfs.util.ToastUtil;
import com.ipfs.api.IPFSAnroid;
import com.ipfs.api.entity.FileGet;
import com.socks.library.KLog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetFileActivity extends SimpleActivity {

    @BindView(R.id.et_hash)
    EditText et_hash;

    @BindView(R.id.btn_get_hash)
    Button btn_get_hash;


    @BindView(R.id.tv_file_content)
    TextView tv_file_content;

    Response<ResponseBody> responseBodyResponse;

    @Override
    protected int getLayout() {
        return R.layout.activity_ipfs_get_file;
    }

    @Override
    protected void initEventAndData() {

    }

    @OnClick(R.id.btn_get_hash)
    public void getFile() {
        if (TextUtils.isEmpty(et_hash.getText().toString().trim())) {
            ToastUtil.show("没有Hash地址");
            return;
        }
        IPFSAnroid ipfsAnroid = new IPFSAnroid();

        StringBuffer sb = new StringBuffer();
        ipfsAnroid.catFile(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                responseBodyResponse = response;
                runnable.run();
            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                ToastUtil.show(t.getMessage());

            }
        }, et_hash.getText().toString().trim());
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            File outPutFile = new File(App.getInstance().getFilesDir() + "outputFile");

            byte[] fileReader = new byte[4096];
            long fileSize = responseBodyResponse.body().contentLength();
            long fileSizeDownloaded = 0;
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                if (!outPutFile.exists()) {
                    outPutFile.createNewFile();
                }

                inputStream = responseBodyResponse.body().byteStream();
                outputStream = new FileOutputStream(outPutFile);
                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                    LogUtil.d("file download: " + fileSizeDownloaded + " of " + fileSize);
                }
                outputStream.flush();

                tv_file_content.setText(FileReaderUtil.readFile(outPutFile));

            } catch (
                    IOException ie)

            {
                KLog.e(ie.getMessage());
            } finally

            {
                try {
                    inputStream.close();
                    outputStream.close();
                } catch (IOException ie) {

                }
            }
        }
    };


}
