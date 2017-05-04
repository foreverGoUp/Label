package com.kc.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kc.base.BaseActivity;
import com.kc.base.BasePopupWindow;
import com.kc.data.DataHandle;
import com.kc.label.R;
import com.kc.tool.SvgGenerator;
import com.kc.util.AppConstants;
import com.kc.util.FileUtil;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class ViewSvgActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout mRLayoutContainer;
    private WebView mWebView;
    private TextView mTvHead;

    private String mFileName;
    private String mCurHtmlFilePath = null;

    private WebViewClient mWebViewClient = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            Log.e(TAG,"shouldOverrideUrlLoading:"+url);

            try {
                url = URLDecoder.decode(url, "Utf-8");
                Log.e(TAG, "webview跳转页面：" + url);

                String htmlName = url.substring(url.lastIndexOf("/") + 1);
                String suffix = htmlName.substring(htmlName.lastIndexOf(".") + 1).toLowerCase();
                Log.e(TAG, "shouldOverrideUrlLoading 得到打开文件的后缀：" + suffix);
                String fp = url.substring(AppConstants.URL_HEAD.length());
                Log.e(TAG, "fp=" + fp);
                if (suffix.equals(AppConstants.SUFFIX_HTML)) {//svg网页
                    if (htmlName.contains(AppConstants.DEVICE)) {
                        String devId = htmlName.substring(AppConstants.DEVICE.length(), htmlName.lastIndexOf("."));
                        Log.e(TAG, "devId=" + devId);
                        File file = new File(fp);
                        if (!file.exists()) {
                            String fDir = fp.substring(0, fp.lastIndexOf(File.separator) + 1);
                            String con = SvgGenerator.getSvg(Integer.parseInt(devId), fDir);
                            if (con == null) {
                                showToast("数据库中无此设备的关系");
//                                finish();

                            } else {
                                FileUtil.write(fp, con, false);
//                            mWebView.getSettings().setSupportZoom(true);
                                view.loadUrl(url);
                                Log.e(TAG, "创建文件：" + fp);
                            }
                        } else {
                            view.loadUrl(url);
                        }

                    } else if (htmlName.contains(AppConstants.CABLE)) {
//                        String cableId = htmlName.substring(AppConstants.CABLE.length(), htmlName.lastIndexOf("."));
//                        Log.e(TAG, "cableId=" + cableId);
                        showToast("数据库中的柜关系图未开放");
//                        finish();

                    } else {
                        Log.e(TAG, "!!!未处理网页名称：" + url);
                    }
                } else if (suffix.equals(AppConstants.SUFFIX_TXT)) {//如果是txt文本
                    view.loadUrl(url);
                } else {
                    Log.e(TAG, "!!!未处理文件名称：" + url);
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

                mCurHtmlFilePath = url.substring(AppConstants.URL_HEAD.length());

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
        mRLayoutContainer = getViewById(R.id.rlayout_view_svg_container);

        mViewSearch = LayoutInflater.from(this).inflate(R.layout.view_search_dialog, null);
        mEtSearchContent = getViewById(mViewSearch, R.id.et_search_content);
        getViewById(mViewSearch, R.id.bt_search_cancel).setOnClickListener(this);
        getViewById(mViewSearch, R.id.bt_search_ok).setOnClickListener(this);

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

        //判断是否显示搜索按钮
        String suffix = mFileName.substring(mFileName.lastIndexOf(".") + 1);
        if (!suffix.equals(AppConstants.SUFFIX_TXT)) {
            getViewById(R.id.iv_com_head_search).setVisibility(View.VISIBLE);
        }

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
            fp = FileUtil.getFilePath(childDir, fileName + AppConstants.STR_POINT + AppConstants.SUFFIX_HTML);//文件名格式为：device12或cable10
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

    private EditText mEtSearchContent;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_com_head_search:
                showSvgSearchPWindow();
                break;
            case R.id.bt_search_ok:
                String sC = mEtSearchContent.getText().toString();
                if (sC.equals("")) {
                    showToast("输入无效");
                    return;
                }
                searchSvgContent(sC);

            case R.id.bt_search_cancel:
                mSvgSearchPWindow.dismiss();
                break;
        }
    }

    private BasePopupWindow mSvgSearchPWindow;
    private View mViewSearch;

    private void showSvgSearchPWindow() {
        if (mSvgSearchPWindow == null) {
            mSvgSearchPWindow = new BasePopupWindow(this);
            mSvgSearchPWindow.setOutsideTouchable(false);
            mSvgSearchPWindow.setFocusable(true);
            mSvgSearchPWindow.setContentView(mViewSearch);
            mSvgSearchPWindow.setWidth((int) (this.getResources().getDisplayMetrics().widthPixels * 0.75));
        }
        mSvgSearchPWindow.showAtLocation(mRLayoutContainer, Gravity.CENTER, 0, 0);
    }

    private void searchSvgContent(String sC) {
        String svgC = FileUtil.read(mCurHtmlFilePath);
        String[] arr = svgC.split(AppConstants.STR_TAG);
        int len = arr.length;

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < len; i++) {
            //恢复未搜索状态
            arr[i] = arr[i].replaceAll(AppConstants.STR_RED, AppConstants.STR_WHITE);

            String text = arr[i].substring(arr[i].lastIndexOf(AppConstants.STR_TAG_SUFFIX) + 1);
            if (text.contains(sC)) {
                sb.append(arr[i].replaceAll(AppConstants.STR_WHITE, AppConstants.STR_RED));
            } else {
                sb.append(arr[i]);
            }
            if (i != len - 1) {
                sb.append(AppConstants.STR_TAG);
            }
        }

        FileUtil.write(mCurHtmlFilePath, sb.toString(), false);
        Log.e(TAG, "搜索svg完毕");
        mWebView.loadUrl(AppConstants.URL_HEAD + mCurHtmlFilePath);
    }
}
