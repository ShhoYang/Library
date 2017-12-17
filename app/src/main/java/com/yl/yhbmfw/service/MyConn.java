package com.yl.yhbmfw.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.socks.library.KLog;

/**
 * @author Yang Shihao
 */

public class MyConn implements ServiceConnection {

    private static final String TAG = "MyConn";

    private Context mContext;

    public MyConn(Context context) {
        mContext = context;
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        KLog.d(TAG, "服务已开启");
        ((DownApkService.MyBinder) iBinder).mService.startService(new Intent(mContext, DownApkService.class));
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        KLog.d(TAG, "服务断开");
    }
}
