package com.adildsw.frappe.models.components;

import org.json.JSONException;
import org.json.JSONObject;

public class TextComponent implements UIComponent{
    private final String id, text, textColor, parent;
    private final boolean bold, italic;

    public TextComponent(String id, String text, String textColor, String parent, boolean bold,
                         boolean italic) {
        this.id = id;
        this.text = text;
        this.textColor = textColor;
        this.parent = parent;
        this.bold = bold;
        this.italic = italic;
    }

    public TextComponent(JSONObject data) throws JSONException {
        this(
                data.getString("id"),
                data.getString("text"),
                data.getString("text-color"),
                data.getString("parent"),
                data.getBoolean("bold"),
                data.getBoolean("italic")
        );
    }

    @Override
    public String getType() {
        return "enfrappe-ui-text";
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

    public String getText() {
        return text;
    }

    public String getTextColor() {
        return textColor;
    }

    public boolean isBold() {
        return bold;
    }

    public boolean isItalic() {
        return italic;
    }

    @Override
    public String toString() {
        return "TextComponent{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", textColor='" + textColor + '\'' +
                ", parent='" + parent + '\'' +
                ", bold=" + bold +
                ", italic=" + italic +
                '}';
    }
}
