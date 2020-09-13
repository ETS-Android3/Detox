package com.example.tp33_detoxers.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.tp33_detoxers.R;

public class TipsObjectFragment extends Fragment {
    public TipsObjectFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View tipsView = inflater.inflate(R.layout.fragment_tips_object, container, false);
        return tipsView;
    }
}
