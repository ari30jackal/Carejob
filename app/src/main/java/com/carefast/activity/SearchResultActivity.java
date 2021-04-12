package com.carefast.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.carefast.Login.R;
import com.carefast.adapter.AdvertisementAdapterRekomendasiLoker;
import com.carefast.apihelper.BaseApiService;
import com.carefast.apihelper.UtilsApi;
import com.carefast.contract.AppException;
import com.carefast.contract.PrefContract;
import com.carefast.contract.SecuredPreference;
import com.carefast.home.HomeeActivity;
import com.carefast.model.AdvertisementItem;
import com.carefast.model.AdvertisementResponse;
import com.carefast.register.AdapterOnItemClickListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultActivity extends AppCompatActivity {
    TextView tvsearch;
    RecyclerView rvsearch;
    String keyword;
    RecyclerView.LayoutManager layoutManager;
    ImageView back;
    SecuredPreference pref;
    BaseApiService mApiService;
    private AdvertisementAdapterRekomendasiLoker adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        back = findViewById(R.id.backbutton);
        pref = new SecuredPreference(SearchResultActivity.this, PrefContract.PREF_EL);
        try {
            keyword = pref.getString(PrefContract.keyword,"");
        } catch (AppException e) {
            e.printStackTrace();
        }
        mApiService = UtilsApi.getAPIService();
        rvsearch=findViewById(R.id.rv_search);
        rvsearch.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(SearchResultActivity.this, LinearLayoutManager.VERTICAL, false);
        rvsearch.setLayoutManager(layoutManager);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchResultActivity.this, HomeeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        getAdvertisement();

    }
    private void getAdvertisement() {


        mApiService.advertisementsearch(keyword).enqueue(new Callback<AdvertisementResponse>() {
            @Override
            public void onResponse(Call<AdvertisementResponse> call, Response<AdvertisementResponse> response) {
                if (response.isSuccessful()) {
                    List<AdvertisementItem> advertisementItems = response.body().getAdvertisement();
                    adapter = new AdvertisementAdapterRekomendasiLoker(SearchResultActivity.this, advertisementItems, genProductAdapterListener());
                    rvsearch.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {

                    Toast.makeText(SearchResultActivity.this, "Gagal mengambil data lowongan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AdvertisementResponse> call, Throwable t) {

                Toast.makeText(SearchResultActivity.this, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
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