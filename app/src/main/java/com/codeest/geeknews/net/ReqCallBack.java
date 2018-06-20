package com.codeest.geeknews.net;

/**
 * Created by zhengpingli on 2017/3/27.
 */

public interface ReqCallBack<T> {
    /**
     * 响应成功
     */
    void onReqSuccess(T result);

    /**
     * 响应失败
     */
    void onReqFailed(String errorMsg);
}