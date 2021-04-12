package com.carefast.qr;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.carefast.Login.R;
import com.carefast.home.HomeeActivity;


public class indialogFragment extends DialogFragment {
    Button cls;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_home_ads, null);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        cls = view.findViewById(R.id.closeads);

        cls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              dismiss();
              getActivity().startActivity(new Intent(getActivity(), HomeeActivity.class));
              getActivity().finish();



            }
        });



    }
}
