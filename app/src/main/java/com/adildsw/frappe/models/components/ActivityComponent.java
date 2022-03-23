package com.adildsw.frappe.models.components;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class ActivityComponent implements UIComponent {
    private final String id, name, background;
    private final String[] children;

    public ActivityComponent(String id, String name, String background, String[] children) {
        this.id = id;
        this.name = name;
        this.background = background;
        if (children.length == 1 && children[0].equals("")) {
            this.children = new String[0];
        }
        else {
            this.children = children;
        }
    }

    public ActivityComponent(JSONObject data) throws JSONException {
        this(
                data.getString("id"),
                data.getString("name"),
                data.getString("background"),
                data.getString("children")
                        .replaceAll("\\[", "")
                        .replaceAll("\\]", "")
                        .replaceAll("\"", "")
                        .split(",")
        );
    }

    @Override
    public String getType() {
        return "enfrappe-ui-activity";
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getParent() {
        return null;
    }

    @Override
    public boolean hasChildren() {
        return true;
    }

    public String getName() {
        return name;
    }

    public String getBackground() {
        return background;
    }

    public String[] getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return "ActivityComponent{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", background='" + background + '\'' +
                ", children=" + Arrays.toString(children) +
                '}';
    }
}
