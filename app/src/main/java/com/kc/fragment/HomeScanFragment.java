package com.kc.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kc.base.BaseFragment;
import com.kc.label.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeScanFragment extends BaseFragment {

    private TextView mTvScanContent;

    public HomeScanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_scan, container, false);
        mTvScanContent = (TextView) view.findViewById(R.id.tv_scan_content);
        return view;
    }

    @Override
    protected void findViews(View view) {

    }

    @Override
    protected void init() {

    }

    @Override
    protected void initData() {

    }

    public void outputScanResult(String content) {
        mTvScanContent.setText(content);
    }

}
