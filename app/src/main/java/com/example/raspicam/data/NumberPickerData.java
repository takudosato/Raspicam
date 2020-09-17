package com.example.raspicam.data;

import android.widget.NumberPicker;

import java.util.Arrays;
import java.util.List;

public class NumberPickerData {

    private static String preplus = "+";
    private static String plamaizero = "±0.0";

    //キャリブレーションの設定
    final String[] np_calibration_list = {
            "+5.0", "+4.5", "+4.0", "+3.5", "+3.0", "+2.5", "+2.0", "+1.5", "+1.0", "+0.5",
            "±0.0",
            "-0.5", "-1.0", "-1.5", "-2.0", "-2.5", "-3.0", "-3.5", "-4.0", "-4.5", "-5.0"
    };

    //高温閾値の設定
    final String[] np_hightemperaturethreshold_list = {
            "40.5", "40.4", "40.3", "40.2", "40.1", "40.0",
            "39.9", "39.8", "39.7", "39.6", "39.5", "39.4", "39.3", "39.2", "39.1", "39.0",
            "38.9", "38.8", "38.7", "38.6", "38.5", "38.4", "38.3", "38.2", "38.1", "38.0",
            "37.9", "37.8", "37.7", "37.6", "37.5", "37.4", "37.3", "37.2", "37.1", "37.0",
            "36.9", "36.8", "36.7", "36.6", "36.5", "36.4", "36.3", "36.2", "36.1", "36.0",
            "35.9", "35.8", "35.7", "35.6", "35.5", "35.4", "35.3", "35.2", "35.1", "35.0",
            "34.9", "34.8", "34.7", "34.6", "34.5"
    };


    //キャリブレーションNumberPickerに設定する個数を返す
    public int getCalibralinNPMaxvalue() {
        return np_calibration_list.length - 1;
    }

    //キャリブレーションNumberPickerに設定する文字列リストを返す
    public String[] getCalibrationListContents() {
        return np_calibration_list;
    }

    /**
     * 指定の値のキャリブレーションNumberPickerのインデックスを返す
     * @param calibration キャリブレーション値
     * @return　キャリブレーション値に対応するリストのインデックス
     */
    public int getCalibrationNPIndexOf(double calibration) {

        //キャリブレーション値の文字列化
        String strcalib = String.valueOf(calibration);

        //文字列に記号を追加
        if (calibration == 0.0) {
            strcalib = plamaizero;
        } else if (calibration > 0) {
            strcalib = preplus + strcalib;
        }
        List<String> list = Arrays.asList(np_calibration_list); //配列をList型オブジェクトに変換
        return list.indexOf(strcalib);
    }

    public double getCalibrationIndexDoublenum(int index) {

        //indexの位置の文字列を取得する＝ユーザが設定した値
        String str = np_calibration_list[index];

        double value = 0.0;

        if (str == plamaizero) {
            value = 0.0;
        } else {
            if  (str.contains("+")) {
                str.substring(0);
            }
            value = Double.parseDouble(str);
        }
        return value;
    }

    //高温閾値NumberPickerに設定する個数を返す
    public int getHighTemperatureThresholdMaxvalue() {
        return np_hightemperaturethreshold_list.length - 1;
    }

    //高温閾値NumberPickerに設定する文字列リストを返す
    public String[] getHighTemperatureThresholdListContents() {
        return np_hightemperaturethreshold_list;
    }

    /**
     * 高温閾値NumberPickerのインデックスを返す
     * @param hightemperaturethreshold
     * @return
     */
    public int getHighTemperatureThresholdNPIndexOf(double hightemperaturethreshold) {
        String strht = String.valueOf(hightemperaturethreshold);
        List<String> list = Arrays.asList(np_hightemperaturethreshold_list); //配列をList型オブジェクトに変換
        return list.indexOf(strht);
    }

    public double getHighTemperatureThresholdNPIndexDoublenum(int index) {
        //indexの位置の文字列を取得する＝ユーザが設定した値
        String str = np_hightemperaturethreshold_list[index];
        return Double.parseDouble(str);
    }
}
