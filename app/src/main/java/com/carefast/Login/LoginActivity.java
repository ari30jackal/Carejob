package com.carefast.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.carefast.apihelper.BaseApiService;
import com.carefast.apihelper.UtilsApi;
import com.carefast.contract.AppException;
import com.carefast.contract.PrefContract;
import com.carefast.contract.SecuredPreference;
import com.carefast.home.HomeeActivity;
import com.carefast.home.Homepage;
import com.carefast.register.TahapSatuActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.xwray.passwordview.PasswordView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    SecuredPreference pref;
    private SignInButton login;
    private GoogleSignInClient mGoogleSignInClient;
    private int RC_SIGN_IN;
    RelativeLayout BtnDaftar, BtnMasuk;
    PasswordView etpw;
    EditText etemail;
    TextView forget;
    BaseApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        etemail = findViewById(R.id.et_email);
        etpw = findViewById(R.id.et_password);
        pref = new SecuredPreference(this, PrefContract.PREF_EL);
        mApiService = UtilsApi.getAPIService();
        BtnDaftar = findViewById(R.id.btn_daftar);
        forget= findViewById(R.id.forget);
        BtnMasuk = findViewById(R.id.btn_masuk);


        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgetPassActivity.class);
                startActivity(intent);
                finish();
            }
        });
        BtnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, TahapSatuActivity.class);

                startActivity(intent);
                finish();
            }
        });

        BtnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mApiService.login(etemail.getText().toString(), etpw.getText().toString()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
                                //String pesan = jsonRESULTS.getString("success");
                                String pesanError = jsonRESULTS.getString("message");
                                if (pesanError.equals("Logged In Successfully")) {
                                    JSONArray userId = jsonRESULTS.getJSONArray("user");
                                    JSONObject obj = userId.getJSONObject(0);
                                    String firstname = obj.getString("first_name_applicant");
                                    String id = obj.getString("id_applicant");
                                    String lastname = obj.getString("last_name_applicant");
                                    String email = obj.getString("email_applicant");
                                    String phone = obj.getString("phone_applicant");
                                    String nik = obj.getString("nik_number");
                                    String job_position_applicant = obj.getString("job_position_applicant");
                                    String gender = obj.getString("gender_applicant");
                                    String height = obj.getString("height_applicant");
                                    String weight = obj.getString("weight_applicant");
                                    pref.put(PrefContract.user_last_name,lastname);
                                    pref.put(PrefContract.user_email,email);
                                    pref.put(PrefContract.user_phone,phone);
                                    pref.put(PrefContract.user_nik,nik);
                                    pref.put(PrefContract.user_gender,gender);
                                    pref.put(PrefContract.user_job_position,job_position_applicant);
                                    pref.put(PrefContract.user_height,height);
                                    pref.put(PrefContract.user_weight,weight);
                                    pref.put(PrefContract.user_login,"1");
                                    pref.put(PrefContract.user_id, id);
                                    pref.put(PrefContract.user_first_name, firstname);
Log.d("jobpost","jobpost"+job_position_applicant);

                                    Intent intent = new Intent(LoginActivity.this, HomeeActivity.class);
                                    startActivity(intent);
                                    finish();
                                    Log.d("test", "id applicant   =       " + id);
                                } else {
                                    Toast.makeText(LoginActivity.this, "" + pesanError, Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException | IOException | AppException e) {
                                e.printStackTrace();
                            }
                        } else {

                            Toast.makeText(LoginActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        Toast.makeText(LoginActivity.this, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
//        login = findViewById(R.id.sign_in_button);
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .build();
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LoginGoogle();
//            }
//        });
    }

    private void LoginGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            final GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (!account.getId().equals("") && !account.getId().equals(null)) {
                Intent i = new Intent(LoginActivity.this, Homepage.class);
                startActivity(i);
                finish();
            } else {
                Toast.makeText(this, "Login Gagal", Toast.LENGTH_SHORT).show();
            }
        } catch (ApiException e) {
            Log.d("error", "" + e);
            Toast.makeText(this, "Login Gagal", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {

    }
}

