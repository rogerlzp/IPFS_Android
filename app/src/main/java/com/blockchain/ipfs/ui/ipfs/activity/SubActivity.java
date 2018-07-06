package com.blockchain.ipfs.ui.ipfs.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blockchain.ipfs.model.bean.IpfsChannelNodeBean;
import com.blockchain.ipfs.R;
import com.blockchain.ipfs.app.App;
import com.blockchain.ipfs.app.Constants;
import com.blockchain.ipfs.base.SimpleActivity;
import com.blockchain.ipfs.model.bean.IpfsChannelNodeBean;
import com.blockchain.ipfs.model.bean.IpfsNodeBean;
import com.blockchain.ipfs.net.ReqCallBack;
import com.blockchain.ipfs.net.WCOKHttpClient;
import com.blockchain.ipfs.util.LogUtil;
import com.blockchain.ipfs.util.ToastUtil;
import com.blockchain.ipfs.model.bean.IpfsChannelNodeBean;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ipfs.api.IPFSAnroid;
import com.ipfs.api.entity.Pub;
import com.ipfs.api.entity.Sub;
import com.socks.library.KLog;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by zhengpingli on 2018/6/15.
 */

public class SubActivity extends SimpleActivity implements IpfsSubService.SupCallback {

    @BindView(R.id.et_topic)
    EditText et_topic;

    @BindView(R.id.tv_message)
    TextView tv_message;


    @BindView(R.id.btn_sub)
    Button btn_sub;

    String topic = "";

    @Override
    protected int getLayout() {
        return R.layout.activity_ipfs_sub;
    }


    @Override
    protected void initEventAndData() {

    }

    @OnClick(R.id.btn_sub)
    public void subMessage() {
        topic = et_topic.getText().toString().trim();
        if (TextUtils.isEmpty(topic)) {
            ToastUtil.shortShow("et_topic 不能为空");
            return;
        }
        if (mBinderService != null) {
            //重新连接Service
            unbindService(connection);
        }
        Bundle b = new Bundle();
        Intent intent = new Intent(this, IpfsSubService.class);
        b.putString(Constants.IPFS_PUBSUB_TOPIC, topic);
        intent.putExtras(b);
        bindService(intent, connection, BIND_AUTO_CREATE);

//        IpfsSubService ipfsSubService = new IpfsSubService();
//        Intent intent = new Intent(this, IpfsSubService.class);
//        startService(intent);


    }


    private void output(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv_message.setText(tv_message.getText().toString() + "\n\n" + text);
            }
        });
    }

    private Interceptor mLoggingInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {

            Request request = chain.request();
            Request.Builder requestBuilder = request.newBuilder();
            requestBuilder.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
            //添加UUID
//            requestBuilder.addHeader(Constant.UUID, LoginUtils.getSessionKey());
            request = requestBuilder.build();
            RequestBody requestBody = request.body();
            Buffer bufferRequest = new Buffer();
            if (bufferRequest.size() != 0) {
                //为空
                requestBody.writeTo(bufferRequest);
            }

            Charset charsetRequest = Charset.forName("UTF-8");
            if (requestBody != null) {
                MediaType contentTypeRequest = requestBody.contentType();
                if (contentTypeRequest != null) {
                    charsetRequest = contentTypeRequest.charset(charsetRequest);
                }
            }

            String bodyStr = null;
            if (isPlaintext(bufferRequest)) {
                bodyStr = bufferRequest.readString(charsetRequest);

            }
            final Response response = chain.proceed(request);

            KLog.e("请求网址: \n" + request.url() + " \n " + "请求头部信息：\n" + request.headers() + "请求body信息：\n" + bodyStr + "\n响应头部信息：\n" + response.headers());

            final ResponseBody responseBody = response.body();
            final long contentLength = responseBody.contentLength();
            BufferedSource source = responseBody.source();
            source.request(10000); // Buffer the entire body.
            Buffer buffer = source.buffer();

            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                try {
                    charset = contentType.charset(charset);
                } catch (UnsupportedCharsetException e) {
                    KLog.e("");
                    KLog.e("Couldn't decode the response body; charset is likely malformed.");
                    return response;
                }


            }

            if (contentLength != 0) {
                KLog.e("--------------------------------------------开始打印返回数据----------------------------------------------------");
                KLog.e(buffer.clone().readString(charset));
                KLog.e("--------------------------------------------结束打印返回数据----------------------------------------------------");
            }

            return response;
        }
    };

    static boolean isPlaintext(Buffer buffer) throws EOFException {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }


    @Override
    public void onDestroy() {
        unbindService(connection);
        super.onDestroy();
    }

    private IpfsSubService.ServiceBinder mBinderService;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinderService = (IpfsSubService.ServiceBinder) service;
            try {
                mBinderService.setSupCallback(SubActivity.this);
                mBinderService.listenOnTopic();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };


    @Override
    public void getSubTopicData(Sub sub) {
        ToastUtil.show("data:" + new String(Base64.decode(sub.getData(), Base64.DEFAULT)));
        ToastUtil.show("FROM:" + new String(Base64.decode(sub.getFrom(), Base64.DEFAULT)));
        // 如果含有节点信息，则添加该节点到数据库中
        IpfsChannelNodeBean ipfsChannelNodeBean = new IpfsChannelNodeBean();
        ipfsChannelNodeBean.setGetTime(new Date());
        String originData = new String(Base64.decode(sub.getData(), Base64.DEFAULT));

        if (originData.indexOf(";") != -1) {
            ipfsChannelNodeBean.setHash(originData.split(";")[0].split(":")[1]);
            ipfsChannelNodeBean.setTotalPrice(Double.parseDouble(originData.split(";")[1].split(":")[1]));
            ipfsChannelNodeBean.setReceiverAddress(originData.split(";")[2].split(":")[1]);

        } else {
            ipfsChannelNodeBean.setHash(originData);
            ipfsChannelNodeBean.setTotalPrice(0d);
            ipfsChannelNodeBean.setReceiverAddress("");
        }

        ipfsChannelNodeBean.setTopic(topic);
        ipfsChannelNodeBean.setName(new String(Base64.decode(sub.getFrom(), Base64.DEFAULT)));
        App.getInstance().mDaoSession.getIpfsChannelNodeBeanDao().insert(ipfsChannelNodeBean);
    }


}
