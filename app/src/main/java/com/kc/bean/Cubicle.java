package com.kc.bean;

/**
 * Created by Administrator on 2017/3/1 0001.
 */
public class Cubicle {

    private int cubicleId;
    private int roomId;
    private String name;
    private String number;

    @Override
    public String toString() {
        return "Cubicle{" +
                "cubicleId=" + cubicleId +
                ", roomId=" + roomId +
                ", name='" + name + '\'' +
                ", number='" + number + '\'' +
                '}';
    }

    public int getCubicleId() {
        return cubicleId;
    }

    public void setCubicleId(int cubicleId) {
        this.cubicleId = cubicleId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
