package com.kc.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.kc.adapter.CommonAdapter;
import com.kc.adapter.ViewHolder;
import com.kc.base.BaseActivity;
import com.kc.bean.Device;
import com.kc.data.DataCenter;
import com.kc.data.DataHandle;
import com.kc.label.R;
import com.kc.util.AppConstants;

import java.util.ArrayList;
import java.util.List;

public class DeviceListActivity extends BaseActivity {

    private ListView mListView;
    private CommonAdapter<Device> mAdapter;
    private List<Device> mlist;
    private int mCubicleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
        findViews();
        init();
        initData();
    }


    @Override
    protected void findViews() {
        mListView = (ListView) findViewById(R.id.lv);
    }

    @Override
    protected void init() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showToast("暂未开放");
            }
        });

        Intent intent = getIntent();
        if (intent != null) {
            mCubicleId = intent.getIntExtra(AppConstants.KEY_CUBICLE_ID, -1);
            String head = intent.getStringExtra(AppConstants.KEY_HEAD);
            ((TextView) findViewById(R.id.tv_head)).setText(head);
            Log.e(TAG, "获得柜子id：" + mCubicleId);
        } else {
            Log.e(TAG, "获得柜子id失败!!!");
        }
    }

    @Override
    protected void initData() {
        mlist = getList(mCubicleId);
        mAdapter = new CommonAdapter<Device>(this, mlist, R.layout.list_item_room) {
            @Override
            public void convert(ViewHolder helper, Device item, int position) {
                String text = item.getName() + "\n" + item.getDescription();
                TextView view = helper.getView(R.id.tv_list_item_room_name);
                if (DataCenter.sSearchC != null && text.contains(DataCenter.sSearchC)) {
                    view.setTextColor(Color.parseColor("#ff0000"));
                } else {
                    view.setTextColor(Color.parseColor("#000000"));
                }

                helper.setText(R.id.tv_list_item_room_name, text);
            }
        };
        mListView.setAdapter(mAdapter);
    }

    private List<Device> getList(int cubicleId) {
        List<Device> list = new ArrayList<>();
        SQLiteDatabase db = DataCenter.getInstance().getDb(DataHandle.getCurDbFileName());
        if (db != null) {
            Cursor cursor = db.query("Device", new String[]{"device_id", "cubicle_id", "name", "description"}
                    , "cubicle_id = ?", new String[]{"" + cubicleId}, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    Device info = new Device();
                    int DeviceId = cursor.getInt(cursor.getColumnIndex("device_id"));
                    info.setDeviceId(DeviceId);
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    info.setName(name);
                    String desc = cursor.getString(cursor.getColumnIndex("description"));
                    info.setDescription(desc);
                    list.add(info);
                } while (cursor.moveToNext());
            }
            cursor.close();

        } else {
            showToast("未找到相应的数据库文件");
        }
        return list;
    }

    public static void actionStart(Context context, int cubicleId, String head) {
        Intent intent = new Intent(context, DeviceListActivity.class);
        intent.putExtra(AppConstants.KEY_CUBICLE_ID, cubicleId);
        intent.putExtra(AppConstants.KEY_HEAD, head);
        context.startActivity(intent);
    }
}
