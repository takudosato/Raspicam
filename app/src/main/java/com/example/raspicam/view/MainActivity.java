package com.example.raspicam.view;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.raspicam.R;
import com.example.raspicam.data.DeviceSettingData;
import com.example.raspicam.data.ScanDeviceData;
import com.example.raspicam.model.BleInterface;

import java.util.ArrayList;
import java.util.Set;

import static android.os.SystemClock.sleep;

public class MainActivity extends AppCompatActivity {

    BleInterface bleinterface = new BleInterface();
    ArrayList<ScanDeviceData> list;

    DeviceSettingData devicesetting = new DeviceSettingData();


//    BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
 //   BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();

    private void getDeviceListThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                list = bleinterface.getDeviceList("",0);

                Handler mainThreadHandler = new Handler(Looper.getMainLooper());
                mainThreadHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        //メインスレッドで実行する処理
                        Set<ScanResult> set = bleinterface.getlisttest();
                        Log.d("set",set.toString());
                        //Log.d(list.get(0).mdeviceName, "Getttttttttttttttttttttttttttttttttttttt!");
                    }
                });
            }
        }).start();

        //終了を受け取る

    }

    private void getDeviceSettingThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                devicesetting = bleinterface.getDeviceSetting();
            }
        }).start();
    }

    private void setDeviceSettingThread() {
        devicesetting = new DeviceSettingData();
        new Thread(new Runnable() {
            @Override
            public void run() {
                bleinterface.setDeviceSetting(devicesetting);
            }
        }).start();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int REQUEST_ENABLE_BT = 1;

        //リスト取得ボタン
        Button btn_getlist = findViewById(R.id.getdevicelist);
        btn_getlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDeviceListThread();

            }
        });

        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();

        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(intent, REQUEST_ENABLE_BT);

        //デバイスの現在の設定取得
        Button btn_getSetting = findViewById(R.id.getdevicesetting);
        btn_getSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDeviceSettingThread();

            }
        });

        //デバイスへの設定
        Button btn_setSetting = findViewById(R.id.setdeviceinfo);
        btn_setSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDeviceSettingThread();
            }
        });
    }
}