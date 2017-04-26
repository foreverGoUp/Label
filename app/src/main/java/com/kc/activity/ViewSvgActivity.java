package com.kc.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.kc.base.BaseActivity;
import com.kc.data.DataHandle;
import com.kc.label.R;
import com.kc.tool.SvgGenerator;
import com.kc.util.AppConstants;
import com.kc.util.FileUtil;

import java.io.File;

public class ViewSvgActivity extends BaseActivity {

    private WebView mWebView;

    private String mFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_svg);
        findViews();
        init();
        initData();
    }

    @Override
    protected void findViews() {
        mWebView = getViewById(R.id.webview);
    }

    @Override
    protected void init() {
        WebSettings settings = mWebView.getSettings();
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);
        settings.setSupportMultipleWindows(true);
        settings.setDefaultTextEncodingName("UTF-8");
        mWebView.setInitialScale(75);

//        mWebView.setWebViewClient(new WebViewClient(){
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                Uri uri = request.getUrl();
//                return super.shouldOverrideUrlLoading(view, request);
//            }
//        });
//        String fp = "file:///android_asset/my.svg";
//        String fp = "file:///"+ FileUtil.getFilePath(null, "my.svg");
//

        mFileName = getIntent().getStringExtra(AppConstants.KEY_FILE_NAME);
        Log.d(TAG, "打开文件:" + mFileName);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    @Override
    protected void initData() {
        TextView tvHead = getViewById(R.id.tv_head);
        if (mFileName == null) {
            finish();
            return;
        }

        tvHead.setText(mFileName);

        String suffix = mFileName.substring(mFileName.lastIndexOf(".") + 1).toLowerCase();
        String fp = null;
        if (suffix.equals("html")) {
            String dbName = DataHandle.getCurDbInfo().getFile();
            String childDir = FileUtil.DIR_APP_SVG + dbName + "/";
            fp = FileUtil.getFilePath(childDir, mFileName);
            Log.e(TAG, "fp=" + fp);

            if (!new File(fp).exists()) {
                String ids = mFileName.substring(0, mFileName.lastIndexOf("."));
                String[] arr = ids.split(",");
                String fDir = FileUtil.getFilePath(childDir, null);
                String content = SvgGenerator.getSvg(Integer.parseInt(arr[2]), fDir);
                if (content == null) {
                    showToast("数据库中不存在该设备的关系");
                    finish();
                    return;
                }
                FileUtil.write(fp, content, false);
            }
        } else {//txt
            fp = FileUtil.getFilePath(FileUtil.DIR_APP_DOC, mFileName);
        }
        mWebView.loadUrl("file://" + fp);
    }

    public static void actionStart(Context context, String fileName) {
        Intent intent = new Intent(context, ViewSvgActivity.class);
        intent.putExtra(AppConstants.KEY_FILE_NAME, fileName);
        context.startActivity(intent);
    }
}
