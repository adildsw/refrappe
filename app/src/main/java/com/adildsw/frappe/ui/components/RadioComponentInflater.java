package com.adildsw.frappe.ui.components;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.adildsw.frappe.R;
import com.adildsw.frappe.models.AppModel;
import com.adildsw.frappe.models.components.ButtonComponent;
import com.adildsw.frappe.models.components.RadioComponent;
import com.adildsw.frappe.utils.LiveData;
import com.adildsw.frappe.utils.Utils;

import org.json.JSONObject;

public class RadioComponentInflater {
    AppModel app;
    RadioComponent component;
    Context context;
    ViewGroup viewGroup;

    LiveData liveData = LiveData.getInstance();

    public RadioComponentInflater(AppModel app, RadioComponent component, ViewGroup viewGroup,
                                  Context context) {
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
        String initValue = "";
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
                    initValue = option.getString("value");
                }
                ((RadioGroup) view.findViewById(R.id.radio)).addView(radioButton);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Update Live Data
        liveData.set(component.getId(), component.getName(), initValue);
        ((RadioGroup) view.findViewById(R.id.radio)).setOnCheckedChangeListener((radioGroup, i) -> {
            RadioButton radioButton = (RadioButton) radioGroup.findViewById(i);
            liveData.set(component.getId(), component.getName(), radioButton.getTag().toString());
        });

        return view;
    }
}
