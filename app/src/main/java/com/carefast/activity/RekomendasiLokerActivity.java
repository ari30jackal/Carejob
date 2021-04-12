package com.carefast.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.carefast.Login.R;
import com.carefast.adapter.AdvertisementAdapter;
import com.carefast.adapter.AdvertisementAdapterRekomendasiLoker;
import com.carefast.apihelper.BaseApiService;
import com.carefast.apihelper.UtilsApi;
import com.carefast.contract.SecuredPreference;
import com.carefast.detailjob.DetailJobActivity;
import com.carefast.home.HomeeActivity;
import com.carefast.model.AdvertisementItem;
import com.carefast.model.AdvertisementResponse;
import com.carefast.register.AdapterOnItemClickListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RekomendasiLokerActivity extends AppCompatActivity {
RecyclerView rv_advertisement;
    RecyclerView.LayoutManager layoutManager;
    ImageView back;
SecuredPreference pref;
    BaseApiService mApiService;
    private AdvertisementAdapterRekomendasiLoker adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rekomendasi_loker);
        rv_advertisement =findViewById(R.id.rv_advertisement);
        back = findViewById(R.id.backbutton);
        mApiService = UtilsApi.getAPIService();
        rv_advertisement.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(RekomendasiLokerActivity.this, LinearLayoutManager.VERTICAL, false);
        rv_advertisement.setLayoutManager(layoutManager);
    getAdvertisement();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RekomendasiLokerActivity.this, HomeeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void getAdvertisement() {


        mApiService.advertisement_get().enqueue(new Callback<AdvertisementResponse>() {
            @Override
            public void onResponse(Call<AdvertisementResponse> call, Response<AdvertisementResponse> response) {
                if (response.isSuccessful()) {
                    List<AdvertisementItem> advertisementItems = response.body().getAdvertisement();
                    adapter = new AdvertisementAdapterRekomendasiLoker(RekomendasiLokerActivity.this, advertisementItems, genProductAdapterListener());
                    rv_advertisement.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {

                    Toast.makeText(RekomendasiLokerActivity.this, "Gagal mengambil data lowongan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AdvertisementResponse> call, Throwable t) {

                Toast.makeText(RekomendasiLokerActivity.this, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
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