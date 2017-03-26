package com.kc.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.kc.activity.HomeActivity;
import com.zhy.m.permission.MPermissions;

/**
 * Created by Administrator on 2017/2/28.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected String TAG = this.getClass().getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    protected void showToast(String content) {
        if (content.length() < 10) {
            Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, content, Toast.LENGTH_LONG).show();
        }
    }

    //6.0权限部分开始----------------------------
    /*
    * 注意：若请求码固定为0对于多处地方不同申请的权限有影响，可以改成传参形式。
    *
    * 这是活动中发起权限请求的方法。
    * */
    protected void requestPermissions(String... permissions) {
        if (!shouldShowRequestPermissionRationale(this, 0, permissions)) {
            MPermissions.requestPermissions(this, 0, permissions);
        }
    }

    private boolean shouldShowRequestPermissionRationale(AppCompatActivity activity, int requestCode, String... permissions) {
        for (int i = 0; i < permissions.length; i++) {
            if (MPermissions.shouldShowRequestPermissionRationale(activity
                    , permissions[i], requestCode)) {
                return true;
            }
        }
        return false;
    }

    /*
    * 必须重写此方法，然后必须在发起权限请求的活动中在你想要回调的方法上使用@PermissionGrant、
    * @PermissionDenied、@ShowRequestPermissionRationale
    * 三者中的一个进行注释，运行后进行权限申请后会根据对应情况回调所注释的3个方法，3个注释分别表示
    * 已授权、未授权、显示申请理由。
    * */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    //6.0权限部分结束----------------------------

    public void onBackClick(View view) {
        this.finish();
    }

    @Override
    public void onBackPressed() {
        if (this.getClass() != HomeActivity.class) {
            this.finish();
        }
    }

    protected abstract void findViews();

    protected abstract void init();

    protected abstract void initData();

    protected <T extends View> T getViewById(int id) {
        return (T) findViewById(id);
    }
}
