package com.example.tp33_detoxers.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp33_detoxers.R;
import com.example.tp33_detoxers.model.IngredientDetail;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RVToxinAdapter extends RecyclerView.Adapter<RVToxinAdapter.ViewHolder> implements Filterable {
    private List<IngredientDetail> ingredientDetails; // original data
    private List<IngredientDetail> ingredientFiltered; // the copy one

    public RVToxinAdapter(List<IngredientDetail> products) {
        ingredientDetails = products;
        ingredientFiltered = new ArrayList<>(products);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView tv_name;

        private ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.rv_circle);
            tv_name = itemView.findViewById(R.id.rv_quantity);
        }
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        //Inflate the view from an XML layout file
        View toxinsView = inflater.inflate(R.layout.recycview_toxins, parent,false);
        //construct the view holder with the new view
        return new ViewHolder(toxinsView);
    }


    @Override
    public void onBindViewHolder(@NonNull RVToxinAdapter.ViewHolder holder, int position) {
        final IngredientDetail products = ingredientFiltered.get(position);
        String name = products.getiName();
        String quantity = products.getiQuantity();
        String level = "";
        switch (name){
            case "salt":
                if(Double.parseDouble(quantity) > 1.5){
                    level = "high";
                }else if(Double.parseDouble(quantity) < 0.3){
                    level = "low";
                }else{
                    level = "moderate";
                }
                break;
            case "sugars":
                if(Double.parseDouble(quantity) > 22.5){
                    level = "high";
                }else if(Double.parseDouble(quantity) < 5){
                    level = "low";
                }else{
                    level = "moderate";
                }
                break;
            case "saturated-fat":
                if(Double.parseDouble(quantity) > 5){
                    level = "high";
                }else if(Double.parseDouble(quantity) < 1.5){
                    level = "low";
                }else{
                    level = "moderate";
                }
                break;
            case "fat":
                if(Double.parseDouble(quantity) > 17.5){
                    level = "high";
                }else if(Double.parseDouble(quantity) < 3){
                    level = "low";
                }else{
                    level = "moderate";
                }
                break;
        }
//        String level = products.getiLevel();

        String text = quantity + " g " + name + " in " + level + " quantity";
        TextView tvName = holder.tv_name;
        tvName.setText(text);
        ImageView iv_circle = holder.imageView;
        switch (level){
            case "low":
                Picasso.get().load(R.drawable.greencircle).placeholder(R.drawable.greencircle).into(iv_circle);
                break;
            case "moderate":
                Picasso.get().load(R.drawable.yellowcircle).placeholder(R.drawable.yellowcircle).into(iv_circle);
                break;
            case  "high":
                Picasso.get().load(R.drawable.redcircle).placeholder(R.drawable.redcircle).into(iv_circle);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return ingredientFiltered.size();
    }

    public void addLevel(List<IngredientDetail> products){
        ingredientDetails = products;
        notifyDataSetChanged();
    }

    //filter the ingredient based on the illness
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        //run on background thread
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<IngredientDetail> toxins = new ArrayList<>();
            List<String> result = new ArrayList<>();

            switch (constraint.toString()){
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
                    break;
            }

            if (constraint.toString().equals("All")){
                toxins.addAll(ingredientDetails);
            }else {
                for (IngredientDetail item: ingredientDetails){
                    for (String value: result){
                        if(item.getiName().equals(value)){
                            toxins.add(item);
                        }
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = toxins;
            return filterResults;
        }

        //runs on a ui thread
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ingredientFiltered.clear();
            ingredientFiltered =(ArrayList<IngredientDetail>) results.values;
            notifyDataSetChanged();
        }
    };
}
