package com.xmwdkk.boothprint.print;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.text.TextUtils;

import com.xmwdkk.boothprint.base.AppInfo;
import com.xmwdkk.boothprint.bt.BtService;

import java.util.ArrayList;

/**
 * Created by liuguirong on 8/1/17.
 * <p/>
 * this is print queue.
 * you can simple add print bytes to queue. and this class will send those bytes to bluetooth device
 * 这是打印队列。
   你可以简单地添加打印字节来排队。 并且这个类将这些字节发送到蓝牙设备
 */
public class PrintQueue {

    /**
     * instance
     */
    private static PrintQueue mInstance;
    /**
     * context
     */
    private static Context mContext;
    /**
     * print queue
     */
    private ArrayList<byte[]> mQueue;
    /**
     * bluetooth adapter
     */
    private BluetoothAdapter mAdapter;
    /**
     * bluetooth service
     */
    private BtService mBtService;


    private PrintQueue() {
    }

    public static PrintQueue getQueue(Context context) {
        if (null == mInstance) {
            mInstance = new PrintQueue();
        }
        if (null == mContext) {
            mContext = context;
        }
        return mInstance;
    }

    /**
     * add print bytes to queue. and call print
     *
     * @param bytes bytes
     */
    public synchronized void add(byte[] bytes) {
        if (null == mQueue) {
            mQueue = new ArrayList<byte[]>();
        }
        if (null != bytes) {
            mQueue.add(bytes);
        }
        print();
    }

    /**
     * add print bytes to queue. and call print
     *
     */
    public synchronized void add(ArrayList<byte[]> bytesList) {
        if (null == mQueue) {
            mQueue = new ArrayList<byte[]>();
        }
        if (null != bytesList) {
            mQueue.addAll(bytesList);
        }
        print();
    }

    /**
     * print queue
     */
    public synchronized void print() {
        try {
            if (null == mQueue || mQueue.size() <= 0) {
                return;
            }
            if (null == mAdapter) {
                mAdapter = BluetoothAdapter.getDefaultAdapter();
            }
            if (null == mBtService) {
                mBtService = new BtService(mContext);
            }
            if (mBtService.getState() != BtService.STATE_CONNECTED) {
                if (!TextUtils.isEmpty(AppInfo.btAddress)) {
                    BluetoothDevice device = mAdapter.getRemoteDevice(AppInfo.btAddress);
                    mBtService.connect(device);
                    return;
                }
            }
            while (mQueue.size() > 0) {
                mBtService.write(mQueue.get(0));
                mQueue.remove(0);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * disconnect remote device
     */
    public void disconnect() {
        try {
            if (null != mBtService) {
                mBtService.stop();
                mBtService = null;
            }
            if (null != mAdapter) {
                mAdapter = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * when bluetooth status is changed, if the printer is in use,
     * connect it,else do nothing
     */
    public void tryConnect() {
        try {
            if (TextUtils.isEmpty(AppInfo.btAddress)) {
                return;
            }
            if (null == mAdapter) {
                mAdapter = BluetoothAdapter.getDefaultAdapter();
            }
            if (null == mAdapter) {
                return;
            }
            if (null == mBtService) {
                mBtService = new BtService(mContext);
            }
            if (mBtService.getState() != BtService.STATE_CONNECTED) {
                if (!TextUtils.isEmpty(AppInfo.btAddress)) {
                    BluetoothDevice device = mAdapter.getRemoteDevice(AppInfo.btAddress);
                    mBtService.connect(device);
                    return;
                }
            } else {


            }
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e) {
            e.printStackTrace();
        }
    }

    /**
     * 将打印命令发送给打印机
     *
     * @param bytes bytes
     */
    public void write(byte[] bytes) {
        try {
            if (null == bytes || bytes.length <= 0) {
                return;
            }
            if (null == mAdapter) {
                mAdapter = BluetoothAdapter.getDefaultAdapter();
            }
            if (null == mBtService) {
                mBtService = new BtService(mContext);
            }
            if (mBtService.getState() != BtService.STATE_CONNECTED) {
                if (!TextUtils.isEmpty(AppInfo.btAddress)) {
                    BluetoothDevice device = mAdapter.getRemoteDevice(AppInfo.btAddress);
                    mBtService.connect(device);
                    return;
                }
            }
            mBtService.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
