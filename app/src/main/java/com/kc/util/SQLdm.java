package com.kc.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;

/**
 * Created by Administrator on 2017/3/2.
 */
public class SQLdm {

    public SQLiteDatabase openDatabase(Context context, String filePath) {
        File jhPath = new File(filePath);
        if (jhPath.exists()) {
            return SQLiteDatabase.openDatabase(filePath, null, SQLiteDatabase.OPEN_READONLY);
        } else {
//            File path=new File(filePath);
//            if (path.mkdir()){
//            }else{
//            };
            String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
            //复制数据库文件到存储
            String assetFP = "db/" + fileName;
            String fPath = FileUtil.getFilePath(FileUtil.DIR_APP_DATABASE, null);
            FileUtil.copyAssetsToFile(MyApp.getApplication(), assetFP, fPath);
            Log.e("", "!!数据库导出到文件夹完毕:" + fileName);
            return openDatabase(context, filePath);
        }
    }
}
