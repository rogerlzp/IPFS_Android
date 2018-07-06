package com.blockchain.ipfs.ui.ipfs.fragment;


import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.blockchain.ipfs.app.App;
import com.blockchain.ipfs.app.Constants;
import com.blockchain.ipfs.base.RootFragment;
import com.blockchain.ipfs.base.contract.ipfs.IpfsContract;
import com.blockchain.ipfs.ipfs.IPFSDaemon;
import com.blockchain.ipfs.ipfs.IPFSDaemonService;
import com.blockchain.ipfs.model.bean.ImageBean;
import com.blockchain.ipfs.model.bean.WXItemBean;
import com.blockchain.ipfs.ui.ipfs.activity.ChannelListActivity;
import com.blockchain.ipfs.ui.ipfs.activity.GetFileActivity;
import com.blockchain.ipfs.ui.ipfs.activity.IpfsBootstrapAddActivity;
import com.blockchain.ipfs.ui.ipfs.activity.IpfsSwarmPeersActivity;
import com.blockchain.ipfs.ui.ipfs.activity.PubActivity;
import com.blockchain.ipfs.ui.ipfs.activity.PubNodeActivity;
import com.blockchain.ipfs.ui.ipfs.activity.SubActivity;
import com.blockchain.ipfs.ui.ipfs.activity.SwarmActivity;
import com.blockchain.ipfs.ui.ipfs.event.DaemonEvent;
import com.blockchain.ipfs.util.PermissionUtil;
import com.blockchain.ipfs.R;
import com.blockchain.ipfs.presenter.ipfs.IpfsPresenter;
import com.blockchain.ipfs.ui.ipfs.activity.IpfsImageListActivity;
//import com.hwangjr.rxbus.RxBus;
import com.blockchain.ipfs.util.ToastUtil;
import com.ipfs.api.IPFSAnroid;
import com.ipfs.api.entity.FileAdd;
import com.ipfs.api.entity.StatsBwEntity;
import com.ipfs.api.entity.Version;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
//import io.realm.RealmObjectSchema;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by zhengpingli on 2018/6/12.
 */

public class IpfsFragment extends RootFragment<IpfsPresenter> implements IpfsContract.View {

    @BindView(R.id.view_main)
    RecyclerView rvWechatList;

    @BindView(R.id.btn_checkIpfs)
    Button btn_checkIpfs;
    @BindView(R.id.btn_start_ipfs)
    Button btn_start_ipfs;
    @BindView(R.id.tv_ipfs_status)
    EditText tv_ipfs_status;

    @BindView(R.id.btn_check_Ipfs_version)
    Button btn_check_Ipfs_version;

    @BindView(R.id.btn_checkID)
    Button btn_checkID;

    @BindView(R.id.btn_upload)
    Button btn_upload;


    @BindView(R.id.btn_swarm_peers)
    Button btn_swarm_peers;

    @BindView(R.id.btn_file_get)
    Button btn_file_get;

    @BindView(R.id.btn_ipfs_channel_node)
    Button btn_ipfs_channel_node;

    @BindView(R.id.indeterminate_progress_small_library)
    ProgressBar indeterminateProgressSmall;

    @BindView(R.id.btn_choose)
    Button btn_choose;

    @BindView(R.id.tv_filename)
    TextView tv_filename;


    @BindView(R.id.tv_ipfs_stats)
    TextView tv_ipfs_stats;

    @BindView(R.id.btn_check_image)
    Button btn_check_image;

    @BindView(R.id.btn_pub)
    Button btn_pub;
    @BindView(R.id.btn_sub)
    Button btn_sub;

    @BindView(R.id.btn_swarm)
    Button btn_swarm;

    @BindView(R.id.btn_pub_node)
    Button btn_pub_node;


    @BindView(R.id.btn_bootstrap_add)
    Button btn_bootstrap_add;


    @BindView(R.id.et_desc)
    EditText et_desc;

    @BindView(R.id.et_price)
    EditText et_price;

    String filePath = "";
    Timer timer;


    private BroadcastReceiver ipfsDaemonReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == Constants.IPFS_ACTION_START) {
                //  isIPFSDaemonRunning = true;
            } else if (intent.getAction() == Constants.IPFS_ACTION_STOP) {
                //  isIPFSDaemonRunning = false;
            }
        }
    };

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ipfs;
    }


    private void registerReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.IPFS_ACTION_START);
        filter.addAction(Constants.IPFS_ACTION_STOP);
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        registerReceiver();

        EventBus.getDefault().register(this);
