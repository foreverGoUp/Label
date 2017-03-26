package com.kc.bean.dbjs;


import java.util.List;

/**
 * Created by Administrator on 2017/3/17.
 */
public class Cubicle {

    private String cubicleid;
    private String cubiclename;
    private List<Device> devices;

    public String getCubicleid() {
        return cubicleid;
    }

    public void setCubicleid(String cubicleid) {
        this.cubicleid = cubicleid;
    }

    public String getCubiclename() {
        return cubiclename;
    }

    public void setCubiclename(String cubiclename) {
        this.cubiclename = cubiclename;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    @Override
    public String toString() {
        return "Cubicle{" +
                "cubicleid='" + cubicleid + '\'' +
                ", cubiclename='" + cubiclename + '\'' +
                ", devices=" + devices +
                '}';
    }
}
