package com.adildsw.frappe.models.components;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class ButtonComponent implements UIComponent {
    private final String id, text, background, textColor, parent, onPressActionType, onPressActivity, onPressAPIResultDisplayType, onPressAPICallType, onPressAPIURL;
    private final String[] onPressAPIParams;
    private final JSONObject onPressAPICustomParams;

    public ButtonComponent(String id, String text, String background, String textColor, String parent,
                         String onPressActionType, String onPressActivity,
                         String onPressAPIResultDisplayType, String onPressAPICallType,
                         String onPressAPIURL, String[] onPressAPIParams,
                         JSONObject onPressAPICustomParams) {
        this.id = id;
        this.text = text;
        this.background = background;
        this.textColor = textColor;
        this.parent = parent;
        this.onPressActionType = onPressActionType;
        this.onPressActivity = onPressActivity;
        this.onPressAPIResultDisplayType = onPressAPIResultDisplayType;
        this.onPressAPICallType = onPressAPICallType;
        this.onPressAPIURL = onPressAPIURL;
        this.onPressAPIParams = onPressAPIParams;
        this.onPressAPICustomParams = onPressAPICustomParams;
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

    public String getOnPressAPIResultDisplayType() {
        return onPressAPIResultDisplayType;
    }

    public String getOnPressAPICallType() {
        return onPressAPICallType;
    }

    public String getOnPressAPIURL() {
        return onPressAPIURL;
    }

    public String[] getOnPressAPIParams() {
        return onPressAPIParams;
    }

    public JSONObject getOnPressAPICustomParams() {
        return onPressAPICustomParams;
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
                ", onPressAPIResultDisplayType='" + onPressAPIResultDisplayType + '\'' +
                ", onPressAPICallType='" + onPressAPICallType + '\'' +
                ", onPressAPIURL='" + onPressAPIURL + '\'' +
                ", onPressAPIParams=" + Arrays.toString(onPressAPIParams) +
                ", onPressAPICustomParams=" + onPressAPICustomParams +
                '}';
    }
}
