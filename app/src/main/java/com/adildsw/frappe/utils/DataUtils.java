package com.adildsw.frappe.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.MenuItem;

import com.adildsw.frappe.models.AppModel;
import com.google.gson.Gson;

import org.json.JSONException;

import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.zip.Inflater;

public class DataUtils {

    public static String saveAppFromDeepLink(String data, Context context) {
        if (data != null) {
            Log.println(Log.ASSERT, "FrappeMainActivity", "Data: " + data.toString());
            String dlData = data.replace("http://frappe.com/load?data=", "");
            String appId = dlData.split("_")[0];
            int currentPacket = Integer.parseInt(dlData.split("_")[1]);
            int totalPacket = Integer.parseInt(dlData.split("_")[2]);
            int dataLength = Integer.parseInt(dlData.split("_")[3]);
            String dataString = dlData.split("_")[4];
            String decompressedData = Utils.decompressString(dataString, dataLength);

            AppModel app;
            try {
                app = new AppModel(decompressedData);
            } catch (JSONException e) {
                Log.e("FrappeMainActivity", "Error while decompressing data", e);
                return null;
            }

            if (totalPacket == 1) {
                DataUtils.saveApp(app.getAppId(), app.getAppName(), decompressedData, context);
                return appId;
            }
        }
        return null;
    }

    public static void clearStoredData(Context context) {
        SharedPreferences sPref = context.getSharedPreferences("apps", Context.MODE_PRIVATE);
        sPref.edit().clear().commit();

        SharedPreferences sPref2 = context.getSharedPreferences("appNames", Context.MODE_PRIVATE);
        sPref2.edit().clear().commit();
    }

    private static void saveApp(String appId, String appName, String apps, Context context) {
        SharedPreferences sPref = context.getSharedPreferences("apps", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putString(appId, apps);
        editor.commit();

        SharedPreferences sPref2 = context.getSharedPreferences("appNames", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sPref2.edit();
        editor2.putString(appId, appName);
        Log.println(Log.ASSERT, "FrappeMainActivity", "Saving app: " + appId + " " + appName);
        editor2.commit();
    }

    private static boolean saveAppPacket(String appId, int currentPacket, int totalPacket, String data, Context context) {
        SharedPreferences sPref = context.getSharedPreferences("appPackets", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putString(appId + "_" + currentPacket + "_" + totalPacket, data);
        editor.commit();

        String[] packets = new String[totalPacket];
        Arrays.fill(packets, "");

        String[] appPackets = sPref.getAll().keySet().toArray(new String[0]);
        for (String appPacket : appPackets) {
            if (appPacket.startsWith(appId)) {
                int packet = Integer.parseInt(appPacket.split("_")[1]);
                packets[packet - 1] = sPref.getString(appPacket, "");
            }
        }

        for (String packet : packets) {
            if (packet.equals("")) {
                return false;
            }
        }

        StringBuilder combinedAppData = new StringBuilder();
        for (String packet : packets) {
            combinedAppData.append(packet);
        }
//        sPref = context.getSharedPreferences("apps", Context.MODE_PRIVATE);
//        editor = sPref.edit();
//        editor.putString(appId, combinedAppData.toString());
//        editor.commit();

        return true;
    }

    public static boolean doesPacketExist(String rawData, Context context) {
        rawData = rawData.replace("http://frappe.com/load?data=", "");
        String appId = rawData.split("_")[0];
        int currentPacket = Integer.parseInt(rawData.split("_")[1]);

        SharedPreferences sPref = context.getSharedPreferences("appPackets", Context.MODE_PRIVATE);
        String[] appPackets = sPref.getAll().keySet().toArray(new String[0]);
        for (String appPacket : appPackets) {
            if (appPacket.startsWith(appId)) {
                int packet = Integer.parseInt(appPacket.split("_")[1]);
                if (packet == currentPacket) {
                    return true;
                }
            }
        }
        return false;
    }

    public static AppModel loadApp(String appId, Context context) {
        SharedPreferences sPref = context.getSharedPreferences("apps", Context.MODE_PRIVATE);
        String data = sPref.getString(appId, "");
        if (data == null) {
            return null;
        }
        AppModel app;
        try {
            app = new AppModel(data);
        } catch (JSONException e) {
            Log.e("DataUtils", "Error while loading app from stored data.", e);
            return null;
        }
        return app;
    }

    public static String getAppNameFromId(String appId, Context context) {
        SharedPreferences sPref = context.getSharedPreferences("appNames", Context.MODE_PRIVATE);
        Log.println(Log.ASSERT, "DataUtils", "App name: " + Arrays.toString(sPref.getAll().keySet().toArray(new String[0])));
        Log.d("DataUtils", "App name: " + sPref.getString(appId, "nooo"));
        return sPref.getString(appId, appId);
    }

    public static String[] getAllAppIds(Context context) {
        SharedPreferences sPref = context.getSharedPreferences("apps", Context.MODE_PRIVATE);
        return sPref.getAll().keySet().toArray(new String[0]);
    }


}
