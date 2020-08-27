package com.example.tp33_detoxers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                    }
                    return true;
                }


    };

    private void replaceFragment(Fragment nextFragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, nextFragment);
        fragmentTransaction.commit();
    }
}
