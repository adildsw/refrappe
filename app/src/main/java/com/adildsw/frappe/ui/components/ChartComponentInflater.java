package com.adildsw.frappe.ui.components;

import android.content.Context;
import android.graphics.Color;
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
import com.adildsw.frappe.models.components.ChartComponent;
import com.adildsw.frappe.models.components.DataViewerComponent;
import com.adildsw.frappe.utils.FrappeTask;
import com.adildsw.frappe.utils.FrappeTaskManager;
import com.adildsw.frappe.utils.ResponseCallback;
import com.adildsw.frappe.utils.Utils;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ChartComponentInflater {
    AppModel app;
    ChartComponent component;
    Context context;
    ViewGroup viewGroup;

    RequestQueue queue;

    public ChartComponentInflater(AppModel app, ChartComponent component, ViewGroup viewGroup,
                                  Context context) {
        this.app = app;
        this.component = component;
        this.context = context;
        this.viewGroup = viewGroup;

        this.queue = Volley.newRequestQueue(this.context);
    }

    public View inflate() {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.component_chart, viewGroup, false);

        LineChart chart = view.findViewById(R.id.chart);
        chart.setTag(component.getId());

        int textColor = Color.parseColor(component.getTextColor());

        // Background
        chart.setBackgroundColor(Color.parseColor(component.getBackground()));

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.enableGridDashedLine(10f, 0f, 0f);
        xAxis.setGridColor(Color.argb(32, Color.red(textColor), Color.green(textColor), Color.blue(textColor)));
        xAxis.setTextColor(textColor);

        YAxis yAxis = chart.getAxisLeft();
        yAxis.enableGridDashedLine(10f, 0f, 0f);
        yAxis.setGridColor(Color.argb(32, Color.red(textColor), Color.green(textColor), Color.blue(textColor)));
        yAxis.setTextColor(textColor);

        // Defining Response Callback
        ResponseCallback callback = new ResponseCallback() {
            @Override
            public void onSuccess(String response) {
                // On Success
                Log.println(Log.ASSERT, "ChartComponentInflaterSUCCESS", response);

                // Parse JSON
                try {
                    JSONObject responseJson = new JSONObject(response);

                    JSONArray xArray = responseJson.getJSONArray("x");
                    String[] x = new String[xArray.length()];
                    for (int i = 0; i < xArray.length(); i++) {
                        x[i] = xArray.getString(i);
                    }

                    JSONArray yArray = responseJson.getJSONArray("y");
                    float[] y = new float[yArray.length()];
                    for (int i = 0; i < yArray.length(); i++) {
                        y[i] = (float) yArray.getDouble(i);
                    }

                    setData(chart, x, y);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.println(Log.ASSERT, "ChartComponentInflaterERROR", e.getMessage());
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onError(String error) {
                // On Error
                Log.println(Log.ASSERT, "ChartComponentInflaterERROR", error);
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
            }
        };

        performAPICall(callback);

        return view;
    }

    private void setData(LineChart chart, String[] x, float[] y) {
        ArrayList<Entry> entries = new ArrayList<>();
        for (int i = 0; i < x.length; i++) {
            entries.add(new Entry(i, y[i]));
        }

        LineDataSet dataSet = new LineDataSet(entries, "data");
        dataSet.setColor(Color.parseColor(component.getLineColor()));
        dataSet.setCircleColor(Color.parseColor(component.getLineColor()));
        dataSet.setLineWidth(1f);
        dataSet.setCircleRadius(3f);
        dataSet.setDrawCircleHole(false);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet);

        LineData lineData = new LineData(dataSets);
        chart.setData(lineData);
        dataSet.notifyDataSetChanged();
        lineData.notifyDataChanged();
        chart.getData().notifyDataChanged();
        chart.notifyDataSetChanged();
        chart.invalidate();
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
