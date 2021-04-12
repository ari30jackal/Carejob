package com.carefast.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.carefast.Login.R;
import com.carefast.qr.QRActivity;
import com.carefast.fragment.HomeFragment;
import com.carefast.fragment.JobFragment;
import com.carefast.fragment.NotifFragment;
import com.carefast.fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    TextView teks;
    BottomNavigationView menu_bawah;
    FragmentManager fragmentManager;
    ImageView Scan;
    FragmentTransaction fragmentTransaction;
    Fragment fragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homee);
        menu_bawah = findViewById(R.id.bn_main);
Scan  = findViewById(R.id.ll_scan);


        menu_bawah.setOnNavigationItemSelectedListener(this);

        Fragment fragment = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.screen_area, fragment);
        ft.commit();
Scan.setOnClickListener(v->{
    Intent intent = new Intent(HomeeActivity.this, QRActivity.class);
startActivity(intent);
finish();
});
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.home:

                fragment = new HomeFragment();
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.screen_area,fragment);
                fragmentTransaction.commit();
                break;
            case R.id.notif:

                fragment = new NotifFragment();
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.screen_area,fragment);
                fragmentTransaction.commit();
                break;
            case R.id.profile:


                fragment = new ProfileFragment();
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.screen_area,fragment);
                fragmentTransaction.commit();

                break;
            case R.id.scan:
//
//
//                fragment = new ScanFragment();
//                fragmentManager = getSupportFragmentManager();
//                fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.screen_area,fragment);
//                fragmentTransaction.commit();

                break;
            case R.id.jobs:


                fragment = new JobFragment();
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.screen_area,fragment);
                fragmentTransaction.commit();

                break;
        }

        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }



}