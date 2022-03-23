package com.adildsw.frappe.ui;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adildsw.frappe.R;
import com.adildsw.frappe.models.AppModel;
import com.adildsw.frappe.models.components.ActivityComponent;
import com.adildsw.frappe.models.components.SectionComponent;
import com.adildsw.frappe.models.components.UIComponent;
import com.adildsw.frappe.ui.components.SectionComponentInflater;

import org.json.JSONException;
import org.json.JSONObject;

public class AppRendererFragment extends Fragment {

    private final AppModel app;
    private final ActivityComponent activity;

    public AppRendererFragment(AppModel app, ActivityComponent activity) {
        this.app = app;
        this.activity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_app_renderer, container, false);
        view = appRenderer(view, container);
        return view;
    }

    private View appRenderer(View view, ViewGroup viewGroup) {
        for (int i = 0; i < activity.getChildren().length; i++) {
            UIComponent child = app.getComponentById(activity.getChildren()[i]);

            // Section Component
            if (child.getType().equals("enfrappe-ui-section")) {
                View sectionView = new SectionComponentInflater(
                        app,
                        (SectionComponent) child,
                        getContext(),
                        viewGroup
                ).inflate();
                ((LinearLayout) view.findViewById(R.id.fragmentContent)).addView(sectionView);
            }

            // Other root components go here...

        }

        return view;
    }
}