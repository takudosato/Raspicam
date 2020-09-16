package com.example.raspicam.data;

public class DeviceSettingData {

    public static final int MEASURE_FACE_MODE = 0;
    public static final int MEASURE_WRIST_MODE = 1;

    public String mdeviceName = ""; //デバイス固有情報

    public int measurementMode = MEASURE_FACE_MODE; //測定モード
    public void setMeasurementMode(int mode) {
        measurementMode = mode;
    }

    //顔検出
    public boolean facedetection = false;

    //キャリブレーション
    public double calibration = 0.0;

    //高温閾値
    public double hightemperaturethreshold = 0.0;


}
