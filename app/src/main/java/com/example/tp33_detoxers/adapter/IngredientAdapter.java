package com.example.tp33_detoxers.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tp33_detoxers.R;
import com.example.tp33_detoxers.model.IngredientAll;
import com.squareup.picasso.Picasso;

import java.util.List;

public class IngredientAdapter extends BaseAdapter {
    private Context context;
    private List<IngredientAll> ingredientAlls;

    public IngredientAdapter(Context context, List<IngredientAll> ingredientAlls) {
        this.context = context;
        this.ingredientAlls = ingredientAlls;
    }

    public void addData(List<IngredientAll> products){
        ingredientAlls = products;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return ingredientAlls.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridView;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            gridView = new View(context);
            gridView = inflater.inflate(R.layout.grid_item, null);
            final IngredientAll array = ingredientAlls.get(position);
            ImageView imageView = gridView.findViewById(R.id.grid_icon);
            TextView tvName = gridView.findViewById(R.id.grid_name);
            TextView tvNum = gridView.findViewById(R.id.grid_number);
            String unit = "";
            String name = array.getAllName();
            switch (name){
                case "energy":
                    Picasso.get().load(R.drawable.ic_energy).placeholder(R.drawable.ic_energy).into(imageView);
                    unit = " kJ";
                    break;
                case "sodium":
                    Picasso.get().load(R.drawable.ic_sodium).placeholder(R.drawable.ic_sodium).into(imageView);
                    unit = " g";
                    break;
                case "sugars":
                    Picasso.get().load(R.drawable.ic_sugar).placeholder(R.drawable.ic_sugar).into(imageView);
                    unit = " g";
                    break;
                case "proteins":
                    Picasso.get().load(R.drawable.ic_protein).placeholder(R.drawable.ic_protein).into(imageView);
                    unit = " g";
                    break;
                case "carbohydrates":
                    Picasso.get().load(R.drawable.ic_carbohydrate).placeholder(R.drawable.ic_carbohydrate).into(imageView);
                    unit = " g";
                    break;
                case "saturated-fat":
                    Picasso.get().load(R.drawable.ic_saturated).placeholder(R.drawable.ic_saturated).into(imageView);
                    unit = " g";
                    name = "sat-fat";
                    break;
                case "salt":
                    Picasso.get().load(R.drawable.ic_salt).placeholder(R.drawable.ic_salt).into(imageView);
                    unit = " g";
                    break;
                case "fat":
                    Picasso.get().load(R.drawable.ic_fat).placeholder(R.drawable.ic_fat).into(imageView);
                    unit = " g";
                    break;
            }
            String message = array.getAllNumber() + unit;
            tvName.setText(name);
            tvNum.setText(message);
        }else {
            gridView = convertView;
        }
        return gridView;
    }
}
