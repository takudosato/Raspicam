package com.example.raspicam.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.raspicam.R;
import com.example.raspicam.data.DeviceSettingData;
import com.example.raspicam.data.ScanDeviceData;
import com.example.raspicam.model.BleInterface;

import java.util.ArrayList;

import static android.os.SystemClock.sleep;

public class MainActivity extends AppCompatActivity {

    BleInterface bleinterface = new BleInterface();
    ArrayList<ScanDeviceData> list;

    DeviceSettingData devicesetting = new DeviceSettingData();

    private void getDeviceListThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                list = bleinterface.getDeviceList("",0);
            }
        }).start();
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

        //リスト取得ボタン
        Button btn_getlist = findViewById(R.id.getdevicelist);
        btn_getlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDeviceListThread();
                Log.d("mainthread","戻っているかな？");
            }
        });

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