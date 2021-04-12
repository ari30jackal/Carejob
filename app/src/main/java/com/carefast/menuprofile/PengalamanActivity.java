package com.carefast.menuprofile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.carefast.Login.R;
import com.carefast.apihelper.BaseApiService;
import com.carefast.apihelper.UtilsApi;
import com.carefast.contract.AppException;
import com.carefast.contract.PrefContract;
import com.carefast.contract.SecuredPreference;
import com.carefast.register.TahapEmpatActivity;
import com.carefast.register.TahapTigaActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PengalamanActivity extends AppCompatActivity {
    SecuredPreference pref;
    BaseApiService mApiService;
    private Bitmap bitmap;
    ImageView back;
    private Bitmap bitmaps;
    private static final int FILE_SELECT_CODE = 0;
    private static final int CREATE_FILE = 1;
    Spinner splastedu,spworkexp,splastjob;
    ImageView postlastedu,postlastjob,camera,camera2;
    Button saveupdate;
    ArrayAdapter<CharSequence> adapter,adapter2,adapter3;
    String getuid,getlastedu,getlastjob,getworkexp,getjobfile,getedufile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengalaman);
        pref = new SecuredPreference(PengalamanActivity.this, PrefContract.PREF_EL);
        mApiService = UtilsApi.getAPIService();
        saveupdate = findViewById(R.id.btn_save);
        splastedu = findViewById(R.id.spinnerpendidikan);
        splastjob = findViewById(R.id.spinnerlastposition);
        spworkexp = findViewById(R.id.spinnerpengalaman);
        camera =findViewById(R.id.camera);
        camera2 =findViewById(R.id.camera2);
        postlastedu = findViewById(R.id.postlastedu);
        postlastjob = findViewById(R.id.postlastjob);

        back = findViewById(R.id.backbutton);
        back.setOnClickListener(v -> {

            finish();
        });
        try {
            getuid = pref.getString(PrefContract.user_id,"");
        } catch (AppException e) {
            e.printStackTrace();
        }

        getpengalaman();


        saveupdate.setOnClickListener(v->{
            if ( camera.getDrawable() == null && camera2.getDrawable() != null){

                bitmaps = ((BitmapDrawable) camera2.getDrawable()).getBitmap();
                pengalamandanedu(bitmaps);
            }
            else if ( camera.getDrawable() != null && camera2.getDrawable() == null){

                bitmap = ((BitmapDrawable) camera.getDrawable()).getBitmap();
                pengalamandanjob(bitmap);
            }
            else if( camera.getDrawable() != null && camera2.getDrawable() != null){
                bitmap = ((BitmapDrawable) camera.getDrawable()).getBitmap();
                bitmaps = ((BitmapDrawable) camera2.getDrawable()).getBitmap();



                stepRegisThird(bitmap, bitmaps);}
            else if( camera.getDrawable() == null && camera2.getDrawable() == null){


//cp

                pengalamanaja();}

        });

        postlastjob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                panggilgambar();
            }
        });

        postlastedu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                panggilgambar2();
            }
        });
        adapter = ArrayAdapter.createFromResource(PengalamanActivity.this,
                R.array.position, android.R.layout.simple_spinner_item);
        // mengeset Array Adapter tersebut ke Spinner
        splastjob.setAdapter(adapter);
        // mengeset listener untuk mengetahui saat item dipilih
        splastjob.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // memunculkan toast + value Spinner yang dipilih (diambil dari adapter)

                getlastjob = String.valueOf(adapter.getItem(i));
//                Toast.makeText(TahapTigaActivity.this, "Selected "+ lastpositionStr, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        adapter2 = ArrayAdapter.createFromResource(PengalamanActivity.this,
                R.array.pendidikanterakhir, android.R.layout.simple_spinner_item);
        // mengeset Array Adapter tersebut ke Spinner
        splastedu.setAdapter(adapter2);
        // mengeset listener untuk mengetahui saat item dipilih
        splastedu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // memunculkan toast + value Spinner yang dipilih (diambil dari adapter)

                getlastedu = String.valueOf(adapter2.getItem(i));
//                Toast.makeText(TahapTigaActivity.this, "Selected "+ lastpositionStr, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        adapter3 = ArrayAdapter.createFromResource(PengalamanActivity.this,
                R.array.pengalaman, android.R.layout.simple_spinner_item);
        // mengeset Array Adapter tersebut ke Spinner
        spworkexp.setAdapter(adapter3);
        // mengeset listener untuk mengetahui saat item dipilih
        spworkexp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // memunculkan toast + value Spinner yang dipilih (diambil dari adapter)

                getworkexp = String.valueOf(adapter3.getItem(i));
