package com.rbu.erp_wms.utils;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 转换工具类
 */
public class TransformUtil {
    /**
     * text局部变色
     */
    public static void textColor(String s, int start, int end, TextView view) {
        SpannableStringBuilder style = new SpannableStringBuilder(s);
        style.setSpan(new ForegroundColorSpan(Color.RED), start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        view.setText(style);
    }

    /**
     * 报关转换文字
     */
    public static String isCustomsTostring(String isCustoms) {
        String isCustoms1 = "";
        if (isCustoms.equals("true") || isCustoms.equals("1") || isCustoms.equals("是")) {
            isCustoms1 = "是";
        } else if (isCustoms.equals("false") || isCustoms.equals("0") || isCustoms.equals("否"))  {
            isCustoms1 = "否";
        } else {
            isCustoms1 = "";
        }

        return isCustoms1;
    }

    /**
     * 判断字符串是否为空
     */
    public static String isNull(String s) {
        String s1 = null;
        if (s == null) {
            s1 = "";
        } else {
            s1 = s;
        }
        return s1;
    }

    /**
     * 仓库类型转换
     */
    public static void isWarehouseNull(String s, TextView textView) {
        if (TextUtils.isEmpty(s)) {
            textView.setText("------");
        } else {
            textView.setText(s);
        }
    }

    /**
     * 将文件集合转成map集合
     */
    public static Map<String, File> fils2Map(List<File> files) {
        LinkedHashMap<String, File> map = new LinkedHashMap<>();
        for (File file : files) {
            map.put(file.getName(), file);
        }
        return map;
    }

    /**
     * 将map转换成url
     */
    public static String getUrlParamsByMap(Map<String, String> map) {
        if (map == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey() + "=" + entry.getValue());
            sb.append("&");
        }
        String s = sb.toString();
        if (s.endsWith("&")) {
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }


    /**
     * 将M类型的文件大小转换成字节
     *
     * @param gSize 大小
     * @return 字节
     */
    public static long getSize(String gSize) {
        float size = gSize == null || gSize.equals("null") ? 0f : Float.valueOf(gSize);
        return (long) (size * 1024 * 1024);
    }

    /**
     * @param bytes 数据大小
     * @return 将一个单位为BYTE的LONG整数转化为以MB, 为单位的STRING忽略小数点第二位
     */
    public static String bytes2kb(long bytes) {
        BigDecimal filesize = new BigDecimal(bytes);
        BigDecimal megabyte = new BigDecimal(1024 * 1024);
        float returnValue = filesize.divide(megabyte, 1, BigDecimal.ROUND_FLOOR).floatValue();
        return ((returnValue - 0.1) + "");

    }

    public static <T> String toString(List<String> datas) {
        String info = "";
        for (int i = 0; i < datas.size(); i++) {
            if ((i == datas.size() - 1)) {
                info += datas.get(i);
            } else {
                info += datas.get(i) + ",";
            }
        }
        return info;
    }

}
