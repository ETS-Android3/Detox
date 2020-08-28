package com.example.tp33_detoxers;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp33_detoxers.adapter.RVSearchAdpater;
import com.example.tp33_detoxers.model.SearchResult;
import com.example.tp33_detoxers.SearchAPI;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    private SearchAPI searchAPI=null;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<SearchResult> products;
    private RVSearchAdpater adapter;


    public SearchFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View searchView = inflater.inflate(R.layout.fragment_search, container, false);
        searchAPI = new SearchAPI();

        recyclerView = searchView.findViewById(R.id.recySearch);
        products = new ArrayList<>();

        final EditText et_search = searchView.findViewById(R.id.ed_search);
        final Button btn_search =searchView.findViewById(R.id.btn_search);

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                btn_search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        products.clear();
                        adapter = new RVSearchAdpater(products);
                        String keyword = et_search.getText().toString();
                        searchProduct searchProduct = new searchProduct();
                        searchProduct.execute(keyword);
                    }
                });
            }
        });
        return searchView;
    }

    private void saveData(String name, String url, String id){
        SearchResult searchResult = new SearchResult(url, name, id);
        products.add(searchResult);
        adapter.addProducts(products);
    }

    //async to get the result of the search
    public class searchProduct extends AsyncTask<String,Void,RVSearchAdpater>{

        @Override
        protected RVSearchAdpater doInBackground(String... strings) {
            String result = SearchAPI.search(strings[0]);
            try{
                JSONArray j = new JSONArray(SearchAPI.getSource(result));
                for(int i = 0; i < 10; i++){
                    String name = j.getJSONObject(i).getString("product_name");
                    String id = j.getJSONObject(i).getString("id");
                    String url = j.getJSONObject(i).getString("image_front_url");
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
