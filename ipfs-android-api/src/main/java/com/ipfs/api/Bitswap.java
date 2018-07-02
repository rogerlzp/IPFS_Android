package com.ipfs.api;




import com.ipfs.api.entity.BitswapStat;
import com.ipfs.api.service.CommandService;

import retrofit2.Call;
import retrofit2.Retrofit;

public class Bitswap {
    Retrofit retrofit;

    public Bitswap(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public void ledger() {

    }

    public void reprovide() {

    }
    public void stat(retrofit2.Callback<BitswapStat> callback) {
//        CommandService.bitswap commandService = retrofit.create(CommandService.bitswap.class);
//        Call<BitswapStat> bitswap_statCall = commandService.stat();
//        bitswap_statCall.enqueue(callback);

    }
    public void unwant() {

    }
    public void wantlist() {

    }
}
