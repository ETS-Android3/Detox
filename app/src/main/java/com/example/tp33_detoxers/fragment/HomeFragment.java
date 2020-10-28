package com.example.tp33_detoxers.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.app.abby.xbanner.AbstractUrlLoader;
import com.app.abby.xbanner.XBanner;
import com.example.tp33_detoxers.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class HomeFragment extends Fragment {
    private ArrayList<String> urls = new ArrayList<>();
    private ArrayList<String> titles = new ArrayList<>();
//    private XBanner xBanner;

    public HomeFragment() {}

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View homeView = inflater.inflate(R.layout.fragment_home, container, false);
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().getItem(0).setChecked(true);
        ImageView ivHome = homeView.findViewById(R.id.iv_home);
        Picasso.get().load(R.drawable.trolley1).fit().into(ivHome);
        Button bt_health = homeView.findViewById(R.id.bt_tips);
        Button bt_about = homeView.findViewById(R.id.bt_aboutUs);
        Button bt_start = homeView.findViewById(R.id.btn_start);

        bt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new SearchFragment()).addToBackStack(null).commit();
            }
        });

        bt_health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new TipsCollectionFragment()).addToBackStack(null).commit();
            }
        });

        bt_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new AboutUsFragment()).addToBackStack(null).commit();
            }
        });
        return homeView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        xBanner.releaseBanner();
    }
}
