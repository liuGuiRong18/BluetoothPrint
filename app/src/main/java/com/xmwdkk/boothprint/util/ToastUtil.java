package com.xmwdkk.boothprint.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by liuguirong on 8/1/17.
 */
public class ToastUtil {

    public static void showToast(Context context, String message) {
        if (null != context && !TextUtils.isEmpty(message)) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

    public static void showToast(Context context, int message) {
        if (null != context && message != 0) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

}
