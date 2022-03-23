package com.adildsw.frappe;

import static android.util.Log.ASSERT;
import static com.adildsw.frappe.utils.DataUtils.decompressString;
import static com.adildsw.frappe.utils.DataUtils.test;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.adildsw.frappe.models.AppModel;
import com.adildsw.frappe.models.components.ActivityComponent;
import com.adildsw.frappe.ui.AppRendererFragment;
import com.adildsw.frappe.utils.DataUtils;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
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

        binding = ActivityFrappeMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DrawerLayout drawer = binding.drawerLayout;
        drawer.open();

        navigationView = binding.navView;
        navigationView.setNavigationItemSelectedListener(this);

        Menu menu = navigationView.getMenu();
        menu.add(R.id.mainGroup, 35, Menu.NONE, "abc");

        Fragment fragment = null;
        try {
            AppModel app = DataUtils.sampleApp();
            ActivityComponent mainActivity = (ActivityComponent) app.getComponentById("main-activity");
            fragment = new AppRendererFragment(DataUtils.sampleApp(), mainActivity);
            loadFragment(fragment);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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