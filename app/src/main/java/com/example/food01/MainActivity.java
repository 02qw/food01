package com.example.food01;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.food01.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private NavController navController;
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置主题
        setTheme(R.style.Theme_Food01);
        
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        // 设置Toolbar
        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        
        // 初始化导航
        setupNavigation();
    }

    private void setupNavigation() {
        try {
            // 获取NavHostFragment
            NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.nav_host_fragment);
            
            if (navHostFragment != null) {
                navController = navHostFragment.getNavController();
                
                // 设置底部导航
                BottomNavigationView bottomNav = binding.bottomNavigation;
                if (bottomNav != null) {
                    NavigationUI.setupWithNavController(bottomNav, navController);
                }
                
                // 设置Toolbar，定义顶层目标
                appBarConfiguration = new AppBarConfiguration.Builder(
                        R.id.restaurantListFragment,
                        R.id.orderFragment,
                        R.id.profileFragment
                ).build();
                
                // 设置Toolbar的导航
                NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
            } else {
                Log.e(TAG, "NavHostFragment not found");
            }
        } catch (Exception e) {
            Log.e(TAG, "setupNavigation: Error", e);
            e.printStackTrace();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        try {
            return NavigationUI.navigateUp(navController, appBarConfiguration)
                    || super.onSupportNavigateUp();
        } catch (Exception e) {
            Log.e(TAG, "onSupportNavigateUp: Error", e);
            return super.onSupportNavigateUp();
        }
    }
}