package com.adildsw.frappe.models.components;

import org.json.JSONException;
import org.json.JSONObject;

public class ChartComponent implements UIComponent{
    private final String id, background, textColor, lineColor, title, xLabel, yLabel, parent;
    private final String apiCallType, apiUrl;
    private final JSONObject apiCustomParams;

    public ChartComponent(String id, String background, String textColor, String lineColor, String title, String xLabel, String yLabel,
                          String apiCallType, String apiUrl, JSONObject apiCustomParams,
                          String parent) {
        this.id = id;
        this.background = background;
        this.textColor = textColor;
        this.lineColor = lineColor;
        this.title = title;
        this.xLabel = xLabel;
        this.yLabel = yLabel;
        this.apiCallType = apiCallType;
        this.apiUrl = apiUrl;
        this.apiCustomParams = apiCustomParams;
        this.parent = parent;
    }

    public ChartComponent(JSONObject data) throws JSONException {
        this(
                data.getString("id"),
                data.getString("background"),
                data.getString("text-color"),
                data.getString("line-color"),
                data.getString("title"),
                data.getString("x-label"),
                data.getString("y-label"),
                data.getString("api-call-type"),
                data.getString("api-url"),
                data.getJSONObject("api-custom-params"),
                data.getString("parent")
        );
    }

    @Override
    public String getType() {
        return "enfrappe-ui-chart";
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

    public String getBackground() {
        return background;
    }

    public String getTextColor() {
        return textColor;
    }

    public String getLineColor() {
        return lineColor;
    }

    public String getTitle() {
        return title;
    }

    public String getXLabel() {
        return xLabel;
    }

    public String getYLabel() {
        return yLabel;
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

    @Override
    public String toString() {
        return "ChartComponent{" +
                "id='" + id + '\'' +
                ", background='" + background + '\'' +
                ", textColor='" + textColor + '\'' +
                ", lineColor='" + lineColor + '\'' +
                ", title='" + title + '\'' +
                ", xLabel='" + xLabel + '\'' +
                ", yLabel='" + yLabel + '\'' +
                ", parent='" + parent + '\'' +
                ", apiCallType='" + apiCallType + '\'' +
                ", apiUrl='" + apiUrl + '\'' +
                ", apiCustomParams=" + apiCustomParams +
                '}';
    }
}
