package com.codeest.geeknews.ui.ipfs.activity;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.codeest.geeknews.app.Constants;
import com.codeest.geeknews.ipfs.IPFSDaemon;
import com.codeest.geeknews.ipfs.IPFSDaemonAsyn;
import com.codeest.geeknews.util.LogUtil;
import com.codeest.geeknews.util.ToastUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zhengpingli on 2018/6/19.
 * <p>
 * 通过ipfs pubsub sub命令来获取值，
 * TODO: 后续再根据Java链接来获取
 */

public class IpfsSubCommandService extends Service {
    Timer timer;

    public IpfsSubCommandService() {
    }


    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.d("create in IpfsSubCommandService");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {


                LogUtil.d("onStartCommand in IpfsSubCommandService");
                IPFSDaemonAsyn ipfsDaemon = new IPFSDaemonAsyn(IpfsSubCommandService.this);
                //  ipfsDaemon.runCmd(Constants.IPFS_CMD_DAEMON); // 启动脚本

                String result = ipfsDaemon.runCmd(Constants.IPFS_CMD_PUBSUB_SUB + " RussiaCup"); // 启动脚本，加入pubsub 特性

                ToastUtil.show("get message " + result);
                //返回BroadcastManager
            }
        }, 0, 1000);
        return super.onStartCommand(intent, flags, startId);


    }
}
