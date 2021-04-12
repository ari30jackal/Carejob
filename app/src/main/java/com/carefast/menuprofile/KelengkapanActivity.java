package com.carefast.menuprofile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class KelengkapanActivity extends AppCompatActivity {
    Button lanjut;
    ImageView kembali;
    RadioGroup rgTindik, rgTato, rgKawin;
    String selectedText,selectedText2,selectedText3;
    SecuredPreference pref;
    BaseApiService mApiService;
    private String selectedType="";
    String passing_uid;
    LinearLayout ll_skck, ll_bpkb, ll_sio, ll_sim, ll_serti;
    Bitmap bitmap, bitmapsio, bitmapcerti, bitmapsim,bitmapbpkb;
    ImageView skck,sio,certi,sim,bpkb;

    Button btn_skck,btn_sio,btn_certi,btn_sim,btn_bpkb;
    String validation, validationsio, validationcerti, validationsim,validationbpkb;
    ImageView shadowskck,shadowsio,shadowcerti,shadowsim,shadowbpkb;
    String selectedTato, selectedPiercing, selectedMarried;
    TextView tglskck,tglsim,tglsio,tglbpkb,tglserti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelengkapan);
        pref = new SecuredPreference(this, PrefContract.PREF_EL);
        lanjut = findViewById(R.id.saveKelengkapan);
        kembali = findViewById(R.id.backbutton);
        rgTindik = findViewById(R.id.rg_tindik);
        rgTato = findViewById(R.id.rg_tato);
        rgKawin = findViewById(R.id.rg_kawin);
        ll_bpkb = findViewById(R.id.ll_bpkb);
        ll_skck = findViewById(R.id.ll_skck);
        ll_sim = findViewById(R.id.ll_sim);
        ll_sio = findViewById(R.id.ll_sio);
        ll_serti = findViewById(R.id.ll_serti);
        btn_skck = findViewById(R.id.btn_skck);
        btn_sio = findViewById(R.id.btn_sio);
        btn_certi = findViewById(R.id.btn_serti);
        btn_sim = findViewById(R.id.btn_sim);
        btn_bpkb = findViewById(R.id.btn_bpkb);

        skck = findViewById(R.id.skck);
        sio = findViewById(R.id.sio);
        certi = findViewById(R.id.serti);
        sim = findViewById(R.id.sim);
        bpkb = findViewById(R.id.bpkb);
        shadowskck = findViewById(R.id.shadowskck);
        shadowsio = findViewById(R.id.shadowsio);
        shadowcerti = findViewById(R.id.shadowcerti);
        shadowsim = findViewById(R.id.shadowsim);
        shadowbpkb = findViewById(R.id.shadowbpkb);
        tglbpkb = findViewById(R.id.tanggalbpkb);
        tglserti = findViewById(R.id.tanggalserti);
        tglsim =  findViewById(R.id.tanggalsim);
        tglsio = findViewById(R.id.tanggalsio);
        tglskck = findViewById(R.id.tanggalskck);



        kembali.setOnClickListener(v->{
            onBackPressed();
        });
        mApiService = UtilsApi.getAPIService();

        try {
            passing_uid = pref.getString(PrefContract.user_id, "");
        } catch (AppException e) {
            e.printStackTrace();
        }

        Log.d("uidnya kelengkapan", "onCreate: " + passing_uid);

        btn_skck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                panggilgambar();
            }
        });

        btn_sio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                panggilgambar2();
            }
        });


        btn_certi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                panggilgambar3();
            }
        });

        btn_sim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                panggilgambar4();
            }
        });

        btn_bpkb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                panggilgambar5();
            }
        });


        getDataKelengkapan();
        getDataKelengkapan2();
        getDataKelengkapan3();
        getDataKelengkapanTgl();



        rgTato.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radioButtonID = rgTato.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) rgTato.findViewById(radioButtonID);
                selectedText = String.valueOf(radioButton.getText());



                if (selectedText.equals("Tidak")){
                    selectedTato = "No";
                }else if (selectedText.equals("Ya")){
                    selectedTato = "Required";
                }else if (selectedText.equals("")){
                    selectedTato = "";
                }

                Log.d("married", "onCheckedChanged: " + selectedTato);


