package com.carefast.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.carefast.Login.R;
import com.carefast.apihelper.BaseApiService;
import com.carefast.apihelper.UtilsApi;
import com.carefast.contract.AppException;
import com.carefast.contract.PrefContract;
import com.carefast.contract.SecuredPreference;
import com.carefast.model.ProvResponse;
import com.carefast.model.ProvinceItem;
import com.carefast.model.ResponseProvince;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TahapDuaActivity extends AppCompatActivity {
    ImageView btnkembali, btnlanjut, hasilktp, kamera;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    RelativeLayout rlprov, rlcity, rlkecamatan, rlpostal, takepict, rlrincian;
    Calendar myCalendar;
    SecuredPreference pref;
    EditText edittext;
    String rincianshowpref;
    ImageView plustinggi, plusberat, minustinggi, minusberat, pria_iv, wanita_iv;
    TextView tinggi, berat;
    EditText weight,height;
    TextView tv_provinsi_show, tv_city_show, tv_kecamatan_show, tv_postal_show, tv_kamera;
    String provinsi_name_show, provinsi_id_show, city_name_show, city_id_show, kecamatan_name_show, kecamatan_id_show, postal_name_show, postal_id_show, passing_uid, passing_email;
    String passing_password, passing_phone;
    BaseApiService mApiService;
    EditText birthp,birthd,nik,first,last;
    String gender;
    Integer inttinggi, intberat;
    String passing_birthd,passing_birthp,passing_nik,passing_firstname,passing_lastname,passing_gender;
    TextView rincianshow, etctext;
    private Bitmap bitmap;
    private String uid = "";
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    String datenya;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tahap_dua);
        btnkembali = findViewById(R.id.btn_kembali);
        mApiService = UtilsApi.getAPIService();
        pref = new SecuredPreference(this, PrefContract.PREF_EL);
        tv_provinsi_show = findViewById(R.id.tv_provinsi_show);
        tv_kecamatan_show = findViewById(R.id.tv_kecamatan_show);
        tv_postal_show = findViewById(R.id.tv_postcode_show);
        hasilktp = findViewById(R.id.hasilfoto);
        kamera = findViewById(R.id.camera);
        tv_kamera = findViewById(R.id.tv_kamera);
        plusberat = findViewById(R.id.btn_plusberat);
        plustinggi = findViewById(R.id.btn_plustinggi);
        minusberat = findViewById(R.id.btn_minusberat);
        rincianshow = findViewById(R.id.rincianshow);
        etctext = findViewById(R.id.etctext);
        minustinggi = findViewById(R.id.btn_minustinggi);
        berat = findViewById(R.id.berat);
        tinggi = findViewById(R.id.tinggi);
        tv_city_show = findViewById(R.id.tv_city_show);
        birthp = findViewById(R.id.et_tempat);
        birthd = findViewById(R.id.et_tanggallahir);
        nik = findViewById(R.id.et_nik);
        first = findViewById(R.id.et_namadepan);
        last = findViewById(R.id.et_namabelakang);
        weight = findViewById(R.id.berat);
        height = findViewById(R.id.tinggi);
//cptry

        try {
            passing_uid = pref.getString(PrefContract.user_id, "");
            passing_email = pref.getString(PrefContract.email, "");
            passing_password = pref.getString(PrefContract.password, "");
            passing_phone = pref.getString(PrefContract.phone, "");
            city_name_show = pref.getString(PrefContract.city_name_choose, "Kota/Kabupaten");
            city_id_show = pref.getString(PrefContract.city_id_choose, "");
            provinsi_name_show = pref.getString(PrefContract.province_name_choose, "Provinsi");
            provinsi_id_show = pref.getString(PrefContract.province_id_choose, "");
            kecamatan_id_show = pref.getString(PrefContract.districts_id_choose, "");
            kecamatan_name_show = pref.getString(PrefContract.districts_name_choose, "Kecamatan");
            postal_id_show = pref.getString(PrefContract.postal_id_choose, "");
            postal_name_show = pref.getString(PrefContract.postal_name_choose, "Kode Pos");
            rincianshowpref = pref.getString(PrefContract.rincian_show, "Rincian Alamat");

passing_gender = pref.getString(PrefContract.user_gender,"Pria");

            passing_birthd = pref.getString(PrefContract.user_birthd,"");
            passing_birthp = pref.getString(PrefContract.user_birthp,"");
            passing_nik = pref.getString(PrefContract.user_nik,"");
            passing_firstname = pref.getString(PrefContract.user_first_name,"");
            passing_lastname = pref.getString(PrefContract.user_last_name,"");

        } catch (AppException e) {
            e.printStackTrace();
        }



