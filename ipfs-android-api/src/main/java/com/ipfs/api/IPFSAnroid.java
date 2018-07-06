package com.ipfs.api;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ipfs.api.converter.StringConverterFactory;
import com.ipfs.api.entity.FileGet;
import com.ipfs.api.entity.Id;
import com.ipfs.api.entity.NodeCat;
import com.ipfs.api.entity.Version;
import com.ipfs.api.service.BootStrapService;
import com.ipfs.api.service.CommandService;
import com.ipfs.api.service.StatsService;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by zhengpingli on 2018/6/13.
 */

public class IPFSAnroid {

    Retrofit retrofit;

    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder().
            connectTimeout(300, TimeUnit.SECONDS).
            readTimeout(300, TimeUnit.SECONDS).
            writeTimeout(300, TimeUnit.SECONDS).build();

    Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    /**
     * default url
     */
    private String gateway = "http://127.0.0.1:8080";
    private String baseUrl = "http://127.0.0.1:5001";

    public IPFSAnroid() {
        this("http://127.0.0.1:5001");
    }

    public IPFSAnroid(String baseUrl) {
        retrofit = new Retrofit.Builder().baseUrl(baseUrl + "/api/v0/")
                .client(okHttpClient)

                .addConverterFactory(GsonConverterFactory.create(gson)).build();
    }

//    public IPFSAnroid(String baseUrl, String stringConverter) {
//        retrofit = new Retrofit.Builder().baseUrl(this.baseUrl + "/api/v0/")
//                .client(okHttpClient)
//                .addConverterFactory(StringConverterFactory.create()).build();
//    }

    public IPFSAnroid(String gateway, String prefix) {
        retrofit = new Retrofit.Builder().baseUrl(gateway + "/api/v0/").addConverterFactory(GsonConverterFactory.create(gson)).build();
    }

    public void version(retrofit2.Callback<Version> callback) {
        CommandService commandService = retrofit.create(CommandService.class);
        Call<Version> version = commandService.version();
        version.enqueue(callback);
    }

    public void id(retrofit2.Callback<Id> callback) {
        CommandService commandService = retrofit.create(CommandService.class);
        Call<Id> idCall = commandService.id();
        idCall.enqueue(callback);
    }

    public void get(retrofit2.Callback<FileGet> callback, String hash) {
        //IPFS 获取文件有问题，不能用http://127.0.0.1:5001/api/v0/get?hash=sxx 这种方式来获取文件
        CommandService commandService = retrofit.create(CommandService.class);
        Call<FileGet> get = commandService.get(hash);
        get.enqueue(callback);
    }


    public void catNode(retrofit2.Callback<List<NodeCat>> callback, String hash) {
        //IPFS 获取文件有问题，不能用http://127.0.0.1:5001/api/v0/get?hash=sxx 这种方式来获取文件
        CommandService commandService = retrofit.create(CommandService.class);
        Call<List<NodeCat>> node = commandService.catNode(hash);
        node.enqueue(callback);
    }


    public void add(retrofit2.Callback callback, File file, String fileName) {
        CommandService commandService = retrofit.create(CommandService.class);

        MultipartBody.Builder builder = new MultipartBody.Builder();
        try {
            String encodedFileName = URLEncoder.encode(fileName, "UTF-8");

            Headers headers = Headers.of("Content-Disposition", "file; filename=\"" + encodedFileName + "\"",
                    "Content-Transfer-Encoding", "binary");

            if (file.isDirectory()) {
                // add directory
                builder.addPart(headers, RequestBody.create(MediaType.parse("application/x-directory"), ""));
                // add files and subdirectories
                for (File f : file.listFiles()) {
//                    addFile(builder, f, f.name, filename + "/" + f.name);
                }
            } else {
                builder.addPart(headers, RequestBody.create(MediaType.parse("application/octet-stream"), file));
            }
//            File file = new File(path);
//            RequestBody requestBody = RequestBody.create(MediaType.parse("video/mp4"), file);
//            MultipartBody.Part data = MultipartBody.Part.createFormData("data", file.getName(), requestBody);

            builder.setType(MultipartBody.FORM);
            MultipartBody request = builder.build();

            MultipartBody.Part data = request.part(0);


//            return   new Request.Builder().url(url).post(body).build();
            Call add = commandService.add(data);

            add.enqueue(callback);
        } catch (UnsupportedEncodingException encodingEx) {

        }
    }

    public void catFile(retrofit2.Callback<ResponseBody> callback, String hash) {
        //IPFS 获取文件有问题，不能用http://127.0.0.1:5001/api/v0/get?hash=sxx 这种方式来获取文件
        CommandService commandService = retrofit.create(CommandService.class);
        Call<ResponseBody> node = commandService.catFile(hash);
        node.enqueue(callback);
    }


    public Pubsub pubsub() {
        return new Pubsub(retrofit);
    }

    public SwarmService swarm() {
        return new SwarmService(retrofit);
    }

    public NameService name() {
        return new NameService(retrofit);
    }


    public StatsService stats() {
        return new StatsService(retrofit);
    }

    public BootStrapService bootstrap() {
        return new BootStrapService(retrofit);
    }

}
