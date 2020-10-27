package com.example.tp33_detoxers.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.tp33_detoxers.R;
import com.example.tp33_detoxers.adapter.FragTipsAdapter;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Objects;

public class TipsCollectionFragment extends Fragment {
    FragTipsAdapter fragTipsAdapter;
    ViewPager2 viewPager;
    TabLayout tabLayout;

    public TipsCollectionFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View healthyView = inflater.inflate(R.layout.fragment_tips_collection, container, false);
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().getItem(0).setChecked(true);

        fragTipsAdapter = new FragTipsAdapter(this);
        viewPager = healthyView.findViewById(R.id.viewPage);
        tabLayout = healthyView.findViewById(R.id.tab_layout);
        viewPager.setAdapter(fragTipsAdapter);

        MaterialToolbar toolbar = healthyView.findViewById(R.id.tips_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objects.requireNonNull(getActivity()).onBackPressed();
            }
        });

        new TabLayoutMediator(tabLayout, viewPager, (tab,position) -> {
            if(position == 0){
                tab.setText("High blood sugar");
            }else if(position == 1){
                tab.setText("High blood pressure");
            }else {
                tab.setText("High cholesterol");
            }
        }).attach();

        return healthyView;
    }
}
