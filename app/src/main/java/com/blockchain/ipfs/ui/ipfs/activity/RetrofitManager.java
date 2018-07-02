package com.blockchain.ipfs.ui.ipfs.activity;


import android.app.Activity;
import android.util.SparseArray;



import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


/**
 * ClassName: RetrofitManager<p>
 * Author: oubowu<p>
 * Fuction: Retrofit请求管理类<p>
 * CreateDate:2016/2/13 20:34<p>
 * UpdateUser:<p>
 * UpdateDate:<p>
 */
public class RetrofitManager {
//
//    // 设缓存有效期为两天
//    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;
//    // 30秒内直接读缓存
//    private static final long CACHE_AGE_SEC = 0;
//
//    private static volatile OkHttpClient sOkHttpClient;
//    // 管理不同HostType的单例
//    private static SparseArray<RetrofitManager> sInstanceManager = new SparseArray<>(HostType.TYPE_COUNT);
//    private NewsService mNewsService;
//    // 云端响应头拦截器，用来配置缓存策略
//    private Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//            Request request = chain.request();
//            // 在这里统一配置请求头缓存策略以及响应头缓存策略
//            if (NetUtil.isConnected(App.getContext())) {
//                // 在有网的情况下CACHE_AGE_SEC秒内读缓存，大于CACHE_AGE_SEC秒后会重新请求数据
//                request = request.newBuilder().removeHeader("Pragma").removeHeader("Cache-Control").header("Cache-Control", "public, max-age=" + CACHE_AGE_SEC).build();
//                Response response = chain.proceed(request);
//                return response.newBuilder().removeHeader("Pragma").removeHeader("Cache-Control").header("Cache-Control", "public, max-age=" + CACHE_AGE_SEC).build();
//            } else {
//                // 无网情况下CACHE_STALE_SEC秒内读取缓存，大于CACHE_STALE_SEC秒缓存无效报504
//                request = request.newBuilder().removeHeader("Pragma").removeHeader("Cache-Control")
//                        .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC).build();
//                Response response = chain.proceed(request);
//                return response.newBuilder().removeHeader("Pragma").removeHeader("Cache-Control")
//                        .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC).build();
//            }
//
//        }
//    };
//
//    // 打印返回的json数据拦截器
//    private Interceptor mLoggingInterceptor = new Interceptor() {
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//
//            Request request = chain.request();
//            Request.Builder requestBuilder = request.newBuilder();
//            requestBuilder.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
//            //添加UUID
//            requestBuilder.addHeader(Constant.UUID, LoginUtils.getSessionKey());
//            request = requestBuilder.build();
//            RequestBody requestBody = request.body();
//            Buffer bufferRequest = new Buffer();
//            requestBody.writeTo(bufferRequest);
//            Charset charsetRequest = Charset.forName("UTF-8");
//            MediaType contentTypeRequest = requestBody.contentType();
//            if (contentTypeRequest != null) {
//                charsetRequest = contentTypeRequest.charset(charsetRequest);
//            }
//
//            String bodyStr = null;
//            if (isPlaintext(bufferRequest)) {
//                bodyStr = bufferRequest.readString(charsetRequest);
//
//            }
//            final Response response = chain.proceed(request);
//
//            KLog.e("请求网址: \n" + request.url() + " \n " + "请求头部信息：\n" + request.headers() + "请求body信息：\n" + bodyStr + "\n响应头部信息：\n" + response.headers());
//
//            final ResponseBody responseBody = response.body();
//            final long contentLength = responseBody.contentLength();
//            BufferedSource source = responseBody.source();
//            source.request(Long.MAX_VALUE); // Buffer the entire body.
//            Buffer buffer = source.buffer();
//
//            Charset charset = Charset.forName("UTF-8");
//            MediaType contentType = responseBody.contentType();
//            if (contentType != null) {
//                try {
//                    charset = contentType.charset(charset);
//                } catch (UnsupportedCharsetException e) {
//                    KLog.e("");
//                    KLog.e("Couldn't decode the response body; charset is likely malformed.");
//                    return response;
//                }
//
//
//            }
//
//            if (contentLength != 0) {
//                KLog.e("--------------------------------------------开始打印返回数据----------------------------------------------------");
//                KLog.e(buffer.clone().readString(charset));
//                KLog.e("--------------------------------------------结束打印返回数据----------------------------------------------------");
//            }
//
//            return response;
//        }
//    };
//
//    static boolean isPlaintext(Buffer buffer) throws EOFException {
//        try {
//            Buffer prefix = new Buffer();
//            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
//            buffer.copyTo(prefix, 0, byteCount);
//            for (int i = 0; i < 16; i++) {
//                if (prefix.exhausted()) {
//                    break;
//                }
//                int codePoint = prefix.readUtf8CodePoint();
//                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
//                    return false;
//                }
//            }
//            return true;
//        } catch (EOFException e) {
//            return false; // Truncated UTF-8 sequence.
//        }
//    }
//
//    private RetrofitManager() {
//    }
//
//    private RetrofitManager(@HostType.HostTypeChecker int hostType) {
//
//        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.getHost(hostType)).client(getOkHttpClient()).addConverterFactory(JacksonConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
//        mNewsService = retrofit.create(NewsService.class);
//    }
//
//    /**
//     * 获取单例
//     *
//     * @param hostType host类型
//     * @return 实例
//     */
//    public static RetrofitManager getInstance(int hostType) {
//        RetrofitManager instance = sInstanceManager.get(hostType);
//        if (instance == null) {
//            instance = new RetrofitManager(hostType);
//            sInstanceManager.put(hostType, instance);
//            return instance;
//        } else {
//            return instance;
//        }
//    }
//
//    // 配置OkHttpClient
//    private OkHttpClient getOkHttpClient() {
//        if (sOkHttpClient == null) {
//            synchronized (RetrofitManager.class) {
//                if (sOkHttpClient == null) {
//                    // OkHttpClient配置是一样的,静态创建一次即可
//                    // 指定缓存路径,缓存大小100Mb
//                    Cache cache = new Cache(new File(App.getContext().getCacheDir(), "HttpCache"), 1024 * 1024 * 100);
//
//                    sOkHttpClient = new OkHttpClient.Builder().cache(cache).addNetworkInterceptor(mRewriteCacheControlInterceptor)
//                            .addInterceptor(mRewriteCacheControlInterceptor).addInterceptor(mLoggingInterceptor).retryOnConnectionFailure(true)
//                            .connectTimeout(30, TimeUnit.SECONDS).build();
//
//                }
//            }
//        }
//        return sOkHttpClient;
//    }
//
//    //登录
//    public Observable<LoginData> login(Map<String, String> params) {
//        return mNewsService.login(params)
//                .map(new BaseResultTransformer<LoginData>())
//                .compose(new BaseSchedulerTransformer<LoginData>());
//    }

}
