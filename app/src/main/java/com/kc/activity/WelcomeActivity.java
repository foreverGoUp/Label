package com.kc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.kc.base.BaseActivity;
import com.kc.label.R;
import com.kc.util.MyApp;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;
import com.zhy.m.permission.ShowRequestPermissionRationale;

public class WelcomeActivity extends BaseActivity {

    private TextView mTvTip;
    private ImageView mIvLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mTvTip = (TextView) findViewById(R.id.tv_tip);
        mIvLoading = (ImageView) findViewById(R.id.iv_loading);

        startAnimation();
//        requestPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE
//                ,Manifest.permission.CAMERA);
    }


    @PermissionGrant(0)
    public void onGrant() {
//        showToast("已授权");
        mTvTip.setText("正在准备数据...");
        MyApp.initDefaultFile();
        grantFinish();
    }

    @PermissionDenied(0)
    public void onDenied() {
        showToast("授权失败，将无法正常使用软件。");
        grantFinish();
    }

    @ShowRequestPermissionRationale(0)
    public void onShowExplain() {
        showToast("需要授权才能正常使用该软件的重要功能。");
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void init() {

    }

    @Override
    protected void initData() {

    }

    private void grantFinish() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeActivity.this, HomeActivity.class));
                WelcomeActivity.this.finish();
            }
        }, 2000);
    }

    private void startAnimation() {
        Animation operatingAnim = AnimationUtils.loadAnimation(this, R.anim.rotate_loading);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);

        mIvLoading.startAnimation(operatingAnim);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mIvLoading.clearAnimation();
    }
}
