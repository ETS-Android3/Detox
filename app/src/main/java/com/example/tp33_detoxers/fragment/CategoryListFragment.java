package com.example.tp33_detoxers.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.example.tp33_detoxers.adapter.RVSearchAdapter;
import com.example.tp33_detoxers.model.IngredientDetail;
import com.example.tp33_detoxers.model.SearchResult;
import com.example.tp33_detoxers.viewModel.resultListViewModel;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class CategoryListFragment extends Fragment {


    private SearchAPI searchAPI;
    private TextView bundle;
    private String category;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<SearchResult> products;
    private RVSearchAdapter adapter;
    private String selectedIngredient = "all";
    private String selectedLevel = "all";
    private resultListViewModel resultListViewModel;
    private List<SearchResult> filterResult;
    private searchProduct search = new searchProduct(); //


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View catListView = inflater.inflate(R.layout.fragment_categorylist, container, false);
        recyclerView = catListView.findViewById(R.id.categoryList_rv);
        products = new ArrayList<>();
        TextView categoryName = catListView.findViewById(R.id.category_bundle);
        searchAPI = new SearchAPI();
        Spinner sp_ingredient = catListView.findViewById(R.id.categoryList_sp_ingredient);
        Spinner sp_ingredientLevel = catListView.findViewById(R.id.categoryList_sp_ingredient_level);
        adapter = new RVSearchAdapter(products);
        filterResult = new ArrayList<>();
        products = new ArrayList<>();
//        resultListViewModel = new ViewModelProvider(this.requireActivity()).get(resultListViewModel.class);


        if (getArguments() != null) { //make sure the bundle contains category info
            category = getArguments().getString("CATEGORY");
            categoryName.setText(category);
            search.execute(category); //***
        }
//        sp_ingredient.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {}
//
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//
//            }
//        });
//
//        sp_ingredientLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                selectedLevel = parent.getItemAtPosition(position).toString().toLowerCase();
//                search.execute(category, selectedIngredient, selectedLevel);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                selectedLevel = "all";
//            }
//        });



        return catListView;
    }

    private void saveData(String name, String url, String id){
        SearchResult searchResult = new SearchResult(url, name, id);
        products.add(searchResult);
        adapter.addProducts(products);
    }

    public class searchProduct extends AsyncTask<String,Void,RVSearchAdapter>{

        @Override
        protected RVSearchAdapter doInBackground(String... strings) {
            String result = SearchAPI.searchCategory(strings[0]);

            try{
                JSONArray j = new JSONArray(SearchAPI.getSource(result));

                    for(int i = 0; i < 20; i++){

                        if ((j.getJSONObject(i).has("image_url")) && (j.getJSONObject(i).has("nutrient_levels"))
                                && (j.getJSONObject(i).has("product_name"))
                                && (j.getJSONObject(i).getJSONObject("nutrient_levels").has("saturated-fat"))
                                && (j.getJSONObject(i).getJSONObject("nutrient_levels").has("sugars"))
                                && (j.getJSONObject(i).getJSONObject("nutrient_levels").has("fat"))
                                && (j.getJSONObject(i).getJSONObject("nutrient_levels").has("salt"))
                                && (j.getJSONObject(i).has("nutriments"))
                                &&(j.getJSONObject(i).getJSONObject("nutriments").length() != 0)
                        ) {
                                String name = j.getJSONObject(i).getString("product_name");
                                String id = j.getJSONObject(i).getString("_id");
                                String url = j.getJSONObject(i).getString("image_url");
                                saveData(name, url, id);
                        }
                    }

            }
            catch (Exception e){
                        e.printStackTrace();
                    }
            adapter = new RVSearchAdapter(products);
            return adapter;
        }

        @Override
        protected void onPostExecute(RVSearchAdapter a){
            recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
            recyclerView.setAdapter(a);
            layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
        }
    }








}
