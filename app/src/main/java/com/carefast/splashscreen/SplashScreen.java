package com.carefast.splashscreen;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.Toast;

import com.carefast.Login.LoginActivity;
import com.carefast.Login.R;
import com.carefast.activity.LowkerQrActivity;
import com.carefast.contract.AppException;
import com.carefast.contract.PrefContract;
import com.carefast.contract.SecuredPreference;
import com.carefast.home.HomeeActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class SplashScreen extends AppCompatActivity {
    SecuredPreference pref;
    String login;
    private static final int PERMISSION_REQUEST_CODE = 200;
    SharedPreferences prefs = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);
        Uri uri = getIntent().getData();
        if (checkPermission()) {
            //main logic or main code

            // . write your main code to execute, It will execute if the permission is already given.

        } else {
            requestPermission();
        }
        pref = new SecuredPreference(this, PrefContract.PREF_EL);

        try {
           login= pref.getString(PrefContract.user_login,"");

        } catch (AppException e) {
            e.printStackTrace();
        }
        final Handler handler = new Handler();
        handler.postDelayed(() -> {


            if (login.equals("1"))
            {
                if (uri!=null){

                    String path = uri.toString();

                    String[] separated = path.split("=");
                    path = separated[1] = separated[1].trim();
                    try {
                        pref.put(PrefContract.id_advertisement,path);
                    } catch (AppException e) {
                        e.printStackTrace();
                    }

                    startActivity(new Intent(getApplicationContext(), LowkerQrActivity.class));
                    finish();
                }
                else{
                    startActivity(new Intent(getApplicationContext(), HomeeActivity.class));
                    finish();
                }
            }
            else{

                pref.clear();
                if (uri!=null){

                    String path = uri.toString();

                    String[] separated = path.split("=");
                    path = separated[1] = separated[1].trim();
                    try {
                        pref.put(PrefContract.id_advertisement,path);
                    } catch (AppException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(this, "Anda Harus Login Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }
                else{
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();}
            }


        }, 3000L); //3000 L = 3 detik

    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();

                    // main logic
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            showMessageOKCancel("You need to allow access permissions",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermission();
                                            }
                                        }
                                    });
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(SplashScreen.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


}
