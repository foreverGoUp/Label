package com.kc.adapter;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.kc.bean.RoomInfo;
import com.kc.data.DataCenter;
import com.kc.label.R;

import java.util.List;

public class RoomListAdapter1 extends ArrayAdapter<RoomInfo> {

    private int mResourceId;

    public RoomListAdapter1(Context context, int resource, List<RoomInfo> objects) {
        super(context, resource, objects);
        // TODO 自动生成的构造函数存根
        mResourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO 自动生成的方法存根
        RoomInfo menuItem = getItem(position);
        String name = menuItem.getName();
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(mResourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.tvRoomName = (TextView) convertView.findViewById(R.id.tv_list_item_room_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (DataCenter.sSearchC != null && name.contains(DataCenter.sSearchC)) {
            viewHolder.tvRoomName.setTextColor(Color.parseColor("#ff0000"));
        } else {
            viewHolder.tvRoomName.setTextColor(Color.parseColor("#000000"));
        }
        viewHolder.tvRoomName.setText(name);

        return convertView;
    }


    class ViewHolder {
        TextView tvRoomName;
        TextView tvCubicleName;
        GridView gvDevices;
    }

}
