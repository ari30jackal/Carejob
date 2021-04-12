package com.carefast.menuprofile.akunsaya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePhoneActivity extends AppCompatActivity {
    ImageView back;
    SecuredPreference pref;
    Button save;
    BaseApiService mApiService;
    String uid,phone;
    EditText etphone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_phone);
        back = findViewById(R.id.backbutton);
        save  =findViewById(R.id.save);
        etphone = findViewById(R.id.et_phone);
        mApiService = UtilsApi.getAPIService();
        pref = new SecuredPreference(this, PrefContract.PREF_EL);
        back.setOnClickListener(v->{
            onBackPressed();
        });
        try {
            uid = pref.getString(PrefContract.user_id,"");
        } catch (AppException e) {
            e.printStackTrace();
        }
        save.setOnClickListener(v->{

            phone = etphone.getText().toString();
if (phone.length()<10){
    Toast.makeText(this, "minimal 10 digit", Toast.LENGTH_SHORT).show();

}
else{

    updatephone(uid,phone);
}

        });
    }
    public void updatephone(String uid,String phone) {
        //ngambil id. data form 2/5

        mApiService.updatephone(uid,phone)
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

                                    Toast.makeText(ChangePhoneActivity.this, "Phone berhasil diganti", Toast.LENGTH_SHORT).show();

                                    finish();


                                } else {
                                    Toast.makeText(ChangePhoneActivity.this, "Phone gagal diganti", Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                                Log.d("asd",e.toString());
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
}