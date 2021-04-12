package com.carefast.register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
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

public class ListProvince extends AppCompatActivity {
ImageView back;
TextView title;
RecyclerView rvprov;
String selectedName,nama_province,province,passing_province;
SecuredPreference pref;
    RecyclerView.LayoutManager layoutManager,layoutManager2;
    BaseApiService mApiService;
    private ProvinceAdapter adapter2;
    private ArrayList<ProvinceItem> provinceArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_province);

        pref = new SecuredPreference(this, PrefContract.PREF_EL);
        back = findViewById(R.id.backbutton);
        title = findViewById(R.id.title);
        title.setText("Provinsi");
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


        layoutManager = new LinearLayoutManager(ListProvince.this, LinearLayoutManager.VERTICAL, false);

        rvprov.setLayoutManager(layoutManager);




initSpinnerProvince();

    }



    public void initSpinnerProvince(){

        mApiService.province_get().enqueue(new Callback<ProvResponse>() {
            @Override
            public void onResponse(Call<ProvResponse> call, Response<ProvResponse> response) {

                if (response.isSuccessful()) {

                    List<ProvinceItem> provinceItems = response.body().getProvince();
                   adapter2 = new ProvinceAdapter(ListProvince.this,provinceItems, genProductAdapterListener());
                    rvprov.setAdapter(adapter2);
                 adapter2.notifyDataSetChanged();

                } else {

                    Toast.makeText(ListProvince.this, "Gagal mengambil data province", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProvResponse> call, Throwable t) {

                Toast.makeText(ListProvince.this, "Koneksi internet bermasalah..", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onBackPressed() {
        finish();
    }

    private AdapterOnItemClickListener genProductAdapterListener() {
        return new AdapterOnItemClickListener() {
            @Override
            public void onClick(View view, int position) {

            }
        };
    }
}