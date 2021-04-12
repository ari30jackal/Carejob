package com.carefast.register;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.carefast.Login.R;
import com.carefast.apihelper.BaseApiService;
import com.carefast.apihelper.UtilsApi;
import com.carefast.contract.AppException;
import com.carefast.contract.PrefContract;
import com.carefast.contract.SecuredPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TahapSatuActivity extends AppCompatActivity {
    ImageView btn_lanjutkan;
    BaseApiService mApiService;
    EditText email, pass, phone,cpass;
    String passing_uid,passing_email,passing_password,passing_phone;

    SecuredPreference pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tahap_satu);
        btn_lanjutkan = findViewById(R.id.btn_lanjutkan);
        email = findViewById(R.id.et_email);
        pass = findViewById(R.id.et_password);
        cpass = findViewById(R.id.et_confirmpassword);
        phone = findViewById(R.id.et_phone);
        mApiService = UtilsApi.getAPIService();
        pref = new SecuredPreference(this, PrefContract.PREF_EL);


        try {
            passing_uid = pref.getString(PrefContract.user_id, "");
            passing_email = pref.getString(PrefContract.email, "");
            passing_password = pref.getString(PrefContract.password, "");
            passing_phone = pref.getString(PrefContract.phone, "");
        } catch (AppException e) {
            e.printStackTrace();
        }

        Log.d("uidnya", "onCreate: "+passing_uid);
        Log.d("emailnya", "onCreate: "+passing_email);

        if (passing_email.equals("") && passing_password.equals("") && passing_phone.equals("")){

        }else{
//            email.setText(passing_email);
//            pass.setText(passing_password);
//            phone.setText(passing_phone);
            email.setText("");
            pass.setText("");
            phone.setText("");
        }

        btn_lanjutkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
isEmailValid(email.getText().toString());
                if (cpass.getText().toString().equals(pass.getText().toString())){

if (phone.getText().length()<10){
    Toast.makeText(TahapSatuActivity.this, "Nomor Telepon Minimal 10 Digit", Toast.LENGTH_SHORT).show();

}
else{

                if (email.getText().toString().equals("") || phone.getText().toString().equals("") || pass.getText().toString().equals("")) {
                    Toast.makeText(TahapSatuActivity.this, "please fill this form", Toast.LENGTH_SHORT).show();
                } else {

                    try {
                        pref.put(PrefContract.email, email.getText().toString());
                        pref.put(PrefContract.password, pass.getText().toString());
                        pref.put(PrefContract.phone, phone.getText().toString());
                        pref.put(PrefContract.user_id, passing_uid);
                    } catch (AppException e) {
                        e.printStackTrace();
                    }
                    if (passing_uid.equals("")){
                        stepRegisFirst();
                    }else {
                        stepRegisFirstUpdate();
                    }
                }

}





                }
                else{

                Toast.makeText(TahapSatuActivity.this, "Konfirmasi Password belum tepat", Toast.LENGTH_SHORT).show();
            }
            }
        });
    }

    public void stepRegisFirst() {
        //nginsert data form 1/5

//        try {
////            province_id = pref.getString(PrefContract.province_id, "");
////            city_id = pref.getString(PrefContract.city_id, "");
//        } catch (AppException e) {
//            e.printStackTrace();
//        }


        mApiService.stepOne(email.getText().toString(), pass.getText().toString(), phone.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
//                            loading.dismiss();

                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();

                                String pesanError = jsonRESULTS.getString("code");
                                String pesan = jsonRESULTS.getString("message");

                                if (pesanError.equals("200")) {

                                    Intent intent = new Intent(TahapSatuActivity.this, TahapDuaActivity.class);
                                    startActivity(intent);





                                    Log.d("sukses insert", "onResponse: ");

                                } else {
                                    stepRegisFirstUpdate();
                                    Log.d("call api update", "onResponse: ");
                                }

                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                                Toast.makeText(TahapSatuActivity.this, "Email sudah terdaftar. Silahkan masukkan email lainnya.", Toast.LENGTH_SHORT).show();
                            }
                            //if (jsonRESULTS.getString("message").equals("Successfully login.")) {
                            // Jika login berhasil maka data nama yang ada di response API
                            // akan diparsing ke activity selanjutnya.

                                            /*} else {
                                                // Jika login gagal
                                                String error = jsonRESULTS.getString("message");
                                                //loading.dismiss();
                                                Toast.makeText(mContext, "USERNAME / PASSWORD SALAH", Toast.LENGTH_LONG).show();
                                            }*/
                        } else {
//                            loading.dismiss();
                            Toast.makeText(TahapSatuActivity.this, "Email sudah terdaftar. Silahkan masukkan email lainnya.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        //Log.e("debug", "onFailure: ERROR > " + t.toString());
//                        loading.dismiss();
                        Toast.makeText(TahapSatuActivity.this, "Email sudah terdaftar. Silahkan masukkan email lainnya.", Toast.LENGTH_SHORT).show();
                    }
                });


    }

    private void stepRegisFirstUpdate() {
        //ngupdate apabila back ke data form 1/5

        mApiService.stepOneUpdate(passing_uid,email.getText().toString(),pass.getText().toString(),phone.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
//                            loading.dismiss();

                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();

                                String pesanError = jsonRESULTS.getString("code");
//                                    String message = jsonRESULTS.getString("message");
                                if (pesanError.equals("200")) {
                                    Intent intent = new Intent(TahapSatuActivity.this, TahapDuaActivity.class);
                                    startActivity(intent);

                                } else if (pesanError.equals("201")){
                                    Intent intent = new Intent(TahapSatuActivity.this, TahapDuaActivity.class);
                                    startActivity(intent);

                                } else if (pesanError.equals("300")) {
                                    Toast.makeText(TahapSatuActivity.this, "email already exist", Toast.LENGTH_SHORT).show();
                                } else if (pesanError.equals("301")) {
                                    Toast.makeText(TahapSatuActivity.this, "error. Please reopen and clear cached data apps", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(TahapSatuActivity.this, "error. Please reopen and clear cached data apps", Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }
                            //if (jsonRESULTS.getString("message").equals("Successfully login.")) {
                            // Jika login berhasil maka data nama yang ada di response API
                            // akan diparsing ke activity selanjutnya.

                                            /*} else {
                                                // Jika login gagal
                                                String error = jsonRESULTS.getString("message");
                                                //loading.dismiss();
                                                Toast.makeText(mContext, "USERNAME / PASSWORD SALAH", Toast.LENGTH_LONG).show();
                                            }*/
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
    public void  isEmailValid(String email)
    {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if(matcher.matches())
        {}
        else
        {
            Toast.makeText(this, "Format email Tidak Valid", Toast.LENGTH_SHORT).show();

        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
