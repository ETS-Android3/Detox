package com.example.tp33_detoxers.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
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

    private SimpleAdapter listAdapter;
    private ListView ingredientList;
    private SearchAPI searchAPI;
    private TextView tv_name;
    private TextView tv_title;
    private TextView tv_list_title;
    private TextView tv_spTitle;
    private ImageView iv_products;
    private Spinner sp_illness;
    private RecyclerView toxinRecycler;
    private RecyclerView.LayoutManager layoutManager;
    private RVToxinAdapter toxinAdapter;

    private String[] colHEAD = new String[] {"Ingredient Name", "Ingredient Quantity"};
    private int[] dataCell = new int[] {R.id.ingredient_name, R.id.ingredient_quantity};
    private String[] iName = new String[] {"energy", "sodium","sugars","proteins","carbohydrates","saturated-fat","salt","fat",};
    private String[] levelName = new String[] {"salt","sugars","saturated-fat","fat"};

    public IngredientFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View ingredientView = inflater.inflate(R.layout.fragment_ingredient, container, false);

        searchAPI = new SearchAPI();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("id", Context.MODE_PRIVATE);
        String pId = sharedPreferences.getString("id", null);

        getIngredients g = new getIngredients();
        g.execute(pId);

        tv_name = ingredientView.findViewById(R.id.tv_pName);
        tv_title = ingredientView.findViewById(R.id.tv_title);
        tv_list_title = ingredientView.findViewById(R.id.tv_list_title);
        tv_spTitle = ingredientView.findViewById(R.id.tv_spTitle);
        iv_products = ingredientView.findViewById(R.id.iv_products);
        ingredientList = ingredientView.findViewById(R.id.listView);
        sp_illness =  ingredientView.findViewById(R.id.sp_illness);
        sp_illness.setSelection(0);
        tv_spTitle.setVisibility(View.GONE);
        sp_illness.setVisibility(View.GONE);
        iDetail = new ArrayList<>();
        toxinAdapter = new RVToxinAdapter(iDetail);
        toxinRecycler = ingredientView.findViewById(R.id.rv_toxin);
//        toxinLevelViewModel = new ViewModelProvider(this.getActivity()).get(ToxinLevelViewModel.class);



        //filter the toxin when change the spinner
        sp_illness.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               toxinAdapter.getFilter().filter(parent.getItemAtPosition(position).toString());
            }
        });

        return ingredientView;
    }

    public void saveLevel(String name, String number, String level){
        IngredientDetail ingredientDetails = new IngredientDetail(name, number, level);
        iDetail.add(ingredientDetails);
        toxinAdapter.addLevel(iDetail);
    }

    public static void justifyListViewHeightBasedOnChildren(ListView listView){
        ListAdapter adapter = listView.getAdapter();

        if(adapter == null){
            return;
        }
        ViewGroup vg = listView;
        int totalHeight = 0;
        for (int i = 0; i < 5; i++) {
            View listItem = adapter.getView(i, null, vg);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams par = listView.getLayoutParams();
        par.height = totalHeight + (listView.getDividerHeight() * 5);
        listView.setLayoutParams(par);
        listView.requestLayout();
    }

    // get ingredients from the api
    public class getIngredients extends AsyncTask<String, Void, ArrayList<String>>{

        @Override
        protected ArrayList<String> doInBackground(String... s) {
            ArrayList<String> list = new ArrayList<>();
            String result = SearchAPI.searchProductDetail(s[0]);
            try{
                String json = SearchAPI.getDetail(result);
                if(json.equals("No info found")){
                    list.add("0");
                    return list;
                }
                JSONObject j = new JSONObject(json);
                String pName = j.getString("product_name");
                String url = j.getString("image_url");
                for (String value : iName) {
                    String quantity = j.getString(value + "_100g");
                    if(quantity.length() > 6){
                        quantity = quantity.substring(0, 6);
                    }
                    list.add(quantity);
                }
                for (String value: levelName){
                    String level = j.getString(value + "_100g");
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
            if(l.get(0).equals("0")){
                Toast.makeText(getActivity(), "No result found", Toast.LENGTH_LONG).show();
            }else{
                tv_title.setText("Toxin level for 100g");
                tv_name.setText(l.get(iName.length+levelName.length));
                Picasso.get().load(l.get(iName.length+levelName.length+1)).into(iv_products);
                tv_spTitle.setVisibility(View.VISIBLE);
                sp_illness.setVisibility(View.VISIBLE);
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
                            saveLevel(levelName[j],l.get(i),l.get(iName.length+j));
                        }
                    }
                }
                tv_list_title.setText("Ingredient list per 100 g");
                listAdapter = new SimpleAdapter(getActivity(), ingredientArray, R.layout.listview_ingredient, colHEAD, dataCell);
                ingredientList.setAdapter(listAdapter);

                justifyListViewHeightBasedOnChildren(ingredientList);
                toxinAdapter.getFilter().filter("All");
                toxinRecycler.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
                toxinRecycler.setAdapter(toxinAdapter);
                layoutManager = new LinearLayoutManager(getActivity());
                toxinRecycler.setLayoutManager(layoutManager);
            }
    }
    }
}
