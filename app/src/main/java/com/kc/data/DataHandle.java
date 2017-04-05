package com.kc.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.kc.bean.Cubicle;
import com.kc.bean.DbInfo;
import com.kc.bean.Device;
import com.kc.bean.InfoSet;
import com.kc.bean.RoomInfo;
import com.kc.bean.dbjs.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/3.
 */
public class DataHandle {

    private static final String TAG = "DataHandle";

    public static boolean isDbIndexFileExist() {
        return DataCenter.getInstance().getDbInfos() != null ? true : false;
    }

    public static boolean isDbExist() {
        return DataCenter.getInstance().getDb(getCurDbFileName()) != null ? true : false;
    }

    public static String getCurDbFileName() {
        String ret = DataCenter.getInstance().getDbInfos().get(DataCenter.sCurDbIndex).getFile();
        return ret + ".sqlite";
    }

    public static DbInfo getCurDbInfo() {
        return DataCenter.getInstance().getDbInfos().get(DataCenter.sCurDbIndex);
    }

    public static List<RoomInfo> getRoomInfos() {
        List<RoomInfo> roomInfos = new ArrayList<>();

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
                    roomInfos.add(info);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        return roomInfos;
    }

    public static List<Cubicle> getCubicles(int roomId) {
        List<Cubicle> roomInfos = new ArrayList<>();
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
                    roomInfos.add(info);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return roomInfos;
    }

