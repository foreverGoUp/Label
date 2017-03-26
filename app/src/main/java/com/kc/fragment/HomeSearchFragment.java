package com.kc.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.kc.activity.HomeActivity;
import com.kc.base.BaseFragment;
import com.kc.data.DataCenter;
import com.kc.label.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeSearchFragment extends BaseFragment implements View.OnClickListener {

    private EditText mEtSearchContent;
    private Button mBtSearch, mBtSearchCancel;

    public HomeSearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_search, container, false);
        findViews(view);
        init();
        initData();
        return view;
    }

    @Override
    protected void findViews(View view) {
        mEtSearchContent = (EditText) view.findViewById(R.id.et_search_content);
        mBtSearch = (Button) view.findViewById(R.id.bt_search);
        mBtSearchCancel = (Button) view.findViewById(R.id.bt_search_cancel);
        mBtSearch.setOnClickListener(this);
        mBtSearchCancel.setOnClickListener(this);
    }

    @Override
    protected void init() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_search:
                String con = mEtSearchContent.getText().toString();
                if (con != null && !con.equals("")) {
                    DataCenter.sSearchC = con;
                    ((HomeActivity) getActivity()).switchFragmentToLabel();
                } else {
                    showToast("输入无效");
                }
                break;
            case R.id.bt_search_cancel:
                mEtSearchContent.setText(null);
                DataCenter.sSearchC = null;
                break;
        }
    }
}
