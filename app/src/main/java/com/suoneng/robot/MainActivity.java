package com.suoneng.robot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.suoneng.robot.utils.RoToast;

public class MainActivity extends AppCompatActivity {

    private long mClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onBackPressed() {
        exit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    private void exit() {
        if ((System.currentTimeMillis() - mClickTime) > 2000) {
            RoToast.showToast(this, getString(R.string.exit));
            mClickTime = System.currentTimeMillis();
        } else {
            this.finish();
        }
    }
}