birthd.setText(passing_birthd);
        birthp.setText(passing_birthp);
        nik.setText(passing_nik);
        first.setText(passing_firstname);
        last.setText(passing_lastname);
        Log.d("email", "user_email: "+passing_email);
        Log.d("password", "user_pass: "+passing_password);
        Log.d("phone", "user_phone: "+passing_phone);

        Log.d("uidnya tahap 2", "uid: "+passing_uid);
        tv_city_show.setText(city_name_show);
        rincianshow.setText(rincianshowpref);

        tv_provinsi_show.setText(provinsi_name_show);
        tv_kecamatan_show.setText(kecamatan_name_show);
        tv_postal_show.setText(postal_name_show);
        btnlanjut = findViewById(R.id.btn_lanjutkan);
        rlkecamatan = findViewById(R.id.rlkecamatan);
        rlcity = findViewById(R.id.rlcity);
        rlprov = findViewById(R.id.rlprovince);
        rlrincian = findViewById(R.id.rldetilalamat);
        rlpostal = findViewById(R.id.rl_postcode);
        takepict = findViewById(R.id.takepict);
        pria_iv = findViewById(R.id.pria_iv);
        wanita_iv = findViewById(R.id.wanita_iv);
        if (passing_gender.equals("Pria")){
            pria_iv.setBackgroundResource(R.drawable.priaactive);
            wanita_iv.setBackgroundResource(R.drawable.wanitaaa);

        }
        else if (passing_gender.equals("Wanita")){

            pria_iv.setBackgroundResource(R.drawable.priaaa);
            wanita_iv.setBackgroundResource(R.drawable.wanitaactive);

        }


        getIdAwal();

        gender = "Pria";

        pria_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pria_iv.setBackgroundResource(R.drawable.priaactive);
                wanita_iv.setBackgroundResource(R.drawable.wanitaaa);
                gender = "Pria";
                try {
                    pref.put(PrefContract.user_gender,gender);
                } catch (AppException e) {
                    e.printStackTrace();
                }
            }
        });
        wanita_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pria_iv.setBackgroundResource(R.drawable.priaaa);
                wanita_iv.setBackgroundResource(R.drawable.wanitaactive);
                gender = "Wanita";
                try {
                    pref.put(PrefContract.user_gender,gender);
                } catch (AppException e) {
                    e.printStackTrace();
                }
            }
        });
        takepict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

                }

            }
        });
        btnkembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //dapetin id buat di pass ke 1/5
                getId();
onBackPressed();

            }
        });
        rlpostal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setpref();
                Intent intent = new Intent(TahapDuaActivity.this, ListPostal.class);
                startActivity(intent);

            }
        });
        rlkecamatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setpref();
                Intent intent = new Intent(TahapDuaActivity.this, ListKecamatan.class);

                startActivity(intent);
        }
        });
        rlrincian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


