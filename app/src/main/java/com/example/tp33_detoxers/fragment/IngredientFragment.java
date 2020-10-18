package com.example.tp33_detoxers.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp33_detoxers.R;
import com.example.tp33_detoxers.SearchAPI;
import com.example.tp33_detoxers.adapter.DialogColorAdapter;
import com.example.tp33_detoxers.adapter.IngredientAdapter;
import com.example.tp33_detoxers.adapter.RVToxinAdapter;
import com.example.tp33_detoxers.database.IntakeDatabase;
import com.example.tp33_detoxers.model.IngredientAll;
import com.example.tp33_detoxers.model.IngredientDetail;
import com.example.tp33_detoxers.model.IntakeProduct;
import com.example.tp33_detoxers.viewModel.IntakeViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class IngredientFragment extends Fragment {
    private List<IngredientDetail> iDetail;
    private List<IngredientAll> iAll;

    private SearchAPI searchAPI;
    private TextView tv_name;
    private TextView tv_quantity;
    private TextView tv_title;
    private TextView tv_list_title;
    private TextView tv_spTitle;
    private ImageView iv_products;
    private Spinner sp_illness;
    private RecyclerView toxinRecycler;
    private RecyclerView.LayoutManager layoutManager;
    private RVToxinAdapter toxinAdapter;

    private IntakeDatabase db = null;
    private IntakeViewModel intakeViewModel;

    private GridView gridView;
    private IngredientAdapter gridAdapter;
    private DialogColorAdapter dialogColorAdapter;

    private View getListview;
    private SimpleAdapter adapter;

    private String[] iName = new String[] {"energy", "sodium","sugars","proteins","carbohydrates","saturated-fat","salt","fat"};
    private String[] levelName = new String[] {"salt","sugars","saturated-fat","fat"};
    private String[] colHEAD = new String[] {"circle", "text"};
    private int[] dataCell = new int[] {R.id.rv_circle, R.id.rv_quantity};
    private String[] color = new String[] {"red","yellow", "green"};
    private ArrayList<HashMap<String, String>> hashMapArrayList = new ArrayList<>();
    private ArrayList<String> levelNumber = new ArrayList<>();
    private Button bt_add;
    public IngredientFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        intakeViewModel = new ViewModelProvider(this.getActivity()).get(IntakeViewModel.class);
        intakeViewModel.initializeVars(this.getActivity().getApplication());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View ingredientView = inflater.inflate(R.layout.fragment_ingredient, container, false);

        searchAPI = new SearchAPI();
        db = IntakeDatabase.getInstance(this.getContext());
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("id", Context.MODE_PRIVATE);
        String pId = sharedPreferences.getString("id", null);

        getIngredients g = new getIngredients();
        g.execute(pId);

        tv_name = ingredientView.findViewById(R.id.tv_pName);
        tv_title = ingredientView.findViewById(R.id.tv_title);
        tv_quantity = ingredientView.findViewById(R.id.tv_pQuantity);
        bt_add = ingredientView.findViewById(R.id.bt_addList);
        Button bt_help = ingredientView.findViewById(R.id.bt_helper);
        tv_list_title = ingredientView.findViewById(R.id.tv_list_title);
        tv_spTitle = ingredientView.findViewById(R.id.tv_spTitle);
        iv_products = ingredientView.findViewById(R.id.iv_products);
        sp_illness =  ingredientView.findViewById(R.id.sp_illness);
        sp_illness.setSelection(0);
        tv_spTitle.setVisibility(View.GONE);
        sp_illness.setVisibility(View.GONE);
        iDetail = new ArrayList<>();
        toxinAdapter = new RVToxinAdapter(iDetail);
        toxinRecycler = ingredientView.findViewById(R.id.rv_toxin);
        gridView = ingredientView.findViewById(R.id.gridView);
        iAll = new ArrayList<>();
        gridAdapter = new IngredientAdapter(getActivity(), iAll);
        dialogColorAdapter = new DialogColorAdapter(getActivity(), color);

        MaterialToolbar toolbar = ingredientView.findViewById(R.id.ingredient_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objects.requireNonNull(getActivity()).onBackPressed();
            }
        });

        addMap(color);


        //filter the toxin when change the spinner
        sp_illness.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               toxinAdapter.getFilter().filter(parent.getItemAtPosition(position).toString());
            }
        });

        //add to the intake list
        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = tv_name.getText().toString();
                String quantity = tv_quantity.getText().toString().split(" ")[0];
                String salt = levelNumber.get(0);
                String sugars = levelNumber.get(1);
                String saturated = levelNumber.get(2);
                String fat = levelNumber.get(3);
                String url = levelNumber.get(levelNumber.size()-1);
                insertAsync insertAsync = new insertAsync();
                insertAsync.execute(pId,name, url,quantity,sugars,salt,saturated, fat);
            }
        });

        //click the button to show the information
        bt_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialAlertDialogBuilder(getContext())
                        .setTitle("Introduce the meaning of the circle")
                        .setAdapter(dialogColorAdapter, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("Back", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();
            }
        });

        return ingredientView;
    }

    public void saveLevel(String name, String number, String level){
        IngredientDetail ingredientDetails = new IngredientDetail(name, number, level);
        iDetail.add(ingredientDetails);
        toxinAdapter.addLevel(iDetail);
    }

    public void saveAllIngredient(String name, String number){
        IngredientAll ingredientAlls = new IngredientAll(name, number);
        iAll.add(ingredientAlls);
        gridAdapter.addData(iAll);
    }

    public void CreateDialog() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        getListview = inflater.inflate(R.layout.recycview_toxins, null);
    }

    public void addMap(String[] array) {
        HashMap<String, String> map = new HashMap<>();
        for(int i = 0; i < array.length; i++){
            map.put("circle","red");
            map.put("text","Ingredient is in high quantity and need to reconsider the intake");
            hashMapArrayList.add(map);
        }
    }

    //change the list height to fit the screen
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
                String allQuantity = j.getString("quantity");
                for (String value : iName) {
                    String quantity = j.getString(value + "_100g");
                    if(quantity.length() > 6){
                        quantity = quantity.substring(0, 6);
                    }
                    list.add(quantity);
                }
                for (String value: levelName){
                    String level = j.getString(value + "_100g");
                    levelNumber.add(level);
                    list.add(level);
                }
                list.add(pName);
                list.add(url);
                list.add(allQuantity);
            }catch (Exception e){
                e.printStackTrace();
            }
            return list;
        }

        @Override
        protected void onPostExecute(ArrayList<String> l){
            if(l.get(0).equals("0")){
                Snackbar.make(getView(), "Oops, no result found! ", Snackbar.LENGTH_LONG).show();
//                Toast.makeText(getActivity(), "No result found", Toast.LENGTH_LONG).show();
            }else{
                tv_title.setText("Toxin level for 100g");
                tv_name.setText(l.get(iName.length+levelName.length));
                tv_quantity.setText(l.get(iName.length+levelName.length+2) + " g");
                Picasso.get().load(l.get(iName.length+levelName.length+1)).into(iv_products);
                levelNumber.add(l.get(iName.length+levelName.length+1));
                tv_spTitle.setVisibility(View.VISIBLE);
                sp_illness.setVisibility(View.VISIBLE);
                for(int i = 0; i < iName.length; i++){
                    saveAllIngredient(iName[i],l.get(i));
                }
                for(int i = 0; i <iName.length;i++ ){
                    for (int j = 0; j< levelName.length;j++){
                        if (iName[i].equals(levelName[j])){
                            saveLevel(levelName[j],l.get(i),l.get(iName.length+j));
                        }
                    }
                }
                tv_list_title.setText("Ingredient list per 100 g");
                gridView.setAdapter(gridAdapter);
                toxinAdapter.getFilter().filter("All");
                toxinRecycler.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
                toxinRecycler.setAdapter(toxinAdapter);
                layoutManager = new LinearLayoutManager(getActivity());
                toxinRecycler.setLayoutManager(layoutManager);
            }
    }
    }

    //insert the data to the intake database
    public class insertAsync extends AsyncTask<String, Void, List<String>>{

        @Override
        protected List<String> doInBackground(String... s) {
            List<String> result = new ArrayList<>();
            try{
                IntakeProduct intakeProduct = intakeViewModel.findByProductId(s[0]); //check whether the product is existed in the database
                String name = s[1];
                String url = s[2];
                String quantity = s[3];
                String sugar = s[4];
                String salt = s[5];
                String fat = s[6];
                String saturated = s[7];
                if(intakeProduct == null){
                    IntakeProduct source = new IntakeProduct(s[0],url,name,sugar,salt,fat,saturated,quantity);
                    intakeViewModel.insert(source); //insert the data into database
                    result.add(name);
                }else {
                    result.add("None");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(List<String> result) {
            // return the result of inserting into the database and show the notice whether the product is added into the database
            if (result.get(0).equals("None")){ //check the return value
                Snackbar.make(bt_add, "This product has been added to the intake list", Snackbar.LENGTH_LONG).show();
//                Toast.makeText(getActivity(), "This product has been added to the intake list", Toast.LENGTH_LONG).show();
            }else{
                Snackbar.make(bt_add, "Added product: " + result.get(0), Snackbar.LENGTH_LONG).show();
//                Toast.makeText(getActivity(), "Added product: " + result.get(0), Toast.LENGTH_LONG).show();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new IntakeFragment()).addToBackStack(null).commit();
            }
        }
    }
}
