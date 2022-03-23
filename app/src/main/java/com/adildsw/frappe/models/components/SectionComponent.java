package com.adildsw.frappe.models.components;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class SectionComponent implements UIComponent {
    private final String id, title, subtitle, background, textColor, parent;
    private final String[] children;

    public SectionComponent(String id, String title, String subtitle, String background,
                            String textColor, String parent, String[] children) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.background = background;
        this.textColor = textColor;
        this.parent = parent;
        if (children.length == 1 && children[0].equals("")) {
            this.children = new String[0];
        }
        else {
            this.children = children;
        }
    }

    public SectionComponent(JSONObject data) throws JSONException {
        this(
                data.getString("id"),
                data.getString("title"),
                data.getString("subtitle"),
                data.getString("background"),
                data.getString("text-color"),
                data.getString("parent"),
                data.getString("children")
                        .replaceAll("\\[", "")
                        .replaceAll("\\]", "")
                        .replaceAll("\"", "")
                        .split(",")
        );
    }

    @Override
    public String getType() {
        return "enfrappe-ui-section";
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getParent() {
        return parent;
    }

    @Override
    public boolean hasChildren() {
        return true;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getBackground() {
        return background;
    }

    public String getTextColor() {
        return textColor;
    }

    public String[] getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return "SectionComponent{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", background='" + background + '\'' +
                ", textColor='" + textColor + '\'' +
                ", parent='" + parent + '\'' +
                ", children=" + Arrays.toString(children) +
                '}';
    }
}
