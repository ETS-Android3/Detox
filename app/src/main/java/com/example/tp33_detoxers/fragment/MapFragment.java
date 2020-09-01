package com.example.tp33_detoxers.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.tp33_detoxers.R;

public class MapFragment extends Fragment {
    public MapFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View mapView = inflater.inflate(R.layout.fragment_map, container, false);
        return mapView;
    }
}
