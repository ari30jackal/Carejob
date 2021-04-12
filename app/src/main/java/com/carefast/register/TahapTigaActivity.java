package com.carefast.register;

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
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.carefast.Login.R;
import com.carefast.apihelper.BaseApiService;
import com.carefast.apihelper.FileUtils;
import com.carefast.apihelper.UtilsApi;
import com.carefast.contract.AppException;
import com.carefast.contract.PrefContract;
import com.carefast.contract.SecuredPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

public class TahapTigaActivity extends AppCompatActivity {
    ImageView btnlanjutkan, btnkembali, camera, camera2;
    Spinner spinnerpendidikan, spinnerlastposition, spinnerpengalaman;
    TextView tekspendidikan;
    String path;
    RelativeLayout rlpendidikan, rljabatan;
    private static final int FILE_SELECT_CODE = 0;
    private static final int CREATE_FILE = 1;
    private Bitmap bitmap;
    private Bitmap bitmaps;
    BaseApiService mApiService;
    String passing_uid;
    SecuredPreference pref;
    String lastpositionStr,pengalamanStr,pendidikanStr;
    String passing_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tahap_tiga);
        pref = new SecuredPreference(this, PrefContract.PREF_EL);
        mApiService = UtilsApi.getAPIService();
        camera = findViewById(R.id.camera);
        camera2 = findViewById(R.id.camera2);
        btnlanjutkan = findViewById(R.id.btn_lanjutkan);
        btnkembali = findViewById(R.id.btn_kembali);
        tekspendidikan = findViewById(R.id.tekspendidikan);
        spinnerpendidikan = findViewById(R.id.spinnerpendidikan);
        spinnerlastposition = findViewById(R.id.spinnerlastposition);
        spinnerpengalaman = findViewById(R.id.spinnerpengalaman);
        rljabatan = findViewById(R.id.rljabatan);
        rlpendidikan = findViewById(R.id.rlpendidikan);


        try {
            passing_uid = pref.getString(PrefContract.user_id, "");
            passing_email = pref.getString(PrefContract.email, "");
        } catch (AppException e) {
            e.printStackTrace();
        }

        Log.d("uidnya tahap 3", "onCreate: " + passing_uid + passing_email);


        rljabatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                panggilgambar();
            }
        });

        rlpendidikan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                panggilgambar2();
            }
        });




        btnlanjutkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pengalamanStr.toString().equals("Belum ada pengalaman")||lastpositionStr.toString().equals("Belum ada Pengalaman Kerja"))
                {

                    if (camera2.getDrawable() == null){

                        Toast.makeText(TahapTigaActivity.this, "Harap melampirkan File Pendidikan Terakhir", Toast.LENGTH_SHORT).show();
                    }

                    else {
                        bitmaps = ((BitmapDrawable) camera2.getDrawable()).getBitmap();
                        pengalamandanedu(bitmaps);
                    }
                }


                else{
                    if (camera2.getDrawable() == null || camera.getDrawable() == null){

                        Toast.makeText(TahapTigaActivity.this, "Harap melengkapi Lampiran File Pendukung", Toast.LENGTH_SHORT).show();
                    }
                    bitmap = ((BitmapDrawable) camera.getDrawable()).getBitmap();
                    bitmaps = ((BitmapDrawable) camera2.getDrawable()).getBitmap();
                    stepRegisThird(bitmap, bitmaps);
                }
            }
        });
        btnkembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
       Intent intent = new Intent(TahapTigaActivity.this,TahapDuaActivity.class);
       startActivity(intent);
       finish();
            }
        });



        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(TahapTigaActivity.this,
                R.array.position, android.R.layout.simple_spinner_item);
        // mengeset Array Adapter tersebut ke Spinner
        spinnerlastposition.setAdapter(adapter);
        // mengeset listener untuk mengetahui saat item dipilih
        spinnerlastposition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // memunculkan toast + value Spinner yang dipilih (diambil dari adapter)

                lastpositionStr = String.valueOf(adapter.getItem(i));
//                Toast.makeText(TahapTigaActivity.this, "Selected "+ lastpositionStr, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(TahapTigaActivity.this,
                R.array.pendidikanterakhir, android.R.layout.simple_spinner_item);
        // mengeset Array Adapter tersebut ke Spinner
        spinnerpendidikan.setAdapter(adapter2);
        // mengeset listener untuk mengetahui saat item dipilih
        spinnerpendidikan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // memunculkan toast + value Spinner yang dipilih (diambil dari adapter)

                pendidikanStr = String.valueOf(adapter2.getItem(i));
