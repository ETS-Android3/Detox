package com.example.tp33_detoxers.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp33_detoxers.R;
import com.example.tp33_detoxers.adapter.RVReportAdapter;
import com.example.tp33_detoxers.model.ReportRecord;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ReportFragment extends Fragment {
    private List<ReportRecord> reportRecords;
    private String[] rName = new String[] {"sugars","salt","saturated-fat","fat"};

    private RecyclerView reportRecycler;
    private RecyclerView.LayoutManager layoutManager;
    private RVReportAdapter reportAdapter;

    public ReportFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View reportView = inflater.inflate(R.layout.fragment_myreport, container, false);

        SharedPreferences report = getActivity().getSharedPreferences("list", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = report.getString("list", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        ArrayList<String> array = gson.fromJson(json, type);


        ImageView imageView = reportView.findViewById(R.id.iv_report);
        TextView tv_suggest = reportView.findViewById(R.id.tv_suggest);
        reportRecycler = reportView.findViewById(R.id.rv_report);
        reportRecords = new ArrayList<>();
        reportAdapter = new RVReportAdapter(reportRecords);
        Button bt_compare = reportView.findViewById(R.id.bt_compare);
        Button bt_save = reportView.findViewById(R.id.bt_save);
        ArrayList<String> arrayLevel = new ArrayList<>();
        int risk = 0;
        String suggestion = "";

        for(int i = 0; i < rName.length; i++){
            String name = rName[i];
            String quantity = array.get(i);
            String levels = "";
            switch (name){
                case "salt":
                    if(Double.parseDouble(quantity) > 6){
                        levels = "high";
                    }else if(Double.parseDouble(quantity) < 4.8){
                        levels = "low";
                    }else{
                        levels = "moderate";
                    }
                    break;
                case "sugars":
                    if(Double.parseDouble(quantity) > 90){
                        levels = "high";
                    }else if(Double.parseDouble(quantity) < 72){
                        levels = "low";
                    }else{
                        levels = "moderate";
                    }
                    break;
                case "saturated-fat":
                    if(Double.parseDouble(quantity) > 20){
                        levels = "high";
                    }else if(Double.parseDouble(quantity) < 16){
                        levels = "low";
                    }else{
                        levels = "moderate";
                    }
                    break;
                case "fat":
                    if(Double.parseDouble(quantity) > 70){
                        levels = "high";
                    }else if(Double.parseDouble(quantity) < 56){
                        levels = "low";
                    }else{
                        levels = "moderate";
                    }
                    break;
            }
            arrayLevel.add(levels);
            double percentage = Double.parseDouble(array.get(i))/Double.parseDouble(array.get(array.size()-1));
            saveReport(name, quantity, Double.toString(percentage),levels);
        }

        for(int i = 0; i < arrayLevel.size(); i++){
            if(arrayLevel.get(i).equals("high")){
                risk = -1;
                break;
            }else if(arrayLevel.get(i).equals("moderate")){
                risk = 1;
            }
        }
        if (risk == 1){
            Picasso.get().load(R.drawable.ic_report_problem_24px).placeholder(R.drawable.ic_report_problem_24px).into(imageView);
            suggestion = "Be Care of Toxins";
            tv_suggest.setText(suggestion);
            tv_suggest.setTextColor(Color.parseColor("#F9BF45"));
        }else if(risk == 0){
            Picasso.get().load(R.drawable.ic_verified_user_24px).placeholder(R.drawable.ic_verified_user_24px).into(imageView);
            suggestion = "Healthy Meal";
            tv_suggest.setText(suggestion);
            tv_suggest.setTextColor(Color.parseColor("#00AA90"));
        }else {
            Picasso.get().load(R.drawable.ic_not_interested_24px).placeholder(R.drawable.ic_not_interested_24px).into(imageView);
            suggestion = "Too Much Toxins";
            tv_suggest.setText(suggestion);
            tv_suggest.setTextColor(Color.parseColor("#C73E3A"));
        }

        reportRecycler.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        reportRecycler.setAdapter(reportAdapter);
        layoutManager = new LinearLayoutManager(getActivity());
        reportRecycler.setLayoutManager(layoutManager);

        return reportView;
    }

    public void saveReport(String name, String units, String percentage,String level){
        ReportRecord record = new ReportRecord(name, units, percentage, level);
        reportRecords.add(record);
        reportAdapter.addLevel(reportRecords);
    }
}
