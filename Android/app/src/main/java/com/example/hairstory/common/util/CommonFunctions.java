package com.example.hairstory.common.util;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class CommonFunctions extends AppCompatActivity {

    public static Context mContext;
    public static Class mClass;



    // change page from previous to next one
    public static void changeIntent(Context currentContext, Class nextClass) {

        mContext = currentContext;
        mClass = nextClass;

        Intent intent = new Intent(mContext, mClass);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    public static void changeIntentWithValues(Context currentContext, Class nextClass, String strName, String strValue) {

        mContext = currentContext;
        mClass = nextClass;

        Intent intent = new Intent(mContext, mClass);
        intent.putExtra(strName, strValue);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    public static ArrayList formatPhoneNumber(String getPhoneNum){

        ArrayList<String> mPhoneNum = new ArrayList<String>();

        mPhoneNum.add(getPhoneNum.substring(0, 3));
        mPhoneNum.add(getPhoneNum.substring(3, 6));
        mPhoneNum.add(getPhoneNum.substring(6, getPhoneNum.length()));

        return mPhoneNum;
    }

    public static ArrayList formatEmailAddress(String getEmailAddress){

        ArrayList<String> mEmail = new ArrayList<String>();

        mEmail.add(getEmailAddress.substring(0, getEmailAddress.lastIndexOf("@")));
        mEmail.add(getEmailAddress.substring(getEmailAddress.lastIndexOf("@")+1));

        return mEmail;
    }



}
