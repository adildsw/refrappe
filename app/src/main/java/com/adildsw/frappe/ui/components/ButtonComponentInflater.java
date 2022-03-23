package com.adildsw.frappe.ui.components;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.adildsw.frappe.R;
import com.adildsw.frappe.models.AppModel;
import com.adildsw.frappe.models.components.ButtonComponent;
import com.adildsw.frappe.utils.Utils;

public class ButtonComponentInflater {
    AppModel app;
    ButtonComponent component;
    Context context;
    ViewGroup viewGroup;

    public ButtonComponentInflater(AppModel app, ButtonComponent component, Context context,
                                 ViewGroup viewGroup) {
        this.app = app;
        this.component = component;
        this.context = context;
        this.viewGroup = viewGroup;
    }

    public View inflate() {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.component_button, viewGroup, false);
        ((Button) view.findViewById(R.id.button)).setTag(component.getId());

        // Text
        ((Button) view.findViewById(R.id.button)).setText(component.getText());
        ((Button) view.findViewById(R.id.button))
                .setTextColor(Utils.parseColorStateList(component.getTextColor()));
        ((Button) view.findViewById(R.id.button))
                .setBackgroundTintList(Utils.parseColorStateList(component.getBackground()));

        // TODO: On-click event handler goes here

        return view;
    }
}
