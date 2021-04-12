package com.carefast.menuprofile.akunsaya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
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
import com.carefast.menuprofile.AkunSayaActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeEmailActivity extends AppCompatActivity {
    ImageView back;
    SecuredPreference pref;
    Button save;
    BaseApiService mApiService;
    String uid,email;
    EditText etemail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);

        save  =findViewById(R.id.save);
        etemail = findViewById(R.id.et_email);
        mApiService = UtilsApi.getAPIService();
        pref = new SecuredPreference(this, PrefContract.PREF_EL);
        back = findViewById(R.id.backbutton);
        back.setOnClickListener(v->{
        finish();
        });
        try {
            uid = pref.getString(PrefContract.user_id,"");
        } catch (AppException e) {
            e.printStackTrace();
        }
        save.setOnClickListener(v->{

            email = etemail.getText().toString();
isEmailValid(email);



        });
    }
    public void updateemail(String uid,String email) {
        //ngambil id. data form 2/5

        mApiService.updateemail(uid,email)
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

                                    Toast.makeText(ChangeEmailActivity.this, "Email berhasil diganti", Toast.LENGTH_SHORT).show();

                                    finish();


                                } else {
                                    Toast.makeText(ChangeEmailActivity.this, "Email gagal diganti", Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                                Log.d("asd",e.toString());
                                Toast.makeText(ChangeEmailActivity.this, "Email tidak valid atau sudah terdaftar", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(ChangeEmailActivity.this, "Email tidak valid atau sudah terdaftar", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        //Log.e("debug", "onFailure: ERROR > " + t.toString());
//                        loading.dismiss();
                        Toast.makeText(ChangeEmailActivity.this, "Email tidak valid atau sudah terdaftar", Toast.LENGTH_SHORT).show();
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
        {
            updateemail(uid,email);
        }
        else
        {
            Toast.makeText(this, "Format email Tidak Valid", Toast.LENGTH_SHORT).show();

        }
    }
    @Override
    public void onBackPressed() {
        finish();
    }
}