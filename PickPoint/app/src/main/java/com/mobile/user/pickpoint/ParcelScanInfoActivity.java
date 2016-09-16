package com.mobile.user.pickpoint;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.mobile.user.pickpoint.IntentIntegrator;
import com.mobile.user.pickpoint.IntentResult;


public class ParcelScanInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcel_info);
    }

    public void showParcelArranging(View view)
    {

        Intent intent = new Intent(ParcelScanInfoActivity.this, ParcelArrangingActivity.class);
        startActivity(intent);


    }


    public void scanParcelInfo(View view) {

        //Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        //intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
        //startActivityForResult(intent, 0);
        IntentIntegrator integrator = new IntentIntegrator(ParcelScanInfoActivity.this);
        integrator.initiateScan(IntentIntegrator.TARGET_BARCODE_SCANNER_ONLY);
    }

    /*
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                Log.i("BARCODE CONTENTS ", contents);
                Log.i("BARCODE FORMAT ", format);
                // Handle successful scan
            } else if (resultCode == RESULT_CANCELED) {
                // Handle cancel
                Log.i("BARCODE ERROR ", "");
            }
        }
    }
    */

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult result =
                IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (result != null) {
            String contents = result.getContents();
            if (contents != null) {
                Log.i("BARCODE SCANNED", contents);
            } else {
                Log.i("BARCODE NOT SCANNED", "");
            }
        }
    }



}