//                Toast.makeText(KelengkapanActivity.this,  selectedText, Toast.LENGTH_SHORT).show();


//                switch(checkedId){
//                    case R.id.rb_ya:
//                        // do operations specific to this selection
//                        Log.d("ya", "onCheckedChanged: ");
//
//                        break;
//                    case R.id.rb_tidak:
//                        // do operations specific to this selection
//
//                        Log.d("tidak", "onCheckedChanged: ");
//
//                        break;
//                }
            }
        });


        rgTindik.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radioButtonID = rgTindik.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) rgTindik.findViewById(radioButtonID);
                selectedText2 = String.valueOf(radioButton.getText());

//                Toast.makeText(KelengkapanActivity.this,  selectedText2, Toast.LENGTH_SHORT).show();

                Log.d("pass", "onCheckedChanged: " + selectedText2);




                if (selectedText2.equals("Tidak")){
                    selectedPiercing = "No";
                }else if (selectedText2.equals("Ya")){
                    selectedPiercing = "Required";
                }else if (selectedText2.equals("")){
                    selectedPiercing = "";
                }
//                switch(checkedId){
//                    case R.id.rb_1:
//                        // do operations specific to this selection
//                        Log.d("ya", "onCheckedChanged: ");
//                        break;
//                    case R.id.rb_2:
//                        // do operations specific to this selection
//
//                        Log.d("tidak", "onCheckedChanged: ");
//                        break;
//                }
            }
        });


        rgKawin.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radioButtonID = rgKawin.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) rgKawin.findViewById(radioButtonID);
                selectedText3 = String.valueOf(radioButton.getText());




                if (selectedText3.equals("Belum Kawin")){
                    selectedMarried = "Single";
                }else if (selectedText3.equals("Sudah Kawin")){
                    selectedMarried = "Married";
                }else if (selectedText3.equals("")){
                    selectedMarried = "";
                }

                Log.d("married", "onCheckedChanged: " + selectedMarried);

//                Toast.makeText(KelengkapanActivity.this,  selectedText3, Toast.LENGTH_SHORT).show();

