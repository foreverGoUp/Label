package com.kc.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kc.adapter.CommonAdapter;
import com.kc.adapter.ViewHolder;
import com.kc.base.BaseActivity;
import com.kc.bean.DbInfo;
import com.kc.data.DataCenter;
import com.kc.label.R;
import com.kc.util.FileUtil;

import java.util.ArrayList;
import java.util.List;

public class ClearCacheActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ListView mListView;

    private List<DbInfo> mList;
    private CommonAdapter<DbInfo> mAdapter;
    private List<Integer> mSelPosList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear_cache);
        findViews();
        init();
        initData();
    }

    @Override
    protected void findViews() {
        TextView textView = getViewById(R.id.tv_head);
        textView.setText("缓存管理");
        mListView = getViewById(R.id.lv_db_list);
    }

    @Override
    protected void init() {
        mList = DataCenter.getInstance().getDbInfos();
        mAdapter = new CommonAdapter<DbInfo>(this, mList, R.layout.item_clear_cache_db_list) {
            @Override
            public void convert(ViewHolder helper, DbInfo item, int position) {
                helper.setText(R.id.tv_item_db_list_name, item.getName());
                ImageView ivState = helper.getView(R.id.iv_item_db_list_state);
                if (getPosInSelList(position) == -1) {
                    ivState.setSelected(false);
                } else {
                    ivState.setSelected(true);
                }
            }
        };
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
    }

    @Override
    protected void initData() {

    }

    private int getPosInSelList(int clickPos) {
        int ret = -1;
        int size = mSelPosList.size();
        for (int i = 0; i < size; i++) {
            if (clickPos == mSelPosList.get(i)) {
                ret = i;
                break;
            }
        }
        return ret;
    }

    private List<DbInfo> mClearList;

    @Override
    public void onClick(View view) {
        List<DbInfo> list = new ArrayList<>();
        int size = mSelPosList.size();
        for (int i = 0; i < size; i++) {
            list.add(mList.get(mSelPosList.get(i)));
        }
        if (list.size() > 0) {
            mClearList = list;
            StringBuffer sb = new StringBuffer();
            sb.append("以下数据库的缓存将会被清空：");
            int size0 = list.size();
            for (int i = 0; i < size0; i++) {
                sb.append("\n").append(list.get(i).getName());
            }
            showTipDialog(sb.toString());
        }
    }

    private void execClear() {
        String dir1 = FileUtil.getFilePath(FileUtil.DIR_APP_HTML_PAGE, null);
        Log.e(TAG, "dir1=" + dir1);
        int size = mClearList.size();
        for (int i = 0; i < size; i++) {
            String fDir = dir1 + mClearList.get(i).getName();
            Log.e(TAG, "正在清空缓存目录：fDir=" + fDir);
            FileUtil.deleteDirectory(fDir);
        }
    }


    private AlertDialog mDialog;

    private void showTipDialog(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage(msg);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                execClear();
                showToast("缓存清理完毕~");
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mDialog.dismiss();
            }
        });
        mDialog = builder.create();
        mDialog.show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
        int existListPos = getPosInSelList(pos);
        if (existListPos == -1) {
            mSelPosList.add(pos);
        } else {
            mSelPosList.remove(existListPos);
        }
        mAdapter.getView(pos, view, null);
    }
}
