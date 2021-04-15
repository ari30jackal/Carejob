package com.carefast.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.carefast.Login.R;
import com.carefast.adapter.AdvertisementAdapter;
import com.carefast.adapter.AdvertisementAdapterRekomendasiLoker;
import com.carefast.apihelper.BaseApiService;
import com.carefast.apihelper.UtilsApi;
import com.carefast.contract.AppException;
import com.carefast.contract.PrefContract;
import com.carefast.contract.SecuredPreference;
import com.carefast.home.HomeeActivity;
import com.carefast.model.AdverResponse;
import com.carefast.model.AdvertisementItemItemItem;
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
    String jopo,tato,tindik,kawin;
    List<AdvertisementItemItemItem> advertisementItems;
String user_id;
    BaseApiService mApiService;
    private AdvertisementAdapterRekomendasiLoker adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rekomendasi_loker);
        pref = new SecuredPreference(RekomendasiLokerActivity.this, PrefContract.PREF_EL);
        rv_advertisement =findViewById(R.id.rv_advertisement);
        back = findViewById(R.id.backbutton);
        mApiService = UtilsApi.getAPIService();
        try {
            user_id =pref.getString(PrefContract.user_id,"");
        } catch (AppException e) {
            e.printStackTrace();
        }
        rv_advertisement.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(RekomendasiLokerActivity.this, LinearLayoutManager.VERTICAL, false);
        rv_advertisement.setLayoutManager(layoutManager);
//    getjopo();
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
        mApiService.getjopo(user_id).enqueue(new Callback<AdverResponse>() {
            @Override
            public void onResponse(Call<AdverResponse> call, Response<AdverResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        List<AdvertisementItemItemItem> advertisementItems = response.body().getAdvertisement();
                        adapter = new AdvertisementAdapterRekomendasiLoker(RekomendasiLokerActivity.this, advertisementItems, genProductAdapterListener());
                        rv_advertisement.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

//                        if (advertisementItems.size()==0)
//                        {
//                            lokerkosong.setVisibility(View.VISIBLE);
//                            seeallrekomendasi.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    Toast.makeText(getActivity(), "Tidak ada Lowongan yang sesuai", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        }
//                        else{
//
//                            lokerkosong.setVisibility(View.GONE);
//                            seeallrekomendasi.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    Intent intent = new Intent(getActivity(), RekomendasiLokerActivity.class);
//                                    startActivity(intent);
//                                    getActivity().finish();
//                                }
//                            });
//                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } else {

                    Toast.makeText(RekomendasiLokerActivity.this, "Gagal mengambil data lowongan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AdverResponse> call, Throwable t) {
                Log.d("inifail",""+t.toString());
                Toast.makeText(RekomendasiLokerActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
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