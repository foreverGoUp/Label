package com.kc.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by Administrator on 2017/3/1 0001.
 */
@Entity(
        // If you have more than one schema, you can tell greenDAO
        // to which schema an entity belongs (pick any string as a name).
//        schema = "myschema",

        // Flag to make an entity "active": Active entities have update,
        // delete, and refresh methods.
//        active = true,

        // Specifies the name of the table in the database.
        // By default, the name is based on the entities class name.
        nameInDb = "room",

        // Define indexes spanning multiple columns here.
//        indexes = {
//                @Index(value = "name DESC", unique = true)
//        },

        // Flag if the DAO should create the database table (default is true).
        // Set this to false, if you have multiple entities mapping to one table,
        // or the table creation is done outside of greenDAO.
        createInDb = false,

        // Whether an all properties constructor should be generated.
        // A no-args constructor is always required.
        generateConstructors = true,

        // Whether getters and setters for properties should be generated if missing.
        generateGettersSetters = true
)
public class RoomInfo {

//    @Id(autoincrement = true)
//    @Property(nameInDb = "id")
//    private Long id;

    @Index
    @NotNull
    @Property(nameInDb = "room_id")
    private int roomId;
    @NotNull
    @Property(nameInDb = "name")
    private String name;
    @NotNull
    @Property(nameInDb = "number")
    private String number;

    @Generated(hash = 2142683164)
    public RoomInfo(int roomId, @NotNull String name, @NotNull String number) {
        this.roomId = roomId;
        this.name = name;
        this.number = number;
    }

    @Generated(hash = 1870725637)
    public RoomInfo() {
    }

    public int getRoomId() {
        return this.roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

}
