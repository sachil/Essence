package com.sachil.essence.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import com.sachil.essence.R;

public class WelcomeActivity extends BaseActivity {
    private ImageView mLogoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initView();
        playAnimation();
        UI_THREAD.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                WelcomeActivity.this.finish();
            }
        }, 3000);
    }

    private void initView() {
        mLogoView = (ImageView) findViewById(R.id.welcome_logo);
    }

    private void playAnimation() {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(mLogoView, "scaleX", 0.8f, 1.0f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(mLogoView, "scaleY", 0.8f, 1.0f);
        scaleX.setRepeatCount(ObjectAnimator.INFINITE);
        scaleY.setRepeatCount(ObjectAnimator.INFINITE);
        scaleX.setRepeatMode(ObjectAnimator.REVERSE);
        scaleY.setRepeatMode(ObjectAnimator.REVERSE);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(300);
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.playTogether(scaleX, scaleY);
        animatorSet.start();
    }
}
