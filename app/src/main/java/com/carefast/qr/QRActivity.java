package com.carefast.qr;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.carefast.Login.R;
import com.carefast.contract.AppException;
import com.carefast.contract.PrefContract;
import com.carefast.contract.SecuredPreference;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRActivity extends  AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
SecuredPreference pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_r);
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        pref = new SecuredPreference(this, PrefContract.PREF_EL);
    }
    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        Log.d("try", rawResult.getText()); // Prints scan results
//        Log.d("tryj", rawResult.getBarcodeFormat().toString());
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Scan Result");
//        builder.setMessage(rawResult.getText());
//        AlertDialog alert1 = builder.create();
//        alert1.show();
//
//        mScannerView.resumeCameraPreview(this);
        try {
            pref.put(PrefContract.hasilscan,rawResult.getText());
        } catch (AppException e) {
            e.printStackTrace();
        }
        startActivity(new Intent(QRActivity.this,HadirInterview.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}