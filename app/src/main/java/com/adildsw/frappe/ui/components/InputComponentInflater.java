package com.adildsw.frappe.ui.components;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.adildsw.frappe.R;
import com.adildsw.frappe.models.AppModel;
import com.adildsw.frappe.models.components.InputComponent;
import com.adildsw.frappe.utils.Utils;
import com.google.android.material.textfield.TextInputLayout;

public class InputComponentInflater {
    AppModel app;
    InputComponent component;
    Context context;
    ViewGroup viewGroup;

    public InputComponentInflater(AppModel app, InputComponent component, Context context,
                                   ViewGroup viewGroup) {
        this.app = app;
        this.component = component;
        this.context = context;
        this.viewGroup = viewGroup;
    }

    public View inflate() {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.component_input, viewGroup, false);
        ((EditText) view.findViewById(R.id.input)).setTag(component.getId());

        // Placeholder
        ((EditText) view.findViewById(R.id.input)).setOnFocusChangeListener((view1, hasFocus) -> {
            if (hasFocus) {
                ((EditText) view.findViewById(R.id.input)).setHint(component.getPlaceholder());
            }
            else {
                ((EditText) view.findViewById(R.id.input)).setHint("");
            }
        });
        ((EditText) view.findViewById(R.id.input))
                .setHintTextColor(Utils.parseColorStateList(component.getTextColor()));
        ((EditText) view.findViewById(R.id.input))
                .setTextColor(Utils.parseColorStateList(component.getTextColor()));

        // Label
        ((TextInputLayout) view.findViewById(R.id.inputLayout)).setHint(component.getLabel());
        ((TextInputLayout) view.findViewById(R.id.inputLayout))
                .setHintTextColor(Utils.parseColorStateList(component.getTextColor()));
        ((TextInputLayout) view.findViewById(R.id.inputLayout))
                .setDefaultHintTextColor(Utils.parseColorStateList(component.getTextColor()));
        ((TextInputLayout) view.findViewById(R.id.inputLayout))
                .setBoxStrokeColorStateList(Utils.parseColorStateList(component.getTextColor()));

        return view;
    }
}