    public static List<Device> getDevices(int cubicleId) {
        List<Device> roomInfos = new ArrayList<>();

        SQLiteDatabase db = DataCenter.getInstance().getDb(DataHandle.getCurDbFileName());
        if (db != null) {
            Cursor cursor = db.query("device", new String[]{"device_id", "cubicle_id", "name", "description"}
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
                    roomInfos.add(info);
                } while (cursor.moveToNext());
            }
            cursor.close();

        }
        return roomInfos;
    }

    public static List<InfoSet> getInfoSets(int deviceId) {
        List<InfoSet> retList = new ArrayList<>();

        SQLiteDatabase db = DataCenter.getInstance().getDb(DataHandle.getCurDbFileName());
        if (db != null) {
            String selArg = "" + deviceId;
            Cursor cursor = db.query("infoset", null
//                    new String[]{"name", "txied_id", "txiedport_id"
//                    , "rxied_id","rxiedport_id"
//                    ,"switch1_id","switch1_txport_id","switch1_rxport_id"
//                    ,"switch2_id","switch2_txport_id","switch2_rxport_id"
//                    ,"switch3_id","switch3_txport_id","switch3_rxport_id"
//                    ,"switch4_id","switch4_txport_id","switch4_rxport_id"}
                    , "txied_id = ? or rxied_id=? or switch1_id=? " +
                            "or switch2_id=? or switch3_id=? or switch4_id=?"
                    , new String[]{selArg, selArg, selArg, selArg, selArg, selArg}
                    , null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    InfoSet info = new InfoSet();
                    info.setName(cursor.getString(cursor.getColumnIndex("name")));
                    info.setTxiedId(cursor.getInt(cursor.getColumnIndex("txied_id")));
                    info.setTxiedportId(cursor.getInt(cursor.getColumnIndex("txiedport_id")));
                    info.setRxiedId(cursor.getInt(cursor.getColumnIndex("rxied_id")));
                    info.setRxiedportId(cursor.getInt(cursor.getColumnIndex("rxiedport_id")));
                    info.setSwitch1Id(cursor.getInt(cursor.getColumnIndex("switch1_id")));
                    info.setSwitch1TxportId(cursor.getInt(cursor.getColumnIndex("switch1_txport_id")));
                    info.setSwitch1RxportId(cursor.getInt(cursor.getColumnIndex("switch1_rxport_id")));
                    info.setSwitch2Id(cursor.getInt(cursor.getColumnIndex("switch2_id")));
                    info.setSwitch2TxportId(cursor.getInt(cursor.getColumnIndex("switch2_txport_id")));
                    info.setSwitch2RxportId(cursor.getInt(cursor.getColumnIndex("switch2_rxport_id")));
                    info.setSwitch3Id(cursor.getInt(cursor.getColumnIndex("switch3_id")));
                    info.setSwitch3TxportId(cursor.getInt(cursor.getColumnIndex("switch3_txport_id")));
                    info.setSwitch3RxportId(cursor.getInt(cursor.getColumnIndex("switch3_rxport_id")));
                    info.setSwitch4Id(cursor.getInt(cursor.getColumnIndex("switch4_id")));
                    info.setSwitch4TxportId(cursor.getInt(cursor.getColumnIndex("switch4_txport_id")));
                    info.setSwitch4RxportId(cursor.getInt(cursor.getColumnIndex("switch4_rxport_id")));
                    retList.add(info);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        int size = retList.size();
        for (int i = 0; i < size; i++) {
            Log.d(TAG, retList.get(i).toString());
        }
        return retList;
    }

    public static String getDeviceName(int devId) {
        String ret = null;
        SQLiteDatabase db = DataCenter.getInstance().getDb(DataHandle.getCurDbFileName());
        if (db != null) {
            Cursor cursor = db.query("device", new String[]{"device_id", "description"}
                    , "device_id = ?", new String[]{"" + devId}, null, null, null);
            if (cursor.moveToFirst()) {
                ret = cursor.getString(cursor.getColumnIndex("description"));
            }
            cursor.close();
        }
        return ret;
    }

    public static String getPortName(int portId) {
        String ret = null;
        SQLiteDatabase db = DataCenter.getInstance().getDb(DataHandle.getCurDbFileName());
        if (db != null) {
            Cursor cursor = db.query("port", new String[]{"port_id", "name"}
                    , "port_id = ?", new String[]{"" + portId}, null, null, null);
            if (cursor.moveToFirst()) {
                ret = cursor.getString(cursor.getColumnIndex("name"));
            }
            cursor.close();
        }
        return ret;
    }


    /**
     * html调用部分开始
     */
    public static List<Room> getRooms4Js() {
        List<Room> roomInfos = new ArrayList<>();

        SQLiteDatabase db = DataCenter.getInstance().getDb(DataHandle.getCurDbFileName());
        if (db != null) {
            Cursor cursor = db.query("room", null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    Room info = new Room();
                    int roomId = cursor.getInt(cursor.getColumnIndex("room_id"));
                    info.setRoomid("" + roomId);
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    info.setRoomname(name);
                    roomInfos.add(info);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        return roomInfos;
    }

    public static List<com.kc.bean.dbjs.Cubicle> getCubicles4Js(int roomId) {
        List<com.kc.bean.dbjs.Cubicle> roomInfos = new ArrayList<>();
        SQLiteDatabase db = DataCenter.getInstance().getDb(DataHandle.getCurDbFileName());
        if (db != null) {
            Cursor cursor = db.query("cubicle", new String[]{"cubicle_id", "room_id", "name"}
                    , "room_id = ?", new String[]{"" + roomId}, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    com.kc.bean.dbjs.Cubicle info = new com.kc.bean.dbjs.Cubicle();
                    int cubicleId = cursor.getInt(cursor.getColumnIndex("cubicle_id"));
                    info.setCubicleid("" + cubicleId);
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    info.setCubiclename(name);
                    roomInfos.add(info);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return roomInfos;
    }

    public static List<com.kc.bean.dbjs.Device> getDevices4Js(int cubicleId) {
        List<com.kc.bean.dbjs.Device> roomInfos = new ArrayList<>();

        SQLiteDatabase db = DataCenter.getInstance().getDb(DataHandle.getCurDbFileName());
        if (db != null) {
            Cursor cursor = db.query("device", new String[]{"device_id", "cubicle_id", "name", "description"}
                    , "cubicle_id = ?", new String[]{"" + cubicleId}, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    com.kc.bean.dbjs.Device info = new com.kc.bean.dbjs.Device();
                    int DeviceId = cursor.getInt(cursor.getColumnIndex("device_id"));
                    info.setDeviceid("" + DeviceId);
                    String name = cursor.getString(cursor.getColumnIndex("description"));
                    info.setDevicename(name);
//                    String desc = cursor.getString(cursor.getColumnIndex("description"));
//                    info.setDescription(desc);
                    roomInfos.add(info);
                } while (cursor.moveToNext());
            }
            cursor.close();

        }
        return roomInfos;
    }
    /**html调用部分结束*/

}
