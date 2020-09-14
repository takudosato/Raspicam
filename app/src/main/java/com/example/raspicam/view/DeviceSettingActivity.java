package com.example.raspicam.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.raspicam.R;
import com.example.raspicam.viewmodel.DeviceSettingViewModel;

public class DeviceSettingActivity extends AppCompatActivity {

    DeviceSettingViewModel viewmodel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_setting);

        //ViewModel
        FragmentActivity activity = this;
        ViewModelProvider.Factory factory = new ViewModelProvider.NewInstanceFactory();
        viewmodel = new ViewModelProvider(activity, factory).get(DeviceSettingViewModel.class);

        //BLE通信処理
        doBleAccress();

        findViewById(R.id.btn_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSettingButton();
            }
        });
    }


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