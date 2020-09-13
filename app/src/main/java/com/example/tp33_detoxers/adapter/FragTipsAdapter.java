package com.example.tp33_detoxers.adapter;

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
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
