package com.kc.bean;

/**
 * Created by Administrator on 2017/3/1 0001.
 */
public class Device {

    private int deviceId;
    private int cubicleId;
    private String name;
    private String description;

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public int getCubicleId() {
        return cubicleId;
    }

    public void setCubicleId(int cubicleId) {
        this.cubicleId = cubicleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
