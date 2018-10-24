package com.suoneng.robot.utils;

import android.app.Activity;
import android.content.Context;

public class ContextHolder {
    
    private static Context mContext;

    /**
     * 程序开启的时候，只初始化一次，不要随便初始化
     * @return
     */
    public static Context getContext() {
        return mContext;
    }
    
    /**
     * 程序退出的时候，必须得释放
     * @param mContext
     */
    public static void setContext(Context mContext) {
        ContextHolder.mContext = mContext;
    }
    
    public static Context getApplicationContext() {
        return mContext.getApplicationContext();
    }
    
    public static Activity getContextAsActivityIfPossible() {
        return mContext instanceof Activity ? (Activity) mContext : null;
    }
    
}
