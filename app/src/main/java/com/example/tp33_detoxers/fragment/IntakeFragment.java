package com.example.tp33_detoxers.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp33_detoxers.R;
import com.example.tp33_detoxers.adapter.RVIntakeAdapter;
import com.example.tp33_detoxers.database.IntakeDatabase;
import com.example.tp33_detoxers.model.IntakeProduct;
import com.example.tp33_detoxers.viewModel.IntakeViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class IntakeFragment extends Fragment {
    IntakeDatabase db = null;
    IntakeViewModel intakeViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private RVIntakeAdapter adapter;
    private List<IntakeProduct> intakes;
    private double sugarSum;
    private double saltSum;
    private double fatSum;
    private double saturatedSum;
    private double quantitySum;
    private ArrayList<String> sugars = new ArrayList<>();
    private ArrayList<String> salts = new ArrayList<>();
    private ArrayList<String> fats = new ArrayList<>();
    private ArrayList<String> saturateds = new ArrayList<>();
    private ArrayList<String> quantities = new ArrayList<>();
    private Button bt_calculate;
    private  Button bt_delete;

    public IntakeFragment() {
    }

    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        intakeViewModel = new ViewModelProvider(Objects.requireNonNull(this.getActivity())).get(IntakeViewModel.class);
        intakeViewModel.initializeVars(this.getActivity().getApplication());
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View intakeView = inflater.inflate(R.layout.fragment_intakelist, container, false);
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().getItem(4).setChecked(true);
        recyclerView = intakeView.findViewById(R.id.recyIntake);
        intakes = new ArrayList<>();
        adapter = new RVIntakeAdapter(intakes);
        MaterialToolbar toolbar = intakeView.findViewById(R.id.intake_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objects.requireNonNull(getActivity()).onBackPressed();
            }
        });
        Button bt_addNew = intakeView.findViewById(R.id.bt_addnew);
        bt_delete = intakeView.findViewById(R.id.bt_removeAll);
        bt_calculate = intakeView.findViewById(R.id.calculate);
        String[] items = new String[]{"1. Click the 'Add New Product' button", "2. Use the search function to find your product","3. Click the 'Add To My Meals' button on the product's detail page","4. Repeat steps 1 to 3 for your remaining products",
                "5. Click the 'Calculate Intake' button to generate your personalised intake report","6. To clear the list, click the 'Remove All' button" };

        //set the information tips
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_help) {
                    AlertDialog builder = new MaterialAlertDialogBuilder(getContext())
                            .setTitle("How to use the My Meals function")
                            .setItems(items, null)
                            .setPositiveButton("Back", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).create();
                    builder.show();

                }
                return true;
            }
        });

        //observe the view model
        intakeViewModel.getAllIntakes().observe(getViewLifecycleOwner(), new Observer<List<IntakeProduct>>() {
            @Override
            public void onChanged(@Nullable List<IntakeProduct> intakeProducts) {
                sugars.clear();
                salts.clear();
                fats.clear();
                saturateds.clear();
                quantities.clear();
                for(IntakeProduct temp: intakeProducts){
                    String sugar = temp.getpSugar();
                    String salt = temp.getpSalt();
                    String fat = temp.getpFat();
                    String saturated = temp.getpSaturated();
                    String quantity = temp.getpQuantity();
                    sugars.add(sugar);
                    salts.add(salt);
                    fats.add(fat);
                    saturateds.add(saturated);
                    quantities.add(quantity);
                    saveIntakes(temp.getpName(), temp.getpUrl(), temp.getpId(), sugar, salt, fat, saturated, quantity);
                }
            }
        });

        //click the button to add new product into the list
        bt_addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new SearchFragment()).addToBackStack(null).commit();
            }
        });

        //click the button to delete the all the products in the list
        bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialAlertDialogBuilder(getContext())
                        .setMessage("Do you want to delete all the products?")
                        .setNeutralButton("Cancel", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteAllAsync deleteAllAsync = new deleteAllAsync();
                                deleteAllAsync.execute();
                                intakes.clear();
                                adapter.notifyDataSetChanged();
                            }
                        }).show();
            }
        });

        //click the button to calculate the intakes and go to the report page
        bt_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Double> list = new ArrayList<>();
                double[] num = adapter.getNum();
                sugarSum = 0;
                saltSum = 0;
                fatSum = 0;
                saturatedSum = 0;
                list = calculateAll(num);
                AppCompatActivity activity = (AppCompatActivity)v.getContext();
                SharedPreferences sharedPreferences = activity.getSharedPreferences("list", Context.MODE_PRIVATE);
                Gson json = new Gson();
                String array = json.toJson(list);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("list",array);
                editor.apply();

                String message = "Do you want to calculate the intakes from this list?";
                new MaterialAlertDialogBuilder(getContext())
                        .setMessage(message)
                        .setNeutralButton("Cancel", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(quantities.size() < 1){
                                    Snackbar.make(bt_calculate, "Please add product first!", Snackbar.LENGTH_LONG).show();
                                }else{
                                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new ReportFragment()).addToBackStack(null).commit();
                                }
                            }
                        }).show();
            }
        });

        recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        return intakeView;
    }

    //add the data into RVIntakeAdapter
    private void saveIntakes(String name, String url, String id, String sugar, String salt, String fat, String saturated, String quantity){
        IntakeProduct intakeProduct = new IntakeProduct(id, url, name, sugar, salt, fat, saturated,quantity);
        intakes.add(intakeProduct);
        adapter.addIntakes(intakes);
    }

    //calculate the all ingredients of my meals
    private ArrayList<Double> calculateAll(double[] number){
        ArrayList<Double> list = new ArrayList<>();
        for(int i = 0; i <quantities.size(); i++ ){
            double quantityNum = Double.parseDouble(quantities.get(i));
            sugarSum += Double.parseDouble(sugars.get(i))*quantityNum/100*number[i];
            saltSum += Double.parseDouble(salts.get(i))*quantityNum/100*number[i];
            fatSum += Double.parseDouble(fats.get(i))*quantityNum/100*number[i];
            saturatedSum += Double.parseDouble(saturateds.get(i))*quantityNum/100*number[i];
            quantitySum += quantityNum*number[i];
        }
        list.add(sugarSum);
        list.add(saltSum);
        list.add(fatSum);
        list.add(saturatedSum);
        list.add(quantitySum);
        return list;
    }

    //delete the single data from the database
    public class deleteSingle extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... s) {
            String result = "";
            try{
                IntakeProduct intakeProduct = intakeViewModel.findByProductId(s[0]);
                String title = intakeProduct.getpName();
                intakeViewModel.delete(intakeProduct);
                result = "The product " + " has been removed!";
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Snackbar.make(bt_delete, s, Snackbar.LENGTH_LONG).show();
        }
    }

    //delete the all data from the database
    public class deleteAllAsync extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                intakeViewModel.deleteAll();
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Snackbar.make(bt_delete, "My meals are cleared now!", Snackbar.LENGTH_LONG).show();
        }
    }
}
