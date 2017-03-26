package com.kc.activity.openfile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.itsrts.pptviewer.PPTViewer;
import com.kc.base.BaseActivity;
import com.kc.data.DataHandle;
import com.kc.label.R;
import com.kc.util.AppConstants;
import com.kc.util.FileUtil;

import java.io.File;

public class PPTActivity extends BaseActivity {

    PPTViewer pptViewer;
    private String mFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ppt);
        findViews();
        init();
        initData();

    }

    @Override
    protected void findViews() {
        pptViewer = (PPTViewer) findViewById(R.id.pptviewer);
    }

    @Override
    protected void init() {
        pptViewer.setNext_img(R.drawable.ppt_next).setPrev_img(R.drawable.ppt_last)
                .setSettings_img(R.drawable.ppt_setting)
                .setZoomin_img(R.drawable.ppt_bigger)
                .setZoomout_img(R.drawable.ppt_smaller);

        mFileName = getIntent().getStringExtra(AppConstants.KEY_FILE_NAME);
    }

    @Override
    protected void initData() {
        TextView tvHead = getViewById(R.id.tv_head);
        if (mFileName == null) {
            finish();
            return;
        }

        tvHead.setText(mFileName);

        String dbName = DataHandle.getCurDbInfo().getFile();
        String childDir = FileUtil.DIR_APP_DOC;
        String fp = FileUtil.getFilePath(childDir, mFileName);
        Log.e(TAG, "fp=" + fp);

        if (new File(fp).exists()) {
            pptViewer.loadPPT(this, fp);//不支持pptx

        } else {
            showToast("文件不存在：" + fp);
            finish();
        }
    }

    public static void actionStart(Context context, String fileName) {
        Intent intent = new Intent(context, PPTActivity.class);
        intent.putExtra(AppConstants.KEY_FILE_NAME, fileName);
        context.startActivity(intent);
    }
}
