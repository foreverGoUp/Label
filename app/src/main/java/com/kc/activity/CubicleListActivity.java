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
import com.kc.bean.Cubicle;
import com.kc.data.DataCenter;
import com.kc.data.DataHandle;
import com.kc.label.R;
import com.kc.util.AppConstants;

import java.util.ArrayList;
import java.util.List;

public class CubicleListActivity extends BaseActivity {

    private ListView mListView;
    private CommonAdapter<Cubicle> mAdapter;
    private List<Cubicle> mlist;
    private int mRoomId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cubicle_list);
        findViews();
        init();
        initData();
    }

    protected void findViews() {
        mListView = (ListView) findViewById(R.id.lv);
    }

    protected void init() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DeviceListActivity.actionStart(CubicleListActivity.this, mlist.get(position).getCubicleId(), mlist.get(position).getName());
            }
        });

        Intent intent = getIntent();
        if (intent != null) {
            String head = intent.getStringExtra(AppConstants.KEY_HEAD);
            mRoomId = intent.getIntExtra(AppConstants.KEY_ROOM_ID, -1);
            ((TextView) findViewById(R.id.tv_head)).setText(head);
            Log.e(TAG, "获得房间id：" + mRoomId);
        } else {
            Log.e(TAG, "获得房间id失败!!!");
        }
    }

    @Override
    protected void initData() {
        mlist = getList(mRoomId);
        mAdapter = new CommonAdapter<Cubicle>(this, mlist, R.layout.list_item_room) {
            @Override
            public void convert(ViewHolder helper, Cubicle item) {
                TextView view = helper.getView(R.id.tv_list_item_room_name);
                if (DataCenter.sSearchC != null && item.getName().contains(DataCenter.sSearchC)) {
                    view.setTextColor(Color.parseColor("#ff0000"));
                } else {
                    view.setTextColor(Color.parseColor("#000000"));
                }

                helper.setText(R.id.tv_list_item_room_name, item.getName());
            }
        };
        mListView.setAdapter(mAdapter);
    }

    private List<Cubicle> getList(int roomId) {
        List<Cubicle> list = new ArrayList<>();
        SQLiteDatabase db = DataCenter.getInstance().getDb(DataHandle.getCurDbFileName());
        if (db != null) {
            Cursor cursor = db.query("cubicle", new String[]{"cubicle_id", "room_id", "name"}
                    , "room_id = ?", new String[]{"" + roomId}, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    Cubicle info = new Cubicle();
                    int cubicleId = cursor.getInt(cursor.getColumnIndex("cubicle_id"));
                    info.setCubicleId(cubicleId);
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    info.setName(name);
                    list.add(info);
                } while (cursor.moveToNext());
            }
            cursor.close();

        } else {
            showToast("未找到相应的数据库文件");
        }
        return list;
    }

    public static void actionStart(Context context, int roomId, String head) {
        Intent intent = new Intent(context, CubicleListActivity.class);
        intent.putExtra(AppConstants.KEY_ROOM_ID, roomId);
        intent.putExtra(AppConstants.KEY_HEAD, head);
        context.startActivity(intent);
    }
}
