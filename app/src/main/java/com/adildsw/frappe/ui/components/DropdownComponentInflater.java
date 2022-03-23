package com.adildsw.frappe.ui.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;

import com.adildsw.frappe.R;
import com.adildsw.frappe.models.AppModel;
import com.adildsw.frappe.models.components.ButtonComponent;
import com.adildsw.frappe.models.components.DropdownComponent;
import com.adildsw.frappe.utils.Utils;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

public class DropdownComponentInflater {
    AppModel app;
    DropdownComponent component;
    Context context;
    ViewGroup viewGroup;

    public DropdownComponentInflater(AppModel app, DropdownComponent component, Context context,
                                     ViewGroup viewGroup) {
        this.app = app;
        this.component = component;
        this.context = context;
        this.viewGroup = viewGroup;
    }

    public View inflate() {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.component_dropdown, viewGroup, false);
        ((AutoCompleteTextView) view.findViewById(R.id.dropdown)).setTag(component.getId());

        // Options
        String[] options = new String[component.getOptions().length()];
        for (int i = 0; i < component.getOptions().length(); i++) {
            String optionId = component.getOptionIds()[i];
            try {
                JSONObject option = component.getOptions().getJSONObject(optionId);
                options[i] = option.getString("label");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter(
                context,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                options
        );
        ((AutoCompleteTextView) view.findViewById(R.id.dropdown)).setAdapter(adapter);
        ((AutoCompleteTextView) view.findViewById(R.id.dropdown)).setText(options[0], false);

        // Label
        ((TextInputLayout) view.findViewById(R.id.dropdownLayout)).setHint(component.getLabel());
        ((TextInputLayout) view.findViewById(R.id.dropdownLayout))
                .setHintTextColor(Utils.parseColorStateList(component.getTextColor()));
        ((TextInputLayout) view.findViewById(R.id.dropdownLayout))
                .setDefaultHintTextColor(Utils.parseColorStateList(component.getTextColor()));
        ((TextInputLayout) view.findViewById(R.id.dropdownLayout))
                .setBoxStrokeColorStateList(Utils.parseColorStateList(component.getTextColor()));

        return view;
    }
}
