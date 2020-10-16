package com.example.tp33_detoxers.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp33_detoxers.R;
import com.example.tp33_detoxers.fragment.IngredientFragment;
import com.example.tp33_detoxers.model.IntakeProduct;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
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
        private TextView piece;

        private ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.rv_intakeImage);
            productView = itemView.findViewById(R.id.rv_intakeName);
            minus = itemView.findViewById(R.id.btn_minus);
            plus = itemView.findViewById(R.id.btn_plus);
            piece = itemView.findViewById(R.id.tv_pieces);
        }
    }

    public RVIntakeAdapter(List<IntakeProduct> intakeProducts) {
        intakeProductList = intakeProducts;
        num = new double[50];
        Arrays.fill(num,1.0);
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final IntakeProduct intake = intakeProductList.get(position);
        TextView tvName = holder.productView;
        ImageView imageView = holder.imageView;
        TextView tvPiece = holder.piece;
        ImageView imMinus = holder.minus;
        ImageView imPlus = holder.plus;
        tvName.setText(intake.getpName());
        double iniPiece = num[position];
        tvPiece.setText(String.valueOf(iniPiece));
        String url = intake.getpUrl();
        Picasso.get().load(url).into(imageView);
        DecimalFormat myFormat = new DecimalFormat("0.0");
        //final double[] number = {Double.parseDouble(tvPiece.getText().toString())};
        //num[position] = number[0];
        if (num[position] == 0 ){
            imMinus.setClickable(false);
        }
        imMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num[position] > 0 ){
                    num[position] -= 0.5;
                    //num[position] = number[0];
                    String result = myFormat.format(num[position]);
                    tvPiece.setText(result);
                }
            }
        });

        imPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num[position] += 0.5;
                //num[position] = number[0];
                String result = myFormat.format(num[position]);
                tvPiece.setText(result);
            }
        });

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity)v.getContext();
                String id = intake.getpId();
                SharedPreferences sharedPreferences = activity.getSharedPreferences("id", Context.MODE_PRIVATE);
                SharedPreferences.Editor spEdit = sharedPreferences.edit();
                spEdit.putString("id",id);
                spEdit.apply();

                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, new IngredientFragment()).addToBackStack(null).commit();
            }
        };

        holder.imageView.setOnClickListener(onClickListener);
        holder.productView.setOnClickListener(onClickListener);

    }

    @Override
    public int getItemCount() {
        return intakeProductList.size();
    }
}
