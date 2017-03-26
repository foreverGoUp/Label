package com.kc.activity.openfile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.joanzapata.pdfview.PDFView;
import com.kc.base.BaseActivity;
import com.kc.data.DataHandle;
import com.kc.label.R;
import com.kc.util.AppConstants;
import com.kc.util.FileUtil;

import java.io.File;

public class PdfActivity extends BaseActivity implements View.OnClickListener {

    private PDFView mPdfView;
    private ImageView mIvZoomIn, mIvZoomOut, mIvZoomReset;

    private String mFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        findViews();
        init();
        initData();
    }

    @Override
    protected void findViews() {
        mPdfView = (PDFView) findViewById(R.id.pdfView);
        mIvZoomIn = getViewById(R.id.iv_pdf_zoom_in);
        mIvZoomOut = getViewById(R.id.iv_pdf_zoom_out);
        mIvZoomReset = getViewById(R.id.iv_pdf_zoom_reset);
    }

    @Override
    protected void init() {
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

        File file = new File(fp);

        if (file.exists()) {
            mPdfView.fromFile(file)
//                .fromAsset("FirstLineCode.pdf")
//                .pages(0, 2, 1, 3, 3, 3)
                    .defaultPage(1)
                    .showMinimap(true)
                    .enableSwipe(true)
                    .swipeVertical(true)
//                .onDraw(onDrawListener)
//                .onLoad(onLoadCompleteListener)
//                .onPageChange(onPageChangeListener)
                    .load();

        } else {
            showToast("文件不存在：" + fp);
            finish();
        }

        Log.d(TAG, "zoom level" + mPdfView.getZoom());
    }

    public static void actionStart(Context context, String fileName) {
        Intent intent = new Intent(context, PdfActivity.class);
        intent.putExtra(AppConstants.KEY_FILE_NAME, fileName);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPdfView.recycle();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_pdf_setting:
                if (mIvZoomIn.getVisibility() == View.VISIBLE) {
                    mIvZoomIn.setVisibility(View.GONE);
                    mIvZoomOut.setVisibility(View.GONE);
                    mIvZoomReset.setVisibility(View.GONE);
                } else {
                    mIvZoomIn.setVisibility(View.VISIBLE);
                    mIvZoomOut.setVisibility(View.VISIBLE);
                    mIvZoomReset.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.iv_pdf_zoom_in: {
//                showToast("放大");
                float zl = mPdfView.getZoom() + 0.5f;
                Log.d(TAG, "放大水平：" + zl);
                mPdfView.zoomTo(zl);
                mPdfView.invalidate();

                break;
            }
            case R.id.iv_pdf_zoom_out: {
//                showToast("缩小");
                float zl = mPdfView.getZoom() - 0.5f;
                if (zl >= 1.0) {
                    mPdfView.zoomTo(zl);
                    mPdfView.invalidate();
                }
                break;
            }
            case R.id.iv_pdf_zoom_reset:
//                showToast("缩小");
                float zl = mPdfView.getZoom();
                if (zl > 1.0) {
                    mPdfView.resetZoomWithAnimation();
                }
                break;
        }
    }
}
