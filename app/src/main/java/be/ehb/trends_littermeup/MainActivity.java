package be.ehb.trends_littermeup;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import be.ehb.trends_littermeup.ui.dashboard.DashboardFragment;
import be.ehb.trends_littermeup.ui.home.HomeFragment;
import be.ehb.trends_littermeup.ui.maps.MapsFragment;
import be.ehb.trends_littermeup.ui.profile.ProfileFragment;

import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    // Navigation view
    BottomNavigationView bottomNavigationView;

    // Fragments
    HomeFragment homeFragment = new HomeFragment();
    MapsFragment mapsFragment = new MapsFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    DashboardFragment dashboardFragment = new DashboardFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}