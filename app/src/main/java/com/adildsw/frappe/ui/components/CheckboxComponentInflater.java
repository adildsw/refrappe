package com.adildsw.frappe.ui.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.adildsw.frappe.R;
import com.adildsw.frappe.models.AppModel;
import com.adildsw.frappe.models.components.CheckboxComponent;
import com.adildsw.frappe.utils.Utils;

public class CheckboxComponentInflater {
    AppModel app;
    CheckboxComponent component;
    Context context;
    ViewGroup viewGroup;

    public CheckboxComponentInflater(AppModel app, CheckboxComponent component, Context context,
                                   ViewGroup viewGroup) {
        this.app = app;
        this.component = component;
        this.context = context;
        this.viewGroup = viewGroup;
    }

    public View inflate() {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.component_checkbox, viewGroup, false);
        ((CheckBox) view.findViewById(R.id.checkbox)).setTag(component.getId());

        // Label
        ((CheckBox) view.findViewById(R.id.checkbox)).setText(component.getLabel());
        ((CheckBox) view.findViewById(R.id.checkbox))
                .setTextColor(Utils.parseColorStateList(component.getTextColor()));

        return view;
    }
}
