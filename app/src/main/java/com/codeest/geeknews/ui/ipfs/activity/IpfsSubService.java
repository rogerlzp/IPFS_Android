package com.codeest.geeknews.ui.ipfs.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;

import com.codeest.geeknews.R;
import com.codeest.geeknews.util.ToastUtil;
import com.ipfs.api.IPFSAnroid;
import com.ipfs.api.entity.Sub;

import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zhengpingli on 2018/6/15.
 */

public class IpfsSubService extends Service {

    Timer timer;
    String topic;

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
        IPFSAnroid ipfsAnroid = new IPFSAnroid();
//        createNotificationChannel();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                ipfsAnroid.pubsub().sub(new Callback<Sub>() {
                                            @Override
                                            public void onResponse(Call<Sub> call, Response<Sub> response) {
                                                ToastUtil.show("pub succeed");
                                                if(response.body() !=null) {
                                                    ToastUtil.show(response.body().getData());
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<Sub> call, Throwable t) {

                                            }
                                        }, "RussiaCup"
                );
//                new IpfsBox().stats().bw(new Callback<Stats_bw>() {
//                    @Override
//                    public void onResponse(Call<Stats_bw> call, Response<Stats_bw> response) {
//                        if (response != null) {
//                            NotificationManagerCompat.from(BwService.this).notify(1, notification("↑ " + ((int) response.body().getRateOut()) / 1024f + "kb/s" + "\r\n" + "↓ " + ((int) response.body().getRateIn()) / 1024f + "kb/s"));
//                            EventBus.getDefault().post(response.body());
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<Stats_bw> call, Throwable t) {
//                        if (timer != null) {
//                            timer.cancel();
//                        }
//                        Stats_bw stats_bw = new Stats_bw();
//                        stats_bw.setRateIn(0);
//                        stats_bw.setRateOut(0);
//                        EventBus.getDefault().post(stats_bw);
//                        NotificationManagerCompat.from(BwService.this).notify(1, notification("↑ " + 0 + "kb/s" + "\r\n" + "↓ " + 0 + "kb/s"));
//                        NotificationManagerCompat.from(BwService.this).cancel(1);
//                        stopService(new Intent(BwService.this, BwService.class));
//                        LogUtils.e(t.getMessage() + "false");
//
//                    }
//                });
            }
        }, 0, 1000);
        return super.onStartCommand(intent, flags, startId);
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

