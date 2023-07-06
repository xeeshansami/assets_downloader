package com.hbl.assetsmanager.utils;

import static android.content.Context.WIFI_SERVICE;

import android.annotation.SuppressLint;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Constants {
    public static final int TOAST_TIME =2000;
    public static final String fomratDateTime = "yyyy-MM-dd HH:mm:ss";
    public static final String fomratDateTimeException = "hh:mm:s a | dd-MMM-yyyy";
    public static final String fomratDate = "dd-mm-yyyy";
    public static final String fomratDate2 = "yyyy-mm-dd";
    public static final String fomratTime = "HH:mm:ss";
    public static final String ACTIVITY_KEY = "ACTIVITY_KEY";
    public static final int EXCEPTION_TOAST = 3;
    public static final int SUCCESS_TOAST = 2;
    public static final int ERROR_TOAST = 1;
    public static WifiManager wManager;
    public static final long SESSION_DISCONNECT_TIMEOUT = 1000 * 60 * 1000; // 5 min = 5 * 60 * 1000 ms

    @Nullable
    public static final String TAG = "HBL_TAG";

    @Nullable
    public static final int INPUT_TYPE_NUMBER = 0;
    @Nullable
    public static final int INPUT_TYPE_ALPHA_BLOCK_LASTNAME = 9;
    @Nullable
    public static final int INPUT_TYPE_ALPHANUMERIC = 1;
    @Nullable
    public static final int INPUT_TYPE_ALPHA = 2;
    @Nullable
    public static final int INPUT_TYPE_CUSTOM_ALPHA = 3;
    @Nullable
    public static final int INPUT_TYPE_CUSTOM_EMAIL_ALPHA = 4;
    @Nullable
    public static final int INPUT_TYPE_CUSTOM_ADDRESS_ALPHANUMERIC = 5;
    @Nullable
    public static final int INPUT_TYPE_ALPHA_BLOCK = 7;
    @Nullable
    public static final int INPUT_TYPE_CUSTOM_ALPHANUMERIC = 8;

    @SuppressLint("MissingPermission")
    public static String getMacAddress() {
        wManager = (WifiManager) GlobalClass.applicationContext.getSystemService(WIFI_SERVICE);
        WifiInfo info = wManager.getConnectionInfo();
        return info.getMacAddress();
    }

    public static String getOs() {
        return "ANDROID";
    }

    public static String getIP() {
        wManager = (WifiManager) GlobalClass.applicationContext.getSystemService(WIFI_SERVICE);
        return Formatter.formatIpAddress(wManager.getConnectionInfo().getIpAddress());
    }
}
