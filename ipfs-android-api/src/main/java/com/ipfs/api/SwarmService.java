package com.ipfs.api;



import com.ipfs.api.entity.SwarmEntity;
import com.ipfs.api.service.CommandService;

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

    public void peers() {
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
