package com.codeest.geeknews.ui.ipfs.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.codeest.geeknews.R;
import com.codeest.geeknews.net.ReqCallBack;
import com.codeest.geeknews.net.WCOKHttpClient;
import com.codeest.geeknews.util.LogUtil;
import com.codeest.geeknews.util.ToastUtil;
import com.ipfs.api.IPFSAnroid;
import com.ipfs.api.entity.Sub;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zhengpingli on 2018/6/15.
 */

public class IpfsSubService extends Service {

    Timer timer;
    String topic;
    String readLine="";

    public IpfsSubService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
//      intent  topic =
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        timer = new Timer();


        //   IPFSAnroid ipfsAnroid = new IPFSAnroid("13", "213");
//        createNotificationChannel();
        String path = "http://127.0.0.1:5001/api/v0/pubsub/sub?arg=RussiaCup";
        StringBuffer buffer = new StringBuffer();
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setDoOutput(false);
            connection.setDoInput(true);

            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);


            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        //获取响应状态
                        connection.connect();

                        int responseCode = connection.getResponseCode();
                        if (HttpURLConnection.HTTP_OK == responseCode) { //连接成功
                            //当正确响应时处理数据


                            BufferedReader responseReader;//处理响应流
                            responseReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                            while ((readLine = responseReader.readLine()) != null) {
                                Handler handler = new Handler(Looper.getMainLooper());
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(IpfsSubService.this,"get result" +readLine,Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            responseReader.close();
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException me) {

                    }


//
                }
            }, 0, 1000);
        } catch (MalformedURLException me) {
            me.printStackTrace();
        } catch (ProtocolException pe) {
            pe.printStackTrace();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
        return super.

                onStartCommand(intent, flags, startId);

    }

//    public Notification notification(String arg) {
//        Intent intent = new Intent(this, CmdIntentService.class);
//        intent.putExtra(CmdIntentService.EXTRA_EXEC, CmdIntentService.EXEC_TYPE.shutdown);
//        PendingIntent service = PendingIntent.getService(this, 200, intent, 0);
//        Intent main = new Intent(this, MainActivity.class);
//        return new NotificationCompat.Builder(this, getString(R.string.app_name))
//                .setStyle(new NotificationCompat.BigTextStyle().bigText("" + arg))
//                .setSmallIcon(R.mipmap.ic_)
//                .setContentTitle("IpfsBox is running")
//                .setWhen(0)
//                .setColor(Color.GREEN)
//                .setOngoing(true)
//                .setContentIntent(PendingIntent.getActivity(this, 0, main, 0))
////                .setTicker("ticker")
//                .addAction(0, "stop", service).setOnlyAlertOnce(true)
//                .build();
//    }

    //    private void createNotificationChannel() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = getString(R.string.app_name);
//            String description = getString(R.string.app_name);
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel(getString(R.string.app_name), name, importance);
//            channel.setDescription(description);
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//        }
//    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }


}

