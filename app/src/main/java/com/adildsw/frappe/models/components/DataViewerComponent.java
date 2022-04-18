package com.adildsw.frappe.models.components;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class DataViewerComponent implements UIComponent{
    private final String id, text, textColor, size, align, apiCallType, apiUrl, parent;
    private final boolean bold, italic, underline, tight;
    private final JSONObject apiCustomParams;
    private final int refreshInterval;

    public DataViewerComponent(String id, String text, String textColor, String size, String align,
                               boolean bold, boolean italic, boolean underline, boolean tight,
                               String apiCallType, String apiUrl, JSONObject apiCustomParams,
                               int refreshInterval, String parent) {
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
        this.apiCallType = apiCallType;
        this.apiUrl = apiUrl;
        this.apiCustomParams = apiCustomParams;
        this.refreshInterval = refreshInterval;
    }

    public DataViewerComponent(JSONObject data) throws JSONException {
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
                data.getString("api-call-type"),
                data.getString("api-url"),
                data.getJSONObject("api-custom-params"),
                data.getInt("refresh-interval"),
                data.getString("parent")
        );
    }

    @Override
    public String getType() {
        return "enfrappe-ui-dataviewer";
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

    public String getApiCallType() {
        return apiCallType;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public JSONObject getApiCustomParams() {
        return apiCustomParams;
    }

    public int getRefreshInterval() {
        return refreshInterval;
    }

    @Override
    public String toString() {
        return "DataViewerComponent{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", textColor='" + textColor + '\'' +
                ", size='" + size + '\'' +
                ", align='" + align + '\'' +
                ", apiCallType='" + apiCallType + '\'' +
                ", apiUrl='" + apiUrl + '\'' +
                ", parent='" + parent + '\'' +
                ", bold=" + bold +
                ", italic=" + italic +
                ", underline=" + underline +
                ", tight=" + tight +
                ", apiCustomParams=" + apiCustomParams +
                ", refreshInterval=" + refreshInterval +
                '}';
    }
}
