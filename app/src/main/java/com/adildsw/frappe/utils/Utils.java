package com.adildsw.frappe.utils;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;

import java.util.Base64;
import java.util.zip.Inflater;

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

    public static String decompressString(String data, int length) {
        try {
            byte[] decodedString = Base64.getDecoder().decode(data.getBytes("UTF-8"));
            Inflater inflater = new Inflater();
            inflater.setInput(decodedString);
            byte[] result = new byte[length];
            int resultLength = inflater.inflate(result);
            inflater.end();
            return new String(result, 0, resultLength, "UTF-8");
        }
        catch (Exception e) {
            Log.e("MainActivity", "Error decoding string", e);
            return null;
        }
    }
}
