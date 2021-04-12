package com.carefast.menuprofile;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.carefast.Login.R;
import com.carefast.apihelper.BaseApiService;
import com.carefast.apihelper.UtilsApi;
import com.carefast.contract.AppException;
import com.carefast.contract.PrefContract;
import com.carefast.contract.SecuredPreference;
import com.carefast.home.HomeeActivity;
import com.carefast.register.TahapDuaActivity;
import com.carefast.register.TahapSatuActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class KeamananAkunActivity extends AppCompatActivity {
    EditText etPassOld, etPassNew, etPassNew2;
    BaseApiService mApiService;
    String userid;
    SecuredPreference pref;
    Button save,cancel;
    ImageView back;
    private Object HomeeActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keamanan_akun);


        mApiService = UtilsApi.getAPIService();
        pref = new SecuredPreference(this, PrefContract.PREF_EL);

        try {
            userid = pref.getString(PrefContract.user_id, "");
        } catch (AppException e) {
            e.printStackTrace();
        }

        Log.d("userid keamanan", "onCreate: " + userid);

        etPassNew =  findViewById(R.id.passNew);
        etPassNew2 =  findViewById(R.id.passNew2);
        etPassOld =  findViewById(R.id.passOld);
        save = findViewById(R.id.savePass);

        back = findViewById(R.id.backbutton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkOldPass(etPassOld.getText().toString());
                Log.d("oldpass", "onClick: " + etPassOld.getText().toString());
            }
        });




    }

    private void checkOldPass(String passold) {
        mApiService.checkOldPass(userid, passold)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {

                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());

                                String pesanError = jsonRESULTS.getString("code");

                                if (pesanError.equals("200")) {
                                    if (etPassNew.getText().toString().equals(etPassNew2.getText().toString())){
                                        changePass(etPassNew.getText().toString());

                                    }else if (etPassNew.getText().toString().equals("")){
                                        Toast.makeText(KeamananAkunActivity.this, "Isi Kata Sandi Baru terlebih dahulu", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(KeamananAkunActivity.this, "Kata Sandi tidak sama", Toast.LENGTH_SHORT).show();
                                    }

                                    Log.d("betul oldpass", "onResponse: ");

                                } else if (pesanError.equals("400")){
                                    Toast.makeText(KeamananAkunActivity.this, "Kata Sandi lama anda salah.", Toast.LENGTH_SHORT).show();
                                    Log.d("salah oldpass", "onResponse: ");
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

    private void changePass(String newPass) {
        mApiService.changePass(userid, newPass)
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {

                                try {
                                    JSONObject jsonRESULTS = new JSONObject(response.body().string());

                                    String pesanError = jsonRESULTS.getString("code");
                                    String pesan = jsonRESULTS.getString("message");

                                    if (pesanError.equals("200")) {

                                    finish();
                                    } else if (pesanError.equals("300")){
                                        Toast.makeText(KeamananAkunActivity.this, "Kata Sandi Baru harus memiliki minimal 6 karakter", Toast.LENGTH_SHORT).show();
                                        Log.d("fail update", "onResponse: ");
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

    }