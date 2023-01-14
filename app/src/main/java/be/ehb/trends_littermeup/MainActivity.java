package be.ehb.trends_littermeup;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firestore.v1.WriteResponse;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import be.ehb.trends_littermeup.ui.dashboard.DashboardFragment;
import be.ehb.trends_littermeup.ui.home.HomeFragment;
import be.ehb.trends_littermeup.ui.maps.MapsFragment;
import be.ehb.trends_littermeup.ui.profile.ProfileFragment;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import be.ehb.trends_littermeup.ui.settings.SettingsFragment;
import be.ehb.trends_littermeup.ui.shop.Shop5Fragment;


public class MainActivity extends AppCompatActivity {
    // Navigation view
    BottomNavigationView bottomNavigationView;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavView;
    private Toolbar mToolbar;
    private Button mButton;

    // Fragments
    HomeFragment homeFragment = new HomeFragment();
    MapsFragment mapsFragment = new MapsFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    DashboardFragment dashboardFragment = new DashboardFragment();
    SettingsFragment settingsFragment = new SettingsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavView = findViewById(R.id.navigation);
        mToolbar = findViewById(R.id.toolbar);
        mButton = findViewById(R.id.btn_nav);

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(false);
        mButton.setBackgroundResource(R.drawable.ic_menu_green_24dp);

        // Hamburgermenu
        // Set the item click listener
        mNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle navigation item clicks here
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        // Replace the main content with the HomeFragment
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, homeFragment)
                                .commit();
                        break;
                    case R.id.navigation_dashboard:
                        // Replace the main content with the DashboardFragment
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, dashboardFragment)
                                .commit();
                        break;
                    case R.id.navigation_maps:
                        // Replace the main content with the MapsFragment
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, mapsFragment)
                                .commit();
                        break;
                    case R.id.navigation_profile:
                        // Replace the main content with the ProfileFragment
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, profileFragment)
                                .commit();
                        break;
                    case R.id.navigation_settings:
                        // Replace the main content with the SettingsFragment
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, settingsFragment)
                                .commit();
                        break;
                }
                // Close the drawer
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

        // Set the button click listener
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the hamburger menu
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
        
        
        //Bottom navigation
        bottomNavigationView = findViewById(R.id.nav_view);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                        return true;
                    case R.id.navigation_maps:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, mapsFragment).commit();
                        return true;
                    case R.id.navigation_dashboard:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, dashboardFragment).commit();
                        return true;
                    case R.id.navigation_profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, profileFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }


    private void logoutUser(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
}