package com.ipfs.api;

import com.ipfs.api.entity.NameEntity;
import com.ipfs.api.entity.Pub;
import com.ipfs.api.service.CommandService;

import retrofit2.Call;
import retrofit2.Retrofit;


public class NameService {
    Retrofit retrofit;


    // "name/publish?arg=/ipfs/" + hash + id.map(name -> "&key=" + name).orElse(""));
    // http://127.0.0.1:5001/api/v0/name/publish?arg=/ipfs/QmcmphYYdhm7MWwaSCnRmdpG6R5GyWrueyY5ZBNB2EG7xv


    public NameService(Retrofit retrofit) {
        this.retrofit = retrofit;
    }


    public void publish(retrofit2.Callback<NameEntity> callback, String hash) {
        CommandService.Name name = retrofit.create(CommandService.Name.class);
        Call<NameEntity> get = name.publish(hash);
        get.enqueue(callback);
    }

    public void resolve() {

    }
}
