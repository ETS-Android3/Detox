package com.example.tp33_detoxers.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp33_detoxers.R;
import com.example.tp33_detoxers.model.IngredientDetail;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RVToxinAdapter extends RecyclerView.Adapter<RVToxinAdapter.ViewHolder> {
    private List<IngredientDetail> ingredientDetails;

    public RVToxinAdapter(List<IngredientDetail> products) {
        ingredientDetails = products;
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
        final IngredientDetail products = ingredientDetails.get(position);
        String name = products.getiName();
        String level = products.getiLevel();
        String quantity = products.getiQuantity();
        String text = quantity + " g " + name + " in " + level + " quantity";
        TextView tvName = holder.tv_name;
        tvName.setText(text);
        ImageView iv_circle = holder.imageView;
        //Picasso.get().load(R.drawable.greencircle).placeholder(R.drawable.greencircle).into(iv_circle);
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
        return ingredientDetails.size();
    }

    public void addLevel(List<IngredientDetail> products){
        ingredientDetails = products;
        notifyDataSetChanged();
    }
}
