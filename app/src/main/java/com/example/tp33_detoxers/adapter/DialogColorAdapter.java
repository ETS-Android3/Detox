package com.example.tp33_detoxers.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.tp33_detoxers.R;
import com.example.tp33_detoxers.model.IngredientAll;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

public class DialogColorAdapter extends BaseAdapter {
    private Context context;
    private String[] color;

    public DialogColorAdapter(Context context, String[] color) {
        this.context = context;
        this.color = color;
    }

    @Override
    public int getCount() {
        return color.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listView;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            listView = new View(context);
            listView = inflater.inflate(R.layout.recycview_toxins, null);
            String colorName = color[position];
            ImageView imageView = listView.findViewById(R.id.rv_circle);
            TextView textView = listView.findViewById(R.id.rv_quantity);
            String message = "";
            switch (colorName){
                case "red":
                    Picasso.get().load(R.drawable.redcircle).placeholder(R.drawable.redcircle).into(imageView);
                    message = "-- The ingredient is in high quantity and may exacerbate your condition\n ";
                    break;
                case "yellow":
                    Picasso.get().load(R.drawable.yellowcircle).placeholder(R.drawable.yellowcircle).into(imageView);
                    message = "-- The ingredient is in moderate quantity and should be portioned appropriately\n ";
                    break;
                case "green":
                    Picasso.get().load(R.drawable.greencircle).placeholder(R.drawable.greencircle).into(imageView);
                    message = "-- The ingredient is in low quantity and is considered a healthy amount\n ";
                    break;
            }
            textView.setText(message);
        }else{
            listView = convertView;
        }
        return listView;
    }
}