//        RxBus.get().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
//        RxBus.get().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDaemonEvent(DaemonEvent event) {
        ToastUtil.show(event.message);
        if (event.message.equals(Constants.DAEMON_IS_READY)) {
            checkIpfsVersion();
            btn_upload.setEnabled(true);
            btn_checkID.setEnabled(true);
            btn_pub.setEnabled(true);
            btn_sub.setEnabled(true);
            btn_swarm_peers.setEnabled(true);
            btn_swarm.setEnabled(true);
            btn_bootstrap_add.setEnabled(true);
            btn_pub_node.setEnabled(true);
            btn_file_get.setEnabled(true);
            btn_ipfs_channel_node.setEnabled(true);
            indeterminateProgressSmall.setVisibility(View.INVISIBLE);
            App.getInstance().isIPFSDaemonRunning = !App.getInstance().isIPFSDaemonRunning;
        } else if (event.message.equals(Constants.DAEMON_IS_CLOSED)) {
            btn_upload.setEnabled(false);
            btn_checkID.setEnabled(false);
            btn_pub.setEnabled(false);
            btn_sub.setEnabled(false);
            btn_swarm.setEnabled(false);
            btn_bootstrap_add.setEnabled(false);
            btn_swarm_peers.setEnabled(false);
            btn_file_get.setEnabled(false);
            btn_pub_node.setEnabled(false);
            btn_ipfs_channel_node.setEnabled(false);
            indeterminateProgressSmall.setVisibility(View.INVISIBLE);
            App.getInstance().isIPFSDaemonRunning = !App.getInstance().isIPFSDaemonRunning;
        }

    }


    @Override
    public void showContent(List<WXItemBean> list) {
//        if (swipeRefresh.isRefreshing()) {
//            swipeRefresh.setRefreshing(false);
//        }
//        stateMain();
//        mList.clear();
//        mList.addAll(list);
//        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMoreContent(List<WXItemBean> list) {
//        stateMain();
//        mList.addAll(list);
//        mAdapter.notifyDataSetChanged();
//        isLoadingMore = false;
    }

    @Override
    public void stateError() {
        super.stateError();
//        if (swipeRefresh.isRefreshing()) {
//            swipeRefresh.setRefreshing(false);
//        }
    }

    @OnClick(R.id.btn_checkIpfs)
    public void checkIpfs() {
        IPFSDaemon ipfsDaemon = new IPFSDaemon();
        String result = ipfsDaemon.initIpfs();
        Toast.makeText(this.getContext(), result, LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_start_ipfs)
    public void startStopIpfsDaemon() {
        IPFSDaemon ipfsDaemon = new IPFSDaemon();
        indeterminateProgressSmall.setVisibility(View.VISIBLE);
        if (!ipfsDaemon.isIpfsInitalized()) {
            ipfsDaemon.initIpfs();
        }
        Intent startServiceIntent = new Intent(this.mContext, IPFSDaemonService.class);
        startServiceIntent.setAction(App.getInstance().isIPFSDaemonRunning
                ? Constants.IPFS_ACTION_STOP : Constants.IPFS_ACTION_START);

        this.mContext.startService(startServiceIntent);

//        IPFSDaemonService.startStopIPFSDaemon(this.getContext(), App.getInstance().isIPFSDaemonRunning
//                ? Constants.IPFS_ACTION_STOP : Constants.IPFS_ACTION_START);

    }


    @OnClick(R.id.btn_checkID)
    public void checkIPFSID() {
        IPFSDaemon ipfsDaemon = new IPFSDaemon();
        String IdInfo = ipfsDaemon.runCmd(Constants.IPFS_CMD_ID); // 启动脚本
        tv_ipfs_status.setText(IdInfo);
    }


    @OnClick(R.id.btn_check_Ipfs_version)
    public void checkIpfsVersion() {
        IPFSAnroid ipfsAnroid = new IPFSAnroid();
        ipfsAnroid.version(new Callback<Version>() {
            @Override
            public void onResponse(Call<Version> call, Response<Version> response) {
                tv_ipfs_status.setText(response.body().getVersion());
            }

            @Override
            public void onFailure(Call<Version> call, Throwable t) {
                tv_ipfs_status.setText(t.getMessage());
            }
        });

        timer = new Timer();
        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        ipfsAnroid.stats().bw(new Callback<StatsBwEntity>() {
                            @Override
                            public void onResponse(Call<StatsBwEntity> call, Response<StatsBwEntity> response) {
                                if (response != null) {
                                    tv_ipfs_stats.setText("↑ " + ((int) response.body().getRateOut()) / 1024f
                                            + "kb/s" + "\r\n" + "↓ " + ((int) response.body().getRateIn()) / 1024f + "kb/s");

                                }
                            }

                            @Override
                            public void onFailure(Call<StatsBwEntity> call, Throwable t) {
                                if (timer != null) {
                                    timer.cancel();
                                }

                            }

                        });
                    }
                }, 0, 1000);

    }


    @OnClick(R.id.btn_upload)
    public void uploadFile() {

        if (TextUtils.isEmpty(filePath)) {
            ToastUtil.show("请先选择文件并");
            return;
        }

        IPFSAnroid ipfsAnroid = new IPFSAnroid();

        File testFile = new File(filePath);
        ipfsAnroid.add(new Callback<FileAdd>() {
            @Override
            public void onResponse(Call<FileAdd> call, Response<FileAdd> response) {
                tv_ipfs_status.setText("name:" + response.body().getName() + " hash:" + response.body().getHash());

                // 更新到数据库里面
                ImageBean imageBean = new ImageBean();
                imageBean.setDesc(et_desc.getText().toString().trim());
                imageBean.setHash(response.body().getHash());
                imageBean.setName(response.body().getName());
                if (!TextUtils.isEmpty(et_price.getText().toString().trim())) {
                    imageBean.setPrice(Double.parseDouble(et_price.getText().toString().trim()));
                }
                App.getInstance().mDaoSession.getImageBeanDao().insert(imageBean);


            }

            @Override
            public void onFailure(Call<FileAdd> call, Throwable t) {
                tv_ipfs_status.setText(t.getMessage());
            }
        }, testFile, filePath.substring(filePath.lastIndexOf("/"), filePath.length()));
    }

    @OnClick(R.id.btn_choose)
    public void chooseFile() {
        PermissionUtil.requestPermissions(this.getActivity());

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //intent.setType(“image/*”);//选择图片
        //intent.setType(“audio/*”); //选择音频
        //intent.setType(“video/*”); //选择视频 （mp4 3gp 是android支持的视频格式）
        //intent.setType(“video/*;image/*”);//同时选择视频和图片
        intent.setType("*/*");//无类型限制
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, Constants.REQUEST_CODE);
    }


    @OnClick(R.id.btn_check_image)
    public void checkImageFile() {
        Intent intent = new Intent(this.getContext(), IpfsImageListActivity.class);
        startActivity(intent);
    }


    @OnClick(R.id.btn_swarm_peers)
    public void swarmPeers() {
        Intent intent = new Intent(this.getContext(), IpfsSwarmPeersActivity.class);
        startActivity(intent);
    }



    @OnClick(R.id.btn_file_get)
    public void getFile() {
        Intent intent = new Intent(this.getContext(), GetFileActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_bootstrap_add)
    public void bootStrapAdd() {
        Intent intent = new Intent(this.getContext(), IpfsBootstrapAddActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_ipfs_channel_node)
    public void checkChannelNode() {
        Intent intent = new Intent(this.getContext(), ChannelListActivity.class);
        startActivity(intent);
    }


    @OnClick(R.id.btn_pub_node)
    public void pubNode() {
        Intent intent = new Intent(this.getContext(), PubNodeActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_pub)
    public void pub() {
        Intent intent = new Intent(this.getContext(), PubActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_sub)
    public void sub() {
        Intent intent = new Intent(this.getContext(), SubActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_swarm)
    public void swarm() {
        Intent intent = new Intent(this.getContext(), SwarmActivity.class);
        startActivity(intent);
    }


    /**
     * 将DB文件存储到IPFS文件里面
     */
    public void saveDatabaseFile() {
        Intent intent = new Intent(this.getContext(), IpfsImageListActivity.class);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.RESULT_OK) {
            Uri uri = data.getData();
            if ("file".equalsIgnoreCase(uri.getScheme())) {//使用第三方应用打开
                filePath = uri.getPath();
                tv_filename.setText(filePath.substring(filePath.lastIndexOf("/"), filePath.length()));
                et_price.setText("1");
                return;
            }
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {//4.4以后
                filePath = getPath(this.getContext(), uri);
                tv_filename.setText(filePath.substring(filePath.lastIndexOf("/"), filePath.length()));
                return;
            } else {//4.4以下下系统调用方法 nothing
//                path = getRealPathFromURI(uri);
//                tv.setText(path);
//                Toast.makeText(MainActivity.this, path + "222222", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使
     */
    @SuppressLint("NewApi")
    public String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public String getDataColumn(Context context, Uri uri, String selection,
                                String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }
}