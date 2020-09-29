package com.example.raspicam.model;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.example.raspicam.data.DeviceSettingData;
import com.example.raspicam.data.ScanDeviceData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static android.os.SystemClock.sleep;


public class BleInterface {

    private Set<ScanResult> mResults = new HashSet<>();
    private Long SCAN_PERIOD =            TimeUnit.MILLISECONDS.convert(5, TimeUnit.SECONDS);

    // スキャンのタイムアウト処理用
    private Handler handler = new Handler();

    class BleScanCallback extends ScanCallback {

        private List<ScanResult> mBatchScanResults = new ArrayList<>();

        @Override
        public void onScanResult(int callbackType, ScanResult result) {

            if (callbackType == ScanSettings.CALLBACK_TYPE_ALL_MATCHES) {
                mResults.add(result);
                String ddd = result.getDevice().getName();
                if (ddd != null) {
                    Log.d("onScanResult", ddd);
                }
            }
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            // In case onBatchScanResults are called due to buffer full, we want to collect all
            // scan results.
            mBatchScanResults.addAll(results);
        }


    }


    private List<ScanFilter> buildScanFilters() {
        List<ScanFilter> scanFilters = new ArrayList<>();

        ScanFilter.Builder builder = new ScanFilter.Builder();
        // Comment out the below line to see all BLE devices around you
        builder.setServiceUuid(null);
        scanFilters.add(builder.build());

        return scanFilters;
    }


    /**
     * Return a {@link android.bluetooth.le.ScanSettings} object set to use low power (to preserve
     * battery life).
     */
    private ScanSettings buildScanSettings() {
        ScanSettings.Builder builder = new ScanSettings.Builder();
        builder.setScanMode(ScanSettings.SCAN_MODE_LOW_POWER);
        return builder.build();
    }

    /**
     * BLEでScanし、検出されたデバイスの情報をリストとして返す
     * @param filteringStr: Scanされたデバイス名から文字列を含むものだけリストする
     * @param scanmsc: Scanする時間をmscで指定する
     * @return　検出されたデバイスのリスト
     */
    public ArrayList<ScanDeviceData> getDeviceList(String filteringStr, int scanmsc) {

        final BleScanCallback callback = new BleScanCallback();

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        final BluetoothLeScanner bleScanner = bluetoothAdapter.getBluetoothLeScanner();

        // タイムアウト処理の仕込み
        handler.postDelayed( new Runnable()
        {
            @Override
            public void run()
            {
                bleScanner.stopScan( callback );
                // メニューの更新

            }
        }, SCAN_PERIOD );

        bleScanner.startScan(buildScanFilters(), buildScanSettings(), callback);

        Log.d("BleInterface", "getDeviceList start");
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

    public Set<ScanResult> getlisttest () {
        return mResults;

    }

}
