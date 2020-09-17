package com.example.tp33_detoxers.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.tp33_detoxers.fragment.TipsObjectFragment;

public class FragTipsAdapter  extends FragmentStateAdapter {
    public FragTipsAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position){
        Fragment fragment = new TipsObjectFragment();
        Bundle args = new Bundle();
        if(position == 0){
            args.putString("type", "High blood sugar");
        }else if(position == 1){
            args.putString("type", "High blood pressure");
        }else {
            args.putString("type", "High cholesterol");
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
