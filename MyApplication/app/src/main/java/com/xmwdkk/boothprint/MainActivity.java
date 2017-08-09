package com.xmwdkk.boothprint;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.xmwdkk.boothprint.base.AppInfo;
import com.xmwdkk.boothprint.bt.BluetoothActivity;
import com.xmwdkk.boothprint.print.PrintMsgEvent;
import com.xmwdkk.boothprint.print.PrintUtil;
import com.xmwdkk.boothprint.print.PrinterMsgType;
import com.xmwdkk.boothprint.util.ToastUtil;

import de.greenrobot.event.EventBus;

/***
 *  Created by liugruirong on 2017/8/3.
 */
public class MainActivity extends BluetoothActivity implements View.OnClickListener {

     TextView tv_bluename;
     TextView tv_blueadress;
      boolean mBtEnable = true;
    int PERMISSION_REQUEST_COARSE_LOCATION=2;
    /**
     * bluetooth adapter
     */
    BluetoothAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_bluename =findViewById(R.id.tv_bluename);
        tv_blueadress =findViewById(R.id.tv_blueadress);
        findViewById(R.id.button4).setOnClickListener(this);
        findViewById(R.id.button5).setOnClickListener(this);
        findViewById(R.id.button6).setOnClickListener(this);
        findViewById(R.id.button).setOnClickListener(this);
        //6.0以上的手机要地理位置权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
        }
        EventBus.getDefault().register(MainActivity.this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        BluetoothController.init(this);
    }




    @Override
    public void btStatusChanged(Intent intent) {
        super.btStatusChanged(intent);
        BluetoothController.init(this);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button4:
             startActivity(new Intent(MainActivity.this,SearchBluetoothActivity.class));
                break;
            case R.id.button5:
                if (TextUtils.isEmpty(AppInfo.btAddress)){
                    ToastUtil.showToast(MainActivity.this,"请连接蓝牙...");
                    startActivity(new Intent(MainActivity.this,SearchBluetoothActivity.class));
                }else {
                    if ( mAdapter.getState()==BluetoothAdapter.STATE_OFF ){//蓝牙被关闭时强制打开
                         mAdapter.enable();
                        ToastUtil.showToast(MainActivity.this,"蓝牙被关闭请打开...");
                    }else {
                        ToastUtil.showToast(MainActivity.this,"打印测试...");
                        Intent intent = new Intent(getApplicationContext(), BtService.class);
                        intent.setAction(PrintUtil.ACTION_PRINT_TEST);
                        startService(intent);
                    }

                }
                break;
            case R.id.button6:
                if (TextUtils.isEmpty(AppInfo.btAddress)){
                    ToastUtil.showToast(MainActivity.this,"请连接蓝牙...");
                    startActivity(new Intent(MainActivity.this,SearchBluetoothActivity.class));
                }else {
                    ToastUtil.showToast(MainActivity.this,"打印测试...");
                    Intent intent2 = new Intent(getApplicationContext(), BtService.class);
                    intent2.setAction(PrintUtil.ACTION_PRINT_TEST_TWO);
                    startService(intent2);

                }
                case R.id.button:
                if (TextUtils.isEmpty(AppInfo.btAddress)){
                    ToastUtil.showToast(MainActivity.this,"请连接蓝牙...");
                    startActivity(new Intent(MainActivity.this,SearchBluetoothActivity.class));
                }else {
                    ToastUtil.showToast(MainActivity.this,"打印图片...");
                    Intent intent2 = new Intent(getApplicationContext(), BtService.class);
                    intent2.setAction(PrintUtil.ACTION_PRINT_BITMAP);
                    startService(intent2);

                }
//                startActivity(new Intent(MainActivity.this,TextActivity.class));
                break;
        }

    }
    /**
     * handle printer message
     *
     * @param event print msg event
     */
    public void onEventMainThread(PrintMsgEvent event) {
        if (event.type == PrinterMsgType.MESSAGE_TOAST) {
            ToastUtil.showToast(MainActivity.this,event.msg);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().register(MainActivity.this);
    }
}
