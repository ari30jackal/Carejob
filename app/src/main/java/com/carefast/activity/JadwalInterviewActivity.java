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
import com.carefast.adapter.ListInterviewAdapter;
import com.carefast.apihelper.BaseApiService;
import com.carefast.apihelper.UtilsApi;
import com.carefast.contract.AppException;
import com.carefast.contract.PrefContract;
import com.carefast.contract.SecuredPreference;
import com.carefast.home.HomeeActivity;
import com.carefast.model.InterviewItem;
import com.carefast.model.ListInterviewResponse;
import com.carefast.register.AdapterOnItemClickListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JadwalInterviewActivity extends AppCompatActivity {
    RecyclerView rv_confirmed,rv_unconfirmed;
    RecyclerView.LayoutManager layoutManager,layoutManager2;
    ImageView back;
    SecuredPreference pref;
    String user_id;
    BaseApiService mApiService;
    private ListInterviewAdapter listInterviewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_interview);
        rv_confirmed = findViewById(R.id.rv_confirmedschedule);
        rv_unconfirmed = findViewById(R.id.rv_unconfirmedschedule);
        pref = new SecuredPreference(this, PrefContract.PREF_EL);
        try {
            user_id = pref.getString(PrefContract.user_id, "");
        } catch (AppException e) {
            e.printStackTrace();
        }
        back = findViewById(R.id.backbutton);
        mApiService = UtilsApi.getAPIService();
        rv_confirmed.setHasFixedSize(true);
        rv_unconfirmed.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(JadwalInterviewActivity.this, LinearLayoutManager.VERTICAL, false);
        rv_confirmed.setLayoutManager(layoutManager);
        layoutManager2 = new LinearLayoutManager(JadwalInterviewActivity.this, LinearLayoutManager.VERTICAL, false);
        rv_unconfirmed.setLayoutManager(layoutManager2);
        getconfirmedinterview();
        getunconfirmedinterview();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JadwalInterviewActivity.this, HomeeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void getunconfirmedinterview() {


        mApiService.listinterview(user_id).enqueue(new Callback<ListInterviewResponse>() {
            @Override
            public void onResponse(Call<ListInterviewResponse> call, Response<ListInterviewResponse> response) {
                if (response.isSuccessful()) {
                    List<InterviewItem> interviewItems = response.body().getInterview();
                    listInterviewAdapter = new ListInterviewAdapter(JadwalInterviewActivity.this, interviewItems, genProductAdapterListener());
                    rv_unconfirmed.setAdapter(listInterviewAdapter);
                    listInterviewAdapter.notifyDataSetChanged();
                } else {

                    Toast.makeText(JadwalInterviewActivity.this, "Gagal mengambil data list interview", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ListInterviewResponse> call, Throwable t) {

                Toast.makeText(JadwalInterviewActivity.this, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getconfirmedinterview() {


        mApiService.listinterview(user_id).enqueue(new Callback<ListInterviewResponse>() {
            @Override
            public void onResponse(Call<ListInterviewResponse> call, Response<ListInterviewResponse> response) {
                if (response.isSuccessful()) {
                    List<InterviewItem> interviewItems = response.body().getInterview();
                    listInterviewAdapter = new ListInterviewAdapter(JadwalInterviewActivity.this, interviewItems, genProductAdapterListener());
                   rv_confirmed.setAdapter(listInterviewAdapter);
                    listInterviewAdapter.notifyDataSetChanged();
                } else {

                    Toast.makeText(JadwalInterviewActivity.this, "Gagal mengambil data list interview", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ListInterviewResponse> call, Throwable t) {

                Toast.makeText(JadwalInterviewActivity.this, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
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