package com.sachil.essence.view;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;

import com.sachil.essence.R;
import com.sachil.essence.StatusBarUtils;

public class BaseActivity extends AppCompatActivity {
    protected Handler UI_THREAD = new Handler(Looper.getMainLooper());

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setStatusBar();
    }

    protected int getPrimaryColor(){
        TypedValue value = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, value, true);
        return value.data;
    }

    protected void setStatusBar() {
        StatusBarUtils.setColor(this, getPrimaryColor());
    }

}
