package com.example.hairstory.common.util;

import android.widget.Spinner;

import java.util.Map;

public class StringUtil {

    public static String combineTelNum(String telNum1, String telNum2, String telNum3) {
        StringBuilder sb = new StringBuilder();
        sb.append(telNum1).append("-").append(telNum2).append("-").append(telNum3);
        return sb.toString();
    }

    public static String[] splitTelNum(String telNum) {
        String[] arryTelNum = telNum.split("-");
        return arryTelNum;
    }

    public static <K, V> K getKey(Map<K, V> map, V value) {

        for (K key : map.keySet()) {
            if (value.equals(map.get(key))) {
                return key;
            }
        }
        return null;
    }


    public static int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }
        return 0;
    }
}
