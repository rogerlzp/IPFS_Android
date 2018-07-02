package com.blockchain.ipfs.ui.ipfs.activity;

import com.ipfs.api.entity.Sub;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SubService {

    @GET("pubsub/sub")
    Observable<Sub> getSubDataByTopic(@Query("arg") String topic);

}

