package com.carefast.detailjob;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.carefast.Login.R;
import com.carefast.apihelper.BaseApiService;
import com.carefast.apihelper.UtilsApi;
import com.carefast.contract.AppException;
import com.carefast.contract.PrefContract;
import com.carefast.contract.SecuredPreference;
import com.carefast.fragment.DeskripsiDetailFragment;
import com.carefast.fragment.StatusDetailFragment;
import com.carefast.fragment.StatusDetailjadwalFragment;
import com.carefast.home.HomeeActivity;
import com.carefast.register.AdapterOnItemClickListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailJobjadwalActivity extends AppCompatActivity {
    SecuredPreference pref;
    BaseApiService mApiService;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment fragment = null;
    Button btnDeskripsi, btnStatus;
    String id_advertisement, user_id;
    TextView tvJobPosition, tvPlacement, tvKodeJabatan;
    String position, placement, kodejabatan, id_lamaran,id_sch;
    ImageView back,ivadvertisement;

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
        ivadvertisement = findViewById(R.id.iv_advertisement);
        back = findViewById(R.id.backbutton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailJobjadwalActivity.this, HomeeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.screen_detail_job, fragment);
        ft.commit();
        mApiService = UtilsApi.getAPIService();

        pref = new SecuredPreference(DetailJobjadwalActivity.this, PrefContract.PREF_EL);
        try {
            user_id = pref.getString(PrefContract.user_id, "");
            id_advertisement = pref.getString(PrefContract.id_advertisement, "");
            id_lamaran = pref.getString(PrefContract.id_lamaran, "");
            id_sch = pref.getString(PrefContract.id_interview,"");
        } catch (AppException e) {
            e.printStackTrace();
        }


        mApiService = UtilsApi.getAPIService();

getdetailinterview();





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


            Fragment fragment2 = new StatusDetailjadwalFragment();
            FragmentManager fragmentManager2 = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager2.beginTransaction();
            fragmentTransaction.replace(R.id.screen_detail_job, fragment2);
            fragmentTransaction.commit();
        });
    }
    private void getdetailinterview() {
//        Toast.makeText(getActivity(),id_sch, Toast.LENGTH_SHORT).show();
        Log.d("iniinputnyadetil","sch = "+id_sch+"@user="+user_id+"@adv="+id_advertisement);
        mApiService.detailinterview(id_sch,user_id,id_advertisement).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
                        //String pesan = jsonRESULTS.getString("success");
                        JSONArray ds = jsonRESULTS.getJSONArray("Detailinterview");
                        JSONObject dd = ds.getJSONObject(0);
                        String id_user_advertisement = dd.getString("id_user_advertisement");
                        String status_review = dd.getString("status_review");
                        String reason_review = dd.getString("reason_review");
                        String date_schedule = dd.getString("date_schedule");
                        String end_time = dd.getString("end_time");
                        String start_time= dd.getString("start_time");
                        String location= dd.getString("location");
                        String confirmation_date = dd.getString("confirmation_date");
                        String icon_image = dd.getString("icon_image");
                        String advertisement_code = dd.getString("advertisement_code");
                        String placement = dd.getString("placement");
                        String job_description = dd.getString("job_description");
                        String nama_position= dd.getString("nama_position");
                        String admin_master_name = dd.getString("admin_master_name");
                        Picasso.get()
                                .load(DetailJobjadwalActivity.this.getResources().getString(R.string.base_url_asset_interview) +icon_image)
                                .into(ivadvertisement);
                        tvJobPosition.setText(nama_position);
                        tvKodeJabatan.setText(advertisement_code);
                        tvPlacement.setText(placement);
                        try {
                            pref.put(PrefContract.putplace,placement);
                            pref.put(PrefContract.putjabatan,nama_position);
                            pref.put(PrefContract.confirmationdate,confirmation_date);
                            pref.put(PrefContract.interviewerod,admin_master_name);
                            pref.put(PrefContract.placementinterviewod,location);
                            pref.put(PrefContract.jaminterviewod,start_time+" - "+end_time);
                            pref.put(PrefContract.hariinterviewod,date_schedule);
                            pref.put(PrefContract.putstatus,status_review);
                        } catch (AppException e) {
                            e.printStackTrace();
                        }


                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                } else {

                    Toast.makeText(DetailJobjadwalActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(DetailJobjadwalActivity.this, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
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