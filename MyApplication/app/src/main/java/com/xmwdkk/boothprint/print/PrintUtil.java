package com.xmwdkk.boothprint.print;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;

import com.xmwdkk.boothprint.base.AppInfo;
import com.xmwdkk.boothprint.bt.BtUtil;

import java.util.Set;


/**
 * Created by liuguirong on 2017/8/3.
 *   printer util
 */
public class PrintUtil {

    private static final String FILENAME = "bt";
    private static final String DEFAULT_BLUETOOTH_DEVICE_ADDRESS = "default_bluetooth_device_address";//蓝牙设备地址
    private static final String DEFAULT_BLUETOOTH_DEVICE_NAME = "default_bluetooth_device_name";//蓝牙设备名称

    public static final String ACTION_PRINT_TEST = "action_print_test";
    public static final String ACTION_PRINT_TEST_TWO = "action_print_test_two";
    public static final String ACTION_PRINT = "action_print";
    public static final String ACTION_PRINT_TICKET = "action_print_ticket";
    public static final String ACTION_PRINT_BITMAP = "action_print_bitmap";
    public static final String ACTION_PRINT_PAINTING = "action_print_painting";

    public static final String PRINT_EXTRA = "print_extra";

    public static void setDefaultBluetoothDeviceAddress(Context mContext, String value) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(DEFAULT_BLUETOOTH_DEVICE_ADDRESS, value);
        editor.apply();
        AppInfo.btAddress = value;
    }

    public static void setDefaultBluetoothDeviceName(Context mContext, String value) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(DEFAULT_BLUETOOTH_DEVICE_NAME, value);
        editor.apply();
        AppInfo.btName = value;
    }
    //是否绑定了打印机设备
    public static boolean isBondPrinter(Context mContext, BluetoothAdapter bluetoothAdapter) {
        if (!BtUtil.isOpen(bluetoothAdapter)) {
            return false;
        }
        String defaultBluetoothDeviceAddress = getDefaultBluethoothDeviceAddress(mContext);
        if (TextUtils.isEmpty(defaultBluetoothDeviceAddress)) {
            return false;
        }
        Set<BluetoothDevice> deviceSet = bluetoothAdapter.getBondedDevices();
        if (deviceSet == null || deviceSet.isEmpty()) {
            return false;
        }
        for (BluetoothDevice bluetoothDevice : deviceSet) {
            if (bluetoothDevice.getAddress().equals(defaultBluetoothDeviceAddress)) {
                return true;
            }
        }
        return false;

    }

    public static String getDefaultBluethoothDeviceAddress(Context mContext) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(DEFAULT_BLUETOOTH_DEVICE_ADDRESS, "");
    }

    public static boolean isBondPrinterIgnoreBluetooth(Context mContext) {
        String defaultBluetoothDeviceAddress = getDefaultBluethoothDeviceAddress(mContext);
        return !(TextUtils.isEmpty(defaultBluetoothDeviceAddress)
                || TextUtils.isEmpty(getDefaultBluetoothDeviceName(mContext)));
    }
    //绑定设备的蓝牙名称
    public static String getDefaultBluetoothDeviceName(Context mContext) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(DEFAULT_BLUETOOTH_DEVICE_NAME, "");
    }

    /**
     * use new api to reduce file operate
     *
     * @param editor editor
     */
    public static void apply(SharedPreferences.Editor editor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            editor.apply();
        } else {
            editor.commit();
        }
    }
}
