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
import com.adildsw.frappe.utils.LiveData;
import com.adildsw.frappe.utils.Utils;

public class CheckboxComponentInflater {
    AppModel app;
    CheckboxComponent component;
    Context context;
    ViewGroup viewGroup;

    LiveData liveData = LiveData.getInstance();

    public CheckboxComponentInflater(AppModel app, CheckboxComponent component, ViewGroup viewGroup,
                                     Context context) {
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

        // Update Live Data
        liveData.set(component.getId(), component.getName(), String.valueOf(false));
        ((CheckBox) view.findViewById(R.id.checkbox))
                .setOnCheckedChangeListener((buttonView, isChecked) -> {
            liveData.set(component.getId(), component.getName(), String.valueOf(isChecked));
        });

        return view;
    }
}
