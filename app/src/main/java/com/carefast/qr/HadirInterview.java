package com.carefast.qr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.carefast.Login.R;
import com.carefast.apihelper.BaseApiService;
import com.carefast.apihelper.UtilsApi;
import com.carefast.contract.AppException;
import com.carefast.contract.PrefContract;
import com.carefast.contract.SecuredPreference;
import com.carefast.detailjob.DetailJobAfterActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HadirInterview extends AppCompatActivity {
SecuredPreference pref;
String hasilscan;
BaseApiService mApiService;
String firstname,lastname,userid;
Button btnHadir;
TextView nama,kdjabatan,hariinterview,interviewer,jaminterview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hadir_interview);
        pref = new SecuredPreference(this, PrefContract.PREF_EL);
        try {
            hasilscan = pref.getString(PrefContract.hasilscan,"");
            firstname = pref.getString(PrefContract.user_first_name,"");
            lastname = pref.getString(PrefContract.user_last_name,"");
            userid = pref.getString(PrefContract.user_id,"");
        } catch (AppException e) {
            e.printStackTrace();
        }
        hariinterview = findViewById(R.id.hari_interview);
        kdjabatan = findViewById(R.id.kd_jabatan);
        interviewer = findViewById(R.id.interviewer);
        jaminterview =findViewById(R.id.jam_interview);
        btnHadir = findViewById(R.id.btn_masuk);
        mApiService = UtilsApi.getAPIService();
        String[] separated = hasilscan.split("=");
//        for (int i=0; i < arrSplit.length; i++)
//        {
//            System.out.println(arrSplit[i]);
//        }
        hasilscan = separated[1] = separated[1].trim();
        getdata();

        nama = findViewById(R.id.nama);
        nama.setText("halo, "+firstname+" "+lastname);

btnHadir.setOnClickListener(v->{
btnHadir.setText("Sudah Masuk");
confirmjam();
});

//


//        separated[0];
//        separated[1];
    }

    private void confirmjam() {
        mApiService.hadir(hasilscan,userid).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        DialogFragment homeAdsFragment = new indialogFragment();
                        homeAdsFragment.show(getSupportFragmentManager(), "tes");




                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                } else {

                    Toast.makeText(HadirInterview.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(HadirInterview.this, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getdata() {
        mApiService.scanqr(hasilscan).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
                        //String pesan = jsonRESULTS.getString("success");

                        JSONArray userId = jsonRESULTS.getJSONArray("HasilInterview");
                        JSONObject obj = userId.getJSONObject(0);
                        String date_schedule = obj.getString("date_schedule");

                        String end_time = obj.getString("end_time");
                        String start_time = obj.getString("start_time");
                        String nminterviewer = obj.getString("admin_master_name");
String kodejabatan = obj.getString("advertisement_code");
kdjabatan.setText(kodejabatan);
hariinterview.setText(date_schedule);
jaminterview.setText(start_time + " - "+end_time);
interviewer.setText("Interviewer : "+nminterviewer);




                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                } else {

                    Toast.makeText(HadirInterview.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(HadirInterview.this, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }
}