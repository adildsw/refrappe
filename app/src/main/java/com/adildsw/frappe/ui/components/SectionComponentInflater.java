package com.adildsw.frappe.ui.components;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;

import com.adildsw.frappe.R;
import com.adildsw.frappe.models.AppModel;
import com.adildsw.frappe.models.components.ButtonComponent;
import com.adildsw.frappe.models.components.ChartComponent;
import com.adildsw.frappe.models.components.CheckboxComponent;
import com.adildsw.frappe.models.components.DataViewerComponent;
import com.adildsw.frappe.models.components.DropdownComponent;
import com.adildsw.frappe.models.components.InputComponent;
import com.adildsw.frappe.models.components.RadioComponent;
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
    FragmentActivity activity;

    public SectionComponentInflater(AppModel app, SectionComponent component, ViewGroup viewGroup,
                                    Context context, FragmentActivity activity) {
        this.app = app;
        this.component = component;
        this.context = context;
        this.viewGroup = viewGroup;
        this.activity = activity;
    }

    public View inflate() {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.component_section, viewGroup, false);
        ((CardView) view.findViewById(R.id.section)).setTag(component.getId());

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
                        viewGroup,
                        context
                ).inflate();
                addView(view, textView);
            }

            // Button Component
            if (child.getType().equals("enfrappe-ui-button")) {
                View buttonView = new ButtonComponentInflater(
                        app,
                        (ButtonComponent) child,
                        viewGroup,
                        context,
                        activity
                ).inflate();
                addView(view, buttonView);
            }

            // Input Component
            if (child.getType().equals("enfrappe-ui-input")) {
                View inputView = new InputComponentInflater(
                        app,
                        (InputComponent) child,
                        viewGroup,
                        context
                ).inflate();
                addView(view, inputView);
            }

            // Checkbox Component
            if (child.getType().equals("enfrappe-ui-checkbox")) {
                View checkboxView = new CheckboxComponentInflater(
                        app,
                        (CheckboxComponent) child,
                        viewGroup,
                        context
                ).inflate();
                addView(view, checkboxView);
            }

            // Radio Component
            if (child.getType().equals("enfrappe-ui-radio")) {
                View radioView = new RadioComponentInflater(
                        app,
                        (RadioComponent) child,
                        viewGroup,
                        context
                ).inflate();
                addView(view, radioView);
            }

            // Dropdown Component
            if (child.getType().equals("enfrappe-ui-dropdown")) {
                View dropdownView = new DropdownComponentInflater(
                        app,
                        (DropdownComponent) child,
                        viewGroup,
                        context
                ).inflate();
                addView(view, dropdownView);
            }

            // DataViewer Component
            if (child.getType().equals("enfrappe-ui-dataviewer")) {
                View textView = new DataViewerComponentInflater(
                        app,
                        (DataViewerComponent) child,
                        viewGroup,
                        context
                ).inflate();
                addView(view, textView);
            }

            // DataViewer Component
            if (child.getType().equals("enfrappe-ui-chart")) {
                View textView = new ChartComponentInflater(
                        app,
                        (ChartComponent) child,
                        viewGroup,
                        context
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
