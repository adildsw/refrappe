package com.adildsw.frappe.ui.components;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.text.LineBreaker;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.adildsw.frappe.R;
import com.adildsw.frappe.models.AppModel;
import com.adildsw.frappe.models.components.DataViewerComponent;
import com.adildsw.frappe.utils.ResponseCallback;
import com.adildsw.frappe.utils.FrappeTask;
import com.adildsw.frappe.utils.FrappeTaskManager;
import com.adildsw.frappe.utils.Utils;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DataViewerComponentInflater {
    AppModel app;
    DataViewerComponent component;
    Context context;
    ViewGroup viewGroup;

    RequestQueue queue;

    public DataViewerComponentInflater(AppModel app, DataViewerComponent component, ViewGroup viewGroup,
                                       Context context) {
        this.app = app;
        this.component = component;
        this.context = context;
        this.viewGroup = viewGroup;

        this.queue = Volley.newRequestQueue(this.context);
    }

    public View inflate() {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.component_dataviewer, viewGroup, false);
        ((TextView) view.findViewById(R.id.dataviewer)).setTag(component.getId());

        // Text
        ((TextView) view.findViewById(R.id.dataviewer)).setText(component.getText());
        ((TextView) view.findViewById(R.id.dataviewer))
                .setTextColor(Utils.parseColorStateList(component.getTextColor()));

        // Bold/Italic
        if (component.isBold() && component.isItalic()) {
            ((TextView) view.findViewById(R.id.dataviewer)).setTypeface(null, Typeface.BOLD_ITALIC);
        } else if (component.isBold()) {
            ((TextView) view.findViewById(R.id.dataviewer)).setTypeface(null, Typeface.BOLD);
        } else if (component.isItalic()) {
            ((TextView) view.findViewById(R.id.dataviewer)).setTypeface(null, Typeface.ITALIC);
        }

        // Underline
        if (component.isUnderline()) {
            ((TextView) view.findViewById(R.id.dataviewer)).setPaintFlags(
                    ((TextView) view.findViewById(R.id.dataviewer))
                            .getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        }
        
        // Alignment
        if (component.getAlign().equals("right")) {
            ((TextView) view.findViewById(R.id.dataviewer)).setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        }
        if (component.getAlign().equals("center")) {
            ((TextView) view.findViewById(R.id.dataviewer)).setGravity(Gravity.CENTER);
        }
        if (component.getAlign().equals("justify")) {
            ((TextView) view.findViewById(R.id.dataviewer)).setJustificationMode(
                    LineBreaker.JUSTIFICATION_MODE_INTER_WORD
            );
        }

        // Tight
        if (component.isTight()) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, -20);
            view.setLayoutParams(params);
        }

        // Size
        if (component.getSize().equals("medium")) {
            ((TextView) view.findViewById(R.id.dataviewer)).setTextSize(18);
        }
        else if (component.getSize().equals("large")) {
            ((TextView) view.findViewById(R.id.dataviewer)).setTextSize(22);
        }

        // Defining Response Callback
        ResponseCallback callback = new ResponseCallback() {
            @Override
            public void onSuccess(String response) {
                ((TextView) view.findViewById(R.id.dataviewer)).setText(response);
            }
            @Override
            public void onError(String error) {
                ((TextView) view.findViewById(R.id.dataviewer)).setText("Error Loading Data...");
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
            }
        };

        performAPICall(callback);
        if (component.getRefreshInterval() > 0) {
            FrappeTask task = new FrappeTask() {
                @Override
                public void run() {
                    performAPICall(callback);
                }
            };
            FrappeTaskManager.getInstance().addTask(task, component.getRefreshInterval() * 1000L);
        }

        return view;
    }

    private void performAPICall(ResponseCallback callback) {
        // Processing URL for API Call
        String url = Utils.getProcessedUrl(
                app.getServerAddress(),
                app.getServerPort(),
                component.getApiUrl()
        );

        // Making API Call
        if (component.getApiCallType().equals("GET")) {
            // Preparing Params
            url += "?";
            try {
                Iterator<String> keys = component.getApiCustomParams().keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    url += key + "=" + component.getApiCustomParams().getString(key) + "&";
                }
                url = url.substring(0, url.length() - 1);
            } catch (Exception e) {
                Log.e("ERROR", "performAPICall: " + e.getMessage());
            }

            // Making GET Call
            makeGetRequest(url, callback);
        }
        else if (component.getApiCallType().equals("POST")) {
            // Preparing Params
            HashMap<String, String> params = new HashMap<String, String>();
            try {
                Iterator<String> keys = component.getApiCustomParams().keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    params.put(key, component.getApiCustomParams().getString(key));
                }
            } catch (Exception e) {
                Log.e("ERROR", "performAPICall: " + e.getMessage());
            }

            // Making POST Call
            makePostRequest(url, params, callback);
        }
    }

    private void makeGetRequest(String url, ResponseCallback callback) {
        StringRequest request = new StringRequest(Request.Method.GET, url, callback::onSuccess,
                error -> callback.onError(error.toString()));
        queue.add(request);
    }

    private void makePostRequest(String url, HashMap<String, String> params,
                                 ResponseCallback callback) {
        StringRequest request = new StringRequest(Request.Method.POST, url, callback::onSuccess,
                error -> callback.onError(error.toString())){
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
