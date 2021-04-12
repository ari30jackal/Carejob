package com.carefast.register;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.carefast.splashscreen.SplashScreen;

import androidx.appcompat.app.AppCompatActivity;
import kotlin.jvm.internal.Intrinsics;

public class Deeplink extends AppCompatActivity {

    private Uri datas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_deeplink);
        Intent intent = new Intent(Deeplink.this, SplashScreen.class);
        startActivity(intent);
    }

    protected void onResume() {
        super.onResume();
        Intent intent = this.getIntent();
        Intrinsics.checkNotNullExpressionValue(intent, "intent");
        this.datas = intent.getData();
        Log.v("TestingsDatas", "" + this.datas);
    }
}