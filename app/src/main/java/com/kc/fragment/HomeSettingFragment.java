package com.kc.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.kc.activity.ClearCacheActivity;
import com.kc.adapter.CommonAdapter;
import com.kc.adapter.ViewHolder;
import com.kc.base.BaseFragment;
import com.kc.bean.DbInfo;
import com.kc.data.DataCenter;
import com.kc.label.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeSettingFragment extends BaseFragment implements View.OnClickListener {

    private Spinner mSpinnerSelectDb;
    private TextView mTvTip;
    private LinearLayout mLLayoutClear;

    public HomeSettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_setting, container, false);
        findViews(view);
        init();
        return view;
    }

    protected void init() {
        List<DbInfo> list = DataCenter.getInstance().getDbInfos();
        if (list == null) {
            mSpinnerSelectDb.setVisibility(View.GONE);
            mTvTip.setVisibility(View.VISIBLE);
            return;
        }
        CommonAdapter adapter = new CommonAdapter<DbInfo>(getActivity(), list, android.R.layout.simple_spinner_dropdown_item) {

            @Override
            public void convert(ViewHolder helper, DbInfo item, int pos) {
                helper.setText(android.R.id.text1, item.getName());
            }
        };
        mSpinnerSelectDb.setAdapter(adapter);
        mSpinnerSelectDb.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e(TAG, "选择了序号：" + i);
                DataCenter.sCurDbIndex = i;
                DataCenter.getInstance().clearDb();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    protected void initData() {

    }

    protected void findViews(View view) {
        mSpinnerSelectDb = (Spinner) view.findViewById(R.id.spinner_select_db);
        mTvTip = (TextView) view.findViewById(R.id.tv_tip);
        mLLayoutClear = getViewById(view, R.id.llayout_setting_clear);

        mLLayoutClear.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llayout_setting_clear:
                startActivity(new Intent(getActivity(), ClearCacheActivity.class));
                break;
        }
    }
}
