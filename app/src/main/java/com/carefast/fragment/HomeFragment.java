package com.carefast.fragment;

import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.carefast.Login.R;
import com.carefast.activity.JadwalInterviewActivity;
import com.carefast.activity.RekomendasiLokerActivity;
import com.carefast.activity.SearchResultActivity;
import com.carefast.activity.StatusLamaranActivity;
import com.carefast.adapter.AdvertisementAdapter;
import com.carefast.adapter.ListInterviewAdapter;
import com.carefast.adapter.StatusLamaranAdapter;
import com.carefast.apihelper.BaseApiService;
import com.carefast.apihelper.UtilsApi;
import com.carefast.contract.AppException;
import com.carefast.contract.PrefContract;
import com.carefast.contract.SecuredPreference;
import com.carefast.home.HomeeActivity;
import com.carefast.home.Homepage;
import com.carefast.model.AdvertisementItem;
import com.carefast.model.AdvertisementResponse;
import com.carefast.model.InterviewItem;
import com.carefast.model.ListInterviewResponse;
import com.carefast.model.ListLamaranItem;
import com.carefast.model.StatusLamaranResponse;
import com.carefast.register.AdapterOnItemClickListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {
    RecyclerView rv_advertisement, rv_interview, rv_stat;
    RecyclerView.LayoutManager layoutManager, layoutManager2, layoutManager3;

    private AdvertisementAdapter adapter;
    private ListInterviewAdapter listInterviewAdapter;

    private StatusLamaranAdapter statusLamaranAdapter;
    SecuredPreference pref;
    BaseApiService mApiService;
    TextView tvusername, seeallrekomendasi, seeallschedule,seeallstatus;
    String firstname, user_id;
    ImageView btnsearch;
    EditText keyword;
    TextView recent1,recent2,recent3;
    TextView statuskosong,interviewkosong,lokerkosong;
    String temp1,temp2,temp3,temp4;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mApiService = UtilsApi.getAPIService();
        pref = new SecuredPreference(getActivity(), PrefContract.PREF_EL);
        rv_stat = view.findViewById(R.id.rv_stat);
        rv_stat.setHasFixedSize(true);
        layoutManager3 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_stat.setLayoutManager(layoutManager3);
        seeallstatus = view.findViewById(R.id.seeallstatus);
        rv_interview = view.findViewById(R.id.rv_schedule);
        seeallschedule = view.findViewById(R.id.seeallschedule);
        seeallrekomendasi = view.findViewById(R.id.seeallrekomendasi);
        rv_advertisement = view.findViewById(R.id.rv_advertisement);
        rv_advertisement.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rv_advertisement.setLayoutManager(layoutManager);
        btnsearch =view.findViewById(R.id.btn_search);
        keyword = view.findViewById(R.id.keyword);
        recent1 = view.findViewById(R.id.recent1);
        recent2 = view.findViewById(R.id.recent2);
        recent3 = view.findViewById(R.id.recent3);
        lokerkosong = view.findViewById(R.id.lokerkosong);
        interviewkosong = view.findViewById(R.id.interviewkosong);
        statuskosong = view.findViewById(R.id.statuskosong);
        try {
            temp3=pref.getString(PrefContract.recent3,"");
            temp2 = pref.getString(PrefContract.recent2,"");
            temp1 = pref.getString(PrefContract.recent1,"");
        } catch (AppException e) {
            e.printStackTrace();
        }

        if (temp3.equals("")|| temp3.equals(null)){
            recent3.setVisibility(View.INVISIBLE);
        }
        else{
            recent3.setVisibility(View.VISIBLE);
            recent3.setText(temp3);
        }

        if (temp2.equals("")|| temp2.equals(null)){
            recent2.setVisibility(View.INVISIBLE);
        }
        else{
            recent2.setVisibility(View.VISIBLE);
            recent2.setText(temp2);
        }
        if (temp1.equals("")|| temp1.equals(null)){
            recent1.setVisibility(View.INVISIBLE);
        }
        else{
            recent1.setVisibility(View.VISIBLE);
            recent1.setText(temp1);
        }








        btnsearch.setOnClickListener(v->{

            recentlogic();
            Intent intent = new Intent(getActivity(), SearchResultActivity.class);
            startActivity(intent);
            getActivity().finish();

        });
        rv_interview.setHasFixedSize(true);


        tvusername = view.findViewById(R.id.tv_username);

        layoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        try {
            firstname = pref.getString(PrefContract.user_first_name, "");
            user_id = pref.getString(PrefContract.user_id, "");
        } catch (AppException e) {
            e.printStackTrace();
        }







        rv_interview.setLayoutManager(layoutManager2);

        tvusername.setText("Halo, " + firstname);
        getAdvertisement();
        getstatusLamaran();
        getListInterview();
    }

    private void recentlogic() {
        try {
            temp3=pref.getString(PrefContract.recent3,"");
            temp2 = pref.getString(PrefContract.recent2,"");
            temp1 = pref.getString(PrefContract.recent1,"");
            recent3.setVisibility(View.INVISIBLE);
            recent2.setVisibility(View.INVISIBLE);
            recent1.setVisibility(View.INVISIBLE);

            if (temp3.equals(null) || temp3.equals("")){
                if (temp2.equals(null) || temp2.equals("")){
                    if (temp1.equals(null) || temp1.equals("")){
                        pref.put(PrefContract.recent3,temp2);
                        pref.put(PrefContract.recent2,temp1);
                        pref.put(PrefContract.recent1,keyword.getText().toString());
                        pref.put(PrefContract.keyword,keyword.getText().toString());



                        recent1.setVisibility(View.VISIBLE);

                        recent3.setText(temp2);
                        recent2.setText(temp1);
                        recent1.setText(keyword.getText().toString());
                    }
                    else {
                        pref.put(PrefContract.recent3,temp2);
                        pref.put(PrefContract.recent2,temp1);
                        pref.put(PrefContract.recent1,keyword.getText().toString());
                        pref.put(PrefContract.keyword,keyword.getText().toString());



                        recent2.setVisibility(View.VISIBLE);
                        recent1.setVisibility(View.VISIBLE);

                        recent3.setText(temp2);
                        recent2.setText(temp1);
                        recent1.setText(keyword.getText().toString());
                    }
                }
                else{
                    pref.put(PrefContract.recent3,temp2);
                    pref.put(PrefContract.recent2,temp1);
                    pref.put(PrefContract.recent1,keyword.getText().toString());
                    pref.put(PrefContract.keyword,keyword.getText().toString());


                    recent3.setVisibility(View.VISIBLE);
                    recent2.setVisibility(View.VISIBLE);
                    recent1.setVisibility(View.VISIBLE);

                    recent3.setText(temp2);
                    recent2.setText(temp1);
                    recent1.setText(keyword.getText().toString());
                }
            }
            else{
                pref.put(PrefContract.recent3,temp2);
                pref.put(PrefContract.recent2,temp1);
                pref.put(PrefContract.recent1,keyword.getText().toString());
                pref.put(PrefContract.keyword,keyword.getText().toString());


                recent3.setVisibility(View.VISIBLE);
                recent2.setVisibility(View.VISIBLE);
                recent1.setVisibility(View.VISIBLE);

                recent3.setText(temp2);
                recent2.setText(temp1);
                recent1.setText(keyword.getText().toString());
            }



        } catch (AppException e) {
            e.printStackTrace();
        }


    }


    private void getListInterview() {


        mApiService.listinterview(user_id).enqueue(new Callback<ListInterviewResponse>() {
            @Override
            public void onResponse(Call<ListInterviewResponse> call, Response<ListInterviewResponse> response) {
                if (response.isSuccessful()) {
                    List<InterviewItem> interviewItems = response.body().getInterview();
                    listInterviewAdapter = new ListInterviewAdapter(getActivity(), interviewItems, genProductAdapterListener());
                    rv_interview.setAdapter(listInterviewAdapter);
                    listInterviewAdapter.notifyDataSetChanged();
                    if (interviewItems.size()==0)
                    {
                        interviewkosong.setVisibility(View.VISIBLE);
                        seeallschedule.setOnClickListener(v -> {
                            Toast.makeText(getActivity(), "Tidak ada Jadwal Interview", Toast.LENGTH_SHORT).show();
                        });
                    }
                    else{

                        interviewkosong.setVisibility(View.INVISIBLE);
                        seeallschedule.setOnClickListener(v -> {
                            Intent intent = new Intent(getActivity(), JadwalInterviewActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        });
                    }
                } else {

                    Toast.makeText(getActivity(), "Gagal mengambil data list interview", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ListInterviewResponse> call, Throwable t) {

                Toast.makeText(getActivity(), "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
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

                    if (listLamaranItems.size()==0)
                    {
                        statuskosong.setVisibility(View.VISIBLE);
                        seeallstatus.setOnClickListener(v -> {
                            Toast.makeText(getActivity(), "Tidak ada Status lamaran", Toast.LENGTH_SHORT).show();
                        });
                    }
                    else{

                        statuskosong.setVisibility(View.INVISIBLE);

                        seeallstatus.setOnClickListener(v -> {
                            Intent intent = new Intent(getActivity(), StatusLamaranActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        });
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

    private void getAdvertisement() {


        mApiService.advertisement_get().enqueue(new Callback<AdvertisementResponse>() {
            @Override
            public void onResponse(Call<AdvertisementResponse> call, Response<AdvertisementResponse> response) {
                if (response.isSuccessful()) {
                    List<AdvertisementItem> advertisementItems = response.body().getAdvertisement();
                    adapter = new AdvertisementAdapter(getActivity(), advertisementItems, genProductAdapterListener());
                    rv_advertisement.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    if (advertisementItems.size()==0)
                    {
                        lokerkosong.setVisibility(View.VISIBLE);
                        seeallrekomendasi.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(getActivity(), "Tidak ada Lowongan yang sesuai", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else{

                        lokerkosong.setVisibility(View.INVISIBLE);
                        seeallrekomendasi.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getActivity(), RekomendasiLokerActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            }
                        });
                    }



                } else {

                    Toast.makeText(getActivity(), "Gagal mengambil data lowongan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AdvertisementResponse> call, Throwable t) {

                Toast.makeText(getActivity(), "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onResume() {
getAdvertisement();
getstatusLamaran();
getListInterview();
        super.onResume();
    }

    private AdapterOnItemClickListener genProductAdapterListener() {
        return new AdapterOnItemClickListener() {
            @Override
            public void onClick(View view, int position) {

            }
        };
    }
}