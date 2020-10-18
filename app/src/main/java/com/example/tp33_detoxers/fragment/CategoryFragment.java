package com.example.tp33_detoxers.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tp33_detoxers.R;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.Objects;

public class CategoryFragment extends Fragment {

    private CardView meat;
    private CardView veggie;
    private CardView dairy;
    private CardView fruit;
    private CardView snack;
    private CardView drink;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String CATEGORY = "category";

    public CategoryFragment() {}

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View category = inflater.inflate(R.layout.fragment_category, container, false);
        meat = category.findViewById(R.id.category_meat);
        veggie = category.findViewById(R.id.category_veggie);
        fruit = category.findViewById(R.id.category_fruit);
        dairy = category.findViewById(R.id.category_dairy);
        snack = category.findViewById(R.id.category_snack);
        drink = category.findViewById(R.id.category_drinks);
        MaterialToolbar toolbar = category.findViewById(R.id.category_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objects.requireNonNull(getActivity()).onBackPressed();
            }
        });

        meat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFrag("Bread");
            }
        });

        veggie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFrag("Vegetables");
            }
        });

        fruit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFrag("Fruits");
            }
        });

        dairy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFrag("Cheese");
            }
        });

        snack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFrag("Biscuits and cakes");
            }
        });

        drink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFrag("Beverages");
            }
        });
        return category;
    }

    private void replaceFrag(String category){
        Fragment newFrag = new CategoryListFragment(); //When clicking any item on the list view, it'll open another fragment
        Bundle bundle = new Bundle(); //To pass the arguments to another fragment
        bundle.putString("CATEGORY", category);
        newFrag.setArguments(bundle);
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.container, newFrag).addToBackStack(null).commit();
    }
}
