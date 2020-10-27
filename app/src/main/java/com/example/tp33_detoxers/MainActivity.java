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
import com.example.tp33_detoxers.fragment.IntakeFragment;
//import com.example.tp33_detoxers.fragment.MapFragment;
import com.example.tp33_detoxers.fragment.ScanFragment;
import com.example.tp33_detoxers.fragment.SearchFragment;
import com.example.tp33_detoxers.fragment.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.page_home);
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
                        case R.id.page_mymeals:
                            replaceFragment(new IntakeFragment());
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

}
