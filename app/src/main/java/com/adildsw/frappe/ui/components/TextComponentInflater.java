package com.adildsw.frappe.ui.components;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.text.LineBreaker;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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

    public TextComponentInflater(AppModel app, TextComponent component, ViewGroup viewGroup,
                                 Context context) {
        this.app = app;
        this.component = component;
        this.context = context;
        this.viewGroup = viewGroup;
    }

    public View inflate() {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.component_text, viewGroup, false);
        ((TextView) view.findViewById(R.id.text)).setTag(component.getId());

        // Text
        ((TextView) view.findViewById(R.id.text)).setText(component.getText());
        ((TextView) view.findViewById(R.id.text))
                .setTextColor(Utils.parseColorStateList(component.getTextColor()));

        // Bold/Italic
        if (component.isBold() && component.isItalic()) {
            ((TextView) view.findViewById(R.id.text)).setTypeface(null, Typeface.BOLD_ITALIC);
        } else if (component.isBold()) {
            ((TextView) view.findViewById(R.id.text)).setTypeface(null, Typeface.BOLD);
        } else if (component.isItalic()) {
            ((TextView) view.findViewById(R.id.text)).setTypeface(null, Typeface.ITALIC);
        }

        // Underline
        if (component.isUnderline()) {
            ((TextView) view.findViewById(R.id.text)).setPaintFlags(
                    ((TextView) view.findViewById(R.id.text))
                            .getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        }
        
        // Alignment
        if (component.getAlign().equals("right")) {
            ((TextView) view.findViewById(R.id.text)).setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        }
        if (component.getAlign().equals("center")) {
            ((TextView) view.findViewById(R.id.text)).setGravity(Gravity.CENTER);
        }
        if (component.getAlign().equals("justify")) {
            ((TextView) view.findViewById(R.id.text)).setJustificationMode(
                    LineBreaker.JUSTIFICATION_MODE_INTER_WORD
            );
        }

        // Tight
        if (component.isTight()) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, -20);
            view.setLayoutParams(params);
        }

        // Size
        if (component.getSize().equals("medium")) {
            ((TextView) view.findViewById(R.id.text)).setTextSize(18);
        }
        else if (component.getSize().equals("large")) {
            ((TextView) view.findViewById(R.id.text)).setTextSize(22);
        }

        return view;
    }
}
