package com.adildsw.frappe.models.components;

import org.json.JSONException;
import org.json.JSONObject;

public class CheckboxComponent implements UIComponent{
    private final String id, name, label, textColor, parent;

    public CheckboxComponent(String id, String name, String label, String textColor, String parent){
        this.id = id;
        this.name = name;
        this.label = label;
        this.textColor = textColor;
        this.parent = parent;
    }

    public CheckboxComponent(JSONObject data) throws JSONException {
        this(
                data.getString("id"),
                data.getString("name"),
                data.getString("label"),
                data.getString("text-color"),
                data.getString("parent")
        );
    }

    @Override
    public String getType() {
        return "enfrappe-ui-checkbox";
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

    public String getName(){
        return name;
    }

    public String getLabel(){
        return label;
    }

    public String getTextColor(){
        return textColor;
    }

    @Override
    public String toString() {
        return "CheckboxComponent{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", label='" + label + '\'' +
                ", textColor='" + textColor + '\'' +
                ", parent='" + parent + '\'' +
                '}';
    }
}
