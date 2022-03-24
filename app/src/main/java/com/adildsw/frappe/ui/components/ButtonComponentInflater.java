package com.adildsw.frappe.ui.components;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.adildsw.frappe.FrappeMainActivity;
import com.adildsw.frappe.R;
import com.adildsw.frappe.models.AppModel;
import com.adildsw.frappe.models.components.ButtonComponent;
import com.adildsw.frappe.utils.DataUtils;
import com.adildsw.frappe.utils.LiveData;
import com.adildsw.frappe.utils.Utils;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ButtonComponentInflater {
    AppModel app;
    ButtonComponent component;
    Context context;
    ViewGroup viewGroup;
    FragmentActivity activity;

    LiveData liveData = LiveData.getInstance();

    public ButtonComponentInflater(AppModel app, ButtonComponent component, ViewGroup viewGroup,
                                   Context context, FragmentActivity activity) {
        this.app = app;
        this.component = component;
        this.context = context;
        this.viewGroup = viewGroup;
        this.activity = activity;
    }

    public View inflate() {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.component_button, viewGroup, false);
        ((Button) view.findViewById(R.id.button)).setTag(component.getId());

        // Text
        ((Button) view.findViewById(R.id.button)).setText(component.getText());
        ((Button) view.findViewById(R.id.button))
                .setTextColor(Utils.parseColorStateList(component.getTextColor()));
        ((Button) view.findViewById(R.id.button))
                .setBackgroundTintList(Utils.parseColorStateList(component.getBackground()));

        // OnClick
        ((Button) view.findViewById(R.id.button)).setOnClickListener(view1 -> {
            if (component.getOnPressActionType().equals("activity")) {
                changeActivity(component.getOnPressActivity());
            }
            else if (component.getOnPressActionType().equals("api")) {
                performAPICall();
            }
        });

        return view;
    }

    private void changeActivity(String activityId) {
        if (!activityId.equals("none")) {
            ((FrappeMainActivity) activity).renderApp(app, activityId);
        }
    }

    private void performAPICall() {
        String url = DataUtils.getProcessedUrl(
                app.getServerAddress(),
                app.getServerPort(),
                component.getOnPressApiUrl()
        );

        RequestQueue queue = Volley.newRequestQueue(context);

        if (component.getOnPressApiCallType().equals("GET")) {
            url += "?";
            for (String id : component.getOnPressApiParams()) {
                url += liveData.getName(id) + "=" + liveData.getValue(id) + "&";
            }
            try {
                Iterator<String> keys = component.getOnPressApiCustomParams().keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    url += key + "=" + component.getOnPressApiCustomParams().getString(key) + "&";
                }
            } catch (Exception e) {
                Log.e("ERROR", "performAPICall: " + e.getMessage());
            }
            url = url.substring(0, url.length() - 1);

            StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
                Log.e("API CALL RESPONSE", "performAPICall: " + response);
            }, error -> {
                Log.e("API CALL ERROR", "performAPICall: " + error.toString());
            });
            queue.add(request);
        }
        else if (component.getOnPressApiCallType().equals("POST")) {
            HashMap<String, String> params = new HashMap<String, String>();
            for (String id : component.getOnPressApiParams()) {
                params.put(liveData.getName(id), liveData.getValue(id));
            }
            try {
                Iterator<String> keys = component.getOnPressApiCustomParams().keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    params.put(key, component.getOnPressApiCustomParams().getString(key));
                }
            } catch (Exception e) {
                Log.e("ERROR", "performAPICall: " + e.getMessage());
            }


            StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
                Log.e("API CALL RESPONSE", "performAPICall: " + response);
            }, error -> {
                Log.e("API CALL ERROR", "performAPICall: " + error.toString());
            }){
                @Override
                protected Map<String, String> getParams() {
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/x-www-form-urlencoded");
                    return params;
                }
            };
            queue.add(request);
        }

    }




}
