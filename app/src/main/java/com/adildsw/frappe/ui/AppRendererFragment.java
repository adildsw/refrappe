package com.adildsw.frappe.ui;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.adildsw.frappe.R;
import com.adildsw.frappe.models.AppModel;
import com.adildsw.frappe.models.components.ActivityComponent;
import com.adildsw.frappe.models.components.SectionComponent;
import com.adildsw.frappe.models.components.UIComponent;
import com.adildsw.frappe.ui.components.ActivityComponentInflater;
import com.adildsw.frappe.ui.components.SectionComponentInflater;

import org.json.JSONException;
import org.json.JSONObject;

public class AppRendererFragment extends Fragment {

    private final AppModel app;
    private final ActivityComponent activityComponent;

    public AppRendererFragment(AppModel app) {
        this.app = app;
        this.activityComponent = (ActivityComponent) app.getComponentById("main-activity");
    }

    public AppRendererFragment(AppModel app, String activityId) {
        this.app = app;
        this.activityComponent = (ActivityComponent) app.getComponentById(activityId);
    }

    public AppRendererFragment(AppModel app, ActivityComponent activity) {
        this.app = app;
        this.activityComponent = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_app_renderer, container, false);
        if (activityComponent != null) {
            appRenderer(view, container);
        }
        return view;
    }

    private void appRenderer(View view, ViewGroup viewGroup) {
        View activityView = new ActivityComponentInflater(
                app,
                activityComponent,
                viewGroup,
                getContext(),
                getActivity()
        ).inflate();
        ((ScrollView) view).addView(activityView);

        // Coloring Extended Background
        ColorDrawable colorDrawable = (ColorDrawable) activityView.findViewById(R.id.activity).getBackground();
        ((ScrollView) view.findViewById(R.id.fragmentContent)).setBackgroundColor(colorDrawable.getColor());
    }

}