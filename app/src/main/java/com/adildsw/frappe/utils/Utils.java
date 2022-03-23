package com.adildsw.frappe.utils;

import android.content.res.ColorStateList;
import android.graphics.Color;

public class Utils {
    public static ColorStateList parseColorStateList(String colorString) {
        return ColorStateList.valueOf(Color.parseColor(colorString));
    }
}
