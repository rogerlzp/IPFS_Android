package com.ipfs.api.service;

import com.ipfs.api.entity.StatsBwEntity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class StatsService {

    Retrofit retrofit;

    public StatsService(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public void bitswap() {
    }


    // http://127.0.0.1:5001/api/v0/stats/bw
    public void bw(Callback<StatsBwEntity> callback) {
        CommandService.Stats stats = retrofit.create(CommandService.Stats.class);
        Call<StatsBwEntity> bw = stats.bw();
        bw.enqueue(callback);
    }

    public void repo() {
    }

}
