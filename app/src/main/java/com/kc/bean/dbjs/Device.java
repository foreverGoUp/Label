package com.kc.bean.dbjs;

/**
 * Created by Administrator on 2017/3/17.
 */
public class Device {

    private String deviceid;
    private String devicename;

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getDevicename() {
        return devicename;
    }

    public void setDevicename(String devicename) {
        this.devicename = devicename;
    }

    @Override
    public String toString() {
        return "Device{" +
                "deviceid='" + deviceid + '\'' +
                ", devicename='" + devicename + '\'' +
                '}';
    }
}
