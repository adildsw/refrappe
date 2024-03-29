package com.adildsw.frappe.models.components;

import org.json.JSONException;
import org.json.JSONObject;

public class TextComponent implements UIComponent{
    private final String id, text, textColor, size, align, parent;
    private final boolean bold, italic, underline, tight;

    public TextComponent(String id, String text, String textColor, String size, String align,
                         boolean bold, boolean italic, boolean underline, boolean tight,
                         String parent) {
        this.id = id;
        this.text = text;
        this.textColor = textColor;
        this.parent = parent;
        this.bold = bold;
        this.italic = italic;
        this.underline = underline;
        this.tight = tight;
        this.size = size;
        this.align = align;
    }

    public TextComponent(JSONObject data) throws JSONException {
        this(
                data.getString("id"),
                data.getString("text"),
                data.getString("text-color"),
                data.getString("size"),
                data.getString("align"),
                data.getBoolean("bold"),
                data.getBoolean("italic"),
                data.getBoolean("underline"),
                data.getBoolean("tight"),
                data.getString("parent")
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

    public boolean isUnderline() {
        return underline;
    }

    public boolean isTight() {
        return tight;
    }

    public String getSize() {
        return size;
    }

    public String getAlign() {
        return align;
    }

    @Override
    public String toString() {
        return "TextComponent{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", textColor='" + textColor + '\'' +
                ", size='" + size + '\'' +
                ", align='" + align + '\'' +
                ", parent='" + parent + '\'' +
                ", bold=" + bold +
                ", italic=" + italic +
                ", underline=" + underline +
                ", tight=" + tight +
                '}';
    }
}
