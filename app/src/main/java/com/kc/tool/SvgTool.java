package com.kc.tool;

/**
 * Created by Administrator on 2017/3/11.
 */
public class SvgTool {

    private static final float R_DOT = 3;
    private static final float W_Triangle = 8;
    private static final float BLANK = 3;


    public static String drawLine(float x1, float y1, float x2, float y2) {
        x1 += BLANK;
        x2 -= (2 * R_DOT + W_Triangle + BLANK);

        return "<line x1=\"" + x1 + "\" y1=\"" + y1 + "\" x2=\"" + x2 + "\" y2=\"" + y2 + "\" style=\"stroke:rgb(99,99,99);stroke-width:2\" marker-end=\"url(#triangle)\"/>" +
                drawCircle(x1 + R_DOT, y1) + drawCircle(x2 + W_Triangle + R_DOT, y2);
    }

    public static String drawRect(float x, float y, float width, float height) {
        return "<rect x=\"" + x + "\" y=\"" + y + "\" rx=\"20\" ry=\"20\" width=\"" + width + "\" height=\"" + height + "\" style=\"fill:blue;stroke:black;stroke-width:2;opacity:0.5\"/>";
    }

    public static String drawCircle(float cx, float cy) {
        return "<circle cx=\"" + cx + "\" cy=\"" + cy + "\" r=\"3\" fill=\"black\"/>";
    }

    public static String drawText(float x, float y, String text, String fileDir) {
        return "<text x=\"" + x + "\" y=\"" + y + "\" font-size=\"18\" fill =\"white\" font-style=\"italic\" onclick=\"self.location.href='file:///" + fileDir + "'\">" + text + "</text>";
    }

}
