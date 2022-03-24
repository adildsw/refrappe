package com.adildsw.frappe;

import static android.util.Log.ASSERT;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.adildsw.frappe.models.AppModel;
import com.adildsw.frappe.models.components.ActivityComponent;
import com.adildsw.frappe.ui.AppRendererFragment;
import com.adildsw.frappe.utils.DataUtils;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.adildsw.frappe.databinding.ActivityFrappeMainBinding;

import org.json.JSONException;

public class FrappeMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityFrappeMainBinding binding;

    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        binding = ActivityFrappeMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DrawerLayout drawer = binding.drawerLayout;
        drawer.close();

        Uri data = getIntent().getData();
        if (data != null) {
            String dlData = data.toString().substring(29);
            String appId = dlData.split("_")[0];
            int currentPacket = Integer.parseInt(dlData.split("_")[1]);
            int totalPacket = Integer.parseInt(dlData.split("_")[2]);
            int dataLength = Integer.parseInt(dlData.split("_")[3]);
            String dataString = dlData.split("_")[4];
            Toast.makeText(this, data.toString(), Toast.LENGTH_SHORT).show();
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
        menu.add(R.id.mainGroup, 35, Menu.NONE, "abc");

        AppModel app = DataUtils.sampleApp();
        if (app != null) {
            renderApp(app);
        }
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
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}