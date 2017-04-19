package com.kc.fragment;

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
import com.kc.util.FileUtil;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class HomeLabelFragment2 extends BaseFragment implements View.OnClickListener {

    private RelativeLayout mRLayoutContainer;
    private WebView mWebView;
    private int mHasReadDbIndex = -1;
    private static final String DEVICE = "device";
    private static final String CABLE = "cable";
    private static final String URL_HEAD = "file:///";
    private String mCurHtmlFilePath = null;

    private WebViewClient mWebViewClient = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            Log.e(TAG,"shouldOverrideUrlLoading:"+url);

            try {
                url = URLDecoder.decode(url, "Utf-8");
                Log.e(TAG, "webview跳转页面：" + url);

                String htmlName = url.substring(url.lastIndexOf("/") + 1);
                String fp = url.substring(URL_HEAD.length());
                Log.e(TAG, "fp=" + fp);
                if (htmlName.contains(DEVICE)) {
                    String devId = htmlName.substring(DEVICE.length(), htmlName.lastIndexOf("."));
                    Log.e(TAG, "devId=" + devId);

                    File file = new File(fp);
                    if (!file.exists()) {
                        String con = SvgGenerator.getSvg(Integer.parseInt(devId));
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
                    String cableId = htmlName.substring(CABLE.length(), htmlName.lastIndexOf("."));
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

                mCurHtmlFilePath = url.substring(URL_HEAD.length());
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
        String dir = new StringBuffer().append("Html/page/").append(curDbName).append("/").toString();
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

    public boolean backPress() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        } else {
            return false;
        }
    }

    public void showSearchArea() {
        String htmlName = mCurHtmlFilePath.substring(mCurHtmlFilePath.lastIndexOf("/") + 1);
        Log.e(TAG, "点击搜索时所处页面：" + htmlName);
        if (htmlName.contains("index")) {
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
        mWebView.loadUrl(URL_HEAD + mCurHtmlFilePath);
    }

}
