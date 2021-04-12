package com.carefast.detailjob;

import androidx.appcompat.app.AppCompatActivity;

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
import com.carefast.apihelper.BaseApiService;
import com.carefast.apihelper.UtilsApi;
import com.carefast.contract.AppException;
import com.carefast.contract.PrefContract;
import com.carefast.contract.SecuredPreference;
import com.carefast.home.HomeeActivity;
import com.carefast.home.Homepage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;

import io.github.kexanie.library.MathView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailJobActivity extends AppCompatActivity {
SecuredPreference pref;
TextView title,jabatan,placement;
MathView desc;
Button lamar;
String getdesc,getjabatan,getplacement;
ImageView back;
BaseApiService mApiService;
String iduser,idadvertisement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_job);

        pref = new SecuredPreference(DetailJobActivity.this, PrefContract.PREF_EL);
        title = findViewById(R.id.tv_title);

        mApiService = UtilsApi.getAPIService();
        jabatan = findViewById(R.id.tv_jabatan);
        placement = findViewById(R.id.tv_placement);
        desc =findViewById(R.id.desc_advertisement);
        lamar = findViewById(R.id.btn_lamar);
        try {
            getdesc = pref.getString(PrefContract.desc_advertisement,"");
            getjabatan = pref.getString(PrefContract.position_advertisement,"");
            getplacement = pref.getString(PrefContract.placement_advertisement,"");
iduser=pref.getString(PrefContract.user_id,"");
idadvertisement = pref.getString(PrefContract.id_advertisement,"");
        } catch (AppException e) {
            e.printStackTrace();
        }
jabatan.setText(getjabatan);
        desc.setText(getdesc);
        placement.setText(getplacement);
        back = findViewById(R.id.backbutton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailJobActivity.this, HomeeActivity.class);
                startActivity(intent);
                finish();
            }
        });
lamar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        mApiService.lamarkerja(idadvertisement,iduser).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
                        //String pesan = jsonRESULTS.getString("success");
                        String pesanError = jsonRESULTS.getString("message");
                        String pesancode = jsonRESULTS.getString("code");
                        if (pesanError.equals("Lamar berhasil.")) {
                            lamar.setClickable(false);
                        Intent intent = new Intent(DetailJobActivity.this,HomeeActivity.class);
                        startActivity(intent);
                        finish();
                            Toast.makeText(DetailJobActivity.this, "Berhasil melamar pekerjaan", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DetailJobActivity.this, ""+pesanError, Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                } else {

                    Toast.makeText(DetailJobActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(DetailJobActivity.this, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }
});
    }
}