package com.example.tp33_detoxers.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.tp33_detoxers.R;

public class HomeFragment extends Fragment {
    public HomeFragment() {}

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View homeView = inflater.inflate(R.layout.fragment_home, container, false);
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
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new MapFragment()).addToBackStack(null).commit();
            }
        });
        return homeView;
    }
}
