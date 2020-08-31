package com.example.tp33_detoxers.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp33_detoxers.R;
import com.example.tp33_detoxers.SearchAPI;
import com.example.tp33_detoxers.adapter.RVToxinAdapter;
import com.example.tp33_detoxers.model.IngredientDetail;
import com.example.tp33_detoxers.viewModel.ToxinLevelViewModel;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IngredientFragment extends Fragment {
    private List<HashMap<String,String>> ingredientArray;
    private List<IngredientDetail> iDetail;
    private List<IngredientDetail> filterList;

    private SimpleAdapter listAdapter;
    private ListView ingredientList;
    private SearchAPI searchAPI= null;
    private TextView tv_name;
    private TextView tv_title;
    private ImageView iv_products;
    private Spinner sp_illness;
    private RecyclerView toxinRecycler;
    private RecyclerView.LayoutManager layoutManager;
    private RVToxinAdapter toxinAdapter;
    private ToxinLevelViewModel toxinLevelViewModel;

    private String[] colHEAD = new String[] {"Ingredient Name", "Ingredient Quantity"};
    private int[] dataCell = new int[] {R.id.ingredient_name, R.id.ingredient_quantity};
    private String[] iName = new String[] {"energy", "sodium","sugars","proteins","carbohydrates","saturated-fat","salt","fat",};
    private String[] levelName = new String[] {"salt","sugars","saturated-fat","fat"};

    public IngredientFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View ingredientView = inflater.inflate(R.layout.fragment_ingredient, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("id", Context.MODE_PRIVATE);
        String pId = sharedPreferences.getString("id", null);

        tv_name = ingredientView.findViewById(R.id.tv_pName);
        tv_title = ingredientView.findViewById(R.id.tv_title);
        iv_products = ingredientView.findViewById(R.id.iv_products);
        ingredientList = ingredientView.findViewById(R.id.listView);
        sp_illness =  ingredientView.findViewById(R.id.sp_illness);
        sp_illness.setSelection(0);

        iDetail = new ArrayList<>();
        filterList = new ArrayList<>();
        toxinAdapter = new RVToxinAdapter(iDetail);
        toxinRecycler = ingredientView.findViewById(R.id.rv_toxin);
        toxinLevelViewModel = new ViewModelProvider(this.getActivity()).get(ToxinLevelViewModel.class);

        //filter the toxin when change the spinner
        sp_illness.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).toString().equals("All")){
                    toxinLevelViewModel.getAllToxin(iDetail).observe(getActivity(), new Observer<List<IngredientDetail>>() {
                        @Override
                        public void onChanged(List<IngredientDetail> ingredientDetails) {
                            toxinAdapter.addLevel(ingredientDetails);
                        }
                    });
                }else {
                    filterList.clear();
                    filterList = getFilterList(parent.getItemAtPosition(position).toString());
                    toxinLevelViewModel.getAllToxin(filterList).observe(getActivity(), new Observer<List<IngredientDetail>>() {
                        @Override
                        public void onChanged(List<IngredientDetail> ingredientDetails) {
                            toxinAdapter.addLevel(ingredientDetails);
                        }
                    });
                }
                toxinRecycler.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
                toxinRecycler.setAdapter(toxinAdapter);
                layoutManager = new LinearLayoutManager(getActivity());
                toxinRecycler.setLayoutManager(layoutManager);
            }
        });

        getIngredients g = new getIngredients();
        g.execute(pId);

        toxinLevelViewModel.getAllToxin(iDetail).observe(this.getActivity(), new Observer<List<IngredientDetail>>() {
            @Override
            public void onChanged(List<IngredientDetail> ingredientDetails) {
                toxinAdapter.addLevel(ingredientDetails);
                toxinRecycler.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
                toxinRecycler.setAdapter(toxinAdapter);
                layoutManager = new LinearLayoutManager(getActivity());
                toxinRecycler.setLayoutManager(layoutManager);
            }
        });

        return ingredientView;
    }

    private List<IngredientDetail> getFilterList(String text){
        List<IngredientDetail>  filterList = new ArrayList<>();
        List<String> result = new ArrayList<>();

        switch (text){
            case "High blood sugar":
                result.add("sugars");
                result.add("saturated-fat");
                break;
            case "High blood pressure":
                result.add("salt");
                result.add("sugars");
                break;
            case "High cholesterol":
                result.add("fat");
                result.add("saturated-fat");
        }

        for (IngredientDetail item: iDetail){
            for (String ingredient: result){
                if(item.getiName().equals(ingredient)){
                    filterList.add(item);
                }
            }
        }
        return filterList;
    }

    public void saveLevel(String name, String number, String level){
        IngredientDetail ingredientDetails = new IngredientDetail(name, number, level);
        iDetail.add(ingredientDetails);
        toxinAdapter.addLevel(iDetail);
    }

    // get ingredients from the api
    public class getIngredients extends AsyncTask<String, Void, ArrayList<String>>{

        @Override
        protected ArrayList<String> doInBackground(String... s) {
            ArrayList<String> list = new ArrayList<>();
            String result = SearchAPI.searchProductDetail(s[0]);
            try{
                JSONObject j = new JSONObject(SearchAPI.getDetail(result));
                String pName = j.getString("product_name");
                String url = j.getString("image_front_url");
                for (String value : iName) {
                    String quantity = j.getJSONObject("nutriments").getString(value + "_100g");
                    list.add(quantity);
                }
                for (String value: levelName){
                    String level = j.getJSONObject("nutrient_levels").getString(value);
                    list.add(level);
                }
                list.add(pName);
                list.add(url);
            }catch (Exception e){
                e.printStackTrace();
            }
            return list;
        }

        @Override
        protected void onPostExecute(ArrayList<String> l){
            tv_title.setText("Toxin level for 100g");
            tv_name.setText(l.get(iName.length+levelName.length));
            Picasso.get().load(l.get(iName.length+levelName.length+1)).into(iv_products);
            ingredientArray = new ArrayList<>();
            for(int i = 0; i < iName.length; i++){
                HashMap<String, String> map = new HashMap<>();
                map.put("Ingredient Name", iName[i]);
                map.put("Ingredient Quantity", l.get(i));
                ingredientArray.add(map);
            }
            for(int i = 0; i <iName.length;i++ ){
                for (int j = 0; j< levelName.length;j++){
                    if (iName[i].equals(levelName[j])){
                        //saveLevel(levelName[j],l.get(i),l.get(iName.length+j));
                        IngredientDetail ingredientDetails = new IngredientDetail(levelName[j],l.get(i),l.get(iName.length+j));
                        iDetail.add(ingredientDetails);
                    }
                }
            }
            listAdapter = new SimpleAdapter(getActivity(), ingredientArray, R.layout.listview_ingredient, colHEAD, dataCell);
            ingredientList.setAdapter(listAdapter);
            toxinLevelViewModel.setIngredientDetail(iDetail);
//            toxinRecycler.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
//            toxinRecycler.setAdapter(toxinAdapter);
//            layoutManager = new LinearLayoutManager(getActivity());
//            toxinRecycler.setLayoutManager(layoutManager);

        }
    }
}
