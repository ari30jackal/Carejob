package com.carefast.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.carefast.Login.R;
import com.carefast.activity.JadwalInterviewActivity;
import com.carefast.adapter.ListInterviewAdapter;
import com.carefast.adapter.NotifAdapter;
import com.carefast.apihelper.BaseApiService;
import com.carefast.apihelper.UtilsApi;
import com.carefast.contract.AppException;
import com.carefast.contract.PrefContract;
import com.carefast.contract.SecuredPreference;
import com.carefast.model.InterviewItem;
import com.carefast.model.ListInterviewResponse;
import com.carefast.model.NotificationItem;
import com.carefast.model.NotificationResponse;
import com.carefast.register.AdapterOnItemClickListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NotifFragment extends Fragment {
SecuredPreference pref;
BaseApiService mApiService;
String user_id;
RecyclerView rvnotif;
    RecyclerView.LayoutManager layoutManager3;
NotifAdapter notifAdapter;
TextView statuskosong;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notif, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
rvnotif = view.findViewById(R.id.rv_notif);
        mApiService = UtilsApi.getAPIService();
        rvnotif.setHasFixedSize(true);
        statuskosong = view.findViewById(R.id.statuskosong);
        layoutManager3 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvnotif.setLayoutManager(layoutManager3);
        pref = new SecuredPreference(getActivity(), PrefContract.PREF_EL);
        try {

            user_id = pref.getString(PrefContract.user_id, "");
        } catch (AppException e) {
            e.printStackTrace();
        }
        mApiService.getnotif(user_id).enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                if (response.isSuccessful()) {
                    List<NotificationItem> notItems = response.body().getNotification();
                    notifAdapter = new NotifAdapter(getActivity(),notItems, genProductAdapterListener());
                    rvnotif.setAdapter(notifAdapter);
                    notifAdapter.notifyDataSetChanged();
                    if (notItems.size()==0){

                        statuskosong.setVisibility(View.VISIBLE);
                    }
                    else {
                        statuskosong.setVisibility(View.INVISIBLE);
                    }
                } else {

                    Toast.makeText(getActivity(), "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {

                Toast.makeText(getActivity(), "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }





    private void getnot() {


        mApiService.getnotif(user_id).enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                if (response.isSuccessful()) {
                    List<NotificationItem> notItems = response.body().getNotification();
                    notifAdapter = new NotifAdapter(getActivity(),notItems, genProductAdapterListener());
                    rvnotif.setAdapter(notifAdapter);
                    notifAdapter.notifyDataSetChanged();
                } else {

                    Toast.makeText(getActivity(), "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {

                Toast.makeText(getActivity(), "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private AdapterOnItemClickListener genProductAdapterListener() {
        return new AdapterOnItemClickListener() {
            @Override
            public void onClick(View view, int position) {

            }
        };
    }

}