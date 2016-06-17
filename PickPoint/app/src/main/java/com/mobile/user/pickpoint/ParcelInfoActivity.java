package com.mobile.user.pickpoint;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ParcelInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcel_info);
    }

    public void showParcelArranging(View view)
    {
        Intent intent = new Intent(ParcelInfoActivity.this, ParcelArrangingActivity.class);
        startActivity(intent);
    }

}
