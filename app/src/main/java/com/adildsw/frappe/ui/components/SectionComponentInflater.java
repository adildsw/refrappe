package com.adildsw.frappe.ui.components;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.adildsw.frappe.R;
import com.adildsw.frappe.models.AppModel;
import com.adildsw.frappe.models.components.SectionComponent;
import com.adildsw.frappe.models.components.TextComponent;
import com.adildsw.frappe.models.components.UIComponent;
import com.adildsw.frappe.utils.Utils;

import java.util.Arrays;

public class SectionComponentInflater {
    AppModel app;
    SectionComponent component;
    Context context;
    ViewGroup viewGroup;

    public SectionComponentInflater(AppModel app, SectionComponent component, Context context,
                                    ViewGroup viewGroup) {
        this.app = app;
        this.component = component;
        this.context = context;
        this.viewGroup = viewGroup;
    }

    public View inflate() {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.component_section, viewGroup, false);

        // Background
        ((CardView) view.findViewById(R.id.section))
                .setBackgroundTintList(Utils.parseColorStateList(component.getBackground()));

        // Title
        ((TextView) view.findViewById(R.id.sectionTitle)).setText(component.getTitle());
        ((TextView) view.findViewById(R.id.sectionTitle))
                .setTextColor(Utils.parseColorStateList(component.getTextColor()));

        // Subtitle
        ((TextView) view.findViewById(R.id.sectionSubtitle)).setText(component.getSubtitle());
        ((TextView) view.findViewById(R.id.sectionSubtitle))
                .setTextColor(Utils.parseColorStateList(component.getTextColor()));

        // Children
        for (int i = 0; i < component.getChildren().length; i++) {
            UIComponent child = app.getComponentById(component.getChildren()[i]);

            // Text Component
            if (child.getType().equals("enfrappe-ui-text")) {
                View textView = new TextComponentInflater(
                        app,
                        (TextComponent) child,
                        context,
                        viewGroup
                ).inflate();
                addView(view, textView);
            }

            // Other sub components go here...
        }

        return view;
    }

    private void addView(View parent, View child) {
        ((LinearLayout) parent.findViewById(R.id.sectionContent)).addView(child);
    }
}
