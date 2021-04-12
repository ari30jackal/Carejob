package com.carefast.detailjob;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.carefast.Login.LoginActivity;
import com.carefast.Login.R;
import com.carefast.adapter.AdvertisementAdapter;
import com.carefast.apihelper.BaseApiService;
import com.carefast.apihelper.UtilsApi;
import com.carefast.contract.AppException;
import com.carefast.contract.PrefContract;
import com.carefast.contract.SecuredPreference;
import com.carefast.fragment.DeskripsiDetailFragment;
import com.carefast.fragment.HomeFragment;
import com.carefast.fragment.JobFragment;
import com.carefast.fragment.StatusDetailFragment;
import com.carefast.home.HomeeActivity;
import com.carefast.model.AdvertisementItem;
import com.carefast.model.AdvertisementResponse;
import com.carefast.register.AdapterOnItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailJobAfterActivity extends AppCompatActivity {
    SecuredPreference pref;
    BaseApiService mApiService;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment fragment = null;
    Button btnDeskripsi, btnStatus;
    String id_advertisement, user_id;
    TextView tvJobPosition, tvPlacement, tvKodeJabatan;
    String position, placement, kodejabatan, id_lamaran,id_sch;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_job_after);
        btnDeskripsi = findViewById(R.id.btn_deskripsi);
        btnStatus = findViewById(R.id.btn_status);
        tvJobPosition = findViewById(R.id.tv_jabatan);
        tvPlacement = findViewById(R.id.tv_placement);
        tvKodeJabatan = findViewById(R.id.tv_kodejabatan);
        Fragment fragment = new DeskripsiDetailFragment();
        back = findViewById(R.id.backbutton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailJobAfterActivity.this, HomeeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.screen_detail_job, fragment);
        ft.commit();
        mApiService = UtilsApi.getAPIService();
        getAdvertisementbyid();
        pref = new SecuredPreference(DetailJobAfterActivity.this, PrefContract.PREF_EL);
        try {
            user_id = pref.getString(PrefContract.user_id, "");
            id_advertisement = pref.getString(PrefContract.id_advertisement, "");
            id_lamaran = pref.getString(PrefContract.id_lamaran, "");
            id_sch = pref.getString(PrefContract.id_interview,"");
        } catch (AppException e) {
            e.printStackTrace();
        }


        mApiService = UtilsApi.getAPIService();

//getdetailinterview();
        getbyschedule();


        mApiService.advertisementbyid(id_advertisement).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
                        //String pesan = jsonRESULTS.getString("success");

                        JSONArray userId = jsonRESULTS.getJSONArray("Advertisementbyid");
                        JSONObject obj = userId.getJSONObject(0);
                        String job_des = obj.getString("job_description");
                        String job_post = obj.getString("nama_position");
                        String job_code = obj.getString("advertisement_code");
                        String job_place = obj.getString("placement");


                        tvJobPosition.setText(job_post);
                        tvKodeJabatan.setText(job_code);
                        tvPlacement.setText(job_place);
                        Log.d("trai", job_post + "     " + job_code + "    " + job_place);


                        try {
                            pref.put(PrefContract.desc_advertisement, job_des);
                            pref.put(PrefContract.putkodejabatan,job_code);
                        } catch (AppException e) {
                            e.printStackTrace();
                        }


                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                } else {

                    Toast.makeText(DetailJobAfterActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(DetailJobAfterActivity.this, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });


        btnDeskripsi.setOnClickListener(v -> {
            btnStatus.setBackgroundResource(R.drawable.roundedbuttongray);
            btnDeskripsi.setBackgroundResource(R.drawable.roundedbuttonorange);
            btnStatus.setTextColor(getResources().getColor(R.color.gray));

            btnDeskripsi.setTextColor(getResources().getColor(R.color.white));


            Fragment fragment2 = new DeskripsiDetailFragment();
            FragmentManager fragmentManager2 = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager2.beginTransaction();
            fragmentTransaction.replace(R.id.screen_detail_job, fragment2);
            fragmentTransaction.commit();


        });

        btnStatus.setOnClickListener(v -> {

            btnDeskripsi.setBackgroundResource(R.drawable.roundedbuttongray);
            btnStatus.setBackgroundResource(R.drawable.roundedbuttonorange);
            btnStatus.setTextColor(getResources().getColor(R.color.white));
            btnDeskripsi.setTextColor(getResources().getColor(R.color.gray));


            Fragment fragment2 = new StatusDetailFragment();
            FragmentManager fragmentManager2 = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager2.beginTransaction();
            fragmentTransaction.replace(R.id.screen_detail_job, fragment2);
            fragmentTransaction.commit();
        });
    }
//    private void getdetailinterview() {
////        Toast.makeText(getActivity(),id_sch, Toast.LENGTH_SHORT).show();
//        mApiService.detailinterview(id_sch,id_lamaran).enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if (response.isSuccessful()) {
//                    try {
//                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
//                        //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
//                        //String pesan = jsonRESULTS.getString("success");
//                        JSONArray ds = jsonRESULTS.getJSONArray("Detailinterview");
//                        JSONObject dd = ds.getJSONObject(0);
//                        String id_advertisement_code = dd.getString("id_advertisement_code");
//                        String location = dd.getString("location");
//                        String date_schedule= dd.getString("date_schedule");
//                        String start_time = dd.getString("start_time");
//                        String end_time = dd.getString("end_time");
//                        String qrcode_interview = dd.getString("qrcode_interview");
//                        String confirmation_date = dd.getString("confirmation_date");
//                        String admin_master_name = dd.getString("admin_master_name");
//                        String statuslamar = dd.getString("status_review");
////                        String schedule = dd.getString("date_schedule");
//
//                        try {
//                            pref.put(PrefContract.confirmationdate,confirmation_date);
//                            pref.put(PrefContract.interviewerod,admin_master_name);
//                            pref.put(PrefContract.placementinterviewod,location);
//                            pref.put(PrefContract.jaminterviewod,start_time+" - "+end_time);
//                            pref.put(PrefContract.hariinterviewod,date_schedule);
//                            pref.put(PrefContract.putstatus,statuslamar);
//                        } catch (AppException e) {
//                            e.printStackTrace();
//                        }
//
//
//                    } catch (JSONException | IOException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//
//                    Toast.makeText(DetailJobAfterActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//                Toast.makeText(DetailJobAfterActivity.this, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
    private void getbyschedule() {


        mApiService = UtilsApi.getAPIService();
        mApiService.getadvertfromschedule(user_id,id_advertisement).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
                        //String pesan = jsonRESULTS.getString("success");

                        JSONArray userId = jsonRESULTS.getJSONArray("interview");
                        JSONObject obj = userId.getJSONObject(0);
                        String idlam = obj.getString("id_user_advertisement");






                        try {

                            pref.put(PrefContract.id_lamaran,idlam);
                        } catch (AppException e) {
                            e.printStackTrace();
                        }


                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                } else {

                    Toast.makeText(DetailJobAfterActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(DetailJobAfterActivity.this, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void getAdvertisementbyid() {

    }

    private AdapterOnItemClickListener genProductAdapterListener() {
        return new AdapterOnItemClickListener() {
            @Override
            public void onClick(View view, int position) {

            }
        };
    }
}