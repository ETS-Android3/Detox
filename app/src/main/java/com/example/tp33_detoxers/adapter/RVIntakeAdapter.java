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
import com.example.tp33_detoxers.model.IntakeProduct;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RVIntakeAdapter extends RecyclerView.Adapter<RVIntakeAdapter.ViewHolder> {
    private List<IntakeProduct> intakeProductList;
    private double[] num;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView productView;
        private ImageView minus;
        private ImageView plus;
        private TextView num;

        private ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.rv_intakeImage);
            productView = itemView.findViewById(R.id.rv_intakeName);
            minus = itemView.findViewById(R.id.btn_minus);
            plus = itemView.findViewById(R.id.btn_plus);
            num = itemView.findViewById(R.id.tv_pieces);
        }
    }

    public RVIntakeAdapter(List<IntakeProduct> intakeProducts) {
        intakeProductList = intakeProducts;
        num = new double[50];
        Arrays.fill(num,0);
    }

    public void addIntakes(List<IntakeProduct> intakes){
        intakeProductList = intakes;
        notifyDataSetChanged();
    }

    public double[] getNum(){
        return num;
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
        TextView tvNum = holder.num;
        ImageView imMinus = holder.minus;
        ImageView imPlus = holder.plus;
        tvName.setText(intake.getpName());
        String url = intake.getpUrl();
        Picasso.get().load(url).into(imageView);
        java.text.DecimalFormat myFormat = new java.text.DecimalFormat("0.0");
        final double[] number = {Double.parseDouble(tvNum.getText().toString())};
        if (number[0] == 0 ){
            imMinus.setClickable(false);
        }
        imMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (number[0] > 0 ){
                    number[0] -= 0.5;
                    num[position] = number[0];
                    String result = myFormat.format(number[0]);
                    tvNum.setText(result);
                }
            }
        });

        imPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number[0] += 0.5;
                num[position] = number[0];
                String result = myFormat.format(number[0]);
                tvNum.setText(result);
            }
        });

    }

    @Override
    public int getItemCount() {
        return intakeProductList.size();
    }
}
