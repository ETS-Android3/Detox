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
import com.example.tp33_detoxers.model.ReportRecord;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RVReportAdapter extends RecyclerView.Adapter<RVReportAdapter.ViewHolder> {
    private List<ReportRecord> reportRecords;

    public RVReportAdapter(List<ReportRecord> reportRecords) {
        this.reportRecords = reportRecords;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the view from an XML layout file
        View reportView = inflater.inflate(R.layout.recyceview_report, parent, false);
        // construct the view holder with the new view
        return new ViewHolder(reportView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int rowPos = holder.getAdapterPosition();
        TextView tvName = holder.tv_name;
        TextView tvUnits = holder.tv_units;
        ImageView imageView = holder.imageView;
        if(rowPos == 0){
            tvName.setText("Ingredient");
            tvUnits.setText("Total units");
        }else{
            final ReportRecord report = reportRecords.get(rowPos-1);
            String name = report.getrName();
            double quantity = Double.parseDouble(report.getrUnits());
            String levels = report.getrLevel();
            switch (levels){
                case "low":
                    Picasso.get().load(R.drawable.greencircle).placeholder(R.drawable.greencircle).into(imageView);
                    break;
                case "moderate":
                    Picasso.get().load(R.drawable.yellowcircle).placeholder(R.drawable.yellowcircle).into(imageView);
                    break;
                case  "high":
                    Picasso.get().load(R.drawable.redcircle).placeholder(R.drawable.redcircle).into(imageView);
                    break;
            }
            String quantity_df = new java.text.DecimalFormat("#0.###").format(quantity);
            tvName.setText(name);
            tvUnits.setText(quantity_df + " g");
        }
    }

    @Override
    public int getItemCount() {
        return reportRecords.size()+1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView tv_name;
        private TextView tv_units;

        private ViewHolder(View itemView){
            super(itemView);

            imageView = itemView.findViewById(R.id.rv_circle);
            tv_name = itemView.findViewById(R.id.report_title);
            tv_units = itemView.findViewById(R.id.report_quantity);
        }
    }

    public void addLevel(List<ReportRecord> records){
        reportRecords = records;
        notifyDataSetChanged();
    }

}
