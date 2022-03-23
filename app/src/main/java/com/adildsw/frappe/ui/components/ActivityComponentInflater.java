package com.adildsw.frappe.ui.components;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.adildsw.frappe.R;
import com.adildsw.frappe.models.AppModel;
import com.adildsw.frappe.models.components.ActivityComponent;
import com.adildsw.frappe.models.components.SectionComponent;
import com.adildsw.frappe.models.components.UIComponent;
import com.adildsw.frappe.utils.Utils;

public class ActivityComponentInflater {
    AppModel app;
    ActivityComponent component;
    Context context;
    ViewGroup viewGroup;

    public ActivityComponentInflater(AppModel app, ActivityComponent component, Context context,
                                     ViewGroup viewGroup) {
        this.app = app;
        this.component = component;
        this.context = context;
        this.viewGroup = viewGroup;
    }

    public View inflate() {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.component_activity, viewGroup, false);
        ((LinearLayout) view.findViewById(R.id.activity)).setTag(component.getId());

        // Background
        ((LinearLayout) view.findViewById(R.id.activity))
                .setBackgroundColor(Color.parseColor(component.getBackground()));

        // Children
        for (int i = 0; i < component.getChildren().length; i++) {
            UIComponent child = app.getComponentById(component.getChildren()[i]);

            // Section Component
            if (child.getType().equals("enfrappe-ui-section")) {
                View sectionView = new SectionComponentInflater(
                        app,
                        (SectionComponent) child,
                        context,
                        viewGroup
                ).inflate();
                addView(view, sectionView);
            }

            // Other root components go here...

        }

        return view;
    }

    private void addView(View parent, View child) {
        ((LinearLayout) parent).addView(child);
    }
}
