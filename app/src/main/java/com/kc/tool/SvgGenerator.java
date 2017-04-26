package com.kc.tool;

import android.util.Log;

import com.kc.bean.InfoSet;
import com.kc.data.DataHandle;
import com.kc.util.AppConstants;

import java.util.List;

/**
 * Created by Administrator on 2017/3/11.
 */
public class SvgGenerator {

    private static final String TAG = "SvgGenerator";

    public static final String HTML_HEAD = "<?xml version=\"1.0\" standalone=\"no\"?>";

    public static final String SVG_START = "<svg width=\"100%\" height=\"100%\" version=\"1.1\"\n" +
            "xmlns=\"http://www.w3.org/2000/svg\">" +
            "<defs><style type=\"text/css\"><![CDATA[ rect {fill:white;stroke:black;stroke-width:2;opacity:0.1;}]]></style>" +
            "<marker id=\"triangle\" viewBox=\"0 0 10 10\" refX=\"0\" refY=\"5\" markerUnits=\"strokeWidth\" markerWidth=\"5\" markerHeight=\"4\" orient=\"auto\">" +
            "<path d=\"M 0 0 L 10 5 L 0 10 z\" /> </marker></defs>";

    public static final String SVG_END =
            /*"<script xlink:href=\"../../js/find5ex.js\"></script>"+*/
            "</svg>";

    private static final float BLANK_SML = 10;
    private static final float BLANK_NOR = 20;
    private static final float BLANK_MID = 30;
    private static final float BLANK_BIG = 40;
    private static final float BLANK_XBIG = 50;

    private static final float TEXT_SML = 11;
    private static final float TEXT_NOR = 17;
    private static final float TEXT_MID = 18;
    private static final float TEXT_BIG = 20;
    private static final float TEXT_XBIG = 30;

    private static final float X_ORI = 100;
    private static final float Y_ORI = 100;
    private static final float W_RECT = 300;
    private static final float H_RECT = 50;
    private static final float H_BLANK = 100;
    private static final float V_BLANK = 5;

    public static String getSvg() {
        int numPort = 5;

        float xOri = 100;
        float yOri = 100;
        float wRect = 300;
        float hRect = 200;
        float hBlank = 100;
        float vBlank = 50;

        StringBuffer sb = new StringBuffer();
        sb.append(HTML_HEAD).append(SVG_START);
        sb.append(SvgTool.drawRect(xOri, yOri, wRect, hRect));

        float xRect2 = xOri + wRect + hBlank;
        float yRect2 = yOri;
        sb.append(SvgTool.drawRect(xRect2, yRect2, wRect, hRect));

        float vBlankPort = hRect / (numPort + 1);
        for (int i = 0; i < numPort; i++) {
            float y = yOri + vBlankPort * (i + 1);
            sb.append(SvgTool.drawLine(xOri + wRect, y, xRect2, y));
        }

        sb.append(SvgTool.drawText(xOri + BLANK_SML, yOri + BLANK_XBIG, "才哥是", "abc"))
                .append(SvgTool.drawText(xRect2 + BLANK_SML, yRect2 + BLANK_XBIG, "帅才", "abc"))
                .append(SVG_END);

        return sb.toString();
    }

