package com.example.tp33_detoxers.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.tp33_detoxers.R;
import com.example.tp33_detoxers.SearchAPI;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.json.JSONObject;

import java.util.List;

public class TipsObjectFragment extends Fragment {
    private SearchAPI searchAPI;
    private TextView tv_tips;
    private TextView tv_title;

    public TipsObjectFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View tipsView = inflater.inflate(R.layout.fragment_tips_object, container, false);
        searchAPI = new SearchAPI();
        tv_tips = tipsView.findViewById(R.id.tv_tips);
        tv_title = tipsView.findViewById(R.id.tv_tipsTitle);
        ChipGroup chipGroup = tipsView.findViewById(R.id.chip_tips);
        IllnessTips illnessTips = new IllnessTips();

        Chip chip = (Chip) chipGroup.getChildAt(0);
        final String[] type = {chip.getText().toString()};
        Bundle args = getArguments();
        String illness = args.getString("type");
        tv_title.setText(type[0]);
        illnessTips.execute(illness,type[0]);

        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                Chip chips = chipGroup.findViewById(checkedId);
                type[0] = chips.getText().toString();
                tv_title.setText(type[0]);
                IllnessTips illnessTips = new IllnessTips();
                illnessTips.execute(illness,type[0]);
            }
        });

        return tipsView;
    }

    public class IllnessTips extends AsyncTask<String, Void, String[]> {

        @Override
        protected String[] doInBackground(String... strings) {
            String result = SearchAPI.searchTips(strings[0]);
            String[] tips = null;

            try{
                JSONObject j = new JSONObject(SearchAPI.getTips(result));

                String type = j.getString(strings[1]);
                String type_1 = type.replaceAll("\\[|\\]","");
                tips = type_1.split(",");

            }
            catch (Exception e){
                e.printStackTrace();
            }
            return tips;
        }

        @Override
        protected void onPostExecute(String[] l){
            String result = l[0];
            for(int i = 1; i < l.length; i++){
                if(!Character.isDigit(l[i].trim().charAt(0))){
                    result += l[i];
                }else{
                    result += "\n\n" + l[i].trim();
                }
            }
            tv_tips.setText(result);
        }
    }
}
