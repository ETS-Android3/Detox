package com.example.tp33_detoxers.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.tp33_detoxers.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Objects;

public class ScanFragment extends Fragment {
    private TextView tv_scan;

    public ScanFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View scanView = inflater.inflate(R.layout.fragment_scan, container, false);
        tv_scan = scanView.findViewById(R.id.tv_scan);
        Button bt_scan = scanView.findViewById(R.id.bt_scan);
        Button bt_search = scanView.findViewById(R.id.bt_searchCode);

        bt_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScanButton(v);
            }
        });

        bt_search.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = tv_scan.getText().toString();
                if(a.equals("")){
                    Toast.makeText(getActivity(), "Please scan the barcode first", Toast.LENGTH_LONG).show();
                }else {
                    String id = tv_scan.getText().toString();
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("id", Context.MODE_PRIVATE);
                    SharedPreferences.Editor spEdit = sharedPreferences.edit();
                    spEdit.putString("id",id);
                    spEdit.apply();

                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new IngredientFragment()).addToBackStack(null).commit();
                }
            }
        }));
        return scanView;
    }

    public void ScanButton(View view){
        IntentIntegrator integrator = new IntentIntegrator(getActivity());
        integrator.setOrientationLocked(false);
        integrator.forSupportFragment(ScanFragment.this).initiateScan();
    }

    //get the scan results:
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_LONG).show();
            }else {
                tv_scan.setText(result.getContents());
            }
        }
    }
}