    /**
     * @param fileDir 格式为：/0/storage/...
     */
    public static String getSvg(int devId, String fileDir) {
        List<InfoSet> infoSets = DataHandle.getInfoSets(devId);
        if (infoSets.size() == 0) {
            return null;
        }

        StringBuffer sb = new StringBuffer();
        sb.append(HTML_HEAD);
        int size = infoSets.size();
        sb.append(getSvgStart(X_ORI + (W_RECT + H_BLANK) * 6, Y_ORI + (H_RECT + V_BLANK) * size));
        for (int i = 0; i < size; i++) {
            InfoSet infoSet = infoSets.get(i);

            if (infoSet.getTxiedId() != 0) {
                float y = Y_ORI + i * (H_RECT + V_BLANK);
                sb.append(SvgTool.drawRect(X_ORI, y, W_RECT, H_RECT));//画矩形

                String text = DataHandle.getDeviceName(infoSet.getTxiedId());
                Log.d(TAG, "svg line:" + i + ", txiedId:" + infoSet.getTxiedId() + ", name:" + text);
                float y1 = y + H_RECT / 2;
                String fDir = new StringBuffer().append(fileDir).append(AppConstants.DEVICE).append(infoSet.getTxiedId()).append(AppConstants.SUFFIX_HTML).toString();
                sb.append(SvgTool.drawText(X_ORI + BLANK_SML, y1, text, fDir));//画文本
            } else {
                continue;
            }
            if (infoSet.getRxiedId() != 0) {
                float x = X_ORI + W_RECT + H_BLANK;
                float y = Y_ORI + i * (H_RECT + V_BLANK);
                sb.append(SvgTool.drawRect(x, y, W_RECT, H_RECT));//画矩形

                String text = DataHandle.getDeviceName(infoSet.getRxiedId());
                Log.d(TAG, "svg line:" + i + ", RxiedId:" + infoSet.getRxiedId() + ", name:" + text);
                float y0 = y + H_RECT / 2;
                String fDir = new StringBuffer().append(fileDir).append(AppConstants.DEVICE).append(infoSet.getTxiedId()).append(AppConstants.SUFFIX_HTML).toString();
                sb.append(SvgTool.drawText(x + BLANK_SML, y0, text, fDir));//画文本
                //画箭头
                float y1 = y + H_RECT / 2;
                sb.append(SvgTool.drawLine(x - H_BLANK, y1, x, y1));//画线

            } else {
                continue;
            }
            if (infoSet.getSwitch1Id() != 0) {
                float x = X_ORI + (W_RECT + H_BLANK) * 2;
                float y = Y_ORI + i * (H_RECT + V_BLANK);
                sb.append(SvgTool.drawRect(x, y, W_RECT, H_RECT));

                String text = DataHandle.getDeviceName(infoSet.getSwitch1Id());
                Log.d(TAG, "svg line:" + i + ", Switch1Id:" + infoSet.getSwitch1Id() + ", name:" + text);
                float y0 = y + H_RECT / 2;
                String fDir = new StringBuffer().append(fileDir).append(AppConstants.DEVICE).append(infoSet.getTxiedId()).append(AppConstants.SUFFIX_HTML).toString();
                sb.append(SvgTool.drawText(x + BLANK_SML, y0, text, fDir));
                //画箭头
                float y1 = y + H_RECT / 2;
                sb.append(SvgTool.drawLine(x - H_BLANK, y1, x, y1));

            } else {
                continue;
            }
            if (infoSet.getSwitch2Id() != 0) {
                float x = X_ORI + (W_RECT + H_BLANK) * 3;
                float y = Y_ORI + i * (H_RECT + V_BLANK);
                sb.append(SvgTool.drawRect(x, y, W_RECT, H_RECT));

                String text = DataHandle.getDeviceName(infoSet.getSwitch2Id());
                Log.d(TAG, "svg line:" + i + ", Switch2Id:" + infoSet.getSwitch2Id() + ", name:" + text);
                float y0 = y + H_RECT / 2;
                String fDir = new StringBuffer().append(fileDir).append(AppConstants.DEVICE).append(infoSet.getTxiedId()).append(AppConstants.SUFFIX_HTML).toString();
                sb.append(SvgTool.drawText(x + BLANK_SML, y0, text, fDir));
                //画箭头
                float y1 = y + H_RECT / 2;
                sb.append(SvgTool.drawLine(x - H_BLANK, y1, x, y1));

            } else {
                continue;
            }
            if (infoSet.getSwitch3Id() != 0) {
                float x = X_ORI + (W_RECT + H_BLANK) * 4;
                float y = Y_ORI + i * (H_RECT + V_BLANK);
                sb.append(SvgTool.drawRect(x, y, W_RECT, H_RECT));

                String text = DataHandle.getDeviceName(infoSet.getSwitch3Id());
                Log.d(TAG, "svg line:" + i + ", Switch3Id:" + infoSet.getSwitch3Id() + ", name:" + text);
                float y0 = y + H_RECT / 2;
                String fDir = new StringBuffer().append(fileDir).append(AppConstants.DEVICE).append(infoSet.getTxiedId()).append(AppConstants.SUFFIX_HTML).toString();
                sb.append(SvgTool.drawText(x + BLANK_SML, y0, text, fDir));
                //画箭头
                float y1 = y + H_RECT / 2;
                sb.append(SvgTool.drawLine(x - H_BLANK, y1, x, y1));

            } else {
                continue;
            }
            if (infoSet.getSwitch4Id() != 0) {
                float x = X_ORI + (W_RECT + H_BLANK) * 5;
                float y = Y_ORI + i * (H_RECT + V_BLANK);
                sb.append(SvgTool.drawRect(x, y, W_RECT, H_RECT));

                String text = DataHandle.getDeviceName(infoSet.getSwitch4Id());
                Log.d(TAG, "svg line:" + i + ", Switch4Id:" + infoSet.getSwitch4Id() + ", name:" + text);
                float y0 = y + H_RECT / 2;
                String fDir = new StringBuffer().append(fileDir).append(AppConstants.DEVICE).append(infoSet.getTxiedId()).append(AppConstants.SUFFIX_HTML).toString();
                sb.append(SvgTool.drawText(x + BLANK_SML, y0, text, fDir));
                //画箭头
                float y1 = y + H_RECT / 2;
                sb.append(SvgTool.drawLine(x - H_BLANK, y1, x, y1));

            } else {
                continue;
            }

        }
        sb.append(SVG_END);

        return sb.toString();
    }

    private static String getSvgStart(float w, float h) {
        return "<svg width=\"" + w + "\" height=\"" + h + "\" version=\"1.1\"\n" +
                "xmlns=\"http://www.w3.org/2000/svg\">" +
                "<defs><style type=\"text/css\"><![CDATA[ rect {fill:white;stroke:black;stroke-width:2;opacity:0.1;}]]></style>" +
                "<marker id=\"triangle\" viewBox=\"0 0 10 10\" refX=\"0\" refY=\"5\" markerUnits=\"strokeWidth\" markerWidth=\"5\" markerHeight=\"4\" orient=\"auto\">" +
                "<path d=\"M 0 0 L 10 5 L 0 10 z\" /> </marker></defs>";
    }
}
