package com.codeest.geeknews.ipfs;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import com.codeest.geeknews.R;
import com.codeest.geeknews.app.Constants;

/**
 * Created by zhengpingli on 2018/6/12.
 */

public class IPFSDaemonService extends IntentService {

    private NotificationManager nManager;
    private Process daemon;

    private static Context mContext;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
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
