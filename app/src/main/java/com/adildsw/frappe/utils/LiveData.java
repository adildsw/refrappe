package com.adildsw.frappe.utils;

import android.util.Log;

import com.adildsw.frappe.models.DictModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LiveData {
    private static LiveData instance;
    private Map<String, DictModel> data;

    private LiveData() {
        data = new HashMap<String, DictModel>();
    }

    public static LiveData getInstance() {
        if (instance == null) {
            instance = new LiveData();
        }
        return instance;
    }

    public void set(String id, String name, String value) {
        if (data.containsKey(id)) {
            Objects.requireNonNull(data.get(id)).setKey(name);
            Objects.requireNonNull(data.get(id)).setValue(value);
        }
        else {
            data.put(id, new DictModel(name, value));
        }
        Log.println(Log.ASSERT, "LiveData", data.toString());
    }

    public String[] getLiveDataIds() {
        return data.keySet().toArray(new String[0]);
    }

    public String getName(String id) {
        return Objects.requireNonNull(data.get(id)).getKey();
    }

    public String getValue(String id) {
        return Objects.requireNonNull(data.get(id)).getValue();
    }

    public void clearData() {
        data.clear();
    }
}
