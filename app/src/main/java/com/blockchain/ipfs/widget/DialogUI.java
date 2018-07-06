package com.blockchain.ipfs.widget;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Process;
import android.provider.Settings;
import android.view.Gravity;
import android.widget.Toast;

/**
 * @author: bobo create date: 2014-12-23 下午02:36:57
 */

public class DialogUI {
    /**
     * 弹出框，设置网络
     *
     * @param act
     * @param message
     */
    public static void showSettingNetwork(final Activity act, String message, final boolean isStartup) {
        new AlertDialog.Builder(act).setTitle("提示").setMessage(message)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        act.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                        if (isStartup) {
                            act.finish();
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (isStartup) {
                            act.finish();
                        }
                    }
                }).show();
    }

    /**
     * 弹出设置GPS开关的对话框
     *
     * @param act
     * @param message
     * @param isStartup
     */
    public static void showGpsSecuritySetting(final Activity act, String message, final boolean isStartup) {
        new AlertDialog.Builder(act).setTitle("提示").setMessage(message)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                    Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        act.startActivityForResult(intent, 0); //此为设置完成后返回到获取界面
                        if (isStartup) {
                            act.finish();
                        }
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (isStartup) {
                    act.finish();
                }
            }
        }).show();
    }


    /**
     * 弹出退出系统确认框
     *
     * @param act
     * @param imei
     */
    public static void showSystemProcessExit(final Activity act, final String imei) {
        Dialog dialog = new AlertDialog.Builder(act).setTitle("提示")
                .setMessage("确定退出系统吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        act.finish();
                        try {
                            Process.killProcess(Process.myPid());
                        } catch (Exception e) {
                            System.out.println("e.getMessage() = " + e.getMessage());
                        }
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                    }
                }).create();
        dialog.show();
    }

    /**
     * 弹出退出登录系统确认框
     *
     * @param act
     * @param imei
     */
    public static void showAppLogoutExit(final Activity act, final String imei) {
        Dialog dialog = new AlertDialog.Builder(act).setTitle("提示")
                .setMessage("确定退出登录吗？")



                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        act.finish();
                        try {
                            Process.killProcess(Process.myPid());
                        } catch (Exception e) {
                            System.out.println("e.getMessage() = " + e.getMessage());
                        }



                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                    }
                }).create();
        dialog.show();
    }

    /**
     * 弹出系统确认框
     *
     * @param act
     * @param title
     * @param message
     */
    public static void showConfirmExit(final Activity act, String title, String message) {
        Dialog dialog = new AlertDialog.Builder(act).setTitle(title)
                .setMessage(message)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        act.finish();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        act.finish();
                    }
                }).create();
        dialog.show();
    }

    /**
     * 弹出系统确认框
     *
     * @param act
     * @param title
     * @param message
     */
    public static void showConfirm(final Activity act, String title, String message) {
        Dialog dialog = new AlertDialog.Builder(act).setTitle(title)
                .setMessage(message)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
        dialog.show();
    }

    /**
     * 弹出系统确认框
     *
     * @param act
     * @param title
     * @param message
     */
    public static void showAlert(Activity act, String title, String message) {
        if (act.isFinishing()) {
            return;
        }
        new AlertDialog.Builder(act).setTitle(title).setMessage(message)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    /**
     * 弹出系统确认框
     *
     * @param context
     * @param title
     * @param message
     * @param iconId
     */
    public static void showAlert(Context context, int title, int message, int iconId) {
        new AlertDialog.Builder(context)
                .setIcon(iconId)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("关闭", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    /**
     * 弹出退出系统确认框，并退出Activity
     *
     * @param act
     * @param title
     * @param message
     */
    public static void showAlertExitActivity(final Activity act, String title, String message) {
        new AlertDialog.Builder(act).setTitle(title).setMessage(message)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!act.isFinishing()) {
                            act.finish();
                        }
                    }
                }).show();
    }

    /**
     * 弹出提示框，5秒后消失
     *
     * @param context
     * @param str
     */
    public static void showToastLong(Context context, String str) {
        Toast toast = Toast.makeText(context, str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }

    /**
     * 弹出提示框，5秒后消失
     *
     * @param context
     * @param str
     */
    public static void showToastShort(Context context, String str) {
        Toast toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }

}
