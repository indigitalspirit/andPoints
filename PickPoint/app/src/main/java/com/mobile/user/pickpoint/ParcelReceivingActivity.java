package com.mobile.user.pickpoint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by user on 05.10.16.
 */
public class ParcelReceivingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_parcel_receiving);
    }


    public void showPickPointerFIOAssignment(View view) {

        Intent intent = new Intent(ParcelReceivingActivity.this, ParcelPickPointerFioActivity.class);
        startActivity(intent);
    }

}
