package com.carefast.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.carefast.Login.R;
import com.carefast.contract.AppException;
import com.carefast.contract.PrefContract;
import com.carefast.contract.SecuredPreference;

public class RincianAlamat extends AppCompatActivity {
ImageView back,del;
TextView title,submitrincian;
SecuredPreference pref;
EditText detil;
String rincian;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rincian_alamat);


        pref = new SecuredPreference(this, PrefContract.PREF_EL);
        back = findViewById(R.id.backbutton);
        title = findViewById(R.id.title);
        del = findViewById(R.id.del);
        submitrincian = findViewById(R.id.submitrincianalamat);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detil.setText("");
                try {
                    pref.put(PrefContract.rincian_show, "");

                } catch (AppException e) {
                    e.printStackTrace();
                }
            }
        });
        detil = findViewById(R.id.ketikdetil);
        try {
            rincian = pref.getString(PrefContract.rincian_show,"");
        } catch (AppException e) {
            e.printStackTrace();
        }
        detil.setText(rincian);
        title.setText("Rincian Alamat");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();


            }
        });


        submitrincian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
                try {
                    pref.put(PrefContract.rincian_show,detil.getText().toString());
                } catch (AppException e) {
                    e.printStackTrace();
                }
            }
        });




    }
}