package com.adildsw.frappe.models.components;

public interface UIComponent {
    public String getType();
    public String getId();
    public String getParent();
    public boolean hasChildren();
}
