package com.example.tp33_detoxers.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp33_detoxers.R;
import com.example.tp33_detoxers.fragment.IngredientFragment;
import com.example.tp33_detoxers.model.IngredientDetail;
import com.example.tp33_detoxers.model.SearchResult;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class RVSearchAdapter extends RecyclerView.Adapter<RVSearchAdapter.ViewHolder> {
    private List<SearchResult> searchResults;

    public RVSearchAdapter(List<SearchResult> products) {
        searchResults = products;
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

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        //Inflate the view from an XML layout file
        View searchView = inflater.inflate(R.layout.recycview_search, parent,false);
        //construct the view holder with the new view
        return new ViewHolder(searchView);
    }

    @Override
    public void onBindViewHolder(@NonNull RVSearchAdapter.ViewHolder holder, int position) {
        final SearchResult products = searchResults.get(position);
        TextView tvName = holder.nameTextView;
        tvName.setText(products.getpName());
        ImageView imageView = holder.imageView;
        String url = products.getpUrl();
        Picasso.get().load(url).into(imageView);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity)v.getContext();
                String id = products.getpId();
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
        return searchResults.size();
    }

    public void addProducts(List<SearchResult> products){
        searchResults = products;
        notifyDataSetChanged();
    }
}
