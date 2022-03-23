package com.adildsw.frappe.models.components;

import org.json.JSONException;
import org.json.JSONObject;

public class InputComponent implements UIComponent{
    private final String id, name, label, placeholder, textColor, parent;

    public InputComponent(String id, String name, String label, String placeholder,
                          String textColor, String parent) {
        this.id = id;
        this.name = name;
        this.label = label;
        this.placeholder = placeholder;
        this.textColor = textColor;
        this.parent = parent;
    }

    public InputComponent(JSONObject data) throws JSONException {
        this(
                data.getString("id"),
                data.getString("name"),
                data.getString("label"),
                data.getString("placeholder"),
                data.getString("text-color"),
                data.getString("parent")
        );
    }

    @Override
    public String getType() {
        return "enfrappe-ui-input";
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

    public String getPlaceholder() {
        return placeholder;
    }

    public String getTextColor() {
        return textColor;
    }

    @Override
    public String toString() {
        return "InputComponent{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", label='" + label + '\'' +
                ", placeholder='" + placeholder + '\'' +
                ", textColor='" + textColor + '\'' +
                ", parent='" + parent + '\'' +
                '}';
    }
}
