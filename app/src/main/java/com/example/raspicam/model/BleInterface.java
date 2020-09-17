package com.example.raspicam.model;

import android.util.Log;

import com.example.raspicam.data.DeviceSettingData;
import com.example.raspicam.data.ScanDeviceData;

import java.util.ArrayList;

import static android.os.SystemClock.sleep;

public class BleInterface {

    /**
     * BLEでScanし、検出されたデバイスの情報をリストとして返す
     * @param filteringStr: Scanされたデバイス名から文字列を含むものだけリストする
     * @param scanmsc: Scanする時間をmscで指定する
     * @return　検出されたデバイスのリスト
     */
    public ArrayList<ScanDeviceData> getDeviceList(String filteringStr, int scanmsc) {
        Log.d("BleInterface", "getDeviceList start");
        ArrayList<ScanDeviceData> list = null;

        for(int n=0; n<100 ;n++) {
            Log.d("roop", String.valueOf(n));
        }

        list = new ArrayList<ScanDeviceData>();

        ScanDeviceData test = new ScanDeviceData();

        test.mdeviceName = "list1";
        list.add(test);
        test.mdeviceName = "list2";
        list.add(test);

        Log.d("BleInterface", "getDeviceList end");
        return list;
    }

    /**
     * 指定したデバイスの現在の設定を返す
     * 任意のデバイスの指定方法は未定
     * @return　現在のデバイスの設定値。設定に失敗したらnull
     */
    public DeviceSettingData getDeviceSetting() {
        Log.d("BleInterface", "getDeviceSetting");
        return new DeviceSettingData();
    }

    /**
     * 指定したデバイスに設定を行う
     * 任意のデバイスの設定方法は未定
     * @param deviceSettingData：設定
     * @return 設定に成功したか
     */
    public int setDeviceSetting(DeviceSettingData deviceSettingData) {
        Log.d("BleInterface", "setDeviceSetting");
        return 0;
    }
}
