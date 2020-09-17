package com.example.raspicam.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.raspicam.R;
import com.example.raspicam.data.DeviceSettingData;
import com.example.raspicam.data.ScanDeviceData;
import com.example.raspicam.model.BleInterface;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    BleInterface bleinterface = new BleInterface();

    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //リスト取得ボタン
        Button btn_getlist = findViewById(R.id.getdevicelist);
        btn_getlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<ScanDeviceData> list = new ArrayList<ScanDeviceData>();
                list = bleinterface.getDeviceList("",0);
            }
        });

        //デバイスの現在の設定取得
        Button btn_getSetting = findViewById(R.id.getdevicesetting);
        btn_getSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeviceSettingData devicesetting = new DeviceSettingData();
                devicesetting = bleinterface.getDeviceSetting("");
            }
        });

        //デバイスへの設定
        Button btn_setSetting = findViewById(R.id.setdeviceinfo);
        btn_setSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeviceSettingData devicesetting = new DeviceSettingData();
                bleinterface.setDeviceSetting(devicesetting);
            }
        });
    }
}