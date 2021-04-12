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
import com.carefast.model.CityResponse;
import com.carefast.model.PostalCodeItem;
import com.carefast.model.PostalcodeResponse;
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

public class ListPostal extends AppCompatActivity {
ImageView back;
TextView title;
    RecyclerView rvprov;
    String cityid,disid,provinceid;
    SecuredPreference pref;
    RecyclerView.LayoutManager layoutManager,layoutManager2;
    BaseApiService mApiService;
    private PostalAdapter adapter;
    private ArrayList<PostalCodeItem> provinceArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_province);

        pref = new SecuredPreference(this, PrefContract.PREF_EL);
        back = findViewById(R.id.backbutton);
        title = findViewById(R.id.title);
        title.setText("Kode Pos");
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


        layoutManager = new LinearLayoutManager(ListPostal.this, LinearLayoutManager.VERTICAL, false);

        rvprov.setLayoutManager(layoutManager);




        initSpinnerCity();

    }



    private void initSpinnerCity(){

        try {
            provinceid= pref.getString(PrefContract.province_id_choose,"");
            cityid= pref.getString(PrefContract.city_id_choose,"");
            disid= pref.getString(PrefContract.districts_id_choose,"");
        } catch (AppException e) {
            e.printStackTrace();
        }

        mApiService.postal_get(provinceid,cityid,disid).enqueue(new Callback<PostalcodeResponse>() {
            @Override
            public void onResponse(Call<PostalcodeResponse> call, Response<PostalcodeResponse> response) {
                if (response.isSuccessful()) {
                    List<PostalCodeItem> cityItems = response.body().getPostalCode();
                    adapter = new PostalAdapter(ListPostal.this,cityItems, genProductAdapterListener());
                    rvprov.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {

                    Toast.makeText(ListPostal.this, "Gagal mengambil data postal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PostalcodeResponse> call, Throwable t) {

                Toast.makeText(ListPostal.this, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
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