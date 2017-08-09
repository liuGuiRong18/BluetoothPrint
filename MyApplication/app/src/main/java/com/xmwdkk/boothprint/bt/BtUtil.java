package com.xmwdkk.boothprint.bt;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;

/**
 * Created by liuguirong on 8/1/17.
 */
public class BtUtil {
    /**
     * 判断蓝牙是否打开
     *
     * @return boolean
     */
    public static boolean isOpen(BluetoothAdapter adapter) {
        if (null != adapter) {
            return adapter.isEnabled();
        }
        return false;
    }

    /**
     * 搜索蓝牙设备
     */
    public static void searchDevices(BluetoothAdapter adapter) {
        // 寻找蓝牙设备，android会将查找到的设备以广播形式发出去
        if (null != adapter) {
            adapter.startDiscovery();
        }
    }

    /**
     * 取消搜索蓝牙设备
     */
    public static void cancelDiscovery(BluetoothAdapter adapter) {
        if (null != adapter) {
            adapter.cancelDiscovery();
        }
    }

    /**
     * register bluetooth receiver
     *
     * @param receiver bluetooth broadcast receiver
     * @param activity activity
     */
    public static void registerBluetoothReceiver(BroadcastReceiver receiver, Activity activity) {
        if (null == receiver || null == activity) {
            return;
        }
        IntentFilter intentFilter = new IntentFilter();
        //start discovery
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        //finish discovery
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        //bluetooth status change
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        //found device
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        //bond status change
        intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        //pairing device
        intentFilter.addAction("android.bluetooth.device.action.PAIRING_REQUEST");
        activity.registerReceiver(receiver, intentFilter);
    }

    /**
     * unregister bluetooth receiver
     *
     * @param receiver bluetooth broadcast receiver
     * @param activity activity
     */
    public static void unregisterBluetoothReceiver(BroadcastReceiver receiver, Activity activity) {
        if (null == receiver || null == activity) {
            return;
        }
        activity.unregisterReceiver(receiver);
    }

}
