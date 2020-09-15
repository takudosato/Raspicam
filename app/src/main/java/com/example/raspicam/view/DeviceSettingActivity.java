package com.example.raspicam.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.example.raspicam.R;
import com.example.raspicam.data.DeviceSettingData;
import com.example.raspicam.viewmodel.DeviceSettingViewModel;

import java.util.Arrays;
import java.util.List;

public class DeviceSettingActivity extends AppCompatActivity {

    DeviceSettingViewModel viewmodel = null;

    //キャリブレーション
    private NumberPicker np_calibration = null;
    final String[] np_calibration_list = {
            "+5.0", "+4.5", "+4.0", "+3.5", "+3.0", "+2.5", "+2.0", "+1.5", "+1.0", "+0.5",
            "±0.0",
            "-0.5", "-1.0", "-1.5", "-2.0", "-2.5", "-3.0", "-3.5", "-4.0", "-4.5", "-5.0"
    };

    //高温閾値
    private NumberPicker np_hightemperaturethreshold = null;
    final String[] np_hightemperaturethreshold_list = {
            "40.5", "40.4", "40.3", "40.2", "40.1", "40.0",
            "39.9", "39.8", "39.7", "39.6", "39.5", "39.4", "39.3", "39.2", "39.1", "39.0",
            "38.9", "38.8", "38.7", "38.6", "38.5", "38.4", "38.3", "38.2", "38.1", "38.0",
            "37.9", "37.8", "37.7", "37.6", "37.5", "37.4", "37.3", "37.2", "37.1", "37.0",
            "36.9", "36.8", "36.7", "36.6", "36.5", "36.4", "36.3", "36.2", "36.1", "36.0",
            "35.9", "35.8", "35.7", "35.6", "35.5", "35.4", "35.3", "35.2", "35.1", "35.0",
            "34.9", "34.8", "34.7", "34.6", "34.5"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_setting);

        //ViewModel
        viewmodel = new ViewModelProvider(this).get(DeviceSettingViewModel.class);

        //画面上のコントロールの初期設定
        setControlls();

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
        doBleAccress();
    }

    private void setControlls() {

        //コントロール初期化
        findViewById(R.id.btn_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSettingButton();
            }
        });

        //キャリブレーション
        np_calibration = (NumberPicker) findViewById(R.id.np_calibration);
        np_calibration.setMaxValue(np_calibration_list.length - 1);
        np_calibration.setMinValue(0);
        np_calibration.setDisplayedValues(np_calibration_list);
        np_calibration.setWrapSelectorWheel(false);
        np_calibration.setValue(np_calibration_list.length / 2);

        //高温閾値範囲設定
        np_hightemperaturethreshold = (NumberPicker) findViewById(R.id.np_hightemperaturethreshold);
        np_hightemperaturethreshold.setMaxValue(np_hightemperaturethreshold_list.length - 1);
        np_hightemperaturethreshold.setMinValue(0);
        np_hightemperaturethreshold.setDisplayedValues(np_hightemperaturethreshold_list);
        np_hightemperaturethreshold.setWrapSelectorWheel(false);
        np_hightemperaturethreshold.setValue(np_hightemperaturethreshold_list.length / 2);
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
        String strcalib = String.valueOf(deviceSettingData.calibration);
         if (deviceSettingData.calibration == 0.0) {
            strcalib = "±0.0";
        } else if (deviceSettingData.calibration > 0) {
            strcalib = "+" + strcalib;
        }
        List<String> list = Arrays.asList(np_calibration_list); //配列をList型オブジェクトに変換
        np_calibration.setValue(list.indexOf(strcalib));

        //高温閾値
        String strht = String.valueOf(deviceSettingData.hightemperaturethreshold);
        list = Arrays.asList(np_hightemperaturethreshold_list); //配列をList型オブジェクトに変換
        np_hightemperaturethreshold.setValue(list.indexOf(strht));

    }

    /**
     * 設定ボタン押下時の処理
     */
    private void onSettingButton() {

        //測定モード 顔 or 手首
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rg_measurement_mode);
        int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        RadioButton checkedButton = (RadioButton)findViewById(checkedRadioButtonId);

        if (checkedButton.getId() == R.id.rb_measurementmode_face) {
            Log.d("onSettingButton", "顔");
        }
        else {
            Log.d("onSettingButton", "手首");
        }


    }

    /**
     *
     */
    private void doBleAccress() {

        //ペアリングされているデバイスがなければ、検出処理に移動

        //ペアリングされているデバイスの情報を取得する
        if(viewmodel != null) {
            viewmodel.dispDeviceData();
        }


        return;
    }


}