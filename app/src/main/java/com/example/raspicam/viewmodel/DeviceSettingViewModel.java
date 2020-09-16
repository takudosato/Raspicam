package com.example.raspicam.viewmodel;

import android.util.Log;
import android.widget.NumberPicker;
import android.widget.RadioGroup;

import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.raspicam.data.DeviceSettingData;
import com.example.raspicam.data.NumberPickerData;
import com.example.raspicam.model.BleInterface;

public class DeviceSettingViewModel extends ViewModel {

    //デバイスとの連携クラス
    private BleInterface bleinterface = null;

    //NumberPickerに設定する情報を管理するクラス
    private NumberPickerData npdata = null;

    public DeviceSettingData settingdata;
    public MutableLiveData<DeviceSettingData> ldsettingdata;

    /**
     * コンストラクタ
     */
    public DeviceSettingViewModel() {
        bleinterface = new BleInterface();
        if(bleinterface == null) {
            System.exit(0);
        }

        ldsettingdata = new MutableLiveData<>();
        if(ldsettingdata == null) {
            System.exit(0);
        }

        npdata = new NumberPickerData();
        if(npdata == null) {
            System.exit(0);
        }

        settingdata = new DeviceSettingData();
        if(settingdata == null) {
            System.exit(0);
        }
    }

    /**
     * LiveDataによるデバイス設定情報を返す
     * @return
     */
    public MutableLiveData<DeviceSettingData> getDeviceSettingData() {
        //BLEクラスからデバイス設定情報を取得する
        settingdata = bleinterface.getDeviceSetting();
        if (settingdata != null) {
            ldsettingdata.postValue(settingdata);
        }
        return ldsettingdata;
    }

     /**
     * 現在のデバイス情報を取得する
     */
    public void dispDeviceData() {

        //BLEクラスからデバイス設定情報を取得する
        settingdata = bleinterface.getDeviceSetting();
        if (settingdata != null) {
            ldsettingdata.postValue(settingdata);
        }
    }

    public void setDeviceData() {
        getDispSetting();
    }

    private void getDispSetting() {

    }

    /**
     * 設定ボタン押下時の処理
     */
    public void onSettingClick() {
        //BLEクラスからデバイス設定情報を取得する
        bleinterface.setDeviceSetting(ldsettingdata.getValue());
    }

    /**
     * 測定モードのセッタ
     * @param mode
     */
    public void onMeasurementModeClicked(int mode) {
        settingdata.measurementMode = mode;
        ldsettingdata.postValue(settingdata);
    }

    /**
     * /顔検出ON/OFFのセッタ
     * @param checked
     */
    public void onCheckedChanged(boolean checked) {
        settingdata.facedetection = checked;
        ldsettingdata.postValue(settingdata);
    }

    /**
     * キャリブレーション値のセッタ
     * @param oldv
     * @param newv 選択された値
     */
    public void onCalibrationChange(int oldv, int newv) {
        settingdata.calibration = npdata.getCalibrationIndexDoublenum(newv);
        ldsettingdata.postValue(settingdata);
    }





    public int getCalibralinNPMaxvalue() {
        return npdata.getCalibralinNPMaxvalue();
    }

    public String[] getCalibrationListContents() {
        return npdata.getCalibrationListContents();
    }

    public int getCalibrationNPIndexOf() {
        return npdata.getCalibrationNPIndexOf(settingdata.calibration);
    }

    public int getHighTemperatureThresholdMaxvalue() {
        return npdata.getHighTemperatureThresholdMaxvalue();
    }

    public String[] getHighTemperatureThresholdListContents() {
        return npdata.getHighTemperatureThresholdListContents();
    }

    public int getHighTemperatureThresholdNPIndexOf() {
        return npdata.getHighTemperatureThresholdNPIndexOf(settingdata.hightemperaturethreshold);
    }

}
