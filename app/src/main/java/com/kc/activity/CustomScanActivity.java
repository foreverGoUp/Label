package com.kc.activity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.kc.base.BaseActivity;
import com.kc.label.R;

public class CustomScanActivity extends BaseActivity implements DecoratedBarcodeView.TorchListener {

    private DecoratedBarcodeView mDBV;
    private CaptureManager captureManager;
    private boolean isLightOn = false;
    private ImageView mIvLight;

    @Override
    protected void onPause() {
        super.onPause();
        captureManager.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        captureManager.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        captureManager.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        captureManager.onSaveInstanceState(outState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mDBV.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_scan);
        findViews();
        init();
        initData();

        //重要代码，初始化捕获
        captureManager = new CaptureManager(this, mDBV);
        captureManager.initializeFromIntent(getIntent(), savedInstanceState);
        captureManager.decode();
    }

    @Override
    protected void findViews() {
        mIvLight = (ImageView) findViewById(R.id.btn_switch);
        mDBV = (DecoratedBarcodeView) findViewById(R.id.dbv_custom);
    }

    @Override
    protected void init() {
        mDBV.setTorchListener(this);

        // 如果没有闪光灯功能，就去掉相关按钮
        if (!hasFlash()) {
            mIvLight.setVisibility(View.GONE);
        }

    }

    @Override
    protected void initData() {

    }

    // torch 手电筒
    @Override
    public void onTorchOn() {
//        Toast.makeText(this,"torch on",Toast.LENGTH_LONG).show();
        isLightOn = true;
    }

    @Override
    public void onTorchOff() {
//        Toast.makeText(this,"torch off",Toast.LENGTH_LONG).show();
        isLightOn = false;
    }

    // 判断是否有闪光灯功能
    private boolean hasFlash() {
        return getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    // 点击切换闪光灯
    public void swichLight(View view) {
        if (isLightOn) {
            mDBV.setTorchOff();
            mIvLight.setSelected(false);
        } else {
            mDBV.setTorchOn();
            mIvLight.setSelected(true);
        }
    }

}
