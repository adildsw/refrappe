package com.adildsw.frappe.utils;

public interface ResponseCallback {
    void onSuccess(String response);
    void onError(String error);
}
