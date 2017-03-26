package com.kc.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.kc.activity.HomeActivity;
import com.kc.adapter.MyAdapter;
import com.kc.base.BaseFragment;
import com.kc.label.R;
import com.kc.util.FileUtil;
import com.kc.util.MyApp;

import java.io.File;
import java.util.ArrayList;

public class HomeDocFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    private ListView mListView;
    private MyAdapter mAdapter;

    private String ROOT_PATH = null;
    //存储文件名称
    private ArrayList<String> names = null;
    //存储文件路径
    private ArrayList<String> paths = null;
    private View view;
    private EditText editText;

    public HomeDocFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_doc, container, false);
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
        String rp = FileUtil.getFilePath(FileUtil.DIR_APP_DOC, null);
        if (rp != null) {
            File file = new File(rp);
            if (!file.exists()) {
                file.mkdirs();
            }
            ROOT_PATH = rp.substring(0, rp.lastIndexOf("/"));//若想返回到根目录的上一级，只需改为结尾加/的跟目录。
        } else {
            showToast("存储不可用！");
            return;
        }
        if (new File(rp).exists()) {
            showFileDir(ROOT_PATH);
        }
    }

    protected void init() {
        mListView.setOnItemClickListener(this);
    }

    protected void findViews(View view) {
        mListView = (ListView) view.findViewById(R.id.list);
    }

    /*
    * 传入的路径得最后字符都不带/
    * */
    private void showFileDir(String path) {
        names = new ArrayList<String>();
        paths = new ArrayList<String>();
        File file = new File(path);
        File[] files = file.listFiles();
        //如果当前目录不是根目录
        if (!ROOT_PATH.equals(path)) {
            names.add("@1");
            paths.add(ROOT_PATH);
            names.add("@2");
            paths.add(file.getParent());
        }
        //添加所有文件
        for (File f : files) {
            names.add(f.getName());
            paths.add(f.getPath());
        }
        mAdapter = new MyAdapter(MyApp.getApplication(), names, paths);
        mListView.setAdapter(mAdapter);
    }

    //对文件进行增删改
    private void fileHandle(final File file) {
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 打开文件
                if (which == 0) {
                    openFile(file);
                }
                //修改文件名
                else if (which == 1) {
                    LayoutInflater factory = LayoutInflater.from(getActivity());
                    view = factory.inflate(R.layout.rename_dialog, null);
                    editText = (EditText) view.findViewById(R.id.editText);
                    editText.setText(file.getName());
                    DialogInterface.OnClickListener listener2 = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            String modifyName = editText.getText().toString();
                            final String fpath = file.getParentFile().getPath();
                            final File newFile = new File(fpath + "/" + modifyName);
                            if (newFile.exists()) {
                                //排除没有修改情况
                                if (!modifyName.equals(file.getName())) {
                                    new AlertDialog.Builder(getActivity())
                                            .setTitle("注意!")
                                            .setMessage("文件名已存在，是否覆盖？")
                                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    if (file.renameTo(newFile)) {
                                                        showFileDir(fpath);
                                                        showToast("重命名成功！");
                                                    } else {
                                                        showToast("重命名失败！");
                                                    }
                                                }
                                            })
                                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                }
                                            })
                                            .show();
                                }
                            } else {
                                if (file.renameTo(newFile)) {
                                    showFileDir(fpath);
                                    showToast("重命名成功！");
                                } else {
                                    showToast("重命名失败！");
                                }
                            }
                        }
                    };
                    AlertDialog renameDialog = new AlertDialog.Builder(getActivity()).create();
                    renameDialog.setView(view);
                    renameDialog.setButton("确定", listener2);
                    renameDialog.setButton2("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                        }
                    });
                    renameDialog.show();
                }
                //删除文件
                else {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("注意!")
                            .setMessage("确定要删除此文件吗？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (file.delete()) {
                                        //更新文件列表
                                        showFileDir(file.getParent());
                                        showToast("删除成功！");
                                    } else {
                                        showToast("删除失败！");
                                    }
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).show();
                }
            }
        };
        //选择文件时，弹出增删该操作选项对话框
        String[] menu = {"打开文件", "重命名", "删除文件"};
        new AlertDialog.Builder(getActivity())
                .setTitle("请选择要进行的操作!")
                .setItems(menu, listener)
                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
    }

    //打开文件
    private void openFile(File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        String type = getMIMEType(file);
        intent.setDataAndType(Uri.fromFile(file), type);
        startActivity(intent);
    }

    //获取文件mimetype
    private String getMIMEType(File file) {
        String type = "";
        String name = file.getName();
        //文件扩展名
        String end = name.substring(name.lastIndexOf(".") + 1, name.length()).toLowerCase();
        if (end.equals("m4a") || end.equals("mp3") || end.equals("wav")) {
            type = "audio/*";
        } else if (end.equals("mp4") || end.equals("3gp")) {
            type = "video/*";
        } else if (end.equals("jpg") || end.equals("png") || end.equals("jpeg") || end.equals("bmp") || end.equals("gif")) {
            type = "image/*";
        } else if (end.equals("doc") || end.equals("docx")) {
            type = "application/msword";
        } else if (end.equals("xlsx")) {
            type = "application/vnd.ms-excel";
        } else if (end.equals("ppt")) {
            type = "application/vnd.ms-powerpoint";
        } else if (end.equals("chm")) {
            type = "application/x-chm";
        } else if (end.equals("txt")) {
            type = "text/plain";
        } else if (end.equals("pdf")) {
            type = "application/pdf";
        } else {
            //如果无法直接打开，跳出列表由用户选择
            type = "*/*";
        }
//        type += "/*";
        return type;
    }

    public boolean backPressed() {
        if (paths.get(0).equals(ROOT_PATH)) {
            onItemClick(null, null, 1, 0);
            return true;
        }
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        String path = paths.get(position);
        File file = new File(path);
        // 文件存在并可读
        if (file.exists() && file.canRead()) {
            if (file.isDirectory()) {
                //显示子目录及文件
                showFileDir(path);
            } else {
                //处理文件
//                fileHandle(file);
                //打开文件
//                openFile(file);
                HomeActivity.openFile(getActivity(), file.getName());
            }

        }
        //没有权限
        else {
            Resources res = getResources();
            new AlertDialog.Builder(getActivity()).setTitle("Message")
                    .setMessage("没有操作权限")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
        }
    }
}
