package com.codeest.geeknews.ui.ipfs.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codeest.geeknews.R;
import com.codeest.geeknews.base.SimpleActivity;
import com.codeest.geeknews.net.ReqCallBack;
import com.codeest.geeknews.net.WCOKHttpClient;
import com.codeest.geeknews.util.LogUtil;
import com.codeest.geeknews.util.ToastUtil;
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

public class SubActivity extends SimpleActivity {

    @BindView(R.id.et_topic)
    EditText et_topic;

    @BindView(R.id.tv_message)
    TextView tv_message;


    @BindView(R.id.btn_sub)
    Button btn_sub;

    private WebSocket mSocket;
    OkHttpClient okHttpClient;

    @Override
    protected int getLayout() {
        return R.layout.activity_ipfs_sub;
    }


    @Override
    protected void initEventAndData() {

    }

    @OnClick(R.id.btn_sub)
    public void subMessage() {
        if (TextUtils.isEmpty(et_topic.getText().toString().trim())) {
            ToastUtil.shortShow("et_topic 不能为空");
            return;
        }
//        String baseUrl = "http://127.0.0.1:5001/api/v0/";
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .connectTimeout(600, TimeUnit.SECONDS).
//                        readTimeout(600, TimeUnit.SECONDS).
//                        writeTimeout(600, TimeUnit.SECONDS)
//                .addInterceptor(mLoggingInterceptor)
//                .build();
//
//        Gson gson = new GsonBuilder()
//                .setLenient()
//                .create();
//        SubService subService = new Retrofit.Builder()
//                .client(okHttpClient)
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .baseUrl(baseUrl)
//                .build()
//                .create(SubService.class);
//        Observable<Sub> observable = subService.getSubDataByTopic("RussiaCup");
//
//
//        observable
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<Sub>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        LogUtil.d("onSubscribe");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        KLog.d(e.getMessage());
////                        LogUtil.d("onError");
//                    }
//
//                    @Override
//                    public void onNext(Sub sub) {
//                        KLog.d("onNext");
//                        // do anything with userDetail
////                        LogUtil.d("onNext");
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        LogUtil.d("onComplete");
//                    }
//                });

//        IpfsSubCommandService ipfsSubCommandService =  new IpfsSubCommandService();
//        Intent intent = new Intent(this,IpfsSubCommandService.class );
//        startService(intent);


        IpfsSubService ipfsSubService = new IpfsSubService();
        Intent intent = new Intent(this,IpfsSubService.class );
        startService(intent);


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
}
