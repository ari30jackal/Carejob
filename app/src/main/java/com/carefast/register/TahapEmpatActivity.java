package com.carefast.register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.carefast.Login.LoginActivity;
import com.carefast.Login.R;
import com.carefast.apihelper.BaseApiService;
import com.carefast.apihelper.UtilsApi;
import com.carefast.contract.AppException;
import com.carefast.contract.PrefContract;
import com.carefast.contract.SecuredPreference;
import com.carefast.model.JobPositionItem;
import com.carefast.model.JobPositionResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TahapEmpatActivity extends AppCompatActivity {
    ImageView lanjut, kembali;
    PositionAdapter positionAdapter;
    RecyclerView rv_position;
    BaseApiService mApiService;
    RecyclerView.LayoutManager layoutManager;
    String passing_uid;
    String passing_email;
    SecuredPreference pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tahap_empat);
        lanjut = findViewById(R.id.btn_lanjutkan);
        kembali = findViewById(R.id.btn_kembali);
        mApiService = UtilsApi.getAPIService();
        rv_position = findViewById(R.id.rv_position);
        rv_position.setHasFixedSize(true);
        pref = new SecuredPreference(this, PrefContract.PREF_EL);
        try {
            passing_uid = pref.getString(PrefContract.user_id, "");
            passing_email = pref.getString(PrefContract.user_email, "");
        } catch (AppException e) {
            e.printStackTrace();
        }

        Log.d("uidnya tahap 4", "onCreate: " + passing_uid);

        layoutManager = new GridLayoutManager(TahapEmpatActivity.this, 2);
        rv_position.setLayoutManager(layoutManager);
        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
          onBackPressed();
            }
        });

        mApiService.position_get().enqueue(new Callback<JobPositionResponse>() {
            @Override
            public void onResponse(Call<JobPositionResponse> call, Response<JobPositionResponse> response) {
                if (response.isSuccessful()) {

                    //Log.d("logggSCS", "response berhasil");
                    List<JobPositionItem> dataItemList = response.body().getJobPosition();

                    //Toast.makeText(context, ""+androidItemList, Toast.LENGTH_SHORT).show();
                    positionAdapter = new PositionAdapter(TahapEmpatActivity.this, dataItemList, genProductAdapterListener());
                    rv_position.setAdapter(positionAdapter);


                } else {


                }

            }

            //
            @Override
            public void onFailure(Call<JobPositionResponse> call, Throwable t) {
                //Log.d("logggGGL", "GAGAL");
                //Log.e("debug", "onFailure: ERROR > " + t.toString());


            }
        });

        lanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                stepRegisFourth();
//                Intent intent = new Intent(TahapEmpatActivity.this, TahapLimaActivity.class);
//
//                startActivity(intent);
//
            }
        });
    }

    private void stepRegisFourth() {
        mApiService.stepFour(passing_uid)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
//                            loading.dismiss();

                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();

                                String pesanError = jsonRESULTS.getString("code");

                                if (pesanError.equals("200")) {

                                    try {
                                        pref.put(PrefContract.user_id, passing_uid);
//                                        pref.put(PrefContract.email, passing_email);
                                    } catch (AppException e) {
                                        e.printStackTrace();
                                    }

//                                    Toast.makeText(TahapEmpatActivity.this, "Registration Success", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(TahapEmpatActivity.this, TahapLimaActivity.class);
                                    startActivity(intent);


                                    Log.d("create uid job", "onResponse: " );
                                } else {
                                    Toast.makeText(TahapEmpatActivity.this, "Harap pilih salah satu job.", Toast.LENGTH_SHORT).show();
                                    Log.d("error", "onResponse: ");
                                }

                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }

                        } else {
//                            loading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        //Log.e("debug", "onFailure: ERROR > " + t.toString());
//                        loading.dismiss();
                    }
                });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private AdapterOnItemClickListener genProductAdapterListener() {
        return new AdapterOnItemClickListener() {
            @Override
            public void onClick(View view, int position) {

            }
        };
    }
}