package com.mobile.user.pickpoint;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

public class ParcelArrangingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcel_arranging);
    }




    public void orderParcel(View view) {

                Intent intent = new Intent(ParcelArrangingActivity.this, ParcelFormalizeActivity.class);
                startActivity(intent);

    }


    public void goBack(View view) {

        Intent intent = new Intent(ParcelArrangingActivity.this, ParcelScanInfoActivity.class);
        startActivity(intent);

    }

            // if(!entered_code.isEmpty()) {

            //Intent intent = new Intent(DeliveryActivity.this, ParcelScanInfoActivity.class);
            //startActivity(intent);
            // }







}
