/*Transsion Top Secret*/
package com.suoneng.robot.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.suoneng.robot.R;


/**
 * Created by yaming.liu on 2016/11/15.
 */
public class RoToast {
    private static android.widget.Toast toast;
    private static Context mContext;
    private static TextView textView;
    private static View view;
    private static RelativeLayout.LayoutParams params;

    public synchronized static void showToast(Context context,String msg){
        synchronized (RoToast.class){
            if (toast == null || textView == null){
                synchronized (RoToast.class){
                    mContext = context.getApplicationContext();
                    toast = new android.widget.Toast(mContext);
                    view = LayoutInflater.from(mContext).inflate(R.layout.toast_item,null);
                    params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                            mContext.getResources().getDimensionPixelOffset(R.dimen.toast_width));
                }
            }
        }

        textView = (TextView) view.findViewById(R.id.tv);
        textView.setTextColor(Color.WHITE);
        textView.setText(msg);
        textView.setLayoutParams(params);
        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0,
                mContext.getResources().getDimensionPixelOffset(R.dimen.toast_bottom_magin));
        toast.setDuration(android.widget.Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }

    public synchronized static void showToast(Context context,int  resId){
        synchronized (RoToast.class){
            if (toast == null || textView == null){
                synchronized (RoToast.class){
                    mContext = context.getApplicationContext();
                    toast = new android.widget.Toast(mContext);
                    view = LayoutInflater.from(mContext).inflate(R.layout.toast_item,null);
                    params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                            mContext.getResources().getDimensionPixelOffset(R.dimen.toast_width));
                }

            }
        }
        textView = (TextView) view.findViewById(R.id.tv);
        textView.setText(mContext.getResources().getString(resId));
        textView.setTextColor(Color.WHITE);
        textView.setLayoutParams(params);
        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0,
                mContext.getResources().getDimensionPixelOffset(R.dimen.toast_bottom_magin));
        toast.setDuration(android.widget.Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }
}
