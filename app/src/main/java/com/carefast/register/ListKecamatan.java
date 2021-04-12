package com.carefast.register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.carefast.Login.R;
import com.carefast.apihelper.BaseApiService;
import com.carefast.apihelper.UtilsApi;
import com.carefast.contract.AppException;
import com.carefast.contract.PrefContract;
import com.carefast.contract.SecuredPreference;
import com.carefast.model.CityItem;
import com.carefast.model.CityResponse;
import com.carefast.model.DistrictsItem;
import com.carefast.model.DistrictsResponse;
import com.carefast.model.ProvResponse;
import com.carefast.model.ProvinceItem;
import com.carefast.model.ResponseCity;
import com.carefast.model.ResponseProvince;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListKecamatan extends AppCompatActivity {
ImageView back;
TextView title;
    RecyclerView rvprov;
    String selectedName,nama_province,province,passing_province,provinceid;
    SecuredPreference pref;
    RecyclerView.LayoutManager layoutManager,layoutManager2;
    BaseApiService mApiService;
    private DistrictsAdapter adapter;
    private ArrayList<ProvinceItem> provinceArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_province);

        pref = new SecuredPreference(this, PrefContract.PREF_EL);
        back = findViewById(R.id.backbutton);
        title = findViewById(R.id.title);
        title.setText("Kecamatan");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();


            }
        });






        pref = new SecuredPreference(this, PrefContract.PREF_EL);
        mApiService = UtilsApi.getAPIService();
        rvprov = findViewById(R.id.rvprov);


        rvprov.setHasFixedSize(true);


        layoutManager = new LinearLayoutManager(ListKecamatan.this, LinearLayoutManager.VERTICAL, false);

        rvprov.setLayoutManager(layoutManager);




        initSpinnerCity();

    }



    private void initSpinnerCity(){

        try {
            provinceid= pref.getString(PrefContract.city_id_choose,"");
        } catch (AppException e) {
            e.printStackTrace();
        }

        mApiService.districts_get(provinceid).enqueue(new Callback<DistrictsResponse>() {
            @Override
            public void onResponse(Call<DistrictsResponse> call, Response<DistrictsResponse> response) {
                if (response.isSuccessful()) {
                    List<DistrictsItem> districtsItems = response.body().getDistricts();
                    adapter = new DistrictsAdapter(ListKecamatan.this,districtsItems, genProductAdapterListener());
                    rvprov.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {

                    Toast.makeText(ListKecamatan.this, "Gagal mengambil data kecamatan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DistrictsResponse> call, Throwable t) {

                Toast.makeText(ListKecamatan.this, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
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