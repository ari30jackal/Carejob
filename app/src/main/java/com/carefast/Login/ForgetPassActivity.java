package com.carefast.Login;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.carefast.Login.LoginActivity;
import com.carefast.Login.R;
import com.carefast.apihelper.BaseApiService;
import com.carefast.apihelper.UtilsApi;
import com.carefast.contract.AppException;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ForgetPassActivity extends AppCompatActivity {
    Button btnForgetPass;
    BaseApiService mApiService;
    ProgressDialog loading;
    EditText email;


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);

        mApiService = UtilsApi.getAPIService();

        email = findViewById(R.id.etPhone);
        btnForgetPass = findViewById(R.id.btnForgetPass);

        btnForgetPass.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btnForgetPass.setBackgroundResource(R.color.red_btn_bg_color);
                if (email.getText().toString().isEmpty()) {
                    Toast.makeText(ForgetPassActivity.this, "Please Input Your Email", Toast.LENGTH_SHORT).show();
                } else {
                    register();

                }

            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    public void register() {


        mApiService.forgetPass(email.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {

                            //                                Toast.makeText(ForgetPassActivity.this, "Email Sudah Terkirim", Toast.LENGTH_SHORT).show();
//                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                            //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
//                                String pesanError = jsonRESULTS.getString("message");
//                                String pesanError = jsonRESULTS.getString("status");
//                                if (pesanError.equals("success")) {
////                                    String userId = jsonRESULTS.getJSONObject("data").getString("user_id");
////                                    String token = jsonRESULTS.getString("token");
//

//                                        Toast.makeText(mContext, "" + pesan + " " + userId, Toast.LENGTH_SHORT).show();
//                                        //Log.d("logggIntent", "Berhasil abis Toast");
////                                    pref.put(PrefContract.PREF_TOKEN, token);
////                                    pref.put(PrefContract.user_id, userId);
////                                    pref.put(PrefContract.check_login, "true");
                            Toast.makeText(ForgetPassActivity.this, "Reset Password Success. Please Check your email", Toast.LENGTH_SHORT).show();

//                                        Intent intent = new Intent(ForgetPassActivity.this, HomeActivity.class);
//                                        startActivity(intent);
//                                        finish();


                            //finish();
//                                } else {
//                                    Toast.makeText(ForgetPassActivity.this, "error bizelmail", Toast.LENGTH_SHORT).show();
//                                        Toast.makeText(ForgetPassActivity.this, "Reset password gagal.", Toast.LENGTH_SHORT).show();
//                                }
//

                        } else {
                            Toast.makeText(ForgetPassActivity.this, "cek kembali email anda", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());

                    }
                });


    }

    public void displayExceptionMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onResume() {
        super.onResume();
        // put your code here...

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ForgetPassActivity.this, LoginActivity.class));
        finish();

    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


}