setpref();

                Intent intent = new Intent(TahapDuaActivity.this, RincianAlamat.class);

                startActivity(intent);

            }
        });
        rlprov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setpref();
                Intent intent = new Intent(TahapDuaActivity.this, ListProvince.class);

                startActivity(intent);

            }
        });
        rlcity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setpref();
                Intent intent = new Intent(TahapDuaActivity.this, ListCity.class);

                startActivity(intent);

            }
        });
        myCalendar = Calendar.getInstance();
        edittext = (EditText) findViewById(R.id.et_tanggallahir);
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        edittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(TahapDuaActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        btnlanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //cptry
                if (nik.getText().equals("") ||first.getText().toString().equals("")||birthp.getText().toString().equals("")||birthd.getText().toString().equals("")||city_id_show.equals("")||provinsi_id_show.equals("")||kecamatan_id_show.equals("")||postal_id_show.equals("")||rincianshowpref.equals("Rincian Alamat"))
                {

                    Toast.makeText(TahapDuaActivity.this, "Mohon Lengkapi Data Anda", Toast.LENGTH_SHORT).show();
                }
                else{



                if (nik.length() != 16) {

                    Toast.makeText(TahapDuaActivity.this, "Nik  Harus 16 Digit", Toast.LENGTH_SHORT).show();
                } else {
                    //ubahformattgl
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    datenya = format.format(Date.parse(birthd.getText().toString()));

                    if (tv_kamera.getVisibility() == View.VISIBLE && kamera.getVisibility() == view.VISIBLE) {
                        Toast.makeText(TahapDuaActivity.this, "silahkan foto selfie dengan KTP Anda", Toast.LENGTH_SHORT).show();
                    } else {
                        bitmap = ((BitmapDrawable) hasilktp.getDrawable()).getBitmap();
                        stepRegisSecond(bitmap);
                    }

                    Log.d("ttl", "onClick: " + datenya);
                    Log.d("et", "onClick: " + nik.getText().toString());

                }  }
            }        });

        hasilktp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                potoSelfie();
            }
        });

        minustinggi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                inttinggi = Integer.parseInt(tinggi.getText().toString());
                if (inttinggi <= 100) {
                    tinggi.setText(inttinggi.toString());
                } else {
                    inttinggi--;
                    tinggi.setText(inttinggi.toString());
                }
            }
        });
        plustinggi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inttinggi = Integer.parseInt(tinggi.getText().toString());
                inttinggi++;
                tinggi.setText(inttinggi.toString());

            }
        });
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
    }

    private void setpref() {
        try {
            pref.put(PrefContract.user_nik,nik.getText().toString());
            pref.put(PrefContract.user_first_name,first.getText().toString());
            pref.put(PrefContract.user_last_name,last.getText().toString());
            pref.put(PrefContract.user_birthp,birthp.getText().toString());
            pref.put(PrefContract.user_birthd,birthd.getText().toString());
        } catch (AppException e) {
            e.printStackTrace();
        }
    }


    public void getIdAwal() {
        //ngambil id. data form 2/5

        mApiService.getId(passing_email)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
//                            loading.dismiss();

                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();

                                String pesanError = jsonRESULTS.getString("code");
                                String idnya = jsonRESULTS.getString("id");
                                if (pesanError.equals("200")) {

                                    uid = idnya;

                                } else {
//                                    Toast.makeText(TahapSatuActivity.this, "Step 1 failed, please try again.", Toast.LENGTH_SHORT).show();
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


    public void getId() {
        //ngambil id. data form 2/5

        mApiService.getId(passing_email)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
//                            loading.dismiss();

                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();

                                String pesanError = jsonRESULTS.getString("code");
                                String idnya = jsonRESULTS.getString("id");
                                if (pesanError.equals("200")) {

                                    uid = idnya;

                                    try {
                                        pref.put(PrefContract.user_id, idnya);
//                                        pref.put(PrefContract.email, passing_email);
                                        pref.put(PrefContract.password, passing_password);
                                        pref.put(PrefContract.phone, passing_phone);
                                    } catch (AppException e) {
                                        e.printStackTrace();
                                    }
                                   onBackPressed();


                                } else {
//                                    Toast.makeText(TahapSatuActivity.this, "Step 1 failed, please try again.", Toast.LENGTH_SHORT).show();
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

    @SuppressLint("StaticFieldLeak")
    public void stepRegisSecond(Bitmap bitmap) {
        //ngupdate data form 2/5

//        try {
////            province_id = pref.getString(PrefContract.province_id, "");
////            city_id = pref.getString(PrefContract.city_id, "");
//        } catch (AppException e) {
//            e.printStackTrace();
//        }

        File file = createTempFile(bitmap);
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part image = MultipartBody.Part.createFormData("image", file.getName(), reqFile);

        RequestBody uidRB;

        if (passing_uid.equals("") || passing_uid == "" || passing_uid.equals(null) || passing_uid == null){
            uidRB = RequestBody.create(MediaType.parse("multipart/form-data"), uid);
//            uidRB = RequestBody.create(MediaType.parse("multipart/form-data"), "10");
        }else{
            uidRB = RequestBody.create(MediaType.parse("multipart/form-data"), passing_uid);
//            uidRB = RequestBody.create(MediaType.parse("multipart/form-data"), "10");
        }



        RequestBody genders =
                RequestBody.create(MediaType.parse("multipart/form-data"), gender);
        RequestBody birthps =
                RequestBody.create(MediaType.parse("multipart/form-data"), birthp.getText().toString());
        RequestBody dates =
                RequestBody.create(MediaType.parse("multipart/form-data"), datenya);
        RequestBody niks =
                RequestBody.create(MediaType.parse("multipart/form-data"), nik.getText().toString());
        RequestBody firsts =
                RequestBody.create(MediaType.parse("multipart/form-data"), first.getText().toString());
        RequestBody lasts =
                RequestBody.create(MediaType.parse("multipart/form-data"), last.getText().toString());
        RequestBody heights =
                RequestBody.create(MediaType.parse("multipart/form-data"), height.getText().toString());
        RequestBody weights =
                RequestBody.create(MediaType.parse("multipart/form-data"), weight.getText().toString());
        RequestBody provs =
                RequestBody.create(MediaType.parse("multipart/form-data"), provinsi_id_show);
        RequestBody citys =
                RequestBody.create(MediaType.parse("multipart/form-data"), city_id_show);
        RequestBody diss =
                RequestBody.create(MediaType.parse("multipart/form-data"), kecamatan_id_show);
        RequestBody codes =
                RequestBody.create(MediaType.parse("multipart/form-data"), postal_id_show);
        RequestBody details =
                RequestBody.create(MediaType.parse("multipart/form-data"), rincianshow.getText().toString());


//        if(isValidEmail(email.getText().toString())) {
//        Log.d("logggIntent", "Berhasil method login");
        mApiService.stepTwo(uidRB, genders, birthps, dates, niks, firsts, lasts, heights, weights, provs, citys, diss, codes, details, image)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
//                            loading.dismiss();

                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();

                                String pesanError = jsonRESULTS.getString("code");
//                                String pesan = jsonRESULTS.getString("message");
//                                String img = jsonRESULTS.getString("new_image");
//                                String first_name = jsonRESULTS.getString("first_name");
                                if (pesanError.equals("200")) {
                                    if (passing_uid.equals("") || passing_uid == "" || passing_uid.equals(null) || passing_uid == null) {
                                        try {
                                            pref.put(PrefContract.user_id, uid);
                                        } catch (AppException e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        try {
                                            pref.put(PrefContract.user_id, passing_uid);
                                        } catch (AppException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    Intent intent = new Intent(TahapDuaActivity.this, TahapTigaActivity.class);
                                    startActivity(intent);


                                } else if (pesanError.equals("404")){
                                    Toast.makeText(TahapDuaActivity.this, "NIK sudah terdaftar. Silahkan masukkan NIK lainnya", Toast.LENGTH_SHORT).show();

                                }else{

                                    Toast.makeText(TahapDuaActivity.this, "Pendaftaran Gagal Periksa Kembali Data Anda", Toast.LENGTH_SHORT).show();

                                }

                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                                Toast.makeText(TahapDuaActivity.this, "NIK sudah terdaftar. Silahkan masukkan NIK lainnya.", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(TahapDuaActivity.this, "NIK sudah terdaftar. Silahkan masukkan NIK lainnya.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(TahapDuaActivity.this, "NIK sudah terdaftar. Silahkan masukkan NIK lainnya.", Toast.LENGTH_SHORT).show();
                    }
                });


    }


    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edittext.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
//            {
//                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
//                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(cameraIntent, CAMERA_REQUEST);
//            }
//            else
//            {
            Toast.makeText(this, "Open Camera", Toast.LENGTH_LONG).show();
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);

//            }
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            hasilktp.setImageBitmap(photo);
            tv_kamera.setVisibility(View.GONE);
            kamera.setVisibility(View.GONE);
        }
    }




    private int getInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            int halfHeight = height / 2;
            int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) > reqHeight &&
                    (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
private void nullchecker(EditText et){

        if (et.getText().toString().equals("")||et.getText().toString().equals(null)){

            Toast.makeText(this, "Lengkapi data anda", Toast.LENGTH_SHORT).show();
        }
        else{}

}

    private File createTempFile(Bitmap bitmap) {
        File file = new File(TahapDuaActivity.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                , System.currentTimeMillis() + "_andro"+passing_uid+".JPEG");
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

    private void potoSelfie() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
            }
            else
            {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        try {
            passing_uid = pref.getString(PrefContract.user_id, "");
            passing_email = pref.getString(PrefContract.email, "");
            passing_password = pref.getString(PrefContract.password, "");
            passing_phone = pref.getString(PrefContract.phone, "");
            city_name_show = pref.getString(PrefContract.city_name_choose, "Kota/Kabupaten");
            city_id_show = pref.getString(PrefContract.city_id_choose, "");
            provinsi_name_show = pref.getString(PrefContract.province_name_choose, "Provinsi");
            provinsi_id_show = pref.getString(PrefContract.province_id_choose, "");
            kecamatan_id_show = pref.getString(PrefContract.districts_id_choose, "");
            kecamatan_name_show = pref.getString(PrefContract.districts_name_choose, "Kecamatan");
            postal_id_show = pref.getString(PrefContract.postal_id_choose, "");
            postal_name_show = pref.getString(PrefContract.postal_name_choose, "Kode Pos");
            rincianshowpref = pref.getString(PrefContract.rincian_show, "Rincian Alamat");

            passing_gender = pref.getString(PrefContract.user_gender,"Pria");

            passing_birthd = pref.getString(PrefContract.user_birthd,"");
            passing_birthp = pref.getString(PrefContract.user_birthp,"");
            passing_nik = pref.getString(PrefContract.user_nik,"");
            passing_firstname = pref.getString(PrefContract.user_first_name,"");
            passing_lastname = pref.getString(PrefContract.user_last_name,"");

        } catch (AppException e) {
            e.printStackTrace();
        }



        birthd.setText(passing_birthd);
        birthp.setText(passing_birthp);
        nik.setText(passing_nik);
        first.setText(passing_firstname);
        last.setText(passing_lastname);
        Log.d("email", "user_email: "+passing_email);
        Log.d("password", "user_pass: "+passing_password);
        Log.d("phone", "user_phone: "+passing_phone);

        Log.d("uidnya tahap 2", "uid: "+passing_uid);
        tv_city_show.setText(city_name_show);
        rincianshow.setText(rincianshowpref);

        tv_provinsi_show.setText(provinsi_name_show);
        tv_kecamatan_show.setText(kecamatan_name_show);
        tv_postal_show.setText(postal_name_show);
        btnlanjut = findViewById(R.id.btn_lanjutkan);
        rlkecamatan = findViewById(R.id.rlkecamatan);
        rlcity = findViewById(R.id.rlcity);
        rlprov = findViewById(R.id.rlprovince);
        rlrincian = findViewById(R.id.rldetilalamat);
        rlpostal = findViewById(R.id.rl_postcode);
        takepict = findViewById(R.id.takepict);
        pria_iv = findViewById(R.id.pria_iv);
        wanita_iv = findViewById(R.id.wanita_iv);
        if (passing_gender.equals("Pria")){
            pria_iv.setBackgroundResource(R.drawable.priaactive);
            wanita_iv.setBackgroundResource(R.drawable.wanitaaa);

        }
        else if (passing_gender.equals("Wanita")){

            pria_iv.setBackgroundResource(R.drawable.priaaa);
            wanita_iv.setBackgroundResource(R.drawable.wanitaactive);

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}