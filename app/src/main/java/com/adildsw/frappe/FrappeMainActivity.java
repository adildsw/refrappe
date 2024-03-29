package com.adildsw.frappe;

import static android.util.Log.ASSERT;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.adildsw.frappe.models.AppModel;
import com.adildsw.frappe.models.components.ActivityComponent;
import com.adildsw.frappe.ui.AppRendererFragment;
import com.adildsw.frappe.ui.QrScannerFragment;
import com.adildsw.frappe.ui.HomeFragment;
import com.adildsw.frappe.utils.AppMenuManager;
import com.adildsw.frappe.utils.DataUtils;
import com.adildsw.frappe.utils.FrappeTaskManager;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.adildsw.frappe.databinding.ActivityFrappeMainBinding;

public class FrappeMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityFrappeMainBinding binding;

    NavigationView navigationView;

    long loadTime = 0;
    long currentTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFrappeMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toggleDrawer(false);

        Uri data = getIntent().getData();
        loadTime = System.currentTimeMillis();
        if (data != null) {
            String dlAppId = DataUtils.saveAppFromDeepLink(data.toString(), this);
            if (dlAppId != null) {
                AppModel app = DataUtils.loadApp(dlAppId, this);
                getWindow().getDecorView().post(() -> renderApp(app));
            }
        }

        navigationView = binding.navView;
        navigationView.setNavigationItemSelectedListener(this);
// String -> split -> decodedB64 -> decompressedZLib -> JSONObject -> AppModel
        Menu menu = navigationView.getMenu();
        String[] appList = DataUtils.getAllAppIds(this);
        for (String appId : appList) {
            String appName = DataUtils.getAppNameFromId(appId, this);
            menu.add(R.id.appList, appId.hashCode(), Menu.NONE, appName);
            MenuItem item = menu.findItem(appId.hashCode());
            AppMenuManager.setMenuItemTag(item, appId);
            item.setOnMenuItemClickListener(menuItem -> {
                AppModel newApp = DataUtils
                        .loadApp(AppMenuManager.getMenuItemTag(menuItem), this);
                if (newApp != null) {
                    renderApp(newApp);
                    AppMenuManager.uncheckAllMenuItems();
                    item.setChecked(true);
                }
                return true;
            });
        }
        loadHomeFragment();
    }

    public void renderApp(AppModel app) {
        // Measure start time
        long startTime = System.currentTimeMillis();
        Toast.makeText(this, "Loading app...", Toast.LENGTH_SHORT).show();
        loadFragment(new AppRendererFragment(app));
        long stopTime = System.currentTimeMillis();
        //Measure stop time
        Log.println(ASSERT, "FrappeMainActivity", "Complete Loading time: " + (stopTime - startTime));
        Log.println(ASSERT, "FrappeMainActivity", "Complete Loading time 2: " + (stopTime - loadTime));
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

    public void loadFragment(Fragment fragment) {
        if (fragment != null) {
            FrappeTaskManager.getInstance().stopAll(); // Stops all running handlers
            toggleDrawer(false);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .commitNow();
            Log.println(ASSERT, "FRAGMENT LOADED IN", System.currentTimeMillis() - loadTime + "");
        }
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
        AppMenuManager.uncheckAllMenuItems();
        loadFragment(new HomeFragment());
    }

    public void loadHomeFragment(MenuItem item) {
        loadHomeFragment();
    }

    private void loadQrScannerFragment() {
        AppMenuManager.uncheckAllMenuItems();
        loadFragment(new QrScannerFragment());
    }

    public void loadQrScannerFragment(MenuItem item) {
        loadQrScannerFragment();
    }

    private boolean isHome() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        return fragment instanceof HomeFragment;
    }

    @Override
    public void onBackPressed() {
        if (isHome()) {
            super.onBackPressed();
        }
        else {
            loadHomeFragment();
        }
    }
}