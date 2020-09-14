package com.example.raspicam.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.raspicam.data.DeviceSettingData;
import com.example.raspicam.model.BleInterface;

public class DeviceSettingViewModel extends ViewModel {

    private BleInterface bleinterface = new BleInterface();

    private DeviceSettingData settingdata;
    private MutableLiveData<DeviceSettingData> ldsettingdata;

    public DeviceSettingViewModel() {
        ldsettingdata = new MutableLiveData<>();
    }


        /**
     * 現在のデバイス情報を取得する
     */
    public void dispDeviceData() {

        //BLEクラスからデバイス設定情報を取得する
        if (bleinterface != null) {
            settingdata = bleinterface.getDeviceSetting();
            if (settingdata != null) {
                ldsettingdata.postValue(settingdata);
            }
        }
    }
}
