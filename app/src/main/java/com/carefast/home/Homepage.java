package com.carefast.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.carefast.Login.R;
import com.carefast.adapter.AdvertisementAdapter;
import com.carefast.apihelper.BaseApiService;
import com.carefast.apihelper.UtilsApi;
import com.carefast.model.AdvertisementItem;
import com.carefast.model.AdvertisementResponse;
import com.carefast.model.CityItem;
import com.carefast.model.CityResponse;
import com.carefast.register.AdapterOnItemClickListener;
import com.carefast.register.CityAdapter;
import com.carefast.register.ListCity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Homepage extends AppCompatActivity {
RecyclerView rv_advertisement;
    RecyclerView.LayoutManager layoutManager, layoutManager2;
    BaseApiService mApiService;
    private AdvertisementAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        rv_advertisement = findViewById(R.id.rv_advertisement);
        rv_advertisement.setHasFixedSize(true);
        mApiService = UtilsApi.getAPIService();

        layoutManager = new LinearLayoutManager(Homepage.this, LinearLayoutManager.HORIZONTAL, false);

        rv_advertisement.setLayoutManager(layoutManager);

        getAdvertisement();
    }

    private void getAdvertisement() {


        mApiService.advertisement_get().enqueue(new Callback<AdvertisementResponse>() {
            @Override
            public void onResponse(Call<AdvertisementResponse> call, Response<AdvertisementResponse> response) {
                if (response.isSuccessful()) {
                    List<AdvertisementItem> advertisementItems = response.body().getAdvertisement();
                    adapter = new AdvertisementAdapter(Homepage.this, advertisementItems, genProductAdapterListener());
                    rv_advertisement.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {

                    Toast.makeText(Homepage.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AdvertisementResponse> call, Throwable t) {

                Toast.makeText(Homepage.this, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
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