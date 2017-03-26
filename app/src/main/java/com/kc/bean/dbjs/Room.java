package com.kc.bean.dbjs;

import java.util.List;

/**
 * Created by Administrator on 2017/3/17.
 */
public class Room {

    private String roomid;
    private String roomname;
    private List<Cubicle> cubicles;

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public String getRoomname() {
        return roomname;
    }

    public void setRoomname(String roomname) {
        this.roomname = roomname;
    }

    public List<Cubicle> getCubicles() {
        return cubicles;
    }

    public void setCubicles(List<Cubicle> cubicles) {
        this.cubicles = cubicles;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomid='" + roomid + '\'' +
                ", roomname='" + roomname + '\'' +
                ", cubicles=" + cubicles +
                '}';
    }
}
