package com.example.tp33_detoxers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class SearchFragment extends Fragment {
    public SearchFragment() {}

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View searchView = inflater.inflate(R.layout.fragment_search, container, false);
        return searchView;
    }
}
