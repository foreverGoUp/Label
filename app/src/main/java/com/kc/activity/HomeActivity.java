package com.kc.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.kc.activity.openfile.PPTActivity;
import com.kc.activity.openfile.PdfActivity;
import com.kc.base.BaseActivity;
import com.kc.data.DataHandle;
import com.kc.fragment.HomeDocFragment;
import com.kc.fragment.HomeLabelFragment2;
import com.kc.fragment.HomeScanFragment;
import com.kc.fragment.HomeSearchFragment;
import com.kc.fragment.HomeSettingFragment;
import com.kc.label.R;
import com.kc.util.FileUtil;
import com.kc.util.MyApp;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;
import com.zhy.m.permission.ShowRequestPermissionRationale;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class HomeActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTvTitle;
    private LinearLayout mLLayoutLabelLabel, mLLayoutLabelDoc, mLLayoutLabelSearch, mLLayoutLabelSetting;

    private FragmentManager mFManager;
    private HomeLabelFragment2 mLabelFragment = new HomeLabelFragment2();
    private HomeDocFragment mDocFragment = new HomeDocFragment();
    private HomeScanFragment mScanFragment = new HomeScanFragment();
    private HomeSearchFragment mSearchFragment = new HomeSearchFragment();
    private HomeSettingFragment mSettingFragment = new HomeSettingFragment();

    private final String TAG_FRAG_LABEL = "label";
    private final String TAG_FRAG_DOC = "doc";
    private final String TAG_FRAG_SCAN = "scan";
    private final String TAG_FRAG_SEARCH = "search";
    private final String TAG_FRAG_SETTING = "setting";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findViews();
        init();
        initData();
    }

    @Override
    protected void findViews() {
        mTvTitle = (TextView) findViewById(R.id.tv_home_acti_title);
        mLLayoutLabelLabel = getViewById(R.id.llayout_home_label_label);
        mLLayoutLabelDoc = getViewById(R.id.llayout_home_label_docu);
        mLLayoutLabelSearch = getViewById(R.id.llayout_home_label_search);
        mLLayoutLabelSetting = getViewById(R.id.llayout_home_label_setting);
    }

    @Override
    protected void init() {
        mFManager = getSupportFragmentManager();
        FragmentTransaction ft = mFManager.beginTransaction();
        ft.add(R.id.rLayout_content, mLabelFragment, TAG_FRAG_LABEL);
        ft.add(R.id.rLayout_content, mDocFragment, TAG_FRAG_DOC);
        ft.add(R.id.rLayout_content, mScanFragment, TAG_FRAG_SCAN);
        ft.add(R.id.rLayout_content, mSearchFragment, TAG_FRAG_SEARCH);
        ft.add(R.id.rLayout_content, mSettingFragment, TAG_FRAG_SETTING);
        ft.show(mLabelFragment).hide(mDocFragment).hide(mScanFragment).hide(mSearchFragment).hide(mSettingFragment);
        ft.commit();
        mCurShowFragment = mLabelFragment;

        mLLayoutLabelLabel.setSelected(true);

        requestPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.CAMERA);

    }

    @Override
    protected void initData() {
        mTvTitle.setText(DataHandle.getCurDbInfo().getName());

        try {
            String[] aa = getAssets().list("");
            int len = aa.length;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchFragmentToLabel() {
        switchFragment(mLabelFragment);
    }


    private void switchFragment(Fragment fragment) {
        mCurShowFragment = fragment;
        FragmentTransaction ft = mFManager.beginTransaction();
        List<Fragment> fragments = mFManager.getFragments();
        for (int i = 0; i < fragments.size(); i++) {
            if (fragments.get(i).getClass() == fragment.getClass()) {
                ft.show(fragments.get(i));
                fragments.get(i).onResume();//手动调起片段的生命周期
            } else {
                ft.hide(fragments.get(i));
            }
        }
        ft.commit();
    }

    private Fragment mCurShowFragment;
    private long mBackPressTime;

    private AlertDialog mDialog;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        if (mCurShowFragment == mDocFragment){
//            if (!mDocFragment.backPressed()){
//                this.moveTaskToBack(true);
//            }
//        }else {
//            this.moveTaskToBack(true);
//        }

//        long curTime = System.currentTimeMillis();
//        if (curTime - mBackPressTime<2000){
//            this.finish();
//        }else {
//            showToast("再按一次退出应用");
//            mBackPressTime = curTime;
//        }

        if (!mLabelFragment.backPress()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("确认退出？");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    HomeActivity.this.finish();
                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    mDialog.dismiss();
                }
            });
            mDialog = builder.create();
            mDialog.show();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llayout_home_label_label:
                mTvTitle.setText(DataHandle.getCurDbInfo().getName());
                switchFragment(mLabelFragment);
                mLLayoutLabelLabel.setSelected(true);
                mLLayoutLabelDoc.setSelected(false);
                mLLayoutLabelSearch.setSelected(false);
                mLLayoutLabelSetting.setSelected(false);
                break;
            case R.id.llayout_home_label_docu:
                switchFragment(mDocFragment);
                mTvTitle.setText("文档");
                mLLayoutLabelLabel.setSelected(false);
                mLLayoutLabelDoc.setSelected(true);
                mLLayoutLabelSearch.setSelected(false);
                mLLayoutLabelSetting.setSelected(false);
                break;
            case R.id.iv_home_label_scan:
                mTvTitle.setText("扫描");
                customScan();
                break;
            case R.id.llayout_home_label_search:
//                switchFragment(mSearchFragment);
//                mTvTitle.setText("搜索");
//                mIvLabelLabel.setSelected(false);
//                mLLayoutLabelDoc.setSelected(false);
//                mLLayoutLabelSearch.setSelected(true);
//                mLLayoutLabelSetting.setSelected(false);

                mTvTitle.setText(DataHandle.getCurDbInfo().getName());
                switchFragment(mLabelFragment);
                mLLayoutLabelLabel.setSelected(true);
                mLLayoutLabelDoc.setSelected(false);
                mLLayoutLabelSearch.setSelected(false);
                mLLayoutLabelSetting.setSelected(false);
                mLabelFragment.showSearchArea();

                break;
            case R.id.llayout_home_label_setting:
                switchFragment(mSettingFragment);
                mTvTitle.setText("设置");
                mLLayoutLabelLabel.setSelected(false);
                mLLayoutLabelDoc.setSelected(false);
                mLLayoutLabelSearch.setSelected(false);
                mLLayoutLabelSetting.setSelected(true);

//                startActivity(new Intent(this, PdfActivity.class));
//                startActivity(new Intent(this, PPTActivity.class));
                break;
        }
    }

    // 你也可以使用简单的扫描功能，但是一般扫描的样式和行为都是可以自定义的，这里就写关于自定义的代码了
    // 你可以把这个方法作为一个点击事件
    public void customScan() {
        new IntentIntegrator(this)
                .setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
                .setPrompt("将二维码/条码放入框内，即可自动扫描")//写那句提示的话
                .setOrientationLocked(false)
                .setCaptureActivity(CustomScanActivity.class) // 设置自定义的activity是CustomActivity
                .initiateScan(); // 初始化扫描
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "扫描结束");
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            if (TextUtils.isEmpty(intentResult.getContents())) {
                showToast("未扫描到内容");
            } else {
                // ScanResult 为 获取到的字符串
                String ScanResult = intentResult.getContents();
                showToast("扫描成功：" + ScanResult);
                Log.e(TAG, "扫描成功：" + ScanResult);

                handleScanResult(ScanResult);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleScanResult(String result) {
        openFile(this, result);
    }

    public static void openFile(Context activity, String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return;
        }
        String fileSuffix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        Log.d("HomeActivity", "打开文件的后缀:" + fileSuffix);

        switch (fileSuffix) {
            case "ppt":
                PPTActivity.actionStart(activity, fileName);
                break;
            case "pdf":
                PdfActivity.actionStart(activity, fileName);
                break;
            case "html":
            case "txt":
                ViewSvgActivity.actionStart(activity, fileName);
                break;
            default:
                String fp = FileUtil.getFilePath(FileUtil.DIR_APP_DOC, fileName);
                FileUtil.openFileByOtherApp(activity, new File(fp));
                break;
        }
    }

    @PermissionGrant(0)
    public void onGrant() {
//        showToast("已授权");
        MyApp.initDefaultFile();
    }

    @PermissionDenied(0)
    public void onDenied() {
        showToast("授权失败，将无法正常使用软件。");
    }

    @ShowRequestPermissionRationale(0)
    public void onShowExplain() {
        showToast("需要授权才能正常使用该软件的重要功能。");
    }
}
