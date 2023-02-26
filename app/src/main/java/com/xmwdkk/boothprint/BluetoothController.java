package com.xmwdkk.boothprint;

import android.bluetooth.BluetoothAdapter;
import android.text.TextUtils;
import android.util.Log;

import com.xmwdkk.boothprint.print.PrintUtil;

/**
 * Created by liuguirong on 8/1/17.
 */

public class BluetoothController {

    public static void init(MainActivity activity) {
        if (null == activity.mAdapter) {
            activity.mAdapter = BluetoothAdapter.getDefaultAdapter();
        }
        if (null == activity.mAdapter) {
            activity.tv_bluename.setText("该设备没有蓝牙模块");
            activity.mBtEnable = false;
            return;
        }
        Log.d("activity.mAdapter.getState()","activity.mAdapter.getState()"+activity.mAdapter.getState());
        if (!activity.mAdapter.isEnabled()) {
            //没有在开启中也没有打开
//            if ( activity.mAdapter.getState()!=BluetoothAdapter.STATE_TURNING_ON  && activity.mAdapter.getState()!=BluetoothAdapter.STATE_ON ){
            if ( activity.mAdapter.getState()==BluetoothAdapter.STATE_OFF ){//蓝牙被关闭时强制打开
                 activity.mAdapter.enable();

            }else {
                activity.tv_bluename.setText("蓝牙未打开");
                return;
            }
        }
        String address = PrintUtil.getDefaultBluethoothDeviceAddress(activity.getApplicationContext());
        if (TextUtils.isEmpty(address)) {
            activity.tv_bluename.setText("尚未绑定蓝牙设备");
            return;
        }
        String name = PrintUtil.getDefaultBluetoothDeviceName(activity.getApplicationContext());
        activity.tv_bluename.setText("已绑定蓝牙：" + name);
        activity.tv_blueadress.setText(address);

    }
    public static boolean turnOnBluetooth()
    {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter
                .getDefaultAdapter();
        if (bluetoothAdapter != null)
        {
            return bluetoothAdapter.enable();
        }
        return false;
    }
}
