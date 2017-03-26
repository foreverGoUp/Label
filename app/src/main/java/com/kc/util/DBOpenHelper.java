package com.kc.util;

//@author peter

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {

    public static int DB_VERSION = 1;
    private String CREATE_TABLE_AIR = "create table air(id integer primary key autoincrement,"
            + "device_id text,"
            + "power text,"
            + "model text,"
            + "wind text,"
            + "alarm text,"
            + "swing text,"
            + "set_t integer,"
            + "now_t double)";
    private String CREATE_TABLE_WLDEVICE = "create table wldevice(id integer primary key autoincrement,"
            + "room_id text,"
            + "type text,"
            + "name text,"
            + "device_id text)";
    private String CREATE_TABLE_WLDEVICE_EP = "create table wldevice_ep("
            + "id integer primary key autoincrement,"
            + "id_ep text,"  //这是存放device_id,ep格式数据的字段
            + "device_id text,"
            + "ep integer,"
            + "ep_data text,"
            + "ep_name text,"
            + "ep_status text,"
            + "ep_type text)";

    private String CREATE_TABLE_SCENE = "create table scene ("
            + "scene_id int(5) primary key,"
            + "scene_name varchar(255))";
    private String CREATE_TABLE_SCENE_ACTION = "create table scene_action ("
            + "command_str varchar(50),"
            + "device_id varchar(50),"
            + "order_s varchar(50),"
            + "data varchar(50),"
            + "type varchar(10),"
            + "scene_id int(5))";

    public DBOpenHelper(Context context, String dbName, int dbVersion) {
        super(context, dbName, null, dbVersion);
        // TODO 自动生成的构造函数存根
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO 自动生成的方法存根
//		db.execSQL(CREATE_TABLE_AIR);
//		db.execSQL(CREATE_TABLE_WLDEVICE);
//		db.execSQL(CREATE_TABLE_WLDEVICE_EP);
//		db.execSQL(CREATE_TABLE_SCENE);
//		db.execSQL(CREATE_TABLE_SCENE_ACTION);
//		Log.i("DBOpenHelper", "数据库正在创建....");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO 自动生成的方法存根

    }

}
