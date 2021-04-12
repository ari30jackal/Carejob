package com.carefast.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.carefast.Login.R;
import com.carefast.contract.AppException;
import com.carefast.contract.PrefContract;
import com.carefast.contract.SecuredPreference;

import io.github.kexanie.library.MathView;

public class DeskripsiDetailFragment extends Fragment {
MathView description;
SecuredPreference pref;
String desc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_deskripsi_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
description = view.findViewById(R.id.desc_advertisement);
pref = new SecuredPreference(getActivity(), PrefContract.PREF_EL);
        try {
            desc = pref.getString(PrefContract.desc_advertisement,"");
        } catch (AppException e) {
            e.printStackTrace();
        }
        description.setText(desc);









    }


}