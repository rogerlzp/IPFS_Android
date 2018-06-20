package com.ipfs.api;

import com.ipfs.api.entity.FileGet;
import com.ipfs.api.entity.Pub;
import com.ipfs.api.entity.Sub;
import com.ipfs.api.service.CommandService;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by zhengpingli on 2018/6/15.
 */

public class Pubsub {
    Retrofit retrofit;

    public Pubsub(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public void ls() {
    }

    public void peers() {
    }

    /**
     * http://127.0.0.1:5001/api/v0/pubsub/pub?arg=RussiaCup&arg=123
     */
    public void pub(retrofit2.Callback<Void> callback, String topic, String data) {
        CommandService.Pubsub pubsub = retrofit.create(CommandService.Pubsub.class);
        Call<Void> get = pubsub.pub(topic, data);
        get.enqueue(callback);
    }


//    public void sub(retrofit2.Callback<Sub> callback, String topic) {
//        CommandService.Pubsub pubsub = retrofit.create(CommandService.Pubsub.class);
//        Call<Sub> get = pubsub.sub(topic);
//        get.enqueue(callback);
//    }
    public Observable<Sub>  sub(String topic) {
        CommandService.Pubsub pubsub = retrofit.create(CommandService.Pubsub.class);
        Observable<Sub> subObservable = pubsub.sub(topic);
        return  subObservable;
//        get.enqueue(callback);
    }

}
