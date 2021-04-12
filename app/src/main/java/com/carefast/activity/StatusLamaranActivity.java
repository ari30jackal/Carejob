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
import com.carefast.adapter.AdvertisementAdapterRekomendasiLoker;
import com.carefast.adapter.StatusLamaranAdapter;
import com.carefast.apihelper.BaseApiService;
import com.carefast.apihelper.UtilsApi;
import com.carefast.contract.AppException;
import com.carefast.contract.PrefContract;
import com.carefast.contract.SecuredPreference;
import com.carefast.home.HomeeActivity;
import com.carefast.model.ListLamaranItem;
import com.carefast.model.StatusLamaranResponse;
import com.carefast.register.AdapterOnItemClickListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatusLamaranActivity extends AppCompatActivity {
    RecyclerView rv_stat;
    RecyclerView.LayoutManager layoutManager;
    ImageView back;
    SecuredPreference pref;
    String user_id;
    BaseApiService mApiService;
    private StatusLamaranAdapter statusLamaranAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_lamaran);
        rv_stat = findViewById(R.id.rv_stat);
        back = findViewById(R.id.backbutton);
        mApiService = UtilsApi.getAPIService();
        rv_stat.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(StatusLamaranActivity.this, LinearLayoutManager.VERTICAL, false);
        rv_stat.setLayoutManager(layoutManager);
        pref = new SecuredPreference(this, PrefContract.PREF_EL);
        try {
            user_id = pref.getString(PrefContract.user_id, "");
        } catch (AppException e) {
            e.printStackTrace();
        }
    getstatusLamaran();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StatusLamaranActivity.this, HomeeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void getstatusLamaran() {

        mApiService.statuslamaran(user_id).enqueue(new Callback<StatusLamaranResponse>() {
            @Override
            public void onResponse(Call<StatusLamaranResponse> call, Response<StatusLamaranResponse> response) {
                if (response.isSuccessful()) {
                    List<ListLamaranItem> listLamaranItems = response.body().getListLamaran();
                    statusLamaranAdapter = new StatusLamaranAdapter(StatusLamaranActivity.this, listLamaranItems, genProductAdapterListener());
                    rv_stat.setAdapter(statusLamaranAdapter);
                    statusLamaranAdapter.notifyDataSetChanged();
                } else {

                    Toast.makeText(StatusLamaranActivity.this, "Gagal mengambil data list", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StatusLamaranResponse> call, Throwable t) {

                Toast.makeText(StatusLamaranActivity.this, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
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