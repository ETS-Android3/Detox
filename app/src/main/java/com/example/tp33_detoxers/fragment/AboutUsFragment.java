package com.example.tp33_detoxers.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.tp33_detoxers.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class AboutUsFragment extends Fragment {

    public AboutUsFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View aboutUs = inflater.inflate(R.layout.fragment_about_us, container, false);
        TextView text = aboutUs.findViewById(R.id.aboutUs_text);
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().getItem(0).setChecked(true);
        MaterialToolbar toolbar = aboutUs.findViewById(R.id.aboutUs_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objects.requireNonNull(getActivity()).onBackPressed();
            }

        });

        text.setText("Hi there, we are the Detoxers!\n" +
                "We are 4 graduate students studying information technology at Monash University.\n\n" +
                "Our aim is to help people who are at risk of high blood sugar, high blood pressure, " +
                "and high cholesterol to better understand the ingredients within the food products they consume.\n\n" +
                "Through our research, we found that these conditions are highly affected by sugar, salt, fat and saturated fat." +
                "We consider these ingredients toxins as they may exacerbate the aforementioned conditions.\n\n" +
                "This application will help to highlight the quantities of toxins within food products to better manage your health concerns!");
        text.setTextSize(18);
        //text.setTextColor(Color.parseColor("#FFFFFF"));
        text.setGravity(Gravity.LEFT);

        return aboutUs;
    }
}