//                switch(checkedId){
//                    case R.id.rb_sudah:
//                        // do operations specific to this selection
//                        Log.d("ya", "onCheckedChanged: ");
//                        break;
//                    case R.id.rb_belum:
//                        // do operations specific to this selection
//
//                        Log.d("tidak", "onCheckedChanged: ");
//                        break;
//                }
            }
        });


        //bikin validasi supaya bisa ditentuin foto apa aja yg harus diupload
        getValidation();

        lanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validation = "0";
                validationsio = "0";
                validationcerti = "0";
                validationsim = "0";
                validationbpkb = "0";

                if (ll_skck.getVisibility() == View.VISIBLE) {
                    if (shadowskck.getVisibility() == View.GONE) {
                        Intent i = new Intent(KelengkapanActivity.this, HomeeActivity.class);
                        startActivity(i);
                        Toast.makeText(KelengkapanActivity.this, "update data success", Toast.LENGTH_SHORT).show();
                    } else {
                        bitmap = ((BitmapDrawable) skck.getDrawable()).getBitmap();
                        regisFifth_skck(bitmap);
                        validation = "1";
                    }

                }else{
                    validation = "1";
                }

                if (ll_sio.getVisibility() == View.VISIBLE) {
                    if (shadowsio.getVisibility() == View.GONE) {
                        Intent i = new Intent(KelengkapanActivity.this, HomeeActivity.class);
                        startActivity(i);
                        Toast.makeText(KelengkapanActivity.this, "update data success", Toast.LENGTH_SHORT).show();
                    } else {
                        bitmapsio = ((BitmapDrawable) sio.getDrawable()).getBitmap();
                        regisFifth_sio(bitmapsio);
                        validationsio = "1";
                    }
                }else {
                    validationsio = "1";
                }

                if (ll_serti.getVisibility() == View.VISIBLE) {

                    if (shadowcerti.getVisibility() == View.GONE) {

                        Intent i = new Intent(KelengkapanActivity.this, HomeeActivity.class);
                        startActivity(i);
                        Toast.makeText(KelengkapanActivity.this, "update data success", Toast.LENGTH_SHORT).show();
                    } else {
                        bitmapcerti = ((BitmapDrawable) certi.getDrawable()).getBitmap();
                        regisFifth_certi(bitmapcerti);
                        validationcerti = "1";
                    }
                }else{
                    validationcerti = "1";
                }

                if (ll_sim.getVisibility() == View.VISIBLE) {
                    if (shadowsim.getVisibility() == View.GONE) {

                        Intent i = new Intent(KelengkapanActivity.this, HomeeActivity.class);
                        startActivity(i);
                        Toast.makeText(KelengkapanActivity.this, "update data success", Toast.LENGTH_SHORT).show();
                    } else {
                        bitmapsim = ((BitmapDrawable) sim.getDrawable()).getBitmap();
                        regisFifth_sim(bitmapsim);
                        validationsim = "1";
                    }

                }else {
                    validationsim = "1";
                }

                if (ll_bpkb.getVisibility() == View.VISIBLE) {
                    if (shadowbpkb.getVisibility() == View.GONE) {

                        Intent i = new Intent(KelengkapanActivity.this, HomeeActivity.class);
                        startActivity(i);
                        Toast.makeText(KelengkapanActivity.this, "update data success", Toast.LENGTH_SHORT).show();
                    } else {
                        bitmapbpkb = ((BitmapDrawable) bpkb.getDrawable()).getBitmap();
                        regisFifth_bpkb(bitmapbpkb);
                        validationbpkb = "1";
                    }
                }else {
                    validationbpkb = "1";
                }


                if (validation.equals("1") && validationsim.equals("1") && validationcerti.equals("1") && validationsio.equals("1")
                        && validationbpkb.equals("1")){
                    regisFifth();
                }else{
                    Log.d("validation failed", "onClick: ");
                }


                Log.d("selectnyanih", "onCreate: " + selectedText2 + selectedText3 + selectedText);
            }
        });



    }

    private void getDataKelengkapan() {
        mApiService.getDataKelengkapan(passing_uid)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
//                            loading.dismiss();

                            try {
//                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
//                                //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
//

                                JSONObject json= (JSONObject) new JSONTokener(response.body().string()).nextValue();
                                JSONObject json2 = json.getJSONObject("data");
                                String tato = (String) json2.get("have_tatoo_applicant");


//                                JSONObject jsons= (JSONObject) new JSONTokener(response.body().string()).nextValue();
//                                JSONObject jsons2 = jsons.getJSONObject("data");
//                                String marit = (String) jsons2.get("marital_status_applicant");
//

//                                JSONObject jsonss= (JSONObject) new JSONTokener(response.body().string()).nextValue();
//                                JSONObject jsonss2 = jsonss.getJSONObject("data");
//                                String ear = (String) json.get("ear_piercing_applicant");




                                String pesanError = json.getString("code");


                                if (pesanError.equals("200")) {
                                    Log.d("a", "onResponse: "+"aaaa");
                                    if (tato.equals("No")){


                                        rgTato.check(R.id.rb_tidak);
                                        Log.d("get data kelengkapan bener", "onResponse: ");
                                    }else if (tato.equals("Required")){

                                        rgTato.check(R.id.rb_ya);
                                        Log.d("get data kelengkapan", "onResponse: ");
                                    }

//                                    if (marit.equals("Single")){
//
//                                        rgKawin.check(R.id.rb_belumk);
//                                        Log.d("get data kelengkapan bener", "onResponse: ");
//                                    }else if (marit.equals("Married")){
//
//                                        rgKawin.check(R.id.rb_sudahk);
//                                        Log.d("get data kelengkapan", "onResponse: ");
//                                    }
//
//                                    if (ear.equals("No")){
//
//                                        rgTindik.check(R.id.rb_1k);
//                                        Log.d("get data kelengkapan bener", "onResponse: ");
//                                    }else if (ear.equals("Required")){
//
//                                        rgTindik.check(R.id.rb_2k);
//                                        Log.d("get data kelengkapan", "onResponse: ");
//                                    }
//


                                } else {
                                    Log.d("get data error", "onResponse: ");
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


    private void getDataKelengkapan2() {
        mApiService.getDataKelengkapan(passing_uid)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
//                            loading.dismiss();

                            try {
//                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
//                                //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
//

//                                JSONObject json= (JSONObject) new JSONTokener(response.body().string()).nextValue();
//                                JSONObject json2 = json.getJSONObject("data");
//                                String tato = (String) json2.get("have_tatoo_applicant");


                                JSONObject jsons= (JSONObject) new JSONTokener(response.body().string()).nextValue();
                                JSONObject jsons2 = jsons.getJSONObject("data");
                                String marit = (String) jsons2.get("marital_status_applicant");


//                                JSONObject jsonss= (JSONObject) new JSONTokener(response.body().string()).nextValue();
//                                JSONObject jsonss2 = jsonss.getJSONObject("data");
//                                String ear = (String) json.get("ear_piercing_applicant");




                                String pesanError = jsons.getString("code");


                                if (pesanError.equals("200")) {
                                    Log.d("a", "onResponse: "+"aaaa");
//                                    if (tato.equals("No")){
//
//
//                                        rgTato.check(R.id.rb_tidak);
//                                        Log.d("get data kelengkapan bener", "onResponse: ");
//                                    }else if (tato.equals("Required")){
//
//                                        rgTato.check(R.id.rb_ya);
//                                        Log.d("get data kelengkapan", "onResponse: ");
//                                    }

                                    if (marit.equals("Single")){

                                        rgKawin.check(R.id.rb_belumk);
                                        Log.d("get data kelengkapan bener", "onResponse: ");
                                    }else if (marit.equals("Married")){

                                        rgKawin.check(R.id.rb_sudahk);
                                        Log.d("get data kelengkapan", "onResponse: ");
                                    }

//                                    if (ear.equals("No")){
//
//                                        rgTindik.check(R.id.rb_1k);
//                                        Log.d("get data kelengkapan bener", "onResponse: ");
//                                    }else if (ear.equals("Required")){
//
//                                        rgTindik.check(R.id.rb_2k);
//                                        Log.d("get data kelengkapan", "onResponse: ");
//                                    }
//


                                } else {
                                    Log.d("get data error", "onResponse: ");
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

    private void getDataKelengkapan3() {
        mApiService.getDataKelengkapan(passing_uid)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
//                            loading.dismiss();

                            try {
//                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
//                                //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
//

//                                JSONObject json= (JSONObject) new JSONTokener(response.body().string()).nextValue();
//                                JSONObject json2 = json.getJSONObject("data");
//                                String tato = (String) json2.get("have_tatoo_applicant");


//                                JSONObject jsons= (JSONObject) new JSONTokener(response.body().string()).nextValue();
//                                JSONObject jsons2 = jsons.getJSONObject("data");
//                                String marit = (String) jsons2.get("marital_status_applicant");


                                JSONObject jsonss= (JSONObject) new JSONTokener(response.body().string()).nextValue();
                                JSONObject jsonss2 = jsonss.getJSONObject("data");
                                String ear = (String) jsonss2.get("ear_piercing_applicant");




                                String pesanError = jsonss.getString("code");


                                if (pesanError.equals("200")) {
                                    Log.d("a", "onResponse: "+"aaaa");
//                                    if (tato.equals("No")){
//
//
//                                        rgTato.check(R.id.rb_tidak);
//                                        Log.d("get data kelengkapan bener", "onResponse: ");
//                                    }else if (tato.equals("Required")){
//
//                                        rgTato.check(R.id.rb_ya);
//                                        Log.d("get data kelengkapan", "onResponse: ");
//                                    }

//                                    if (marit.equals("Single")){
//
//                                        rgKawin.check(R.id.rb_belumk);
//                                        Log.d("get data kelengkapan bener", "onResponse: ");
//                                    }else if (marit.equals("Married")){
//
//                                        rgKawin.check(R.id.rb_sudahk);
//                                        Log.d("get data kelengkapan", "onResponse: ");
//                                    }

                                    if (ear.equals("No")){

                                        rgTindik.check(R.id.rb_1k);
                                        Log.d("get data kelengkapan bener", "onResponse: ");
                                    }else if (ear.equals("Required")){

                                        rgTindik.check(R.id.rb_2k);
                                        Log.d("get data kelengkapan", "onResponse: ");
                                    }



                                } else {
                                    Log.d("get data error", "onResponse: ");
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


    private void getValidation() {
        mApiService.getValidationStepFifth(passing_uid)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
//                            loading.dismiss();

                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();

                                String pesanError = jsonRESULTS.getString("code");
//                                String certificate_employment_criteria = jsonRESULTS.getString("certificate_employment_criteria");
                                String sim_photo_criteria = jsonRESULTS.getString("sim_photo_criteria");
                                String sio_photo_criteria = jsonRESULTS.getString("sio_photo_criteria");
                                String certificate_competency_criteria = jsonRESULTS.getString("certificate_competency_criteria");
                                String upload_skck_criteria = jsonRESULTS.getString("upload_skck_criteria");
                                String upload_stnk_criteria = jsonRESULTS.getString("upload_stnk_criteria");



                                if (pesanError.equals("200")) {

                                    Log.d("get Validation", "onResponse: " + jsonRESULTS );



//                                    if(certificate_employment_criteria.equals("exist"))
//                                    {
//                                        ll_serti.setVisibility(View.VISIBLE);
//                                    }else{
//                                        ll_serti.setVisibility(View.GONE);
//                                    }


                                    if(sim_photo_criteria.equals("exist"))
                                    {
                                        ll_sim.setVisibility(View.VISIBLE);
                                    }else{
                                        ll_sim.setVisibility(View.GONE);
                                    }

                                    if(sio_photo_criteria.equals("exist"))
                                    {
                                        ll_sio.setVisibility(View.VISIBLE);
                                    }else{
                                        ll_sio.setVisibility(View.GONE);
                                    }

                                    if(certificate_competency_criteria.equals("exist"))
                                    {
                                        ll_serti.setVisibility(View.VISIBLE);
                                    }else{
                                        ll_serti.setVisibility(View.GONE);
                                    }

                                    if(upload_stnk_criteria.equals("exist"))
                                    {
                                        ll_bpkb.setVisibility(View.VISIBLE);
                                    }else{
                                        ll_bpkb.setVisibility(View.GONE);
                                    }

                                    if(upload_skck_criteria.equals("exist"))
                                    {
                                        ll_skck.setVisibility(View.VISIBLE);
                                    }else{
                                        ll_skck.setVisibility(View.GONE);
                                    }


                                } else {
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





    private void regisFifth() {

        ProgressDialog loading = new ProgressDialog(this);
        loading.setTitle("Loading");
        loading.setMessage("Wait while loading...");
        loading.setCancelable(false); // disable dismiss by tapping outside of the dialog
        loading.show();


        Log.d("seleeeeccct", "regisFifth: " +selectedTato+selectedMarried+selectedPiercing);

        mApiService.stepFifth(passing_uid, selectedTato, selectedPiercing, selectedMarried)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            loading.dismiss();

                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();

                                String pesanError = jsonRESULTS.getString("code");


                                if (pesanError.equals("200")) {
                                    try {
                                        pref.put(PrefContract.user_id, "");
                                        pref.put(PrefContract.phone, "");
                                        pref.put(PrefContract.password, "");
                                        pref.put(PrefContract.email, "");


                                        pref.put(PrefContract.province_id_choose,"");
                                        pref.put(PrefContract.city_id_choose,"");
                                        pref.put(PrefContract.province_name_choose,"");
                                        pref.put(PrefContract.city_name_choose,"");
                                        pref.put(PrefContract.districts_name_choose,"");
                                        pref.put(PrefContract.rincian_show,"");
                                        pref.put(PrefContract.districts_id_choose,"");
                                    } catch (AppException e) {
                                        e.printStackTrace();
                                    }
//                                    active();
                                    Intent intent = new Intent(KelengkapanActivity.this, HomeeActivity.class);
                                    startActivity(intent);
                                    finish();
                                    Toast.makeText(KelengkapanActivity.this, "Update Successfully", Toast.LENGTH_SHORT).show();

                                    Log.d("step fifth data", "Successfully: " + jsonRESULTS );

                                    loading.dismiss();
                                } else if (pesanError.equals("301")){
                                    loading.dismiss();
                                    Toast.makeText(KelengkapanActivity.this, "Please fill the form first.", Toast.LENGTH_SHORT).show();
                                    Log.d("error form", "onResponse: ");
                                }else {
                                    Toast.makeText(KelengkapanActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                    Log.d("error", "onResponse: ");
                                }

                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }

                        } else {
                            loading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                        loading.dismiss();
                    }
                });


    }
//
//    private void active() {
//
//        mApiService.isactive(passing_uid)
//                .enqueue(new Callback<ResponseBody>() {
//                    @Override
//                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                        if (response.isSuccessful()) {
////                            loading.dismiss();
//
//                            try {
//                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
//                                //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
//
//                                String pesanError = jsonRESULTS.getString("code");
//
//                                if (pesanError.equals("200")) {
//
//
//
//                                } else {
//                                    Log.d("error", "onResponse: ");
//                                }
//
//                            } catch (JSONException | IOException e) {
//                                e.printStackTrace();
//                            }
//
//                        } else {
////                            loading.dismiss();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//                        //Log.e("debug", "onFailure: ERROR > " + t.toString());
////                        loading.dismiss();
//                    }
//                });
//
//    }


    private void regisFifth_skck(Bitmap bitmap) {

        //ngupdate data form 5/5

        File file = createTempFile(bitmap);
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part image = MultipartBody.Part.createFormData("images", file.getName(), reqFile);

        RequestBody uidRB =
//                RequestBody.create(MediaType.parse("multipart/form-data"), passing_uid);
                RequestBody.create(MediaType.parse("multipart/form-data"), passing_uid);


        mApiService.stepFiveSkck(uidRB, image)
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

                                    Log.d("get Datas", "onResponse: " + jsonRESULTS );

                                } else {
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


    private void regisFifth_sio(Bitmap bitmap) {

        //ngupdate data form 5/5

        File file = createTempFiles(bitmap);
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part image = MultipartBody.Part.createFormData("images", file.getName(), reqFile);

        RequestBody uidRB =
//                RequestBody.create(MediaType.parse("multipart/form-data"), passing_uid);
                RequestBody.create(MediaType.parse("multipart/form-data"), passing_uid);


        mApiService.stepFiveSio(uidRB, image)
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

                                    Log.d("get Datas", "onResponse: " + jsonRESULTS );

                                } else {
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



    private void regisFifth_certi(Bitmap bitmap) {

        //ngupdate data form 5/5

        File file = createTempFilesCerti(bitmap);
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part image = MultipartBody.Part.createFormData("images", file.getName(), reqFile);

        RequestBody uidRB =
//                RequestBody.create(MediaType.parse("multipart/form-data"), passing_uid);
                RequestBody.create(MediaType.parse("multipart/form-data"), passing_uid);


        mApiService.stepFiveCerti(uidRB, image)
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

                                    Log.d("get Datas", "onResponse: " + jsonRESULTS );

                                } else {
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


    private void regisFifth_sim(Bitmap bitmap) {

        //ngupdate data form 5/5

        File file = createTempFilesSim(bitmap);
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part image = MultipartBody.Part.createFormData("images", file.getName(), reqFile);

        RequestBody uidRB =
//                RequestBody.create(MediaType.parse("multipart/form-data"), passing_uid);
                RequestBody.create(MediaType.parse("multipart/form-data"), passing_uid);


        mApiService.stepFiveSim(uidRB, image)
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

                                    Log.d("get Datas", "onResponse: " + jsonRESULTS );

                                } else {
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


    private void regisFifth_bpkb(Bitmap bitmap) {

        //ngupdate data form 5/5

        File file = createTempFilesBpkb(bitmap);
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part image = MultipartBody.Part.createFormData("images", file.getName(), reqFile);

        RequestBody uidRB =
//                RequestBody.create(MediaType.parse("multipart/form-data"), passing_uid);
                RequestBody.create(MediaType.parse("multipart/form-data"), passing_uid);


        mApiService.stepFiveBpkb(uidRB, image)
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

                                    Log.d("get Datas", "onResponse: " + jsonRESULTS );

                                } else {
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

    private File createTempFile(Bitmap bitmap) {
//        File file = new File(KelengkapanActivity.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
//                , System.currentTimeMillis() + "_job.JPEG");
        File file = new File(KelengkapanActivity.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)  + "_skck"+passing_uid+".JPEG");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] bitmapdata = bos.toByteArray();
        //write the bytes in file

        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    private File createTempFiles(Bitmap bitmap) {
//        File file = new File(KelengkapanActivity.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
//                , System.currentTimeMillis() + "_job.JPEG");
        File file = new File(KelengkapanActivity.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)  + "_sio"+passing_uid+".JPEG");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] bitmapdata = bos.toByteArray();
        //write the bytes in file

        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }



    private File createTempFilesCerti(Bitmap bitmap) {
//        File file = new File(KelengkapanActivity.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
//                , System.currentTimeMillis() + "_job.JPEG");
        File file = new File(KelengkapanActivity.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)  + "_certi"+passing_uid+".JPEG");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] bitmapdata = bos.toByteArray();
        //write the bytes in file

        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }




    private File createTempFilesSim(Bitmap bitmap) {
//        File file = new File(KelengkapanActivity.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
//                , System.currentTimeMillis() + "_job.JPEG");
        File file = new File(KelengkapanActivity.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)  + "_sim"+passing_uid+".JPEG");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] bitmapdata = bos.toByteArray();
        //write the bytes in file

        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }



    private File createTempFilesBpkb(Bitmap bitmap) {
//        File file = new File(KelengkapanActivity.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
//                , System.currentTimeMillis() + "_job.JPEG");
        File file = new File(KelengkapanActivity.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)  + "_bpkb"+passing_uid+".JPEG");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] bitmapdata = bos.toByteArray();
        //write the bytes in file

        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }





    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();


                Bitmap bitmap = null;
                try {
                    bitmap = BitmapFactory.decodeStream(KelengkapanActivity.this.getContentResolver().openInputStream(uri));
                    int nh = (int) (bitmap.getHeight() * (512.0 / bitmap.getWidth()));

                    Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
                    skck.setImageBitmap(scaled);


                    Toast.makeText(KelengkapanActivity.this, "Berhasil Upload.", Toast.LENGTH_SHORT).show();


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                //pictureFlag = 1;

                //ivTemp.setImageBitmap(BitmapFactory.decodeFile(picturePath));

                shadowskck.setVisibility(View.GONE);
            } else {
                Toast.makeText(KelengkapanActivity.this, "Gagal Upload.", Toast.LENGTH_SHORT).show();
            }
        }else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();


                Bitmap bitmap = null;
                try {
                    bitmap = BitmapFactory.decodeStream(KelengkapanActivity.this.getContentResolver().openInputStream(uri));
                    int nh = (int) (bitmap.getHeight() * (512.0 / bitmap.getWidth()));

                    Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
                    sio.setImageBitmap(scaled);

                    Toast.makeText(KelengkapanActivity.this, "Berhasil Upload.", Toast.LENGTH_SHORT).show();


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                //pictureFlag = 1;

                //ivTemp.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                shadowsio.setVisibility(View.GONE);
            } else {
                Toast.makeText(KelengkapanActivity.this, "Gagal Upload.", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == 3) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();


                Bitmap bitmap = null;
                try {
                    bitmap = BitmapFactory.decodeStream(KelengkapanActivity.this.getContentResolver().openInputStream(uri));
                    int nh = (int) (bitmap.getHeight() * (512.0 / bitmap.getWidth()));

                    Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
                    certi.setImageBitmap(scaled);

                    Toast.makeText(KelengkapanActivity.this, "Berhasil Upload.", Toast.LENGTH_SHORT).show();


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                //pictureFlag = 1;

                //ivTemp.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                shadowcerti.setVisibility(View.GONE);
            } else {
                Toast.makeText(KelengkapanActivity.this, "Gagal Upload.", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == 4) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();


                Bitmap bitmap = null;
                try {
                    bitmap = BitmapFactory.decodeStream(KelengkapanActivity.this.getContentResolver().openInputStream(uri));
                    int nh = (int) (bitmap.getHeight() * (512.0 / bitmap.getWidth()));

                    Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
                    sim.setImageBitmap(scaled);

                    Toast.makeText(KelengkapanActivity.this, "Berhasil Upload.", Toast.LENGTH_SHORT).show();


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                //pictureFlag = 1;

                //ivTemp.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                shadowsim.setVisibility(View.GONE);
            } else {
                Toast.makeText(KelengkapanActivity.this, "Gagal Upload.", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == 5) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();


                Bitmap bitmap = null;
                try {
                    bitmap = BitmapFactory.decodeStream(KelengkapanActivity.this.getContentResolver().openInputStream(uri));
                    int nh = (int) (bitmap.getHeight() * (512.0 / bitmap.getWidth()));

                    Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
                    bpkb.setImageBitmap(scaled);

                    Toast.makeText(KelengkapanActivity.this, "Berhasil Upload.", Toast.LENGTH_SHORT).show();


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                //pictureFlag = 1;

                //ivTemp.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                shadowbpkb.setVisibility(View.GONE);
            } else {
                Toast.makeText(KelengkapanActivity.this, "Gagal Upload.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void panggilgambar() {

        ActivityCompat.requestPermissions(KelengkapanActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);

        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, 1);

    }


    private void panggilgambar2() {

        ActivityCompat.requestPermissions(KelengkapanActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                2);


        Intent i = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, 2);


    }

    private void panggilgambar3() {

        ActivityCompat.requestPermissions(KelengkapanActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                3);


        Intent i = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, 3);


    }


    private void panggilgambar4() {

        ActivityCompat.requestPermissions(KelengkapanActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                4);


        Intent i = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, 4);


    }

    private void panggilgambar5() {

        ActivityCompat.requestPermissions(KelengkapanActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                5);


        Intent i = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, 5);


    }



    private void getDataKelengkapanTgl() {
        mApiService.getDataKelengkapan(passing_uid)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
//                            loading.dismiss();

                            try {
//                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
//                                //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
//

                                JSONObject json= (JSONObject) new JSONTokener(response.body().string()).nextValue();
                                JSONObject json2 = json.getJSONObject("data");
                                String created = (String) json2.get("created_at_applicant");

                                DateFormat dateFormat1 = new SimpleDateFormat("dd/mm/yyyy");
                                String curDate= dateFormat1.format(new Date());



                                String pesanError = json.getString("code");


                                if (pesanError.equals("200")) {
                                    tglbpkb.setText(curDate);
                                    tglserti.setText(curDate);
                                    tglsim.setText(curDate);
                                    tglsio.setText(curDate);
                                    tglskck.setText(curDate);

                                } else {
                                    Log.d("get data error", "onResponse: ");
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

    //method dapetin ekstensi file
    private String getExtension(String filename) {
        String extension = "";
        String[] arr = filename.split("\\.");
        if(arr.length > 0) {
            extension = arr[arr.length - 1];
            return "."+extension;
        }else {
            return extension;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}