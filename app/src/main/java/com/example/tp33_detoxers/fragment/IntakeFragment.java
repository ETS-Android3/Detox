package com.example.tp33_detoxers.fragment;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp33_detoxers.R;
import com.example.tp33_detoxers.adapter.RVIntakeAdapter;
import com.example.tp33_detoxers.database.IntakeDatabase;
import com.example.tp33_detoxers.model.IntakeProduct;
import com.example.tp33_detoxers.viewModel.IntakeViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

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

    public IntakeFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        intakeViewModel = new ViewModelProvider(Objects.requireNonNull(this.getActivity())).get(IntakeViewModel.class);
        intakeViewModel.initializeVars(this.getActivity().getApplication());
        View intakeView = inflater.inflate(R.layout.fragment_intakelist, container, false);

        recyclerView = intakeView.findViewById(R.id.recyIntake);
        intakes = new ArrayList<>();
        adapter = new RVIntakeAdapter(intakes);
        Button bt_delete = intakeView.findViewById(R.id.bt_removeAll);
        Button bt_calculate = intakeView.findViewById(R.id.calculate);

        //observe the view model
        intakeViewModel.getAllIntakes().observe(getViewLifecycleOwner(), new Observer<List<IntakeProduct>>() {
            @Override
            public void onChanged(List<IntakeProduct> intakeProducts) {
                for(IntakeProduct temp: intakes){
                    saveIntakes(temp.getpName(), temp.getpUrl(), temp.getpId(), temp.getpSugar(), temp.getpSalt(), temp.getpFat(), temp.getpSaturated());
                }
            }
        });

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
                            }
                        });
            }
        });

        return intakeView;
    }

    //add the data into RVIntakeAdapter
    private void saveIntakes(String name, String url, String id, String sugar, String salt, String fat, String saturated){
        IntakeProduct intakeProduct = new IntakeProduct(id, url, name, sugar, salt, fat, saturated);
        intakes.add(intakeProduct);
        adapter.addIntakes(intakes);
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
            Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
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
            Toast.makeText(getActivity(), "All the data are deleted.", Toast.LENGTH_LONG).show();
        }
    }
}
