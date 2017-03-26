package com.kc.tool;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.kc.bean.dbjs.Cubicle;
import com.kc.bean.dbjs.DbJs;
import com.kc.bean.dbjs.Device;
import com.kc.bean.dbjs.Room;
import com.kc.data.DataHandle;

import java.util.List;

/**
 * Created by Administrator on 2017/3/17.
 */
public class HtmlGenerator {

    private static final String TAG = "HtmlGenerator";

    /**
     * 得到每个数据库对应的index.html页面数据
     */
    public static String getIndexHtmlContent(String dbShortName) {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                " <head> \n" +
                "  <title></title>\n" +
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" /> \n" +
                "  <meta charset=\"UTF-8\" /> \n" +
                "  <!-- Bootstrap --> \n" +
                "  <link href=\"../../css/bootstrap.min.css\" rel=\"stylesheet\" media=\"screen\" /> \n" +
                " <style>\n" +
                "a{\n" +
                " color:gray;\n" +
                " }\n" +
                " ul{\n" +
                " background-color:white;\n" +
                " }\n" +
                "  li{\n" +
                "     display:block;\n" +
                "     white-space:nowrap;     //强制不换行\n" +
                "     overflow:hidden;            //自动隐藏文字\n" +
                "     text-overflow: ellipsis;    //文字隐藏后添加省略号\n" +
                "     -o-text-overflow:ellipsis; //适用于opera浏览器\n" +
                "  }\n" +
                "  Div{overflow:hidden}\n" +
                "    div#div1{ \n" +
                "position:fixed; \n" +
                "top:0; \n" +
                "left:0; \n" +
                "bottom:0; \n" +
                "right:0; \n" +
                "z-index:-1; \n" +
                "} \n" +
                "div#div1 > img { \n" +
                "height:100%; \n" +
                "width:100%; \n" +
                "border:0; \n" +
                "} \n" +
                " </style>\n" +
                " </head> \n" +
                " <script src=\"../../js/find5ex.js\"></script>\n" +
                "   <body>\n" +
                "   <div id=\"div1\"><img src=\"../../img/bg.jpg\" /></div> \n" +
                "  <br />\n" +
                "   <div id = \"rooms\"></div>\n" +
                "\n" +
                "  <script src=\"../../js/jquery-1.7.2.min.js\"></script> \n" +
                "  <script src=\"../../js/bootstrap.min.js\"></script>  \n" +
                "<script src=\"../../js4db/" + dbShortName + ".js\"></script>\n" +
                "   <script src=\"../../js/index.js\"></script>\n" +
                " </body>\n" +
                "</html>";
    }

    /**
     * 得到数据库数据的js文本，如db.js的内容：为json字符串
     */
    public static String getCurDbJsContent() {
        List<Room> roomInfos = DataHandle.getRooms4Js();
        List<Cubicle> cubicles = null;
        List<Device> devices = null;

        DbJs dbJs = new DbJs();

        for (int i = 0; i < roomInfos.size(); i++) {
            Room room = roomInfos.get(i);
            Log.e(TAG, "房间信息：" + room.toString());
            String roomId = room.getRoomid();

            cubicles = DataHandle.getCubicles4Js(Integer.parseInt(roomId));
            for (int j = 0; j < cubicles.size(); j++) {
                Cubicle cubicle = cubicles.get(j);
                Log.e(TAG, "柜信息：" + cubicle.toString());
                String cubicleId = cubicle.getCubicleid();

                devices = DataHandle.getDevices4Js(Integer.parseInt(cubicleId));
                cubicle.setDevices(devices);
            }
            room.setCubicles(cubicles);
        }
        dbJs.setData(roomInfos);

        return new StringBuffer().append("var db = ")
                .append("{\"Data\":")
                .append(JSON.toJSONString(roomInfos))
                .append("}")
                .toString();

//        return new StringBuffer().append("var db = ")
//                .append(JSON.toJSONString(dbJs, false)).toString();

    }
}
