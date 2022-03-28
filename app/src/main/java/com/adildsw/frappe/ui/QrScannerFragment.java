package com.adildsw.frappe.ui;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.adildsw.frappe.FrappeMainActivity;
import com.adildsw.frappe.R;
import com.adildsw.frappe.models.AppModel;
import com.adildsw.frappe.utils.DataUtils;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ScanMode;
import com.google.zxing.Result;

public class QrScannerFragment extends Fragment {

    private CodeScanner mCodeScanner;
    private Toast resultToast = null;

    public QrScannerFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final Activity activity = getActivity();
        View root = inflater.inflate(R.layout.fragment_qr_scanner, container, false);
        CodeScannerView scannerView = root.findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(activity, scannerView);
        mCodeScanner.setScanMode(ScanMode.CONTINUOUS);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!result.getText().startsWith("http://frappe.com/load?data=")) {
                            showToast("Invalid QR Code");
                            return;
                        }

                        if (DataUtils.doesPacketExist(result.getText(), activity)) {
                            showToast("Packet Already Scanned");
                        }
                        else {
                            String res = DataUtils.saveAppFromDeepLink(result.getText(), activity);
                            if (res == null) {
                                showToast("Please Scan The Next Packet");
                            }
                            else {
                                if (resultToast != null) resultToast.cancel();
                                AppModel app = DataUtils.loadApp(res, activity);
                                if (app != null) {
                                    ((FrappeMainActivity) activity).renderApp(app);
                                }
                            }
                        }
//                        Toast.makeText(activity, result.getText(), Toast.LENGTH_SHORT).show();
//                        Log.println(Log.INFO, "QR", result.getText());
//                        mCodeScanner.startPreview();
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    private void showToast(String text) {
        if (resultToast != null) {
            resultToast.cancel();
        }
        resultToast = Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT);
        resultToast.show();
    }


}