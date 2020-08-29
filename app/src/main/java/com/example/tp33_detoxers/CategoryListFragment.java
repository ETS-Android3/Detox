package com.example.tp33_detoxers;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp33_detoxers.adapter.RVSearchAdpater;
import com.example.tp33_detoxers.model.SearchResult;

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
    private RVSearchAdpater adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View catListView = inflater.inflate(R.layout.fragment_categorylist, container, false);
        recyclerView = catListView.findViewById(R.id.categoryList_rv);
        products = new ArrayList<>();
        TextView categoryName = catListView.findViewById(R.id.category_bundle);
        searchAPI = new SearchAPI();
        adapter = new RVSearchAdpater(products);
        products = new ArrayList<>();

        if (getArguments() != null) { //make sure the bundle contains category info
            category = getArguments().getString("CATEGORY");
            categoryName.setText(category);
            searchProduct search = new searchProduct();
            search.execute(category);
        }

        return catListView;
    }

    private void saveData(String name, String url, String id){
        SearchResult searchResult = new SearchResult(url, name, id);
        products.add(searchResult);
        adapter.addProducts(products);
    }

    public class searchProduct extends AsyncTask<String,Void,RVSearchAdpater>{

        @Override
        protected RVSearchAdpater doInBackground(String... strings) {
            String result = SearchAPI.searchCategory(strings[0]);
            try{
                JSONArray j = new JSONArray(SearchAPI.getSource(result));
                for(int i = 0; i < 10; i++){
                    String name = j.getJSONObject(i).getString("product_name");
                    String id = j.getJSONObject(i).getString("_id");
                    String url;
                    if (j.getJSONObject(i).has("image_url")) {
                        url = j.getJSONObject(i).getString("image_url");
                    } else {
                        url = "https://api.time.com/wp-content/uploads/2015/02/cats.jpg?quality=85&w=1024&h=512&crop=1";
                    }

                    saveData(name, url, id);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            adapter = new RVSearchAdpater(products);
            return adapter;
        }

        @Override
        protected void onPostExecute(RVSearchAdpater a){
            recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
            recyclerView.setAdapter(a);
            layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
        }
    }







}
