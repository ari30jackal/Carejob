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
import com.carefast.adapter.AdvertisementAdapter;
import com.carefast.adapter.StatusLamaranAdapter;
import com.carefast.apihelper.BaseApiService;
import com.carefast.apihelper.UtilsApi;
import com.carefast.contract.AppException;
import com.carefast.contract.PrefContract;
import com.carefast.contract.SecuredPreference;
import com.carefast.model.AdvertisementItem;
import com.carefast.model.AdvertisementResponse;
import com.carefast.model.ListLamaranItem;
import com.carefast.model.StatusLamaranResponse;
import com.carefast.register.AdapterOnItemClickListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobFragment extends Fragment {
RecyclerView rv_stat;
    RecyclerView.LayoutManager layoutManager3;
    BaseApiService mApiService;
    private StatusLamaranAdapter statusLamaranAdapter;
    SecuredPreference pref;
    String user_id;
    TextView statuskosong;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_job, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pref = new SecuredPreference(getActivity(), PrefContract.PREF_EL);
        try {

            user_id = pref.getString(PrefContract.user_id, "");
        } catch (AppException e) {
            e.printStackTrace();
        }
        statuskosong = view.findViewById(R.id.statuskosong);
        rv_stat = view.findViewById(R.id.rv_stat);
        rv_stat.setHasFixedSize(true);
        layoutManager3 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_stat.setLayoutManager(layoutManager3);
        mApiService = UtilsApi.getAPIService();
   getstatusLamaran();

    }
    private void getstatusLamaran() {

        mApiService.statuslamaran(user_id).enqueue(new Callback<StatusLamaranResponse>() {
            @Override
            public void onResponse(Call<StatusLamaranResponse> call, Response<StatusLamaranResponse> response) {
                if (response.isSuccessful()) {
                    List<ListLamaranItem> listLamaranItems = response.body().getListLamaran();
                    statusLamaranAdapter = new StatusLamaranAdapter(getActivity(), listLamaranItems, genProductAdapterListener());
                    rv_stat.setAdapter(statusLamaranAdapter);
                    statusLamaranAdapter.notifyDataSetChanged();

                    if (listLamaranItems.size()==0){

                        statuskosong.setVisibility(View.VISIBLE);
                    }
                    else{

                        statuskosong.setVisibility(View.INVISIBLE);
                    }



                } else {

                    Toast.makeText(getActivity(), "Gagal mengambil data list", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StatusLamaranResponse> call, Throwable t) {

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