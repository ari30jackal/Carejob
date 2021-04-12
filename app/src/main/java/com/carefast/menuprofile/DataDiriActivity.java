package com.carefast.menuprofile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.carefast.Login.R;
import com.carefast.apihelper.BaseApiService;
import com.carefast.apihelper.UtilsApi;
import com.carefast.contract.AppException;
import com.carefast.contract.PrefContract;
import com.carefast.contract.SecuredPreference;
import com.carefast.detailjob.DetailJobAfterActivity;
import com.carefast.home.HomeeActivity;
import com.carefast.register.ListCity;
import com.carefast.register.ListKecamatan;
import com.carefast.register.ListPostal;
import com.carefast.register.ListProvince;
import com.carefast.register.RincianAlamat;
import com.carefast.register.TahapDuaActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataDiriActivity extends AppCompatActivity {
    SecuredPreference pref;
    String uid;
    ImageView back;
    Button save;
    Integer intberat;
    ImageView opsiwanita, opsipria, minusberat, plusberat, fotoktp;
    EditText etnik, ettempat, ettanggal;
    BaseApiService mApiService;
    TextView tinggi, berat, tvprov, tvcity, tvkecamata, tvkodepos, tvdetilalamat;
    RelativeLayout rlprov, rlcity, rlkecamatan, rlkodepos, rldetilalamat;
    String provinsi_name_show, provinsi_id_show, city_name_show, city_id_show, kecamatan_name_show, kecamatan_id_show, postal_name_show, postal_id_show, rincianshowpref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_diri);
        pref = new SecuredPreference(DataDiriActivity.this, PrefContract.PREF_EL);
        mApiService = UtilsApi.getAPIService();
        save = findViewById(R.id.btn_save);


        back = findViewById(R.id.backbutton);
        back.setOnClickListener(v -> {

            finish();
        });
        etnik = findViewById(R.id.et_nik);
        fotoktp = findViewById(R.id.hasilfoto);
        ettempat = findViewById(R.id.et_tempat);
        ettanggal = findViewById(R.id.et_tanggallahir);
        opsipria = findViewById(R.id.opsipria);
        opsiwanita = findViewById(R.id.opsiwanita);
        tinggi = findViewById(R.id.tinggi);
        berat = findViewById(R.id.berat);
        minusberat = findViewById(R.id.btn_minusberat);
        plusberat = findViewById(R.id.btn_plusberat);
        rlprov = findViewById(R.id.rlprovince);
        rlcity = findViewById(R.id.rlcity);
        rlkecamatan = findViewById(R.id.rlkecamatan);
        rlkodepos = findViewById(R.id.rl_postcode);
        rldetilalamat = findViewById(R.id.rldetilalamat);
        tvprov = findViewById(R.id.tv_provinsi_show);
        tvcity = findViewById(R.id.tv_city_show);
        tvkecamata = findViewById(R.id.tv_kecamatan_show);
        tvkodepos = findViewById(R.id.tv_postcode_show);
        tvdetilalamat = findViewById(R.id.tv_detil_alamat);


        try {
            city_name_show = pref.getString(PrefContract.city_name_choose, "Kota/Kabupaten");
            city_id_show = pref.getString(PrefContract.city_id_choose, "");
            provinsi_name_show = pref.getString(PrefContract.province_name_choose, "Provinsi");
            provinsi_id_show = pref.getString(PrefContract.province_id_choose, "");
            kecamatan_id_show = pref.getString(PrefContract.districts_id_choose, "");
            kecamatan_name_show = pref.getString(PrefContract.districts_name_choose, "Kecamatan");
            postal_id_show = pref.getString(PrefContract.postal_id_choose, "");
            postal_name_show = pref.getString(PrefContract.postal_name_choose, "Kode Pos");
            rincianshowpref = pref.getString(PrefContract.rincian_show, "Rincian Alamat");

        } catch (AppException e) {
            e.printStackTrace();
        }


        tvcity.setText(city_name_show);
        tvprov.setText(provinsi_name_show);
        tvkecamata.setText(kecamatan_name_show);
        tvkodepos.setText(postal_name_show);
        tvdetilalamat.setText(rincianshowpref);


        minusberat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                intberat = Integer.parseInt(berat.getText().toString());
                if (intberat <= 20) {
                    berat.setText(intberat.toString());
                } else {
                    intberat--;
                    berat.setText(intberat.toString());
                }
            }
        });
        plusberat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intberat = Integer.parseInt(berat.getText().toString());
                intberat++;
                berat.setText(intberat.toString());

            }
        });


        rlkodepos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setpref();
                Intent intent = new Intent(DataDiriActivity.this, ListPostal.class);
                startActivity(intent);

            }
        });
        rlkecamatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setpref();
                Intent intent = new Intent(DataDiriActivity.this, ListKecamatan.class);

                startActivity(intent);

            }
        });
        rldetilalamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                setpref();

                Intent intent = new Intent(DataDiriActivity.this, RincianAlamat.class);

                startActivity(intent);

            }
        });
        rlprov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setpref();
                Intent intent = new Intent(DataDiriActivity.this, ListProvince.class);

                startActivity(intent);

            }
        });
        rlcity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setpref();
                Intent intent = new Intent(DataDiriActivity.this, ListCity.class);

                startActivity(intent);

            }
        });


        try {
            uid = pref.getString(PrefContract.user_id, "");
        } catch (AppException e) {
            e.printStackTrace();
        }
        getdatadiri();

        save.setOnClickListener(v -> {

            updatedata();

        });
    }

    private void updatedata() {

        String we = berat.getText().toString();
        mApiService.updatedatadiri(uid, we, provinsi_id_show, city_id_show, kecamatan_id_show, postal_id_show, rincianshowpref).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {

                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
                        //String pesan = jsonRESULTS.getString("success");

                        String pesanError = jsonRESULTS.getString("code");

                        if (pesanError.equals("200")) {
                            Toast.makeText(DataDiriActivity.this, "Berhasil update data", Toast.LENGTH_SHORT).show();
                            Log.d("create uid job", "onResponse: ");

                            finish();
                        } else {
                            Toast.makeText(DataDiriActivity.this, "Gagal update data", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                } else {

                    Toast.makeText(DataDiriActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(DataDiriActivity.this, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void setpref() {
        try {
            pref.put(PrefContract.user_nik, etnik.getText().toString());


            pref.put(PrefContract.user_birthp, ettempat.getText().toString());
            pref.put(PrefContract.user_birthd, ettanggal.getText().toString());
        } catch (AppException e) {
            e.printStackTrace();
        }
    }

    private void getdatadiri() {

        mApiService.getdatadiri(uid).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
                        //String pesan = jsonRESULTS.getString("success");

                        JSONArray userId = jsonRESULTS.getJSONArray("DataDiri");
                        JSONObject obj = userId.getJSONObject(0);
                        String nik_number = obj.getString("nik_number");
                        String birth_date_applicant = obj.getString("birth_date_applicant");
                        String gender_applicant = obj.getString("gender_applicant");
                        String birth_at_applicant = obj.getString("birth_at_applicant");
                        String height_applicant = obj.getString("height_applicant");
                        String weight_applicant = obj.getString("weight_applicant");
                        JSONObject prov = obj.getJSONObject("province_applicant");
                        String prov_name = prov.getString("prov_name");
                        JSONObject city = obj.getJSONObject("city_applicant");
                        String city_name = city.getString("city_name");
                        JSONObject districts = obj.getJSONObject("districts_applicant");
                        String dis_name = districts.getString("dis_name");
                        String selfie_ktp_applicant = obj.getString("selfie_ktp_applicant");

                        String zip_code_applicant = obj.getString("zip_code_applicant");
                        String address_details_applicant = obj.getString("address_details_applicant");
                        etnik.setText(nik_number);
                        ettanggal.setText(birth_date_applicant);
                        tinggi.setText(height_applicant);
                        berat.setText(weight_applicant);
                        ettempat.setText(birth_at_applicant);
                        tvdetilalamat.setText(address_details_applicant);
                        Picasso.get()
                                .load(DataDiriActivity.this.getResources().getString(R.string.base_url_selfiektp) + selfie_ktp_applicant)
                                .into(fotoktp);
                        if (gender_applicant.equals("Pria")) {
                            opsiwanita.setVisibility(View.INVISIBLE);
                        } else if (gender_applicant.equals("Wanita")) {
                            opsipria.setVisibility(View.INVISIBLE);
                        }
                        tvprov.setText(prov_name);
                        tvcity.setText(city_name);
                        tvkodepos.setText(zip_code_applicant);
                        tvkecamata.setText(dis_name);


                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                } else {

                    Toast.makeText(DataDiriActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(DataDiriActivity.this, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        try {
            city_name_show = pref.getString(PrefContract.city_name_choose, "Kota/Kabupaten");
            city_id_show = pref.getString(PrefContract.city_id_choose, "");
            provinsi_name_show = pref.getString(PrefContract.province_name_choose, "Provinsi");
            provinsi_id_show = pref.getString(PrefContract.province_id_choose, "");
            kecamatan_id_show = pref.getString(PrefContract.districts_id_choose, "");
            kecamatan_name_show = pref.getString(PrefContract.districts_name_choose, "Kecamatan");
            postal_id_show = pref.getString(PrefContract.postal_id_choose, "");
            postal_name_show = pref.getString(PrefContract.postal_name_choose, "Kode Pos");
            rincianshowpref = pref.getString(PrefContract.rincian_show, "Rincian Alamat");

        } catch (AppException e) {
            e.printStackTrace();
        }

        tvcity.setText(city_name_show);
        tvprov.setText(provinsi_name_show);
        tvkecamata.setText(kecamatan_name_show);
        tvkodepos.setText(postal_name_show);
        tvdetilalamat.setText(rincianshowpref);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}