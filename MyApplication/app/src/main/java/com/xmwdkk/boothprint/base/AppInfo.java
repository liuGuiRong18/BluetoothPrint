package com.xmwdkk.boothprint.base;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.util.DisplayMetrics;

import com.xmwdkk.boothprint.print.PrintUtil;


/**
 * Created by liugruirong on 2017/8/3.
 */
public class AppInfo {

    public static String dType;
    public static String dVersion;

    public static int appCode;
    public static String appVersion;


    public static int width;
    public static int height;
    public static float density;
    public static int densityDpi;

    public static String btAddress;
    public static String btName;

    public static void init(Context mContext) {
        dType = Build.MODEL;
        dVersion = Build.VERSION.SDK_INT + "_" + Build.VERSION.RELEASE;
        PackageInfo pi = null;
        try {
            pi = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null != pi) {
            appVersion = pi.versionName;
            appCode = pi.versionCode;
        } else {
            appVersion = "";
            appCode = 0;
        }

        initDisplay(mContext);
        btAddress = PrintUtil.getDefaultBluethoothDeviceAddress(mContext);
        btName = PrintUtil.getDefaultBluetoothDeviceName(mContext);
    }

    public static void initDisplay(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        width = metrics.widthPixels;
        height = metrics.heightPixels;
        density = metrics.density;
        densityDpi = metrics.densityDpi;
    }
}
