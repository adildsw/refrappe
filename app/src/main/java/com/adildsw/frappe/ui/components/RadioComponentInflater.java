package com.adildsw.frappe.ui.components;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.adildsw.frappe.R;
import com.adildsw.frappe.models.AppModel;
import com.adildsw.frappe.models.components.ButtonComponent;
import com.adildsw.frappe.models.components.RadioComponent;
import com.adildsw.frappe.utils.Utils;

import org.json.JSONObject;

public class RadioComponentInflater {
    AppModel app;
    RadioComponent component;
    Context context;
    ViewGroup viewGroup;

    public RadioComponentInflater(AppModel app, RadioComponent component, Context context,
                                   ViewGroup viewGroup) {
        this.app = app;
        this.component = component;
        this.context = context;
        this.viewGroup = viewGroup;
    }

    public View inflate() {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.component_radio, viewGroup, false);
        ((RadioGroup) view.findViewById(R.id.radio)).setTag(component.getId());

        // Label
        ((TextView) view.findViewById(R.id.radioLabel)).setText(component.getLabel());
        ((TextView) view.findViewById(R.id.radioLabel))
                .setTextColor(Utils.parseColorStateList(component.getTextColor()));

        // Options
        for (int i = 0; i < component.getOptions().length(); i++) {
            String optionId = component.getOptionIds()[i];
            try {
                JSONObject option = component.getOptions().getJSONObject(optionId);
                RadioButton radioButton = new RadioButton(context);
                radioButton.setId(View.generateViewId());
                radioButton.setText(option.getString("label"));
                radioButton.setTag(option.getString("value"));
                radioButton.setTextColor(Utils.parseColorStateList(component.getTextColor()));
                if (i == 0) {
                    radioButton.setChecked(true);
                }
                addView(view.findViewById(R.id.radio), radioButton);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        return view;
    }

    private void addView(View parent, View child) {
        ((RadioGroup) parent).addView(child);
    }
}
