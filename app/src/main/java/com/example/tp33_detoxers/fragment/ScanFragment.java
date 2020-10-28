package com.example.tp33_detoxers.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.tp33_detoxers.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class ScanFragment extends Fragment {

    public ScanFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View scanView = inflater.inflate(R.layout.fragment_scan, container, false);
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().getItem(3).setChecked(true);
        Button bt_scan = scanView.findViewById(R.id.bt_scan);
        //Button bt_search = scanView.findViewById(R.id.bt_searchCode);
        ImageView ivScan = scanView.findViewById(R.id.iv_scan);
//        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
//        ivScan.getLayoutParams().height = (int) (width * 0.67);
        Picasso.get().load(R.drawable.scan).fit().into(ivScan);
        MaterialToolbar toolbar = scanView.findViewById(R.id.toolbar_scan);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objects.requireNonNull(getActivity()).onBackPressed();
            }
        });

        bt_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScanButton(v);
            }
        });

        String[] items = new String[]{"1. Click the 'Scan Barcode' button","2. Scan the barcode of the product","3. Once successful, you will see the product details!"};
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_help) {
                    AlertDialog builder = new MaterialAlertDialogBuilder(getContext())
                            .setTitle("How to use the scan function")
                            .setItems(items, null)
                            .setPositiveButton("Back", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).create();
                    builder.show();
                }
                return true;
            }
        });


//        bt_search.setOnClickListener((new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String a = tv_scan.getText().toString();
//                if(a.equals("")){
//                    Snackbar.make(bt_search, "Please scan the barcode first", Snackbar.LENGTH_LONG).show();
//                }else {
//                    String id = tv_scan.getText().toString();
//                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("id", Context.MODE_PRIVATE);
//                    SharedPreferences.Editor spEdit = sharedPreferences.edit();
//                    spEdit.putString("id",id);
//                    spEdit.apply();
//
//                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new IngredientFragment()).addToBackStack(null).commit();
//                }
//            }
//        }));
        return scanView;
    }

    public void ScanButton(View view){
//        IntentIntegrator integrator = new IntentIntegrator(getActivity());
//        integrator.setOrientationLocked(false);
//        integrator.forSupportFragment(ScanFragment.this).initiateScan();
        IntentIntegrator.forSupportFragment(ScanFragment.this).initiateScan();
    }

    //get the scan results:
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Snackbar.make(getView(), "Cancelled", Snackbar.LENGTH_LONG).show();
            }else {
                String id = result.getContents();
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("id", Context.MODE_PRIVATE);
                SharedPreferences.Editor spEdit = sharedPreferences.edit();
                spEdit.putString("id",id);
                spEdit.apply();

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new IngredientFragment()).addToBackStack(null).commit();
            }
        }
    }
}
