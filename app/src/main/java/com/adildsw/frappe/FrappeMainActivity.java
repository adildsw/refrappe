package com.adildsw.frappe;

import static android.util.Log.ASSERT;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;

import com.adildsw.frappe.models.AppModel;
import com.adildsw.frappe.models.components.ActivityComponent;
import com.adildsw.frappe.ui.AppRendererFragment;
import com.adildsw.frappe.ui.QrScannerFragment;
import com.adildsw.frappe.ui.HomeFragment;
import com.adildsw.frappe.utils.DataUtils;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.adildsw.frappe.databinding.ActivityFrappeMainBinding;

import org.json.JSONException;

import pub.devrel.easypermissions.EasyPermissions;

public class FrappeMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityFrappeMainBinding binding;

    public static final String[] PERMISSIONS = { Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION, Manifest.permission.CAMERA };

    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!EasyPermissions.hasPermissions(this, PERMISSIONS)) {
            EasyPermissions.requestPermissions(this, "Please grant permissions", 1, PERMISSIONS);
        }

        binding = ActivityFrappeMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toggleDrawer(false);

        Uri data = getIntent().getData();
        if (data != null) {
            String dlData = data.toString().substring(29);
            String appId = dlData.split("_")[0];
            int currentPacket = Integer.parseInt(dlData.split("_")[1]);
            int totalPacket = Integer.parseInt(dlData.split("_")[2]);
            int dataLength = Integer.parseInt(dlData.split("_")[3]);
            String dataString = dlData.split("_")[4];
            getWindow().getDecorView().post(() -> {
                try {
                    AppModel app = new AppModel(DataUtils.decompressString(dataString, dataLength));
                    renderApp(app);
                } catch (JSONException e) {
                    Log.e("FrappeMainActivity", "Error while decompressing data", e);
                }
            });
        }

        navigationView = binding.navView;
        navigationView.setNavigationItemSelectedListener(this);

        Menu menu = navigationView.getMenu();
        menu.add(R.id.appList, 35, Menu.NONE, "abc");

        loadHomeFragment();
    }

    public void renderApp(AppModel app) {
        loadFragment(new AppRendererFragment(app));
    }

    public void renderApp(AppModel app, String activityId) {
        loadFragment(new AppRendererFragment(app, activityId));
    }

    public void renderApp(AppModel app, ActivityComponent activity) {
        loadFragment(new AppRendererFragment(app, activity));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.println(ASSERT, "", item.toString());
        Log.println(ASSERT, "", item.getItemId() + "");
        return false;
    }

    public boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            toggleDrawer(false);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    public void toggleDrawer(boolean open) {
        DrawerLayout drawer = binding.drawerLayout;
        if (open) {
            drawer.open();
        } else {
            drawer.close();
        }
    }

    private void loadHomeFragment() {
        loadFragment(new HomeFragment());
    }

    private void loadQrScannerFragment() {
        loadFragment(new QrScannerFragment());
    }

    public void loadQrScannerFragment(MenuItem item) {
        loadQrScannerFragment();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}