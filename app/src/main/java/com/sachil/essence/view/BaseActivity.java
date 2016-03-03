package com.sachil.essence.view;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    protected Handler UI_THREAD = new Handler(Looper.getMainLooper());

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setStatusBar();
    }

    protected void setStatusBar(){

    }
}
