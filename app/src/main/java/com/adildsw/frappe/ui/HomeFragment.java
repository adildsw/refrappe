package com.adildsw.frappe.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adildsw.frappe.FrappeMainActivity;
import com.adildsw.frappe.R;

public class HomeFragment extends Fragment {

    public HomeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        view.findViewById(R.id.rootCenterContent).setOnClickListener(view1 -> ((FrappeMainActivity) getActivity()).toggleDrawer(true));
        return view;
    }
}