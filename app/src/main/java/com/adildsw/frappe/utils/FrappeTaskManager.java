package com.adildsw.frappe.utils;

import android.os.Handler;

import java.util.HashMap;

public class FrappeTaskManager {

    private static FrappeTaskManager instance;
    private HashMap<String, Handler> handlerMap;
    private HashMap<String, Runnable> runnableMap;

    private FrappeTaskManager() {
        handlerMap = new HashMap<String, Handler>();
        runnableMap = new HashMap<String, Runnable>();
    }

    public static FrappeTaskManager getInstance() {
        if (instance == null) {
            instance = new FrappeTaskManager();
        }
        return instance;
    }

    public void addTask(FrappeTask task, long delayMillis) {
        String key = String.valueOf(System.currentTimeMillis());
        handlerMap.put(key, new Handler());
        runnableMap.put(key, new Runnable() {
            @Override
            public void run() {
                task.run();
                handlerMap.get(key).postDelayed(this, delayMillis);
            }
        });
        handlerMap.get(key).postDelayed(runnableMap.get(key), delayMillis);
    }

    public void stopAll() {
        for (String key : handlerMap.keySet()) {
            handlerMap.get(key).removeCallbacks(runnableMap.get(key));
        }
    }
}
