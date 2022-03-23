package com.adildsw.frappe.ui.components;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adildsw.frappe.R;
import com.adildsw.frappe.models.AppModel;
import com.adildsw.frappe.models.components.TextComponent;
import com.adildsw.frappe.utils.Utils;

public class TextComponentInflater {
    AppModel app;
    TextComponent component;
    Context context;
    ViewGroup viewGroup;

    public TextComponentInflater(AppModel app, TextComponent component, Context context,
                                    ViewGroup viewGroup) {
        this.app = app;
        this.component = component;
        this.context = context;
        this.viewGroup = viewGroup;
    }

    public View inflate() {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.component_text, viewGroup, false);

        // Text
        ((TextView) view.findViewById(R.id.text)).setText(component.getText());
        ((TextView) view.findViewById(R.id.text))
                .setTextColor(Utils.parseColorStateList(component.getTextColor()));

        // Bold/Italic
        if (component.isBold() && component.isItalic()) {
            ((TextView) view.findViewById(R.id.text)).setTypeface(null, Typeface.BOLD);
        } else if (component.isBold()) {
            ((TextView) view.findViewById(R.id.text)).setTypeface(null, Typeface.BOLD);
        } else if (component.isItalic()) {
            ((TextView) view.findViewById(R.id.text)).setTypeface(null, Typeface.ITALIC);
        }

        return view;
    }
}
