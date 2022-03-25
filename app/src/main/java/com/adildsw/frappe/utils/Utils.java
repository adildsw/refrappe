package com.adildsw.frappe.utils;

import android.content.res.ColorStateList;
import android.graphics.Color;

public class Utils {

    public static ColorStateList parseColorStateList(String colorString) {
        return ColorStateList.valueOf(Color.parseColor(colorString));
    }

    public static String getProcessedUrl(String address, String port, String api) {
        String url = address;

        if (!url.startsWith("http")) {
            url = "http://" + url;
        }
        if (url.endsWith(":")) {
            url = url.substring(0, url.length() - 1);
        }
        url = url + ":" + port;
        if (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }
        url = url + api;

        return url;
    }
}
