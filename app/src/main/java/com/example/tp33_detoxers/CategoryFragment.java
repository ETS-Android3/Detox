package com.example.tp33_detoxers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class CategoryFragment extends Fragment {
    public CategoryFragment() {}

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View category = inflater.inflate(R.layout.fragment_category, container, false);
        return category;
    }
}
