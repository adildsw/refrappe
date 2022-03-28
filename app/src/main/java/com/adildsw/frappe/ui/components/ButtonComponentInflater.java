package com.adildsw.frappe.ui.components;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.adildsw.frappe.FrappeMainActivity;
import com.adildsw.frappe.R;
import com.adildsw.frappe.models.AppModel;
import com.adildsw.frappe.models.components.ButtonComponent;
import com.adildsw.frappe.utils.DataUtils;
import com.adildsw.frappe.utils.LiveData;
import com.adildsw.frappe.utils.ResponseCallback;
import com.adildsw.frappe.utils.Utils;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ButtonComponentInflater {
    AppModel app;
    ButtonComponent component;
    Context context;
    ViewGroup viewGroup;
    FragmentActivity activity;

    RequestQueue queue;
    LiveData liveData = LiveData.getInstance();

    public ButtonComponentInflater(AppModel app, ButtonComponent component, ViewGroup viewGroup,
                                   Context context, FragmentActivity activity) {
        this.app = app;
        this.component = component;
        this.context = context;
        this.viewGroup = viewGroup;
        this.activity = activity;

        this.queue = Volley.newRequestQueue(this.context);
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

    private void changeActivityByName(String activityName) {
        String activityId = app.getActivityIdByName(activityName);
        if (activityId != null) {
            changeActivity(activityId);
        }
    }

    private void performAPICall() {
        // Processing URL for API Call
        String url = Utils.getProcessedUrl(
                app.getServerAddress(),
                app.getServerPort(),
                component.getOnPressApiUrl()
        );

        // Defining Response Callback
        ResponseCallback callback = new ResponseCallback() {
            @Override
            public void onSuccess(String response) {
                if (component.getOnPressApiResultDisplayType().equals("toast"))
                    Toast.makeText(context, response, Toast.LENGTH_LONG).show();
                else if (component.getOnPressApiResultDisplayType().equals("prompt")) {
                    showPrompt("Success", response, false);
                }
            }
            @Override
            public void onError(String error) {
                if (component.getOnPressApiResultDisplayType().equals("toast"))
                    Toast.makeText(context, "Something went wrong.\nError: " + error, Toast.LENGTH_LONG).show();
                else if (component.getOnPressApiResultDisplayType().equals("prompt")) {
                    showPrompt("Failure", "Something went wrong.\n\nError: " + error, true);
                }
            }
        };

        // Making API Call
        if (component.getOnPressApiCallType().equals("GET")) {
            // Preparing Params
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

            // Making GET Call
            makeGetRequest(url, callback);
        }
        else if (component.getOnPressApiCallType().equals("POST")) {
            // Preparing Params
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

            // Making POST Call
            makePostRequest(url, params, callback);
            Log.println(Log.ASSERT, "URL", url + "?" + params.toString());
        }

    }

    private void makeGetRequest(String url, ResponseCallback callback) {
        final Boolean[] changeActivityFlag = {false};
        StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
            Log.e("API CALL RESPONSE", "performAPICall: " + response);
            if (!changeActivityFlag[0]) {
                callback.onSuccess(response);
            }
            else {
                changeActivityFlag[0] = false;
            }
        }, error -> {
            Log.e("API CALL ERROR", "performAPICall: " + error.toString());
            if (!changeActivityFlag[0]) {
                callback.onError(error.toString());
            }
            else {
                changeActivityFlag[0] = false;
            }
        }){
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                if (response.statusCode == 299) {
                    Log.e("API CALL ERROR", "performAPICall: " + response.headers.toString());
                    try {
                        changeActivityFlag[0] = true;
                        changeActivityByName(new String(response.data, HttpHeaderParser.parseCharset(response.headers)));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(request);
    }

    private void makePostRequest(String url, HashMap<String, String> params, ResponseCallback callback) {
        final Boolean[] changeActivityFlag = {false};
        StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
            Log.e("API CALL RESPONSE", "performAPICall: " + response);
            if (!changeActivityFlag[0]) {
                callback.onSuccess(response);
            }
            else {
                changeActivityFlag[0] = false;
            }
        }, error -> {
            Log.e("API CALL ERROR", "performAPICall: " + error.toString());
            if (!changeActivityFlag[0]) {
                callback.onError(error.toString());
            }
            else {
                changeActivityFlag[0] = false;
            }
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

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                if (response.statusCode == 299) {
                    Log.e("API CALL ERROR", "performAPICall: " + response.headers.toString());
                    try {
                        changeActivityFlag[0] = true;
                        changeActivityByName(new String(response.data, HttpHeaderParser.parseCharset(response.headers)));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(request);
    }

    private void showPrompt(String title, String message, boolean isError) {
        new AlertDialog.Builder(context, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert)
                .setTitle(title)
                .setMessage("Server Response: " + message)
                .setIcon(isError ?
                        R.drawable.ic_error : R.drawable.ic_success)
                .show();
    }

}
