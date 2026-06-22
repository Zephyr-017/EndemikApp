package com.example.endemik.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.endemik.R;
import com.example.endemik.util.AppPreferences;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private static final String KEY_SELECTED_NAV = "selected_nav";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView navView = findViewById(R.id.bottom_navigation);
        navView.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            if (item.getItemId() == R.id.nav_hewan) {
                fragment = new HewanFragment();
            } else if (item.getItemId() == R.id.nav_tumbuhan) {
                fragment = new TumbuhanFragment();
            }

            if (fragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
                return true;
            }
            return false;
        });

        if (savedInstanceState == null) {
            navView.setSelectedItemId(R.id.nav_hewan);
        } else {
            navView.setSelectedItemId(savedInstanceState.getInt(KEY_SELECTED_NAV, R.id.nav_hewan));
        }

        findViewById(R.id.btn_search).setOnClickListener(v ->
                startActivity(new Intent(this, PencarianActivity.class)));

        findViewById(R.id.btn_favorite).setOnClickListener(v ->
                startActivity(new Intent(this, FavoritActivity.class)));

        findViewById(R.id.btn_dark_mode).setOnClickListener(v ->
                AppPreferences.toggleNightMode(this));

        findViewById(R.id.btn_profile).setOnClickListener(v -> showProfileBottomSheet());
    }

    private void showProfileBottomSheet() {
        com.google.android.material.bottomsheet.BottomSheetDialog bottomSheetDialog = 
                new com.google.android.material.bottomsheet.BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_profile);
        bottomSheetDialog.show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        BottomNavigationView navView = findViewById(R.id.bottom_navigation);
        outState.putInt(KEY_SELECTED_NAV, navView.getSelectedItemId());
    }
}
