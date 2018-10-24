package com.suoneng.robot.utils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Pattern;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;


/**
 * Copyright (C) 2017 Transsion Inc.
 *
 * @author weiwei.li
 * @version 0.1, 2017/6/19 20:35
 */

public class CommonUtil {
    public static String TAG = "CommonUtil";
    public static boolean isSupportMultiWindowMode(Activity activity) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            if (activity.isInMultiWindowMode()) {
                return true;
            }
        }
        return false;
    }

    /**
     * dp2px
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px2dp
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean isRtl() {
        Locale locale = Locale.getDefault();
        return TextUtils.getLayoutDirectionFromLocale(locale) == View.LAYOUT_DIRECTION_RTL;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getSystemProperties(String name) {
        try {
            Class clazz = Class.forName("android.os.SystemProperties");
            Object o = clazz.newInstance();
            Method method = clazz.getDeclaredMethod("get", String.class);
            String value = (String) method.invoke(o, name);
            return value == null ? "null" : value;
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return "null";
        }
    }

    public static String getIMEI(Context context) {
        TelephonyManager teleMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!readPhoneStatusPermision(context)) {
                return teleMgr.getSimOperator();
            }
        }
        @SuppressLint("MissingPermission") String deviceId = teleMgr.getDeviceId();
        return deviceId == null ? "null" : deviceId;
    }

    public static String getIMSI(Context context) {
        TelephonyManager teleMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!readPhoneStatusPermision(context)) {
                return teleMgr.getSimOperator();
            }
        }
        @SuppressLint({"HardwareIds", "MissingPermission"}) String subscriberId = teleMgr.getSubscriberId();
        return subscriberId == null ? "null" : subscriberId;
    }

    /**
     * 利用MD5进行加密
     *
     * @param str 待加密的字符串
     * @return 加密后的字符串
     */

    public static String encodeByMd5(String str) {
        try {
            return new String(Base64.encode(MessageDigest.getInstance("MD5").digest(str.getBytes("utf-8")), Base64.DEFAULT));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return "null";
        }
    }
    public static boolean readPhoneStatusPermision(Context context) {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED;
    }
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

}
