package com.example.tp33_detoxers.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.tp33_detoxers.R;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingFragment extends Fragment {
    private SwitchMaterial switch_night;
    public SettingFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View settingView = inflater.inflate(R.layout.fragment_setting, container, false);
        switch_night = settingView.findViewById(R.id.switch_night);
//        setNightMode();
//        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("NightMode", Context.MODE_PRIVATE);
//        boolean isNightModeOn = sharedPreferences.getBoolean("nightMode", false);
//
//        //click the switch button to switch the night mode
//        switch_night.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(isNightModeOn){
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                    SharedPreferences.Editor spEdit = sharedPreferences.edit();
//                    spEdit.putBoolean("nightMode",false);
//                    spEdit.apply();
//                }else{
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                    SharedPreferences.Editor spEdit = sharedPreferences.edit();
//                    spEdit.putBoolean("nightMode",true);
//                    spEdit.apply();
//                }
//            }
//        });

        return settingView;
    }

    private void setNightMode() {
        // get the current mode
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        boolean checked = (currentNightMode == Configuration.UI_MODE_NIGHT_NO);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("NightMode", Context.MODE_PRIVATE);
        SharedPreferences.Editor spEdit = sharedPreferences.edit();
        spEdit.putBoolean("nightMode",checked);
        spEdit.apply();
        // switch the mode
        if(checked){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            switch_night.setChecked(true);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            switch_night.setChecked(false);
        }

    }
}
