package com.ipfs.api;


import com.ipfs.api.entity.PeersEntity;
import com.ipfs.api.entity.SwarmEntity;
import com.ipfs.api.service.CommandService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class SwarmService {
    Retrofit retrofit;

    public SwarmService(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public void connect(Callback<SwarmEntity.Connect> callback, String path) {
        CommandService.Swarm swarm = retrofit.create(CommandService.Swarm.class);
        swarm.connect(path).enqueue(callback);
    }

    public void disconnect() {
    }

    //result
    // {"Peers":[{"Addr":"/ip4/1.202.83.200/tcp/52612",
    // "Peer":"QmeE8FjxEW7yBuuPEM9gZSMoMzuRv7pvy3ULV4pvWh6V1m",
    // "Latency":"",
    // "Muxer":"*sm_yamux.conn",
    // "Streams":null},
    // {"Addr":"/ip4/1.215.232.107/tcp/4001","Peer":"QmZSPPHZgpNznyGRedNLX3ZzU2iKK15BCyBhSopAQkJNjT","Latency":"","Muxer":"*sm_yamux.conn","Streams":null},{"Addr":"/ip
    public void peers(Callback<PeersEntity> callback) {
        CommandService.Swarm peers = retrofit.create(CommandService.Swarm.class);
        Call<PeersEntity> getPeers = peers.peers();
        getPeers.enqueue(callback);
    }

    public addrs addrs() {
        return new addrs(retrofit);
    }

    public filters filters() {
        return new filters(retrofit);
    }

    public class addrs {
        Retrofit retrofit;

        public addrs(Retrofit retrofit) {
            this.retrofit = retrofit;
        }

        public void listen() {
        }

        public void local() {
        }
    }

    public class filters {
        Retrofit retrofit;

        public filters(Retrofit retrofit) {
            this.retrofit = retrofit;
        }

        public void add() {
        }

        public void rm() {
        }
    }
}
