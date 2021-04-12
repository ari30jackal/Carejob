package com.carefast.register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.carefast.Login.LoginActivity;
import com.carefast.Login.R;
import com.carefast.apihelper.BaseApiService;
import com.carefast.apihelper.UtilsApi;
import com.carefast.contract.AppException;
import com.carefast.contract.PrefContract;
import com.carefast.contract.SecuredPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Import library

public class VerifActivity extends AppCompatActivity {
Button sudah;
TextView resend,emailuser;
SecuredPreference pref;
BaseApiService mApiService;
String passing_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verif);

        pref = new SecuredPreference(this, PrefContract.PREF_EL);
        sudah = findViewById(R.id.sudahverifemail);
        resend = findViewById(R.id.resendemail);
        mApiService = UtilsApi.getAPIService();
emailuser =findViewById(R.id.email_verif);
        try {
            passing_email = pref.getString(PrefContract.email,"");
        } catch (AppException e) {
            e.printStackTrace();
        }
emailuser.setText(" "+passing_email+" ");
        Log.d("email verif act", "onCreate: " + passing_email);

        sudah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            pref.clear();
                Intent i = new Intent(VerifActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendEmail();
            }
        });
    }

    private void resendEmail() {
        ProgressDialog loading = new ProgressDialog(this);
        loading.setTitle("Loading");
        loading.setMessage("Wait while loading...");
        loading.setCancelable(false); // disable dismiss by tapping outside of the dialog
        loading.show();

        mApiService.verifyMail(passing_email)
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

//                                    resend.setOnClickListener(new View.OnClickListener() {
//                                        public void onClick(View v) {
//                                            new SweetAlertDialog(VerifActivity.this, SweetAlertDialog.WARNING_TYPE)
//                                                    .setTitleText("Are you sure?")
//                                                    .setContentText("Won't be able to recover this file!")
//                                                    .setConfirmText("Yes,delete it!")
//                                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                                        @Override
//                                                        public void onClick(SweetAlertDialog sDialog) {
//                                                            sDialog
//                                                                    .setTitleText("Deleted!")
//                                                                    .setContentText("Your imaginary file has been deleted!")
//                                                                    .setConfirmText("OK")
//                                                                    .setConfirmClickListener(null)
//                                                                    .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
//                                                        }
//                                                    })
//                                                    .show();
//                                        }
//                                    });
//                                    Toast.makeText(VerifActivity.this, "Send email success", Toast.LENGTH_SHORT).show();


                                    new SweetAlertDialog(VerifActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                            .setTitleText("Berhasil!")
                                            .setContentText("Email verifikasi telah dikirim.")
                                            .setConfirmText("Siap!")
                                            .setConfirmClickListener(null).show();

                                    loading.dismiss();
                                } else {


                                    Toast.makeText(VerifActivity.this, "Send email failed, please try again.", Toast.LENGTH_SHORT).show();
                                    Log.d("error", "onResponse: ");
                                    loading.dismiss();
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
                        //Log.e("debug", "onFailure: ERROR > " + t.toString());
                        loading.dismiss();
                    }
                });

    }
}