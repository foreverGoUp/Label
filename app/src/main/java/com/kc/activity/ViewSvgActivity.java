package com.kc.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.kc.base.BaseActivity;
import com.kc.data.DataHandle;
import com.kc.label.R;
import com.kc.tool.SvgGenerator;
import com.kc.util.AppConstants;
import com.kc.util.FileUtil;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class ViewSvgActivity extends BaseActivity {

    private WebView mWebView;
    private TextView mTvHead;

    private String mFileName;

    private WebViewClient mWebViewClient = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            Log.e(TAG,"shouldOverrideUrlLoading:"+url);

            try {
                url = URLDecoder.decode(url, "Utf-8");
                Log.e(TAG, "webview跳转页面：" + url);

                String htmlName = url.substring(url.lastIndexOf("/") + 1);
                String fp = url.substring(AppConstants.URL_HEAD.length());
                Log.e(TAG, "fp=" + fp);
                if (htmlName.contains(AppConstants.DEVICE)) {
                    String devId = htmlName.substring(AppConstants.DEVICE.length(), htmlName.lastIndexOf("."));
                    Log.e(TAG, "devId=" + devId);

                    File file = new File(fp);
                    if (!file.exists()) {
                        String fDir = fp.substring(0, fp.lastIndexOf(File.separator) + 1);
                        String con = SvgGenerator.getSvg(Integer.parseInt(devId), fDir);
                        if (con == null) {
                            showToast("数据库中无此设备的关系");
                        } else {
                            FileUtil.write(fp, con, false);
//                            mWebView.getSettings().setSupportZoom(true);
                            view.loadUrl(url);
                            Log.e(TAG, "创建文件：" + fp);
                        }
                    } else {
                        view.loadUrl(url);
                    }

                } else {
                    String cableId = htmlName.substring(AppConstants.CABLE.length(), htmlName.lastIndexOf("."));
                    Log.e(TAG, "cableId=" + cableId);
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            try {
                url = URLDecoder.decode(url, "Utf-8");
                Log.e(TAG, "onPageStarted：" + url);
                String fileName = url.substring(url.lastIndexOf(File.separator) + 1);
                mTvHead.setText(fileName);
//                mCurHtmlFilePath = url.substring(AppConstants.URL_HEAD.length());
//                String htmlName = url.substring(url.lastIndexOf("/") + 1);
//                if (htmlName.contains("index")){
//                    mWebView.getSettings().setSupportZoom(false);
//                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            super.onPageStarted(view, url, favicon);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_svg);
        findViews();
        init();
        initData();
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void findViews() {
        mWebView = getViewById(R.id.webview);
        mTvHead = getViewById(R.id.tv_head);
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
        mWebView.setWebViewClient(mWebViewClient);

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
        if (mFileName == null) {
            finish();
            return;
        }
        final String fileName = mFileName;

        mTvHead.setText(fileName);

        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        String fp = null;
        if (suffix.contains(AppConstants.DEVICE) || suffix.contains(AppConstants.CABLE)) {
            String curDbName = DataHandle.getCurDbInfo().getName();
            String childDir = new StringBuffer().append(FileUtil.DIR_APP_HTML_PAGE).append(curDbName).append("/").toString();
            fp = FileUtil.getFilePath(childDir, fileName + AppConstants.SUFFIX_HTML);//文件名格式为：device12或cable10
            Log.e(TAG, "fp=" + fp);

            if (!new File(fp).exists()) {
//                String ids = mFileName.substring(0, mFileName.lastIndexOf("."));
//                String[] arr = ids.split(",");
                String fDir = FileUtil.getFilePath(childDir, null);
                String content = null;
                if (suffix.contains(AppConstants.DEVICE)) {
                    String devId = fileName.substring(AppConstants.DEVICE.length());
                    content = SvgGenerator.getSvg(Integer.parseInt(devId), fDir);
                } else {
                    String cableId = fileName.substring(AppConstants.CABLE.length());
                    content = SvgGenerator.getCableSvg();
                }
                if (content == null) {
                    if (suffix.contains(AppConstants.DEVICE)) {
                        showToast("数据库中不存在该设备的关系");
                    } else {
                        showToast("数据库中的柜关系图未开放");
                    }
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
