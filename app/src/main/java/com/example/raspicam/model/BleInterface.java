package com.example.raspicam.model;

import android.util.Log;

import com.example.raspicam.data.DeviceSettingData;
import com.example.raspicam.data.ScanDeviceData;

import java.util.ArrayList;

public class BleInterface {

    /**
     * BLEでScanし、検出されたデバイスの情報をリストとして返す
     * @param filteringStr: Scanされたデバイス名から文字列を含むものだけリストする
     * @param scanmsc: Scanする時間をmscで指定する
     * @return　検出されたデバイスのリスト
     */
    public ArrayList<ScanDeviceData> getDeviceList(String filteringStr, int scanmsc) {
        Log.d("BleInterface", "getDeviceList");
        ArrayList<ScanDeviceData> list = null;
        return list;
    }

    /**
     * 指定したデバイスの現在の設定を返す
     * 任意のデバイスの指定方法は未定
     * @return　現在のデバイスの設定値。設定に失敗したらnull
     */
    public DeviceSettingData getDeviceSetting() {
        Log.d("BleInterface", "getDeviceSetting");

        DeviceSettingData data = new DeviceSettingData();
        if (data == null) {
            return null;
        }

        data.mdeviceName = "test";
        data.measurementMode = 2;

        return data;
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
