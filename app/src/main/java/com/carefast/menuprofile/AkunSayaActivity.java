package com.carefast.menuprofile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.carefast.Login.R;
import com.carefast.apihelper.BaseApiService;
import com.carefast.apihelper.UtilsApi;
import com.carefast.contract.AppException;
import com.carefast.contract.PrefContract;
import com.carefast.contract.SecuredPreference;
import com.carefast.home.HomeeActivity;
import com.carefast.menuprofile.akunsaya.ChangeEmailActivity;
import com.carefast.menuprofile.akunsaya.ChangeNamaActivity;
import com.carefast.menuprofile.akunsaya.ChangePhoneActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AkunSayaActivity extends AppCompatActivity {
TextView nama,email,phone,chphone,chnama,chemail,userid;
ImageView back;
SecuredPreference pref;
String userrid;
BaseApiService mApiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akun_saya);
        mApiService = UtilsApi.getAPIService();
        back = findViewById(R.id.backbutton);
        chnama = findViewById(R.id.changenama);
        chphone = findViewById(R.id.changephone);
        chemail = findViewById(R.id.changeemail);
       nama = findViewById(R.id.tv_nama);
       phone = findViewById(R.id.tv_phone);
       email = findViewById(R.id.tv_email);
        pref = new SecuredPreference(AkunSayaActivity.this, PrefContract.PREF_EL);
getprofile();
        chnama.setOnClickListener(view -> {
            Intent intent = new Intent(AkunSayaActivity.this, ChangeNamaActivity.class);
            startActivity(intent);


        });
        back.setOnClickListener(view -> {

            finish();

        });
        chemail.setOnClickListener(view -> {
            Intent intent = new Intent(AkunSayaActivity.this, ChangeEmailActivity.class);
            startActivity(intent);


        });
        chphone.setOnClickListener(view -> {
            Intent intent = new Intent(AkunSayaActivity.this, ChangePhoneActivity.class);
            startActivity(intent);


        });
    }
    private void getprofile() {
//        Toast.makeText(getActivity(),id_sch, Toast.LENGTH_SHORT).show();
        try {
            userrid = pref.getString(PrefContract.user_id,"");
        } catch (AppException e) {
            e.printStackTrace();
        }
        mApiService.getprofile(userrid).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
                        //String pesan = jsonRESULTS.getString("success");
                        JSONArray ds = jsonRESULTS.getJSONArray("Profile");
                        JSONObject dd = ds.getJSONObject(0);
                        String email_applicant = dd.getString("email_applicant");
                        String phone_applicant = dd.getString("phone_applicant");
                        String last_name_applicant = dd.getString("last_name_applicant");
                        String first_name_applicant = dd.getString("first_name_applicant");
                       nama.setText(first_name_applicant+" "+last_name_applicant);
                        email.setText(email_applicant);
                        phone.setText(phone_applicant);


                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                } else {

                    Toast.makeText(AkunSayaActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(AkunSayaActivity.this, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        getprofile();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}