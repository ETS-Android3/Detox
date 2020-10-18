package com.example.tp33_detoxers.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.app.abby.xbanner.AbstractUrlLoader;
import com.app.abby.xbanner.XBanner;
import com.example.tp33_detoxers.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class HomeFragment extends Fragment {
    private ArrayList<String> urls = new ArrayList<>();
    private ArrayList<String> titles = new ArrayList<>();
    private XBanner xBanner;

    public HomeFragment() {}

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View homeView = inflater.inflate(R.layout.fragment_home, container, false);
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1603032225212&di=fa634a553bfb857807957a31ca1b3abf&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20171018%2Fd7b5f48a975c484aa8a28918661a9c62.jpeg");
        urls.add("https://images.unsplash.com/photo-1591981131824-5cce8b273bd0?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=750&q=80");
        urls.add("https://cdn.pixabay.com/photo/2018/03/09/17/39/paprika-3212137_960_720.jpg");
        titles.add("Health Concerns");
        titles.add("Ingredients List");
        titles.add("Healthy Food");
        xBanner = homeView.findViewById(R.id.xbanner);
        xBanner.setBannerTypes(XBanner.CIRCLE_INDICATOR_TITLE)
                .setImageScaleType(ImageView.ScaleType.FIT_XY)
                .setImageUrlsAndTitles(urls,titles)
                .setImageLoader(new AbstractUrlLoader() {
                    @Override
                    public void loadImages(Context context, String url, ImageView image) {
                        Picasso.get().load(url).into(image);
                    }

                    @Override
                    public void loadGifs(String url, GifImageView gifImageView, ImageView.ScaleType scaleType) {

                    }
                })
                .setDelay(8000)
                .isAutoPlay(true)
                .start();
        Button bt_health = homeView.findViewById(R.id.bt_tips);
        Button bt_about = homeView.findViewById(R.id.bt_aboutUs);
        Button bt_start = homeView.findViewById(R.id.btn_start);

        bt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new SearchFragment()).addToBackStack(null).commit();
            }
        });

        bt_health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new TipsCollectionFragment()).addToBackStack(null).commit();
            }
        });

        bt_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new AboutUsFragment()).addToBackStack(null).commit();
            }
        });
        return homeView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        xBanner.releaseBanner();
    }
}
