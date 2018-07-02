package com.ipfs.api.service;

import com.ipfs.api.entity.FileAdd;
import com.ipfs.api.entity.FileGet;
import com.ipfs.api.entity.Id;
import com.ipfs.api.entity.NameEntity;
import com.ipfs.api.entity.NodeCat;
import com.ipfs.api.entity.Pub;
import com.ipfs.api.entity.StatsBwEntity;
import com.ipfs.api.entity.Sub;
import com.ipfs.api.entity.SwarmEntity;
import com.ipfs.api.entity.Version;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by zhengpingli on 2018/6/13.
 */

public interface CommandService {
    @POST("add")
    @Multipart
    Call<FileAdd> add(@Part MultipartBody.Part part);

    @GET("version")
    Call<Version> version();

    @GET("id")
    Call<Id> id();

    @GET("get")
    Call<FileGet> get(@Query("hash") String hash);

    @GET("cat")
    Call<List<NodeCat>> catNode(@Query("arg") String hash);



    interface Stats {
        @GET("stats/bw")
        Call<StatsBwEntity> bw();
    }

    interface Pubsub {
        // http://127.0.0.1:5001/api/v0/pubsub/pub?arg=RussiaCup3&arg=data
        //必须用GET，而不能是POST
        @GET("pubsub/pub")
        Call<Void> pub(@Query("arg") String arg1, @Query("arg") String arg2);

        // http://127.0.0.1:5001/api/v0/pubsub/sub?arg=RussiaCup3
//        @GET("pubsub/sub")
//        Call<Sub> sub(@Query("arg") String arg);
//        @GET("stats/bw")
//        Call<SwarmEntity.connect> connect(@Query("arg") String arg);

//        @GET("pubsub/sub")
//        Call<String> sub(@Query("arg") String arg);

        @GET("pubsub/sub")
        Observable<Sub> sub(@Query("arg") String arg);
    }


    interface Swarm {
        @GET("swarm/connect")
        Call<SwarmEntity.Connect> connect(@Query("arg") String arg);
    }

    interface Name {
        // http://127.0.0.1:5001/api/v0/pubsub/pub?arg=RussiaCup3&arg=data
        //必须用GET，而不能是POST
        @GET("name/publish")
        Call<NameEntity> publish(@Query("arg") String arg1);
    }

}