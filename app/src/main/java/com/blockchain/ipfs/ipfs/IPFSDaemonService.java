package com.blockchain.ipfs.ipfs;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;

import com.blockchain.ipfs.app.Constants;
import com.blockchain.ipfs.R;
import com.blockchain.ipfs.app.Constants;
import com.blockchain.ipfs.app.Constants;
import com.ipfs.api.IPFSAnroid;
import com.ipfs.api.entity.StatsBwEntity;

import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zhengpingli on 2018/6/12.
 */

public class IPFSDaemonService extends IntentService {

    private NotificationManager nManager;
    private Process daemon;

    private static Context mContext;

    Timer timer;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public IPFSDaemonService() {
        super("IPFSDaemonService");
    }


    public static void startStopIPFSDaemon(Context context, String action) {

        mContext = context;
        Intent intent = new Intent(context, IPFSDaemonService.class);
        intent.setAction(action);
        context.startService(intent);

    }

//    public void createStatus(){
//        timer = new Timer();
//        createNotificationChannel();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                new IPFSAnroid().stats().bw(new Callback<StatsBwEntity>() {
//                    @Override
//                    public void onResponse(Call<StatsBwEntity> call, Response<StatsBwEntity> response) {
//                        if (response != null) {
//                            NotificationManagerCompat.from(IPFSDaemonService.this).notify(1, notification("↑ " + ((int) response.body().getRateOut()) / 1024f + "kb/s" + "\r\n" + "↓ " + ((int) response.body().getRateIn()) / 1024f + "kb/s"));
//                            EventBus.getDefault().post(response.body());
//                        }
//                    }
//
//
//                    @Override
//                    public void onFailure(Call<StatsBwEntity> call, Throwable t) {
//                        if (timer != null) {
//                            timer.cancel();
//                        }
//                        StatsBwEntity statsBwEntity = new StatsBwEntity();
//                        statsBwEntity.setRateIn(0);
//                        statsBwEntity.setRateOut(0);
//                        EventBus.getDefault().post(statsBwEntity);
//                        NotificationManagerCompat.from(IPFSDaemonService.this).notify(1, notification("↑ " + 0 + "kb/s" + "\r\n" + "↓ " + 0 + "kb/s"));
//                        NotificationManagerCompat.from(IPFSDaemonService.this).cancel(1);
//                        stopService(new Intent(IPFSDaemonService.this, IPFSDaemonService.class));
//
//                    }
//                });
//            }
//        }, 0, 1000);
//    }
//


    public Notification notification(String arg) {
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
        return null;
    }


    /**
     * 创建通知栏
     */
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.app_name);
            String description = getString(R.string.app_name);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(getString(R.string.app_name), name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (Constants.IPFS_ACTION_START.equals(action)) {
                IPFSDaemon ipfsDaemon = new IPFSDaemon(mContext);
                //  ipfsDaemon.runCmd(Constants.IPFS_CMD_DAEMON); // 启动脚本

                ipfsDaemon.runCmd(Constants.IPFS_CMD_DAEMON_PUBSUB); // 启动脚本，加入pubsub 特性

                //返回BroadcastManager

            } else if (Constants.IPFS_ACTION_STOP.equals(action)) {
                // 关闭进程
                stopSelf();
            }
        }
    }


}
