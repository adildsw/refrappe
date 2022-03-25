package com.adildsw.frappe.ui.components;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;

import com.adildsw.frappe.R;
import com.adildsw.frappe.models.AppModel;
import com.adildsw.frappe.models.components.ButtonComponent;
import com.adildsw.frappe.models.components.DropdownComponent;
import com.adildsw.frappe.utils.LiveData;
import com.adildsw.frappe.utils.Utils;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

public class DropdownComponentInflater {
    AppModel app;
    DropdownComponent component;
    Context context;
    ViewGroup viewGroup;

    LiveData liveData = LiveData.getInstance();

    public DropdownComponentInflater(AppModel app, DropdownComponent component, ViewGroup viewGroup,
                                     Context context) {
        this.app = app;
        this.component = component;
        this.context = context;
        this.viewGroup = viewGroup;
    }

    public View inflate() {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.component_dropdown, viewGroup, false);
        view.findViewById(R.id.dropdown).setTag(component.getId());

        // Options
        String[] options = new String[component.getOptions().length()];
        String[] values = new String[component.getOptions().length()];
        for (int i = 0; i < component.getOptions().length(); i++) {
            String optionId = component.getOptionIds()[i];
            try {
                JSONObject option = component.getOptions().getJSONObject(optionId);
                options[i] = option.getString("label");
                values[i] = option.getString("value");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
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

        // Update Live Data
        liveData.set(component.getId(), component.getName(), values[0]);
        ((AutoCompleteTextView) view.findViewById(R.id.dropdown))
                .addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                for (int i = 0; i < options.length; i++) {
                    if (editable.toString().equals(options[i])) {
                        liveData.set(component.getId(), component.getName(), values[i]);
                    }
                }
            }
        });

        return view;
    }
}
