package com.carefast.fragment;

import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.carefast.Login.R;


public class ScanFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scan, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//
//        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(getActivity().this);
//
//        integrator.setOrientationLocked(false);
//        integrator.setPrompt("Scan QR code");
//        integrator.setBeepEnabled(false);
//        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
//
//
//        integrator.initiateScan();

    }

}