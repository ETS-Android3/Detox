package com.example.tp33_detoxers.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp33_detoxers.R;
import com.example.tp33_detoxers.fragment.IngredientFragment;
import com.example.tp33_detoxers.model.CategoryResult;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RVCategoryAdapter extends RecyclerView.Adapter<RVCategoryAdapter.ViewHolder> implements Filterable{
    private List<CategoryResult> categoryResults;  // original data
    private List<CategoryResult> categoryFiltered;  // the copy one
    private Context context;

    public RVCategoryAdapter(List<CategoryResult> categoryList) {
        categoryResults = categoryList;
        categoryFiltered = new ArrayList<>(categoryList);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView nameTextView;


        private ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.rv_image);
            nameTextView = itemView.findViewById(R.id.rv_name);
        }
    }

    @NonNull
    @Override
    public RVCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);
        //Inflate the view from an XML layout file
        View categoryView = inflater.inflate(R.layout.recycview_search, parent,false);
        //construct the view holder with the new view
        return new RVCategoryAdapter.ViewHolder(categoryView);
    }

    @Override
    public void onBindViewHolder(@NonNull RVCategoryAdapter.ViewHolder holder, int position) {
        final CategoryResult products = categoryFiltered.get(position);
        TextView tvName = holder.nameTextView;
        tvName.setText(products.getcName());
        ImageView imageView = holder.imageView;
        String url = products.getcUrl();
        Picasso.get().load(url).into(imageView);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity)v.getContext();
                String id = products.getcId();
                SharedPreferences sharedPreferences = activity.getSharedPreferences("id", Context.MODE_PRIVATE);
                SharedPreferences.Editor spEdit = sharedPreferences.edit();
                spEdit.putString("id",id);
                spEdit.apply();

                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, new IngredientFragment()).addToBackStack(null).commit();
            }
        };

        holder.nameTextView.setOnClickListener(onClickListener);
        holder.imageView.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return categoryFiltered.size();
    }

    public void addCategory(List<CategoryResult> products){
        categoryResults = products;
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<CategoryResult> categories = new ArrayList<>();

            String[] ingredient = constraint.toString().split(",");
            switch (ingredient[0]){
                case "Fat":
                    switch (ingredient[1]){
                        case "Low":
                            for(CategoryResult item: categoryResults){
                                if(Double.parseDouble(item.getqFat()) < 3){
                                    categories.add(item);
                                }
                            }
                            break;
                        case "Moderate":
                            for(CategoryResult item: categoryResults){
                                if(Double.parseDouble(item.getqFat()) >= 3 && Double.parseDouble(item.getqFat()) <= 17.5){
                                    categories.add(item);
                                }
                            }
                            break;
                        case "High":
                            for(CategoryResult item: categoryResults){
                                if(Double.parseDouble(item.getqFat()) > 17.5){
                                    categories.add(item);
                                }
                            }
                            break;
                    }
                    break;
                case "Salt":
                    switch (ingredient[1]){
                        case "Low":
                            for(CategoryResult item: categoryResults){
                                if(Double.parseDouble(item.getqSalt()) < 0.3){
                                    categories.add(item);
                                }
                            }
                            break;
                        case "Moderate":
                            for(CategoryResult item: categoryResults){
                                if(Double.parseDouble(item.getqSalt()) >= 0.3 && Double.parseDouble(item.getqSalt()) <= 1.5){
                                    categories.add(item);
                                }
                            }
                            break;
                        case "High":
                            for(CategoryResult item: categoryResults){
                                if(Double.parseDouble(item.getqSalt()) > 1.5){
                                    categories.add(item);
                                }
                            }
                            break;
                    }
                    break;
                case "Saturated-fat":
                    switch (ingredient[1]){
                        case "Low":
                            for(CategoryResult item: categoryResults){
                                if(Double.parseDouble(item.getqSaturated()) < 1.5){
                                    categories.add(item);
                                }
                            }
                            break;
                        case "Moderate":
                            for(CategoryResult item: categoryResults){
                                if(Double.parseDouble(item.getqSaturated()) >= 1.5 && Double.parseDouble(item.getqSaturated()) <= 5){
                                    categories.add(item);
                                }
                            }
                            break;
                        case "High":
                            for(CategoryResult item: categoryResults){
                                if(Double.parseDouble(item.getqSaturated()) > 5){
                                    categories.add(item);
                                }
                            }
                            break;
                    }
                    break;
                case "Sugars":
                    switch (ingredient[1]){
                        case "Low":
                            for(CategoryResult item: categoryResults){
                                if(Double.parseDouble(item.getqSugar()) < 5){
                                    categories.add(item);
                                }
                            }
                            break;
                        case "Moderate":
                            for(CategoryResult item: categoryResults){
                                if(Double.parseDouble(item.getqSugar()) >= 5 && Double.parseDouble(item.getqSugar()) <= 22.5){
                                    categories.add(item);
                                }
                            }
                            break;
                        case "High":
                            for(CategoryResult item: categoryResults){
                                if(Double.parseDouble(item.getqSugar()) > 22.5){
                                    categories.add(item);
                                }
                            }
                            break;
                    }
                    break;
                case "All":
                    switch (ingredient[1]) {
                        case "Low":
                            for (CategoryResult item : categoryResults) {
                                if (Double.parseDouble(item.getqSugar()) < 5 && Double.parseDouble(item.getqSaturated()) < 1.5
                                        && Double.parseDouble(item.getqSalt()) < 0.3 && Double.parseDouble(item.getqFat()) < 3) {
                                    categories.add(item);
                                }
                            }
                            break;
                        case "Moderate":
                            for (CategoryResult item : categoryResults) {
                                if ((Double.parseDouble(item.getqSugar()) >= 5 && Double.parseDouble(item.getqSugar()) <= 22.5) &&
                                        (Double.parseDouble(item.getqFat()) >= 3 && Double.parseDouble(item.getqFat()) <= 17.5) &&
                                        (Double.parseDouble(item.getqSaturated()) >= 1.5 && Double.parseDouble(item.getqSaturated()) <= 5) &&
                                        (Double.parseDouble(item.getqSalt()) >= 0.3 && Double.parseDouble(item.getqSalt()) <= 1.5)) {
                                    categories.add(item);
                                }
                            }
                            break;
                        case "High":
                            for (CategoryResult item : categoryResults) {
                                if (Double.parseDouble(item.getqSugar()) > 22.5 && Double.parseDouble(item.getqFat()) > 17.5
                                        && Double.parseDouble(item.getqSaturated()) > 5 && Double.parseDouble(item.getqSalt()) > 1.5) {
                                    categories.add(item);
                                }
                            }
                            break;
                        case "All":
                            categories.addAll(categoryResults);
                            break;
                    }

            }
            String[] items = new String[]{"Try filtering different criteria"};
            FilterResults filterResults = new FilterResults();
            filterResults.values = categories;
            if (categories.size() == 0) {
                AlertDialog builder = new MaterialAlertDialogBuilder(context)
                        .setTitle("No Result Found!")
                        .setItems(items, null)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).create();
                builder.show();

            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            categoryFiltered.clear();
            categoryFiltered =(ArrayList<CategoryResult>) results.values;
            notifyDataSetChanged();
        }
    };


}