//                Toast.makeText(TahapTigaActivity.this, "Selected "+ lastpositionStr, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void getpengalaman() {



        mApiService.getpengalaman(getuid).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
                        //String pesan = jsonRESULTS.getString("success");

                        JSONArray userId = jsonRESULTS.getJSONArray("Pengalaman");
                        JSONObject obj = userId.getJSONObject(0);
                        getlastedu = obj.getString("last_education_applicant");
                        getlastjob = obj.getString("last_job_applicant");
                        getjobfile= obj.getString("photo_job_applicant");
                        getworkexp= obj.getString("work_experience_applicant");
                        getedufile= obj.getString("photo_education_applicant");



                        int spinnerPosition = adapter.getPosition(getlastjob);
                        splastjob.setSelection(spinnerPosition);
                        int spinnerPosition2 = adapter2.getPosition(getlastedu);
                        splastedu.setSelection(spinnerPosition2);


                        int spinnerPosition3 = adapter3.getPosition(getworkexp);
                        spworkexp.setSelection(spinnerPosition3);
                        Picasso.get()
                                .load(PengalamanActivity.this.getResources().getString(R.string.base_url_lastedu) +getedufile)
                                .into(camera2);

                        Picasso.get()
                                .load(PengalamanActivity.this.getResources().getString(R.string.base_url_lastjob) +getjobfile)
                                .into(camera);

                        camera.buildDrawingCache();
                        bitmap= camera.getDrawingCache();
                        camera2.buildDrawingCache();
                        bitmaps= camera2.getDrawingCache();
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                } else {

                    Toast.makeText(PengalamanActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(PengalamanActivity.this, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });


    }



    private void panggilgambar() {

        ActivityCompat.requestPermissions(PengalamanActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                2);


        Intent i = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, 1);

    }


    private void panggilgambar2() {

        ActivityCompat.requestPermissions(PengalamanActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                2);


        Intent i = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, 2);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();


                Bitmap bitmap = null;
                try {
                    bitmap = BitmapFactory.decodeStream(PengalamanActivity.this.getContentResolver().openInputStream(uri));
                    int nh = (int) (bitmap.getHeight() * (512.0 / bitmap.getWidth()));

                    Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
                    camera.setImageBitmap(scaled);

                    Toast.makeText(PengalamanActivity.this, "Upload Success.", Toast.LENGTH_SHORT).show();


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                //pictureFlag = 1;

                //ivTemp.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            } else {
                Toast.makeText(PengalamanActivity.this, "Upload Failed.", Toast.LENGTH_SHORT).show();
            }
        }else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();


                Bitmap bitmap = null;
                try {
                    bitmap = BitmapFactory.decodeStream(PengalamanActivity.this.getContentResolver().openInputStream(uri));
                    int nh = (int) (bitmap.getHeight() * (512.0 / bitmap.getWidth()));

                    Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
                    camera2.setImageBitmap(scaled);

                    Toast.makeText(PengalamanActivity.this, "Upload Success.", Toast.LENGTH_SHORT).show();


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                //pictureFlag = 1;

                //ivTemp.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            } else {
                Toast.makeText(PengalamanActivity.this, "Upload Failed.", Toast.LENGTH_SHORT).show();
            }
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

    private File createTempFile(Bitmap bitmap) {
//        File file = new File(TahapTigaActivity.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
//                , System.currentTimeMillis() + "_job.JPEG");
        File file = new File(PengalamanActivity.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "_" +getuid + "_job.JPEG");
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
//        File file = new File(TahapTigaActivity.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
//                , System.currentTimeMillis() + "_education.JPEG");
        File file = new File(PengalamanActivity.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "_" + getuid + "_education.JPEG");
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
    @SuppressLint("StaticFieldLeak")
    public void stepRegisThird(Bitmap bitmap, Bitmap bitmaps) {
        //ngupdate data form 3/5

        File file = createTempFile(bitmap);
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part image = MultipartBody.Part.createFormData("images", file.getName(), reqFile);


        File files = createTempFiles(bitmaps);
        RequestBody reqFiles = RequestBody.create(MediaType.parse("image/*"), files);
        MultipartBody.Part images = MultipartBody.Part.createFormData("image2", files.getName(), reqFiles);


        RequestBody uidRB =
                RequestBody.create(MediaType.parse("multipart/form-data"), getuid);
        RequestBody pengalamanStrs =
                RequestBody.create(MediaType.parse("multipart/form-data"), getworkexp);
        RequestBody lastpositionStrs =
                RequestBody.create(MediaType.parse("multipart/form-data"), getlastjob);
        RequestBody pendidikanStrs =
                RequestBody.create(MediaType.parse("multipart/form-data"),getlastedu);

//        if(isValidEmail(email.getText().toString())) {
//        Log.d("logggIntent", "Berhasil method login");
        mApiService.stepThree(uidRB, image, images, pengalamanStrs, lastpositionStrs, pendidikanStrs)
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
                                String img = jsonRESULTS.getString("image_job");
                                String img2 = jsonRESULTS.getString("image_edu");
//                                String first_name = jsonRESULTS.getString("first_name");
                                if (pesanError.equals("200")) {
                                  finish();

                                } else {

                                    Toast.makeText(PengalamanActivity.this, "failed, please try again.", Toast.LENGTH_SHORT).show();

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
    public void pengalamandanjob(Bitmap bitmap) {
        //ngupdate data form 3/5



        File files = createTempFiles(bitmaps);
        RequestBody reqFiles = RequestBody.create(MediaType.parse("image/*"), files);
        MultipartBody.Part images = MultipartBody.Part.createFormData("image2", files.getName(), reqFiles);


        RequestBody uidRB =
                RequestBody.create(MediaType.parse("multipart/form-data"), getuid);
        RequestBody pengalamanStrs =
                RequestBody.create(MediaType.parse("multipart/form-data"), getworkexp);
        RequestBody lastpositionStrs =
                RequestBody.create(MediaType.parse("multipart/form-data"), getlastjob);
        RequestBody pendidikanStrs =
                RequestBody.create(MediaType.parse("multipart/form-data"),getlastedu);

//        if(isValidEmail(email.getText().toString())) {
//        Log.d("logggIntent", "Berhasil method login");
        mApiService.updatejob(uidRB, images, pengalamanStrs, lastpositionStrs, pendidikanStrs)
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

//                                String first_name = jsonRESULTS.getString("first_name");
                                if (pesanError.equals("200")) {
finish();
                                    Toast.makeText(PengalamanActivity.this, "berhasil update", Toast.LENGTH_SHORT).show();


//                                    try {
////                                        pref.put(PrefContract.gender, gender.getText().toString());
////                                        pref.put(PrefContract.birthp, birthp.getText().toString());
////                                        pref.put(PrefContract.birthd, birthd.getText().toString());
////                                        pref.put(PrefContract.address, address.getText().toString());
//////                                        pref.put(PrefContract.province, String.valueOf(provinces));
//////                                        pref.put(PrefContract.city, city.getText().toString());
////                                        pref.put(PrefContract.poscode, zip.getText().toString());
//////                                        pref.put(PrefContract.kelas, education.getText().toString());
//////                                        pref.put(PrefContract.last_education, education2.getText().toString());
////                                        pref.put(PrefContract.institution, school.getText().toString());
////                                        pref.put(PrefContract.bio, bio.getText().toString());
////                                        pref.put(PrefContract.photo, img);
//
////                                        passing_gender = gender.getText().toString();
////                                        passing_birthp = birthp.getText().toString();
////                                        passing_birth = birthd.getText().toString();
////                                        passing_address = address.getText().toString();
////                                        passing_zip = zip.getText().toString();
////                                        passing_school = school.getText().toString();
////                                        passing_bio = bio.getText().toString();
////                                        passing_photo = img;
//                                    } catch (AppException e) {
//                                        e.printStackTrace();
//                                    }

                                } else {

                                    Toast.makeText(PengalamanActivity.this, "failed, please try again.", Toast.LENGTH_SHORT).show();

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
    public void pengalamanaja() {
        //ngupdate data form 3/5




        RequestBody uidRB =
                RequestBody.create(MediaType.parse("multipart/form-data"), getuid);
        RequestBody pengalamanStrs =
                RequestBody.create(MediaType.parse("multipart/form-data"), getworkexp);
        RequestBody lastpositionStrs =
                RequestBody.create(MediaType.parse("multipart/form-data"), getlastjob);
        RequestBody pendidikanStrs =
                RequestBody.create(MediaType.parse("multipart/form-data"),getlastedu);

//        if(isValidEmail(email.getText().toString())) {
//        Log.d("logggIntent", "Berhasil method login");
        mApiService.updatepengalaman(uidRB, pengalamanStrs, lastpositionStrs, pendidikanStrs)
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

//                                String first_name = jsonRESULTS.getString("first_name");
                                if (pesanError.equals("200")) {

                                    Toast.makeText(PengalamanActivity.this, "berhasil update", Toast.LENGTH_SHORT).show();


//                                    try {
////                                        pref.put(PrefContract.gender, gender.getText().toString());
////                                        pref.put(PrefContract.birthp, birthp.getText().toString());
////                                        pref.put(PrefContract.birthd, birthd.getText().toString());
////                                        pref.put(PrefContract.address, address.getText().toString());
//////                                        pref.put(PrefContract.province, String.valueOf(provinces));
//////                                        pref.put(PrefContract.city, city.getText().toString());
////                                        pref.put(PrefContract.poscode, zip.getText().toString());
//////                                        pref.put(PrefContract.kelas, education.getText().toString());
//////                                        pref.put(PrefContract.last_education, education2.getText().toString());
////                                        pref.put(PrefContract.institution, school.getText().toString());
////                                        pref.put(PrefContract.bio, bio.getText().toString());
////                                        pref.put(PrefContract.photo, img);
//
////                                        passing_gender = gender.getText().toString();
////                                        passing_birthp = birthp.getText().toString();
////                                        passing_birth = birthd.getText().toString();
////                                        passing_address = address.getText().toString();
////                                        passing_zip = zip.getText().toString();
////                                        passing_school = school.getText().toString();
////                                        passing_bio = bio.getText().toString();
////                                        passing_photo = img;
//                                    } catch (AppException e) {
//                                        e.printStackTrace();
//                                    }

                                } else {

                                    Toast.makeText(PengalamanActivity.this, "failed, please try again.", Toast.LENGTH_SHORT).show();

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
    public void pengalamandanedu( Bitmap bitmaps) {
        //ngupdate data form 3/5


        File files = createTempFiles(bitmaps);
        RequestBody reqFiles = RequestBody.create(MediaType.parse("image/*"), files);
        MultipartBody.Part images = MultipartBody.Part.createFormData("images", files.getName(), reqFiles);


        RequestBody uidRB =
                RequestBody.create(MediaType.parse("multipart/form-data"), getuid);
        RequestBody pengalamanStrs =
                RequestBody.create(MediaType.parse("multipart/form-data"), getworkexp);
        RequestBody lastpositionStrs =
                RequestBody.create(MediaType.parse("multipart/form-data"), getlastjob);
        RequestBody pendidikanStrs =
                RequestBody.create(MediaType.parse("multipart/form-data"),getlastedu);

//        if(isValidEmail(email.getText().toString())) {
//        Log.d("logggIntent", "Berhasil method login");
        mApiService.updateedu(uidRB,images, pengalamanStrs, lastpositionStrs, pendidikanStrs)
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

//                                String first_name = jsonRESULTS.getString("first_name");
                                if (pesanError.equals("200")) {

                                    Toast.makeText(PengalamanActivity.this, "berhasil update", Toast.LENGTH_SHORT).show();


//                                    try {
////                                        pref.put(PrefContract.gender, gender.getText().toString());
////                                        pref.put(PrefContract.birthp, birthp.getText().toString());
////                                        pref.put(PrefContract.birthd, birthd.getText().toString());
////                                        pref.put(PrefContract.address, address.getText().toString());
//////                                        pref.put(PrefContract.province, String.valueOf(provinces));
//////                                        pref.put(PrefContract.city, city.getText().toString());
////                                        pref.put(PrefContract.poscode, zip.getText().toString());
//////                                        pref.put(PrefContract.kelas, education.getText().toString());
//////                                        pref.put(PrefContract.last_education, education2.getText().toString());
////                                        pref.put(PrefContract.institution, school.getText().toString());
////                                        pref.put(PrefContract.bio, bio.getText().toString());
////                                        pref.put(PrefContract.photo, img);
//
////                                        passing_gender = gender.getText().toString();
////                                        passing_birthp = birthp.getText().toString();
////                                        passing_birth = birthd.getText().toString();
////                                        passing_address = address.getText().toString();
////                                        passing_zip = zip.getText().toString();
////                                        passing_school = school.getText().toString();
////                                        passing_bio = bio.getText().toString();
////                                        passing_photo = img;
//                                    } catch (AppException e) {
//                                        e.printStackTrace();
//                                    }

                                } else {

                                    Toast.makeText(PengalamanActivity.this, "failed, please try again.", Toast.LENGTH_SHORT).show();

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

}