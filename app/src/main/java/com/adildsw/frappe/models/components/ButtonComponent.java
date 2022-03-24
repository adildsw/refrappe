package com.adildsw.frappe.models.components;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class ButtonComponent implements UIComponent {
    private final String id, text, background, textColor, parent, onPressActionType, onPressActivity, onPressApiResultDisplayType, onPressApiCallType, onPressApiUrl;
    private final String[] onPressApiParams;
    private final JSONObject onPressApiCustomParams;

    public ButtonComponent(String id, String text, String background, String textColor, String parent,
                         String onPressActionType, String onPressActivity,
                         String onPressApiResultDisplayType, String onPressApiCallType,
                         String onPressApiUrl, String[] onPressApiParams,
                         JSONObject onPressApiCustomParams) {
        this.id = id;
        this.text = text;
        this.background = background;
        this.textColor = textColor;
        this.parent = parent;
        this.onPressActionType = onPressActionType;
        this.onPressActivity = onPressActivity;
        this.onPressApiResultDisplayType = onPressApiResultDisplayType;
        this.onPressApiCallType = onPressApiCallType;
        this.onPressApiUrl = onPressApiUrl;
        this.onPressApiCustomParams = onPressApiCustomParams;
        if (onPressApiParams.length == 1 && onPressApiParams[0].equals("")) {
            this.onPressApiParams = new String[0];
        }
        else {
            this.onPressApiParams = onPressApiParams;
        }
    }

    public ButtonComponent(JSONObject data) throws JSONException {
        this(
                data.getString("id"),
                data.getString("text"),
                data.getString("background"),
                data.getString("text-color"),
                data.getString("parent"),
                data.getString("on-press-action-type"),
                data.getString("on-press-activity"),
                data.getString("on-press-api-result-display-type"),
                data.getString("on-press-api-call-type"),
                data.getString("on-press-api-url"),
                data.getString("on-press-api-params")
                        .replaceAll("\\[", "")
                        .replaceAll("\\]", "")
                        .replaceAll("\"", "")
                        .split(","),
                data.getJSONObject("on-press-api-custom-params")
        );
    }

    @Override
    public String getType() {
        return "enfrappe-ui-button";
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

    public String getText() {
        return text;
    }

    public String getBackground() {
        return background;
    }

    public String getTextColor() {
        return textColor;
    }

    public String getOnPressActionType() {
        return onPressActionType;
    }

    public String getOnPressActivity() {
        return onPressActivity;
    }

    public String getOnPressApiResultDisplayType() {
        return onPressApiResultDisplayType;
    }

    public String getOnPressApiCallType() {
        return onPressApiCallType;
    }

    public String getOnPressApiUrl() {
        return onPressApiUrl;
    }

    public String[] getOnPressApiParams() {
        return onPressApiParams;
    }

    public JSONObject getOnPressApiCustomParams() {
        return onPressApiCustomParams;
    }

    @Override
    public String toString() {
        return "TextComponent{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", background='" + background + '\'' +
                ", textColor='" + textColor + '\'' +
                ", parent='" + parent + '\'' +
                ", onPressActionType='" + onPressActionType + '\'' +
                ", onPressActivity='" + onPressActivity + '\'' +
                ", onPressApiResultDisplayType='" + onPressApiResultDisplayType + '\'' +
                ", onPressApiCallType='" + onPressApiCallType + '\'' +
                ", onPressApiUrl='" + onPressApiUrl + '\'' +
                ", onPressApiParams=" + Arrays.toString(onPressApiParams) +
                ", onPressApiCustomParams=" + onPressApiCustomParams +
                '}';
    }
}
