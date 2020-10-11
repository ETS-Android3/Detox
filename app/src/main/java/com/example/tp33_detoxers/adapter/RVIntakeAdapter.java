package com.example.tp33_detoxers.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp33_detoxers.MainActivity;
import com.example.tp33_detoxers.R;
import com.example.tp33_detoxers.model.IntakeProduct;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RVIntakeAdapter extends RecyclerView.Adapter<RVIntakeAdapter.ViewHolder> {
    private List<IntakeProduct> intakeProductList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView productView;

        private ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.rv_intakeImage);
            productView = itemView.findViewById(R.id.rv_intakeName);
        }
    }

    public RVIntakeAdapter(List<IntakeProduct> intakeProducts) {
        intakeProductList = intakeProducts;
    }

    public void addIntakes(List<IntakeProduct> intakes){
        intakeProductList = intakes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RVIntakeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the view from an XML layout file
        View intakeView = inflater.inflate(R.layout.recycview_intake, parent, false);
        // construct the view holder with the new view
        return new ViewHolder(intakeView);
    }

    @Override
    public void onBindViewHolder(@NonNull RVIntakeAdapter.ViewHolder holder, int position) {
        final IntakeProduct intake = intakeProductList.get(position);
        TextView tvName = holder.productView;
        ImageView imageView = holder.imageView;
        tvName.setText(intake.getpName());
        String url = intake.getpUrl();
        Picasso.get().load(url).into(imageView);
    }

    @Override
    public int getItemCount() {
        return intakeProductList.size();
    }
}
