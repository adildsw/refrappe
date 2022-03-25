package com.adildsw.frappe.models;

import static android.util.Log.ASSERT;

import android.util.Log;

import com.adildsw.frappe.models.components.ActivityComponent;
import com.adildsw.frappe.models.components.CheckboxComponent;
import com.adildsw.frappe.models.components.DropdownComponent;
import com.adildsw.frappe.models.components.InputComponent;
import com.adildsw.frappe.models.components.RadioComponent;
import com.adildsw.frappe.models.components.SectionComponent;
import com.adildsw.frappe.models.components.ButtonComponent;
import com.adildsw.frappe.models.components.TextComponent;
import com.adildsw.frappe.models.components.UIComponent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AppModel {
    private final String appId, appName, appVersion, serverAddress, serverPort;
    private final boolean singleUse, locationLinked, notifyUser;
    private final long lastEdited;
    private final JSONObject componentData;
    private final List<UIComponent> components;

    public AppModel(String appId, String appName, String appVersion, String serverAddress,
               String serverPort, boolean singleUse, boolean locationLinked, boolean notifyUser,
               long lastEdited, JSONObject componentData ) throws JSONException {
        this.appId = appId;
        this.appName = appName;
        this.appVersion = appVersion;
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.singleUse = singleUse;
        this.locationLinked = locationLinked;
        this.notifyUser = notifyUser;
        this.lastEdited = lastEdited;
        this.componentData = componentData;
        this.components = new ArrayList<>();

        for (Iterator<String> it = componentData.keys(); it.hasNext(); ) {
            String key = it.next();
            String type = componentData.getJSONObject(key).getString("type");
            switch (type) {
                case "enfrappe-ui-activity":
                    this.components.add(new ActivityComponent(componentData.getJSONObject(key)));
                    break;
                case "enfrappe-ui-section":
                    this.components.add(new SectionComponent(componentData.getJSONObject(key)));
                    break;
                case "enfrappe-ui-button":
                    this.components.add(new ButtonComponent(componentData.getJSONObject(key)));
                    break;
                case "enfrappe-ui-text":
                    this.components.add(new TextComponent(componentData.getJSONObject(key)));
                    break;
                case "enfrappe-ui-input":
                    this.components.add(new InputComponent(componentData.getJSONObject(key)));
                    break;
                case "enfrappe-ui-checkbox":
                    this.components.add(new CheckboxComponent(componentData.getJSONObject(key)));
                    break;
                case "enfrappe-ui-radio":
                    this.components.add(new RadioComponent(componentData.getJSONObject(key)));
                    break;
                case "enfrappe-ui-dropdown":
                    this.components.add(new DropdownComponent(componentData.getJSONObject(key)));
                    break;
                default:
                    Log.println(ASSERT, "App", "Unknown component type: " + type);
            }
        }
    }

    public AppModel(String data) throws JSONException {
        this(new JSONObject(data));
    }

    public AppModel(JSONObject data) throws JSONException {
        this(
                (String) data.getJSONObject("app-data").get("app-id"),
                (String) data.getJSONObject("app-data").get("app-name"),
                (String) data.getJSONObject("app-data").get("app-version"),
                (String) data.getJSONObject("app-data").get("server-address"),
                (String) data.getJSONObject("app-data").get("server-port"),
                (boolean) data.getJSONObject("app-data").get("single-use"),
                (boolean) data.getJSONObject("app-data").get("location-linked"),
                (boolean) data.getJSONObject("app-data").get("notify-user"),
                Math.max(
                    (long) data.getJSONObject("app-data").get("last-edited"),
                    (long) data.getJSONObject("component-data").get("last-edited")
                ),
                data.getJSONObject("component-data").getJSONObject(("components"))
        );
    }

    public String getAppId() {
        return appId;
    }

    public String getAppName() {
        return appName;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public String getServerPort() {
        return serverPort;
    }

    public boolean isSingleUse() {
        return singleUse;
    }

    public boolean isLocationLinked() {
        return locationLinked;
    }

    public boolean isNotifyUser() {
        return notifyUser;
    }

    public long getLastEdited() {
        return lastEdited;
    }

    public JSONObject getComponentData() {
        return componentData;
    }

    public List<UIComponent> getComponents() {
        return components;
    }

    public UIComponent getComponentById(String id) {
        for (int i = 0; i < components.size(); i++) {
            if (components.get(i).getId().equals(id)) {
                return components.get(i);
            }
        }
        return null;
    }

    public String getActivityIdByName(String name) {
        for (int i = 0; i < components.size(); i++) {
            if (components.get(i).getType().equals("enfrappe-ui-activity")) {
                if (((ActivityComponent) components.get(i)).getName().equals(name)) {
                    return components.get(i).getId();
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "AppModel{" +
                "appId='" + appId + '\'' +
                ", appName='" + appName + '\'' +
                ", appVersion='" + appVersion + '\'' +
                ", serverAddress='" + serverAddress + '\'' +
                ", serverPort='" + serverPort + '\'' +
                ", singleUse=" + singleUse +
                ", locationLinked=" + locationLinked +
                ", notifyUser=" + notifyUser +
                ", lastEdited=" + lastEdited +
                '}';
    }
}
