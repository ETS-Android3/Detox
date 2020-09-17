package com.example.tp33_detoxers.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp33_detoxers.R;
import com.example.tp33_detoxers.SearchAPI;
import com.example.tp33_detoxers.adapter.RVCategoryAdapter;
import com.example.tp33_detoxers.model.CategoryResult;
import com.google.android.material.appbar.MaterialToolbar;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class CategoryListFragment extends Fragment {
    private SearchAPI searchAPI;
    private TextView bundle;
    private ProgressBar progressBar;
    private String category;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<CategoryResult> categoryProducts;
    private RVCategoryAdapter categoryAdapter;
    private String selectedIngredient = "All";
    private String selectedLevel = "All";
    //private searchProduct search = new searchProduct(); //


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View catListView = inflater.inflate(R.layout.fragment_categorylist, container, false);
        MaterialToolbar toolbar = catListView.findViewById(R.id.toolbar_category_list);
        categoryProducts = new ArrayList<>();
        recyclerView = catListView.findViewById(R.id.recySearch);

        TextView categoryName = catListView.findViewById(R.id.category_bundle);
        searchAPI = new SearchAPI();
        Spinner sp_ingredient = catListView.findViewById(R.id.categoryList_sp_ingredient);
        Spinner sp_ingredientLevel = catListView.findViewById(R.id.categoryList_sp_ingredient_level);

        progressBar = catListView.findViewById(R.id.progress_category);
        progressBar.setVisibility(View.GONE);



        if (getArguments() != null) { //make sure the bundle contains category info
            category = getArguments().getString("CATEGORY");
            toolbar.setTitle(category);
            categoryName.setText(category);
            searchCategoryList searchCategory = new searchCategoryList();
            searchCategory.execute(category);
        }

        categoryAdapter = new RVCategoryAdapter(categoryProducts);

        sp_ingredient.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedIngredient = parent.getItemAtPosition(position).toString();
                if(selectedLevel.equals("All") & !selectedIngredient.equals("All")){
                    Toast.makeText(getActivity(), "Please select the ingredient level", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_ingredientLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedLevel = parent.getItemAtPosition(position).toString();
                if (selectedLevel.equals("All") & !selectedIngredient.equals("All")){
                    Toast.makeText(getActivity(), "Please select the ingredient", Toast.LENGTH_LONG).show();
                }else if(!selectedLevel.equals("All") & !selectedIngredient.equals("All")){
                    String filtered = selectedIngredient + "," + selectedLevel;
                    categoryAdapter.getFilter().filter(filtered);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return catListView;
    }

    private void saveData(String name, String url, String id,String fat,String sugar,String salt,String saturated){
        CategoryResult categoryResult = new CategoryResult(id,url, name,sugar,salt, fat, saturated);
        categoryProducts.add(categoryResult);
        categoryAdapter.addCategory(categoryProducts);
    }

    public class searchCategoryList extends AsyncTask<String,Void,List<CategoryResult>>{

        @Override
        protected List<CategoryResult> doInBackground(String... strings) {
            String result = SearchAPI.searchCategory(strings[0]);

            try{
                JSONArray j = new JSONArray(SearchAPI.getCategory(result));

                    for(int i = 0; i < j.length(); i++){
                        String name = j.getJSONObject(i).getString("product_name");
                        String id = j.getJSONObject(i).getString("code");
                        String url = j.getJSONObject(i).getString("image_url");
                        String fat = j.getJSONObject(i).getString("fat_100g");
                        String sugar = j.getJSONObject(i).getString("sugars_100g");
                        String salt = j.getJSONObject(i).getString("salt_100g");
                        String saturated = j.getJSONObject(i).getString("saturated-fat_100g");
                        saveData(name, url, id,fat,sugar,salt,saturated);
                    }

            }
            catch (Exception e){
                        e.printStackTrace();
                    }
            return categoryProducts;
        }

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(List<CategoryResult> c){
            categoryAdapter = new RVCategoryAdapter(categoryProducts);
            progressBar.setVisibility(View.GONE);
            //recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
            categoryAdapter.getFilter().filter(selectedIngredient);
            recyclerView.setAdapter(categoryAdapter);
            layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);

        }
    }

}
