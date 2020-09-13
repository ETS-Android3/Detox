package com.example.tp33_detoxers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.tp33_detoxers.fragment.CategoryFragment;
import com.example.tp33_detoxers.fragment.HomeFragment;
import com.example.tp33_detoxers.fragment.MapFragment;
import com.example.tp33_detoxers.fragment.ScanFragment;
import com.example.tp33_detoxers.fragment.SearchFragment;
import com.example.tp33_detoxers.fragment.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //switchNightMode();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavigation);

        replaceFragment(new HomeFragment());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavigation=new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int id = item.getItemId();

                    switch (id){
                        case R.id.page_category:
                            replaceFragment(new CategoryFragment());
                            break;
                        case  R.id.page_home:
                            replaceFragment(new HomeFragment());
                            break;
                        case R.id.page_search:
                            replaceFragment(new SearchFragment());
                            break;
                        case R.id.page_scan:
                            replaceFragment(new ScanFragment());
                            break;
                        case R.id.page_setting:
                            replaceFragment(new SettingFragment());
                            break;
                    }
                    return true;
                }


    };

    private void replaceFragment(Fragment nextFragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, nextFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

//    private void switchNightMode() {
//        //get the current mode
//        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
//
//        AppCompatDelegate.setDefaultNightMode(currentNightMode == Configuration.UI_MODE_NIGHT_NO ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
////        startActivity(new Intent(getApplicationContext(), MainActivity.class));
////        finish();
//    }

}
