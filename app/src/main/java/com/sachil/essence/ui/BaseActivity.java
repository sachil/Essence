package com.sachil.essence.ui;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;

import com.sachil.essence.R;
import com.sachil.essence.StatusBarUtils;

public class BaseActivity extends AppCompatActivity {
    protected Handler UI_THREAD = new Handler(Looper.getMainLooper());
    protected int mThemeColor = 0;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setStatusBar();
    }

    protected int getThemeColor(){
        if(mThemeColor == 0){
            TypedValue value = new TypedValue();
            getTheme().resolveAttribute(R.attr.colorPrimary, value, true);
            return value.data;
        }else
            return mThemeColor;
    }

    protected int getNormalTextColor(){
        return getResources().getColor(R.color.grey_600);
    }

    protected void setStatusBar() {
        StatusBarUtils.setColor(this, getThemeColor());
    }

    protected void changeTheme(int color){
        mThemeColor = color;
    }

}
