package com.kc.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kc.bean.DbInfo;
import com.kc.util.FileUtil;
import com.kc.util.MyApp;
import com.kc.util.SQLdm;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/1.
 */
public class DataCenter {

    private static final String TAG = "DataCenter";
    //公共静态变量
    public static int sCurDbIndex = 0;//用户选择的数据库，默认为范庄村。
    public static String sSearchC;

    private Context mContext;
    //    private DaoSession mDaoSession;
    private SQLiteDatabase mDb;
    private List<DbInfo> mDbInfos;
//    private List<RoomInfo> mRoomInfos = new ArrayList<>();
//    private Map<Integer, List<Cubicle>> mCubiclesMap = new ConcurrentHashMap<>();
//    private Map<Integer, List<Device>> mDevicesMap = new ConcurrentHashMap<>();
//    private String mCurDbPageDir = null;


    private static DataCenter INSTANCE;

    private DataCenter() {
        mContext = MyApp.getApplication();
    }

    public static DataCenter getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DataCenter();
        }
        return INSTANCE;
    }

    public void init() {
        Log.e(TAG, "开始初始化！！！");
        String content = FileUtil.read(FileUtil.DIR_APP_DATABASE, "db.json");
        if (TextUtils.isEmpty(content)) {
            InputStream is = null;
            try {
                is = MyApp.getApplication().getAssets().open("db/db.json");
            } catch (IOException e) {
                e.printStackTrace();
            }
            content = FileUtil.read(is);
        }

        if (!TextUtils.isEmpty(content)) {
            JSONObject jsonObject = JSON.parseObject(content);
//            mDbInfos = JSONArray.parseArray(jsonObject.getString("dbs"), DbInfo.class);

            List<DbInfo> dbInfos = JSONArray.parseArray(jsonObject.getString("dbs"), DbInfo.class);
            mDbInfos = new ArrayList<>();
            for (int i = 0; i < dbInfos.size(); i++) {
                DbInfo info = dbInfos.get(i);
                if (info.isActive()) {
                    mDbInfos.add(info);
                }
            }
        }
    }

//    public String getCurDbPageDir(){
//        if (mCurDbPageDir == null){
//            String curDbName = DataHandle.getCurDbInfo().getName();
//            mCurDbPageDir = new StringBuffer().append("Html/page/").append(curDbName).append(File.separator).toString();
//        }
//        return mCurDbPageDir;
//    }

    public List<DbInfo> getDbInfos() {
        return mDbInfos;
    }

    // do this in your activities/fragments to get hold of a DAO
//    public RoomInfoDao getRoomInfoDao() {
//        if (mDaoSession == null) {
//            //greenDao
//            // do this once, for example in your Application class
//            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(mContext, DB_NAME, null);
////            SQLiteDatabase db = helper.getWritableDatabase();
//            SQLdm s = new SQLdm();
//            SQLiteDatabase db = s.openDatabase(mContext);
//            DaoMaster daoMaster = new DaoMaster(db);
//            mDaoSession = daoMaster.newSession();
//        }
//        return mDaoSession.getRoomInfoDao();
//    }

    public void clearDb() {
        mDb = null;
    }

    public SQLiteDatabase getDb(String dbName) {
        if (mDb == null) {
//            SQLiteOpenHelper helper = new DBOpenHelper(mContext, DBOpenHelper.DB_NAME, DBOpenHelper.DB_VERSION);
//            mDb = helper.getReadableDatabase();

//            String fPath = FileUtil.getFilePath(FileUtil.DIR_APP_DATABASE, dbName);
//            if (fPath == null || !new File(fPath).exists()){
//                return null;
//            }
//            mDb = SQLiteDatabase.openDatabase(fPath, null, SQLiteDatabase.OPEN_READONLY);

            SQLdm sqLdm = new SQLdm();
            mDb = sqLdm.openDatabase(MyApp.getApplication(), FileUtil.getFilePath(FileUtil.DIR_APP_DATABASE, dbName));
        }
        return mDb;
    }

}
