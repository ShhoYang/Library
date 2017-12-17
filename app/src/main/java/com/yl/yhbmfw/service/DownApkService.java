package com.yl.yhbmfw.service;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import com.socks.library.KLog;
import com.yl.library.rx.RxBus;
import com.yl.library.utils.FileLocalUtils;
import com.yl.library.utils.T;
import com.yl.yhbmfw.BuildConfig;
import com.yl.yhbmfw.Constant;
import com.yl.yhbmfw.R;
import com.yl.yhbmfw.event.EventUnbindService;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Yang Shihao
 *         下载APK
 */

public class DownApkService extends Service {

    private static final String TAG = "DownApkService";

    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;
    private Notification mNotification;
    private String mDownUrl;
    private DownloadAsync mDownloadAsync;

    final MyBinder mMyBinder = new MyBinder(this);


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        KLog.d(TAG, "onBind");
        if (mDownloadAsync == null && intent != null) {
            mDownUrl = intent.getStringExtra(Constant.KEY_STRING_1);
            if (TextUtils.isEmpty(mDownUrl)) {
                T.showShort(this, "下载地址错误");
                stopSelf();
            } else {
                KLog.d(TAG, "准备下载");
                mDownloadAsync = new DownloadAsync();
                mDownloadAsync.execute(mDownUrl);
            }
        }
        return mMyBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        KLog.d(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        KLog.d(TAG, "onStartCommand");
        return Service.START_STICKY;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        KLog.d(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        KLog.d(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        KLog.d(TAG, "onTaskRemoved");
        mNotificationManager.cancel(0);
    }

    public class MyBinder extends Binder {

        public final Service mService;

        public MyBinder(Service service) {
            mService = service;
        }

    }

    /**
     * 下载新版App的异步任务
     */

    public class DownloadAsync extends AsyncTask<String, Integer, Boolean> {

        private File apkFile;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mNotificationManager = (NotificationManager) getSystemService(Activity.NOTIFICATION_SERVICE);
            mBuilder = new NotificationCompat.Builder(getBaseContext())
                    .setSmallIcon(R.drawable.icon).setContentInfo("下载中...").setContentTitle("正在下载");
            mNotification = mBuilder.build();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            boolean isSuccess = true;
            InputStream is = null;
            BufferedInputStream bis = null;
            OutputStream os = null;
            try {
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                if (conn.getResponseCode() == 200) {
                    is = conn.getInputStream();
                    bis = new BufferedInputStream(is);
                    apkFile = new File(FileLocalUtils.getDownDir(), "yuhang.apk");
                    if (apkFile.exists()) {
                        apkFile.delete();
                    }
                    os = new FileOutputStream(apkFile);
                    byte[] buffer = new byte[128];
                    int len;
                    long data = conn.getContentLength();
                    long length = 0;
                    long lastTime = 0;
                    long nowTime;
                    while ((len = bis.read(buffer)) != -1) {
                        os.write(buffer, 0, len);
                        length += len;
                        nowTime = System.currentTimeMillis();
                        if (nowTime - lastTime > 3000) {
                            publishProgress((int) (length * 100 / data));
                            lastTime = nowTime;
                        }
                    }
                    publishProgress(100);
                } else {
                    isSuccess = false;
                }
            } catch (Exception e) {
                KLog.d(TAG, "下载失败");
                isSuccess = false;
            } finally {
                try {
                    if (os != null) {
                        os.close();
                    }

                    if (bis != null) {
                        bis.close();
                    }

                    if (is != null) {
                        is.close();
                    }

                } catch (IOException e) {
                    KLog.d(TAG, "关闭流发生异常");
                }
            }
            return isSuccess;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            KLog.d(TAG, values[0]);
            mBuilder.setProgress(100, values[0], false).setContentInfo(values[0] + "%");
            mNotification = mBuilder.build();
            mNotificationManager.notify(0, mNotification);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (result) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    Uri contentUri = FileProvider.getUriForFile(DownApkService.this, BuildConfig.APPLICATION_ID + ".provider", apkFile);
                    intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
                } else {
                    intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                startActivity(intent);
                mNotificationManager.cancel(0);
            } else {
                T.showShort(DownApkService.this, "下载失败");
            }
            stopSelf();
            RxBus.getInstance().send(new EventUnbindService());
        }
    }
}
