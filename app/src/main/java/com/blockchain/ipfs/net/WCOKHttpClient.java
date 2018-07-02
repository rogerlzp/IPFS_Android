package com.blockchain.ipfs.net;


import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;



import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by zhengpingli on 2017/3/27.
 */

public class WCOKHttpClient extends OkHttpClient {

    private final static String TAG = WCOKHttpClient.class.getSimpleName();

    private static WCOKHttpClient mOkHttpClient;
    private Handler okHttpHandler;//全局处理子线程和M主线程通信
    public Context context = null;
    public static final int TYPE_GET = 0;//get请求
    public static final int TYPE_POST_JSON = 1;//post请求参数为json
    public static final int TYPE_POST_FORM = 2;//post请求参数为表单
    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder().
            connectTimeout(60, TimeUnit.SECONDS).
            readTimeout(60, TimeUnit.SECONDS).
            writeTimeout(60, TimeUnit.SECONDS).build();

    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    public WCOKHttpClient() {
    }

    public WCOKHttpClient(Context context) {
        this.context = context;
        okHttpHandler = new Handler(context.getMainLooper());
    }

    public static WCOKHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) {
            mOkHttpClient = new WCOKHttpClient();


        }
        return mOkHttpClient;
    }

    public static WCOKHttpClient getOkHttpClient(Context context) {

        if (mOkHttpClient == null) {
            mOkHttpClient = new WCOKHttpClient(context);
        }

        return mOkHttpClient;
    }

    private Request.Builder addHeaders() {
        Request.Builder builder = new Request.Builder()
                .addHeader("Connection", "keep-alive")
                .addHeader("appVersion", "1.0.0")
                .addHeader("Content-type", "application/json");
        return builder;
    }

    /**
     * 统一同意处理成功信息
     *
     * @param result
     * @param callBack
     * @param <T>
     */
    private <T> void successCallBack(final T result, final ReqCallBack<T> callBack) {
        okHttpHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.onReqSuccess(result);
                }
            }
        });
    }

    private <T> void failedCallBack(final String errorMsg, final ReqCallBack<T> callBack) {
        okHttpHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.onReqFailed(errorMsg);
                }
            }
        });
    }

    public <T> Call requestAsyn(String actionUrl, int requestType, HashMap<String, String> paramsMap, ReqCallBack<T> callBack) {
        return requestAsyn(false, actionUrl, requestType, paramsMap, callBack);
    }

    public <T> Call requestPostAsyn(String actionUrl, HashMap<String, String> urlsMap, String jsonMap,
                                    ReqCallBack<T> callBack) {
        Call call = null;
        call = requestPostByAsyn(actionUrl, urlsMap, jsonMap, callBack);
        return call;
    }

    public <T> Call requestAsyn(boolean stormFlag, String actionUrl, int requestType, HashMap<String, String> paramsMap,
                                ReqCallBack<T> callBack) {
        Call call = null;
        switch (requestType) {
            case TYPE_GET:
                call = requestGetByAsyn(actionUrl, paramsMap, callBack, stormFlag);
                break;
            case TYPE_POST_JSON:
                call = requestPostByAsyn(actionUrl, paramsMap, null, callBack);
                break;
            case TYPE_POST_FORM:
                call = requestPostByAsynWithForm(actionUrl, paramsMap, callBack);
                break;
        }
        return call;
    }

    public void requestSyn(String actionUrl, int requestType, HashMap<String, String> paramsMap) {
        switch (requestType) {
            case TYPE_GET:
                requestGetBySyn(actionUrl, paramsMap);
                break;
            case TYPE_POST_FORM:
                //      requestPostBySynWithForm(actionUrl, paramsMap);
                break;
        }
    }

    /**
     * okHttp get同步请求
     *
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     */
    private void requestGetBySyn(String actionUrl, HashMap<String, String> paramsMap) {
        StringBuilder tempParams = new StringBuilder();
        try {
            //处理参数
            int pos = 0;
            for (String key : paramsMap.keySet()) {
                if (pos > 0) {
                    tempParams.append("&");
                }
                //对参数进行URLEncoder
                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
                pos++;

            }
            //补全请求地址
            String requestUrl = String.format("%s?%s", actionUrl, tempParams.toString());
            //创建一个请求
            Request request = addHeaders().url(requestUrl).build();
            //创建一个Call
            final Call call = mOkHttpClient.newCall(request);
            //执行请求
            final Response response = call.execute();
            response.body().string();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    private <T> Call requestGetByAsyn(String actionUrl, HashMap<String, String> paramsMap, final ReqCallBack<T> callBack, final boolean stormFlag) {
        StringBuilder tempParams = new StringBuilder();
        try {
            int pos = 0;
            for (String key : paramsMap.keySet()) {
                if (pos > 0) {
                    tempParams.append("&");
                }
                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
                pos++;
            }
            String requestUrl = String.format("%s?%s", actionUrl, tempParams.toString());
            final Request request = addHeaders().url(requestUrl).build();
            final Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    failedCallBack("访问失败", callBack);
                    Log.e(TAG, e.toString());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {

                        if (stormFlag) {
                            // 读取string
                            byte[] imgaeByte = response.body().bytes();
                            successCallBack((T) imgaeByte, callBack);
                        } else {
//                            String string = response.body().string();
//                            Log.e(TAG, "response ----->" + string);
//                            JSONObject jsonObject = null;
//                            try {
//                                jsonObject = new JSONObject(string);
//                                String responseCode = jsonObject.optString(LTNConstants.RESULT_CODE);
//                                if (responseCode.equals(LTNConstants.MAG_SESSION)) {
//                                    if (mOkHttpClient.context != null) {
//                                        ActivityUtils.finishAll();
//                                        LTNApplication.getInstance().clearUser();
//                                        Intent intent = new Intent(mOkHttpClient.context, MainActivity.class);
//                                        intent.putExtra(LTNConstants.SESSION_KEY_TIME_OUT, true);
//                                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                                        mOkHttpClient.context.startActivity(intent);
//                                    }
//                                    return;
//                                }


//                            } catch (JSONException je) {
//                                Log.e(TAG, je.getMessage());
//                            }
                            successCallBack((T) response, callBack);
                        }
                    } else {
                        failedCallBack("服务器错误", callBack);
                    }
                }

            });
            return call;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return null;
    }

    private <T> Call requestPostByAsyn2(String actionUrl, HashMap<String, String> paramsMap, String jsonString, final ReqCallBack<T> callBack) {
        StringBuilder tempParams = new StringBuilder();
        try {
            int pos = 0;
            for (String key : paramsMap.keySet()) {
                if (pos > 0) {
                    tempParams.append("&");
                }
                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
                pos++;
            }
            String requestUrl = String.format("%s?%s", actionUrl, tempParams.toString());


            RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, jsonString);

            final Request request = addHeaders().url(actionUrl).post(body).build();
            final Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    failedCallBack("访问失败", callBack);
                    Log.e(TAG, e.toString());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String string = response.body().string();
                        Log.e(TAG, "response ----->" + string);
                        successCallBack((T) string, callBack);
                    } else {
                        failedCallBack("服务器错误", callBack);
                    }
                }
            });
            return call;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return null;
    }


  //  private <T> Call requestPostByAsyn(String actionUrl, HashMap<String, String> paramsMap, final ReqCallBack<T> callBack) {
  private <T> Call requestPostByAsyn(String actionUrl, HashMap<String, String> paramsMap,String jsonString, final ReqCallBack<T> callBack) {
      StringBuilder tempParams = new StringBuilder();
        try {
                int pos = 0;
                for (String key : paramsMap.keySet()) {
                    if (pos > 0) {
                        tempParams.append("&");
                    }
                    tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
                    pos++;
                }
                String requestUrl = String.format("%s?%s", actionUrl, tempParams.toString());

            RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, jsonString);

            final Request request = addHeaders().url(requestUrl). post(body).build();
            final Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    failedCallBack("访问失败", callBack);
                    Log.e(TAG, e.toString());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String string = response.body().string();
                        Log.e(TAG, "response ----->" + string);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(string);
//                            String responseCode = jsonObject.optString(LTNConstants.RESULT_CODE);
//                            if (responseCode.equals(LTNConstants.MAG_SESSION)) {
//                                if (mOkHttpClient.context != null) {
//                                    ActivityUtils.finishAll();
//                                    LTNApplication.getInstance().clearUser();
//                                    Intent intent = new Intent(mOkHttpClient.context, MainActivity.class);
//                                    intent.putExtra(LTNConstants.SESSION_KEY_TIME_OUT, true);
//                                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                                    mOkHttpClient.context.startActivity(intent);
//                                }
//                                return;
//                            }


                        } catch (JSONException je) {
                            Log.e(TAG, je.getMessage());
                        }
                        successCallBack((T) jsonObject, callBack);
                    } else {
                        failedCallBack("服务器错误", callBack);
                    }
                }
            });
            return call;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return null;
    }

    /**
     * okHttp post异步请求表单提交
     *
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     * @param callBack  请求返回数据回调
     * @param <T>       数据泛型
     * @return
     */
    private <T> Call requestPostByAsynWithForm(String actionUrl, HashMap<String, String> paramsMap, final ReqCallBack<T> callBack) {
        try {
            FormBody.Builder builder = new FormBody.Builder();
            for (String key : paramsMap.keySet()) {
                builder.add(key, paramsMap.get(key));
            }
            RequestBody formBody = builder.build();
            final Request request = addHeaders().url(actionUrl).post(formBody).build();
            final Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    failedCallBack("访问失败", callBack);
                    Log.e(TAG, e.toString());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String string = response.body().string();
                        Log.e(TAG, "response ----->" + string);
                        successCallBack((T) string, callBack);
                    } else {
                        failedCallBack("服务器错误", callBack);
                    }
                }
            });
            return call;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return null;
    }

}


