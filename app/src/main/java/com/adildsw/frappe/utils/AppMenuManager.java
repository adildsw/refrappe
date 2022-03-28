package com.adildsw.frappe.utils;

import android.view.MenuItem;

import java.util.HashMap;

public class AppMenuManager {

    private static final HashMap<MenuItem, String> menuItemTag = new HashMap<>();

    public static void setMenuItemTag(MenuItem item, String tag)
    {
        menuItemTag.put(item, tag);
    }

    public static String getMenuItemTag(MenuItem item)
    {
        return menuItemTag.get(item);
    }

    public static MenuItem getMenuItem(String tag) {
        for (MenuItem item : menuItemTag.keySet()) {
            if (menuItemTag.get(item).equals(tag)) {
                return item;
            }
        }
        return null;
    }

    public static void uncheckAllMenuItems()
    {
        for (MenuItem item : menuItemTag.keySet())
        {
            item.setChecked(false);
        }
    }

}
