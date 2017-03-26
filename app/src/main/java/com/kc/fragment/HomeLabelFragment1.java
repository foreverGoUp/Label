package com.kc.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kc.activity.ViewSvgActivity;
import com.kc.base.BaseFragment;
import com.kc.bean.Cubicle;
import com.kc.bean.Device;
import com.kc.bean.RoomInfo;
import com.kc.data.DataCenter;
import com.kc.data.DataHandle;
import com.kc.label.R;

import java.util.List;

public class HomeLabelFragment1 extends BaseFragment implements View.OnClickListener, TextView.OnEditorActionListener {

    private LinearLayout mLlayoutContainer, mLLayoutSearchArea;
    private EditText mEtSearchContent;
    private ImageView mIvSearchClear;
    private TextView mTvSearchCancel;

    private int mHasReadDbIndex = -1;

    public HomeLabelFragment1() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_label, container, false);
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
        mLlayoutContainer.removeAllViews();

        if (!DataHandle.isDbIndexFileExist()) {
            showToast("检测到无db.json文件！");
            return;
        }
        if (!DataHandle.isDbExist()) {
            showToast("检测到无对应数据库文件！");
            return;
        }
        mHasReadDbIndex = DataCenter.sCurDbIndex;

        List<RoomInfo> roomInfos = DataHandle.getRoomInfos();
        List<Cubicle> cubicles = null;
        List<Device> devices = null;

        for (int i = 0; i < roomInfos.size(); i++) {
            int roomId = roomInfos.get(i).getRoomId();

            TextView tvRoomName = getTvRoom(roomInfos.get(i).getName());
            mLlayoutContainer.addView(tvRoomName);
            //顶边分割线
            mLlayoutContainer.addView(getDivider(false));

            cubicles = DataHandle.getCubicles(roomId);
            for (int j = 0; j < cubicles.size(); j++) {
                int cubicleId = cubicles.get(j).getCubicleId();

                TextView tvCubicleName = getTvCubicle(cubicles.get(j).getName()
                        , "" + roomId + "," + cubicleId);
                mLlayoutContainer.addView(tvCubicleName);

                devices = DataHandle.getDevices(cubicleId);
                genDevicesUI(devices, roomId, cubicleId);

                //柜底部分割线
                mLlayoutContainer.addView(getDivider(true));
            }

//            mLlayoutContainer.addView(getDivider());
        }
    }

    private void genDevicesUI(List<Device> devices, int roomId, int cubicleId) {
        //                Log.e(TAG, "devices size="+devices.size());
        LinearLayout childLLayout = null;
        for (int k = 0; k < devices.size(); k++) {
            if (k % 4 == 0) {
                if (k != 0) {
                    mLlayoutContainer.addView(childLLayout);
                }
                childLLayout = getHorLLayout();
            }
            int deviceId = devices.get(k).getDeviceId();

            TextView tvDeviceName = getTvDevice(devices.get(k).getName()
                    , "" + roomId + "," + cubicleId + "," + deviceId);
            childLLayout.addView(tvDeviceName);

            if (k == devices.size() - 1) {
                int extra = 4 - devices.size() % 4;
                if (extra == 4) {
                    extra = 0;
                }
                for (int l = 0; l < extra; l++) {
//                            Log.e(TAG, "填充次数="+l);
                    childLLayout.addView(getTvInflater());
                }

                mLlayoutContainer.addView(childLLayout);
            }
        }
    }

    protected void init() {
        mEtSearchContent.setOnEditorActionListener(this);

        mIvSearchClear.setOnClickListener(this);
        mTvSearchCancel.setOnClickListener(this);
    }

    protected void findViews(View view) {
        mLlayoutContainer = getViewById(view, R.id.llayout_label_frag_container);
        mLLayoutSearchArea = getViewById(view, R.id.llayout_search_area);
        mEtSearchContent = getViewById(view, R.id.et_search_content);
        mIvSearchClear = getViewById(view, R.id.iv_search_clear);
        mTvSearchCancel = getViewById(view, R.id.tv_search_cancel);
    }


    private TextView getTvRoom(String name) {
        TextView tv = new TextView(getActivity());
        tv.setText(name);
        tv.setTextSize(30);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 30, 0, 0);
        tv.setLayoutParams(lp);
        tv.setGravity(Gravity.CENTER_HORIZONTAL);
        if (checkSearch(name)) {
            tv.setTextColor(Color.parseColor("#ff0000"));
        } else {
            tv.setTextColor(Color.parseColor("#000000"));
        }

        return tv;
    }

    private TextView getTvCubicle(String name, String tag) {
        TextView tv = new TextView(getActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(BLANK_NOR, BLANK_NOR * 2, 0, 0);
        tv.setLayoutParams(lp);
        tv.setText(name);
        tv.setTag(tag);
        tv.setTextSize(15);
//        tv.setOnClickListener(this);
        if (checkSearch(name)) {
            tv.setTextColor(Color.parseColor("#ff0000"));
        } else {
            tv.setTextColor(Color.parseColor("#000000"));
        }

        return tv;
    }

    private TextView getTvDevice(String name, String tag) {
        TextView tv = new TextView(getActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0
                , 50
                , 1.0f);
        tv.setGravity(Gravity.CENTER_VERTICAL);
        tv.setLayoutParams(lp);
        tv.setText(name);
        tv.setTag(tag);
        tv.setTextSize(13);
        tv.setBackground(getResources().getDrawable(R.drawable.selector_bg_text));
        tv.setOnClickListener(this);
        if (checkSearch(name)) {
            tv.setTextColor(Color.parseColor("#00ff00"));
        } else {
            tv.setTextColor(Color.parseColor("#000000"));
        }

        return tv;
    }

    private TextView getTvInflater() {
        TextView tv = new TextView(getActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0
                , 10
                , 1.0f);
        tv.setGravity(Gravity.CENTER);
        tv.setLayoutParams(lp);
        return tv;
    }

    private boolean checkSearch(String name) {
        if (DataCenter.sSearchC != null && !DataCenter.sSearchC.equals("")) {
            if (name.contains(DataCenter.sSearchC)) {
                return true;
            }
        }
        return false;
    }

    private static final int BLANK_NOR = 20;

    private View getDivider(boolean marginL) {
        View view = new TextView(getActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT
                , 1);
//        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
        if (marginL) {
            lp.setMargins(BLANK_NOR, 0, BLANK_NOR, 0);
        }
        view.setLayoutParams(lp);
        view.setBackgroundColor(Color.parseColor("#000000"));
        return view;
    }

    private LinearLayout getHorLLayout() {
        LinearLayout view = new LinearLayout(getActivity());
        view.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(BLANK_NOR, 0, BLANK_NOR, 0);
        view.setLayoutParams(lp);
//        view.setBackgroundColor(Color.parseColor("#cccccc"));
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getTag() != null) {
            showToast("点击了id=" + v.getTag());
            Log.e(TAG, "点击了id=" + v.getTag());
            ViewSvgActivity.actionStart(getActivity(), v.getTag() + ".html");
            return;
        }

        switch (v.getId()) {
            case R.id.et_search_content:

                break;
            case R.id.iv_search_clear:
                mEtSearchContent.setText(null);
                break;
            case R.id.tv_search_cancel:
                mLLayoutSearchArea.setVisibility(View.GONE);
                // 先隐藏键盘
                hideKeyboard();
                break;
        }
    }

    public void showSearchArea() {
        mLLayoutSearchArea.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onEditorAction(TextView textView, int action, KeyEvent keyEvent) {
        if (action == EditorInfo.IME_ACTION_SEARCH || action == EditorInfo.IME_ACTION_UNSPECIFIED) {
            Log.d(TAG, "收到搜索动作");
            // 先隐藏键盘
            hideKeyboard();

            String sC = mEtSearchContent.getText().toString();
            if (TextUtils.isEmpty(sC)) {
                showToast("输入无效");
            } else {
                DataCenter.sSearchC = sC;
                initData();
            }
            return true;
        }
        return false;
    }

    private void hideKeyboard() {
//        View view = getActivity().getCurrentFocus();
        ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(mEtSearchContent.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
