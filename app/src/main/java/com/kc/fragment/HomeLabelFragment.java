package com.kc.fragment;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kc.activity.CubicleListActivity;
import com.kc.adapter.RoomListAdapter;
import com.kc.base.BaseFragment;
import com.kc.bean.RoomInfo;
import com.kc.data.DataCenter;
import com.kc.data.DataHandle;
import com.kc.label.R;

import java.util.ArrayList;
import java.util.List;

public class HomeLabelFragment extends BaseFragment {

    private ListView mListView;
    private RoomListAdapter mAdapter;
    private List<RoomInfo> mlist;

    public HomeLabelFragment() {
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
        initData();
    }

    protected void initData() {
        mlist.clear();
        mAdapter.notifyDataSetChanged();

        if (!DataHandle.isDbIndexFileExist()) {
            showToast("初始化失败，检测到无db.json文件！");
            return;
        }
        if (!DataHandle.isDbExist()) {
            showToast("初始化失败，检测到无对应数据库文件！");
            return;
        }

        refreshAdapter();
    }

    protected void init() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CubicleListActivity.actionStart(getActivity(), mlist.get(position).getRoomId(), mlist.get(position).getName());
            }
        });

        mlist = new ArrayList<>();
        mAdapter = new RoomListAdapter(getActivity(), R.layout.list_item_room, mlist);
        mListView.setAdapter(mAdapter);
    }

    private void refreshAdapter() {
        mlist.clear();
        SQLiteDatabase db = DataCenter.getInstance().getDb(DataHandle.getCurDbFileName());
        if (db != null) {
            Cursor cursor = db.query("room", null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    RoomInfo info = new RoomInfo();
                    int roomId = cursor.getInt(cursor.getColumnIndex("room_id"));
                    info.setRoomId(roomId);
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    info.setName(name);
                    mlist.add(info);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } else {
            showToast("未找到相应的数据库文件");
        }
        mAdapter.notifyDataSetChanged();
    }

    protected void findViews(View view) {
        mListView = (ListView) view.findViewById(R.id.lv);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
