package com.kc.util;

import android.app.Application;
import android.widget.Toast;

import com.kc.data.DataCenter;
import com.kc.label.R;


/**
 * Created by Administrator on 2017/1/15.
 */
public class MyApp extends Application {


    private static MyApp mMyApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mMyApp = this;
        init();
    }

    private void init() {
        //读取db.json文件
        DataCenter.getInstance().init();
    }

    public static void initDefaultFile() {
        //复制assets中db.json文件到存储
        String fN = "db.json";
        String assetFP = "db/" + fN;
        String fPath = FileUtil.getFilePath(FileUtil.DIR_APP_DATABASE, null);
        FileUtil.copyAssetsToFile(MyApp.getApplication(), assetFP, fPath);
        //复制assets中Html文件夹到存储
        String fPath1 = FileUtil.getFilePath(null, null);
        FileUtil.copyAssetsToFile(MyApp.getApplication(), "Html", fPath1);

        //数据库readme文件
        FileUtil.write(FileUtil.DIR_APP_DATABASE, "说明.txt"
                , MyApp.getApplication().getResources().getString(R.string.dir_db_readme), false);
        //doc中的说明文件
        FileUtil.write(FileUtil.DIR_APP_DOC, "说明.txt"
                , "该文件夹用于存放在app中查看的文档。请将需要查看的文档存放在手机存储的/Label/Doc/目录下,该app会自动显示该目录下的所有文件。", false);
    }

    public static Application getApplication() {
        return mMyApp;
    }

    public static void showToast(String content) {
        if (content.length() < 10) {
            Toast.makeText(mMyApp, content, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mMyApp, content, Toast.LENGTH_LONG).show();
        }
    }


}