//                Toast.makeText(TahapTigaActivity.this, "Selected "+ lastpositionStr, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(TahapTigaActivity.this,
                R.array.pengalaman, android.R.layout.simple_spinner_item);
        // mengeset Array Adapter tersebut ke Spinner
        spinnerpengalaman.setAdapter(adapter3);
        // mengeset listener untuk mengetahui saat item dipilih
        spinnerpengalaman.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // memunculkan toast + value Spinner yang dipilih (diambil dari adapter)

                pengalamanStr = String.valueOf(adapter3.getItem(i));
//                Toast.makeText(TahapTigaActivity.this, "Selected "+ lastpositionStr, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private void panggilgambar() {

        ActivityCompat.requestPermissions(TahapTigaActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                2);


        Intent i = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, 1);

    }


    private void panggilgambar2() {

        ActivityCompat.requestPermissions(TahapTigaActivity.this,
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
                    bitmap = BitmapFactory.decodeStream(TahapTigaActivity.this.getContentResolver().openInputStream(uri));
                    int nh = (int) (bitmap.getHeight() * (512.0 / bitmap.getWidth()));

                    Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
                    camera.setImageBitmap(scaled);

                    Toast.makeText(TahapTigaActivity.this, "Berhasil Upload.", Toast.LENGTH_SHORT).show();


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                //pictureFlag = 1;

                //ivTemp.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            } else {
                Toast.makeText(TahapTigaActivity.this, "Gagal Upload.", Toast.LENGTH_SHORT).show();
            }
        }else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();


                Bitmap bitmap = null;
                try {
                    bitmap = BitmapFactory.decodeStream(TahapTigaActivity.this.getContentResolver().openInputStream(uri));
                    int nh = (int) (bitmap.getHeight() * (512.0 / bitmap.getWidth()));

                    Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
                    camera2.setImageBitmap(scaled);

                    Toast.makeText(TahapTigaActivity.this, "Berhasil Upload.", Toast.LENGTH_SHORT).show();


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                //pictureFlag = 1;

                //ivTemp.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            } else {
                Toast.makeText(TahapTigaActivity.this, "Gagal Upload.", Toast.LENGTH_SHORT).show();
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
        File file = new File(TahapTigaActivity.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "_" + passing_uid + "_job.JPEG");
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
        File file = new File(TahapTigaActivity.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "_" + passing_uid + "_education.JPEG");
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
    public void pengalamandanedu( Bitmap bitmaps) {
        //ngupdate data form 3/5


        File files = createTempFiles(bitmaps);
        RequestBody reqFiles = RequestBody.create(MediaType.parse("image/*"), files);
        MultipartBody.Part images = MultipartBody.Part.createFormData("images", files.getName(), reqFiles);


        RequestBody uidRB =
                RequestBody.create(MediaType.parse("multipart/form-data"), passing_uid);
        RequestBody pengalamanStrs =
                RequestBody.create(MediaType.parse("multipart/form-data"), pengalamanStr);
        RequestBody lastpositionStrs =
                RequestBody.create(MediaType.parse("multipart/form-data"), lastpositionStr);
        RequestBody pendidikanStrs =
                RequestBody.create(MediaType.parse("multipart/form-data"), pendidikanStr);

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
                                    Intent intent = new Intent(TahapTigaActivity.this, TahapEmpatActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(TahapTigaActivity.this, "berhasil update", Toast.LENGTH_SHORT).show();



                                } else {

                                    Toast.makeText(TahapTigaActivity.this, "Gagal Harap Coba Kembali.", Toast.LENGTH_SHORT).show();

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
    public void stepRegisThird(Bitmap bitmap, Bitmap bitmaps) {
        //ngupdate data form 3/5

        File file = createTempFile(bitmap);
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part image = MultipartBody.Part.createFormData("images", file.getName(), reqFile);


        File files = createTempFiles(bitmaps);
        RequestBody reqFiles = RequestBody.create(MediaType.parse("image/*"), files);
        MultipartBody.Part images = MultipartBody.Part.createFormData("image2", files.getName(), reqFiles);


        RequestBody uidRB =
                RequestBody.create(MediaType.parse("multipart/form-data"), passing_uid);
        RequestBody pengalamanStrs =
                RequestBody.create(MediaType.parse("multipart/form-data"), pengalamanStr);
        RequestBody lastpositionStrs =
                RequestBody.create(MediaType.parse("multipart/form-data"), lastpositionStr);
        RequestBody pendidikanStrs =
                RequestBody.create(MediaType.parse("multipart/form-data"), pendidikanStr);

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
                                    Intent intent = new Intent(TahapTigaActivity.this, TahapEmpatActivity.class);
                                    startActivity(intent);

                                    try {
                                        pref.put(PrefContract.user_email, passing_email);
                                    } catch (AppException e) {
                                        e.printStackTrace();
                                    }


//

                                } else {

                                    Toast.makeText(TahapTigaActivity.this, "Gagal Melanjutkan Periksa Kembali Data Anda.", Toast.LENGTH_SHORT).show();

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}