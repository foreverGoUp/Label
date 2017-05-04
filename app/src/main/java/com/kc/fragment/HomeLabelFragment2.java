package com.kc.fragment;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.kc.base.BaseFragment;
import com.kc.base.BasePopupWindow;
import com.kc.data.DataCenter;
import com.kc.data.DataHandle;
import com.kc.label.R;
import com.kc.tool.HtmlGenerator;
import com.kc.tool.SvgGenerator;
import com.kc.util.AppConstants;
import com.kc.util.FileUtil;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class HomeLabelFragment2 extends BaseFragment implements View.OnClickListener {

    private RelativeLayout mRLayoutContainer;
    private WebView mWebView;
    private int mHasReadDbIndex = -1;
    private String mCurHtmlFilePath = null;

    private WebViewClient mWebViewClient = new WebViewClient() {

        /**
         * 打开网页时调用
         */
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
                    File file = new File(fp);
                    if (!file.exists()) {
                        boolean ret = createFile(url);
                        if (ret){
                            view.loadUrl(url);
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

        /**
         * 打开或回退网页时调用
         * */
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            try {
                url = URLDecoder.decode(url, "Utf-8");
                Log.e(TAG, "onPageStarted：" + url);

                mCurHtmlFilePath = url.substring(AppConstants.URL_HEAD.length());

                String htmlName = url.substring(url.lastIndexOf("/") + 1);
                if (htmlName.equals(AppConstants.INDEX_HTML)) {
                    mIsIndexPage = true;
                } else {
                    mIsIndexPage = false;
                }

                //请求横屏
                if (mIsIndexPage) {
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                } else {
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
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

    private boolean createFile(String url) {
        String htmlName = url.substring(url.lastIndexOf("/") + 1);
        String fp = url.substring(AppConstants.URL_HEAD.length());
        String devId = htmlName.substring(AppConstants.DEVICE.length(), htmlName.lastIndexOf("."));
        Log.e(TAG, "devId=" + devId);
        String fDir = fp.substring(0, fp.lastIndexOf(File.separator) + 1);
        String con = SvgGenerator.getSvg(Integer.parseInt(devId), fDir);
        if (con == null) {
            showToast("数据库中无此设备的关系");
            return false;
        } else {
            FileUtil.write(fp, con, false);
//                            mWebView.getSettings().setSupportZoom(true);
//            view.loadUrl(url);
            Log.e(TAG, "创建文件：" + fp);
            return true;
        }
    }

    public HomeLabelFragment2() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_label2, container, false);
        findViews(view);
        init();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mHasReadDbIndex != DataCenter.sCurDbIndex) {
            initData();
        }
    }

    protected void initData() {
        if (!DataHandle.isDbIndexFileExist()) {
            showToast("检测到无db.json文件！");
            return;
        }
        if (!DataHandle.isDbExist()) {
            showToast("检测到无对应数据库文件！");
            return;
        }
        mHasReadDbIndex = DataCenter.sCurDbIndex;

        String curDbName = DataHandle.getCurDbInfo().getName();
        String dir = new StringBuffer().append(FileUtil.DIR_APP_HTML_PAGE).append(curDbName).append("/").toString();
        String fp = FileUtil.getFilePath(dir, "index.html");
        File file = new File(fp);
        if (!file.exists()) {
            Log.e(TAG, fp + "不存在，正在创建...");
            String dbJsContent = HtmlGenerator.getCurDbJsContent();
            String curDbShortName = DataHandle.getCurDbInfo().getFile();
            Log.e(TAG, curDbShortName + ".js内容：" + "\n" + dbJsContent);
            FileUtil.write("Html/js4db/", curDbShortName + ".js", dbJsContent, false);

            String con = HtmlGenerator.getIndexHtmlContent(curDbShortName);
            Log.e(TAG, "index.html内容：" + "\n" + con);
            FileUtil.write(fp, con, false);
        }
        mWebView.loadUrl("file:///" + fp);
    }


    protected void init() {
        WebSettings localWebSettings = mWebView.getSettings();
        localWebSettings.setLoadWithOverviewMode(true);
        localWebSettings.setJavaScriptEnabled(true);
        localWebSettings.setUseWideViewPort(true);
        localWebSettings.setBuiltInZoomControls(true);
        localWebSettings.setSupportZoom(true);
        localWebSettings.setSupportMultipleWindows(true);
        localWebSettings.setDefaultTextEncodingName("UTF-8");
        mWebView.setInitialScale(100);
        mWebView.setWebViewClient(mWebViewClient);
    }

    private EditText mEtSearchContent;
    protected void findViews(View view) {
        mRLayoutContainer = getViewById(view, R.id.rlayout_label_container);
        mWebView = getViewById(view, R.id.webview_home_label_frag_2);
        mViewSearch = LayoutInflater.from(getActivity()).inflate(R.layout.view_search_dialog, null);
        mEtSearchContent = getViewById(mViewSearch, R.id.et_search_content);
        getViewById(mViewSearch, R.id.bt_search_cancel).setOnClickListener(this);
        getViewById(mViewSearch, R.id.bt_search_ok).setOnClickListener(this);
    }

    private boolean mIsIndexPage = false;
    public boolean backPress() {
        if (!mIsIndexPage && mWebView.canGoBack()) {
            if (DataCenter.sHasClearedCurDbCache) {//清除当前显示的数据库的缓存之后该页面重新初始化网页
                initData();
                DataCenter.sHasClearedCurDbCache = false;
            } else {
                mWebView.goBack();
            }
            return true;
        } else {
            return false;
        }
    }

    public void showSearchArea() {
//        String htmlName = mCurHtmlFilePath.substring(mCurHtmlFilePath.lastIndexOf("/") + 1);
//        Log.e(TAG, "点击搜索时所处页面：" + htmlName);
//        if (htmlName.equals(AppConstants.INDEX_HTML)) {
        if (mIsIndexPage) {
            mWebView.loadUrl("javascript:show();");
        } else {
            showSvgSearchPWindow();
        }
    }

    private BasePopupWindow mSvgSearchPWindow;
    private View mViewSearch;

    private void showSvgSearchPWindow() {
        if (mSvgSearchPWindow == null) {
            mSvgSearchPWindow = new BasePopupWindow(getActivity());
            mSvgSearchPWindow.setOutsideTouchable(false);
            mSvgSearchPWindow.setFocusable(true);
            mSvgSearchPWindow.setContentView(mViewSearch);
            mSvgSearchPWindow.setWidth((int) (this.getActivity().getResources().getDisplayMetrics().widthPixels * 0.75));
        }
        mSvgSearchPWindow.showAtLocation(mRLayoutContainer, Gravity.CENTER, 0, 0);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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

    private final String STR_TAG = "</text>";
    private final String STR_TAG_SUFFIX = ">";
    private final String STR_WHITE = "white";
    private final String STR_RED = "red";

    private void searchSvgContent(String sC) {
        String svgC = FileUtil.read(mCurHtmlFilePath);
        String[] arr = svgC.split(STR_TAG);
        int len = arr.length;

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < len; i++) {
            //恢复未搜索状态
            arr[i] = arr[i].replaceAll(STR_RED, STR_WHITE);

            String text = arr[i].substring(arr[i].lastIndexOf(STR_TAG_SUFFIX) + 1);
            if (text.contains(sC)) {
                sb.append(arr[i].replaceAll(STR_WHITE, STR_RED));
            } else {
                sb.append(arr[i]);
            }
            if (i != len - 1) {
                sb.append(STR_TAG);
            }
        }

        FileUtil.write(mCurHtmlFilePath, sb.toString(), false);
        Log.e(TAG, "搜索svg完毕");
        mWebView.loadUrl(AppConstants.URL_HEAD + mCurHtmlFilePath);
    }

}
