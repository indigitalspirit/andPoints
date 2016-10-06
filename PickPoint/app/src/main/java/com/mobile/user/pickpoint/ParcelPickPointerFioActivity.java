package com.mobile.user.pickpoint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by user on 21.09.16.
 */
public class ParcelPickPointerFioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_pointer_fio);
    }

    public void acceptAdmFIO(View view) {

        Intent intent = new Intent(ParcelPickPointerFioActivity.this, ParcelReceiverFIOActivity.class);
        startActivity(intent);
    }


}


