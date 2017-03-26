package com.kc.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kc.label.R;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/3.
 */
public class MyAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    //存储文件名称
    private ArrayList<String> names = null;
    //存储文件路径
    private ArrayList<String> paths = null;

    //参数初始化
    public MyAdapter(Context context, ArrayList<String> na, ArrayList<String> pa) {
        names = na;
        paths = pa;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return names.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return names.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder;
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.file, null);
            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.textView);
            holder.image = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        File f = new File(paths.get(position));
        String name = names.get(position);
        if (name.equals("@1")) {
            holder.text.setText("/");
            holder.image.setImageResource(R.drawable.file_folder);
        } else if (names.get(position).equals("@2")) {
            holder.text.setText("..");
            holder.image.setImageResource(R.drawable.file_folder);
        } else {
            holder.text.setText(name);
            if (f.isDirectory()) {
                holder.image.setImageResource(R.drawable.file_folder);
            } else if (f.isFile()) {
                String suffix = name.substring(name.lastIndexOf(".") + 1).toLowerCase();
                switch (suffix) {
                    case "ppt":
                        holder.image.setImageResource(R.drawable.file_ppt);
                        break;
                    case "pdf":
                        holder.image.setImageResource(R.drawable.file_pdf);
                        break;
                    case "doc":
                    case "docx":
                        holder.image.setImageResource(R.drawable.file_word);
                        break;
                    case "xlsx":
                        holder.image.setImageResource(R.drawable.file_excel);
                        break;
                    case "txt":
                        holder.image.setImageResource(R.drawable.file_txt);
                        break;
                    default:
                        holder.image.setImageResource(R.drawable.file_unknow);
                        break;
                }
            } else {
                System.out.println(f.getName());
            }
        }
        return convertView;
    }

    private class ViewHolder {
        private TextView text;
        private ImageView image;
    }

    private Bitmap small(Bitmap map, float num) {
        Matrix matrix = new Matrix();
        matrix.postScale(num, num);
        return Bitmap.createBitmap(map, 0, 0, map.getWidth(), map.getHeight(), matrix, true);
    }
}
