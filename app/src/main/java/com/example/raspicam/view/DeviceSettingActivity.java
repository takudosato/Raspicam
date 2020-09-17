package com.example.raspicam.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.example.raspicam.R;
import com.example.raspicam.data.DeviceSettingData;
import com.example.raspicam.databinding.ActivityDeviceSettingBinding;
import com.example.raspicam.viewmodel.DeviceSettingViewModel;

import java.util.Arrays;
import java.util.List;

public class DeviceSettingActivity extends AppCompatActivity {

    DeviceSettingViewModel viewmodel = null;

    //キャリブレーションNumberPicker
    private NumberPicker np_calibration = null;

    //高温閾値
    private NumberPicker np_hightemperaturethreshold = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_device_setting);


        Intent intent = new Intent(getApplication(), ScanDeviceListActivity.class);
        startActivity(intent);

        //ViewModel
        viewmodel = new ViewModelProvider(this).get(DeviceSettingViewModel.class);
        ActivityDeviceSettingBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_device_setting);
        binding.setVm(viewmodel);
        binding.setLifecycleOwner(this);

        //画面上のコントロールの初期設定
        initControls();

        // Create the observer which updates the UI.
        final Observer<DeviceSettingData> settingObserver = new Observer<DeviceSettingData>() {

            @Override
            public void onChanged(DeviceSettingData deviceSettingData) {
                // Update the UI, in this case, a TextView.
                dsipDeviceSetting(deviceSettingData);
            }
        };

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        viewmodel.getDeviceSettingData().observe(this, settingObserver);

        //BLE通信処理
        getBleAccress();
    }

    /**
     * コントロールの初期設定を行る
     */
    private void initControls() {

        //キャリブレーション
        np_calibration = (NumberPicker) findViewById(R.id.np_calibration);
        np_calibration.setMaxValue(viewmodel.getCalibralinNPMaxvalue());
        np_calibration.setMinValue(0);
        np_calibration.setDisplayedValues(viewmodel.getCalibrationListContents());
        np_calibration.setWrapSelectorWheel(false);

        //高温閾値範囲設定
        np_hightemperaturethreshold = (NumberPicker) findViewById(R.id.np_hightemperaturethreshold);
        np_hightemperaturethreshold.setMaxValue(viewmodel.getHighTemperatureThresholdMaxvalue());
        np_hightemperaturethreshold.setMinValue(0);
        np_hightemperaturethreshold.setDisplayedValues(viewmodel.getHighTemperatureThresholdListContents());
        np_hightemperaturethreshold.setWrapSelectorWheel(false);
    }

    /**
     * 最新の設定を画面の各コントロールに表示する
     */
    private void dsipDeviceSetting(DeviceSettingData deviceSettingData) {

        //測定モード
        RadioGroup group = (RadioGroup)findViewById(R.id.rg_measurement_mode);
        if (deviceSettingData.measurementMode == DeviceSettingData.MEASURE_FACE_MODE) {
            group.check(R.id.rb_measurementmode_face);
        } else {
            group.check(R.id.rb_measurementmode_wrist);
        }

        //顔検出
        Switch sw_isfacedetection = (Switch)findViewById(R.id.sw_facedetection);
        if (deviceSettingData.facedetection) {
            sw_isfacedetection.setChecked(true);
        }
        else {
            sw_isfacedetection.setChecked(false);
        }

        //キャリブレーション
        np_calibration.setValue(viewmodel.getCalibrationNPIndexOf());

        //高温閾値
        np_hightemperaturethreshold.setValue(viewmodel.getHighTemperatureThresholdNPIndexOf());

    }

    /**
     *
     */
    private void getBleAccress() {

        //ペアリングされているデバイスがなければ、検出処理に移動
        String pareDevice = "ahoaho";

        //ペアリングされているデバイスの情報を取得する
        if(viewmodel != null) {
            viewmodel.dispDeviceData(pareDevice);
        }
    }

}