package com.adildsw.frappe.models.components;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class DropdownComponent implements UIComponent{
    private final String id, name, label, textColor, parent;
    private final String[] optionIds;
    private final JSONObject options;

    public DropdownComponent(String id, String name, String label, String textColor, String parent,
                          String[] optionIds, JSONObject options) {
        this.id = id;
        this.name = name;
        this.label = label;
        this.textColor = textColor;
        this.parent = parent;
        this.options = options;
        if (optionIds.length == 1 && optionIds[0].equals("")) {
            this.optionIds = new String[0];
        }
        else {
            this.optionIds = optionIds;
        }
    }

    public DropdownComponent(JSONObject data) throws JSONException {
        this(
                data.getString("id"),
                data.getString("name"),
                data.getString("label"),
                data.getString("text-color"),
                data.getString("parent"),
                data.getString("option-ids")
                        .replaceAll("\\[", "")
                        .replaceAll("\\]", "")
                        .replaceAll("\"", "")
                        .split(","),
                data.getJSONObject("options")
        );
    }

    @Override
    public String getType() {
        return "enfrappe-ui-dropdown";
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
        return false;
    }

    public String getName() {
        return name;
    }

    public String getLabel() {
        return label;
    }

    public String getTextColor() {
        return textColor;
    }

    public String[] getOptionIds() {
        return optionIds;
    }

    public JSONObject getOptions() {
        return options;
    }

    @Override
    public String toString() {
        return "DropdownComponent{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", label='" + label + '\'' +
                ", textColor='" + textColor + '\'' +
                ", parent='" + parent + '\'' +
                ", optionIds=" + Arrays.toString(optionIds) +
                ", options=" + options +
                '}';
    }
}
