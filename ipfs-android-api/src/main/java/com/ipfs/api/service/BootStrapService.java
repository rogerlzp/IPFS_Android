package com.ipfs.api.service;

import com.ipfs.api.entity.BootStrapEntity;
import com.ipfs.api.entity.StatsBwEntity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class BootStrapService {
    Retrofit retrofit;

    public BootStrapService(Retrofit retrofit) {
        this.retrofit = retrofit;
    }


    // http://127.0.0.1:5001/api/v0/bootstrap/add?arg=
    public void add(Callback<BootStrapEntity> callback, String peer) {
        CommandService.Bootstrap bootstrap = retrofit.create(CommandService.Bootstrap.class);
        Call<BootStrapEntity> addCall = bootstrap.add(peer);
        addCall.enqueue(callback);
    }

    public void rm(Callback<BootStrapEntity> callback, String peer) {
        CommandService.Bootstrap bootstrap = retrofit.create(CommandService.Bootstrap.class);
        Call<BootStrapEntity> addCall = bootstrap.rm(peer);
        addCall.enqueue(callback);
    }

    public void list(Callback<BootStrapEntity> callback) {
        CommandService.Bootstrap bootstrap = retrofit.create(CommandService.Bootstrap.class);
        Call<BootStrapEntity> addCall = bootstrap.list();
        addCall.enqueue(callback);
    }

}

