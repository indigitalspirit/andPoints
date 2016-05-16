package com.mobile.user.pickpoint;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.mobile.user.pickpoint.LoginDialog.EditAuthDialogListener;



public class PickPointActivity extends FragmentActivity implements EditAuthDialogListener {


    Button deliveryBtn, recievingBtn, returningBtn, reportsBtn;
    String tag = "Edit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_pick_point);

        FragmentManager fm = getSupportFragmentManager();
        LoginDialog myLoginDialog = new LoginDialog();
        myLoginDialog.show(fm, "edit");
        //new CustomDialogFragment().show();
    }

    //realize EditAuthorDialog Listener interface

    protected void setAllButtonsToClickable() {
        deliveryBtn = (Button) findViewById(R.id.deliveryButton);
        recievingBtn = (Button) findViewById(R.id.recieveButton);
        reportsBtn = (Button) findViewById(R.id.reportsButton);
        returningBtn = (Button) findViewById(R.id.returnButton);

        deliveryBtn.setClickable(true);
        recievingBtn.setClickable(true);
        reportsBtn.setClickable(true);
        returningBtn.setClickable(true);
    }

    @Override
    public void OnFinishEditDialog(String login) {
        Log.i("LOGIN", login);
        setAllButtonsToClickable();

        //deliveryBtn = (Button) findViewById(R.id.deliveryButton);

    }


}



  /*
        AlertDialog.Builder builder = new AlertDialog.Builder(PickPointActivity.this);


        builder.setTitle("Введите пароль")
                //.setMessage("Покормите кота!")
               // .setIcon(R.drawable.ic_android_cat)
                .setCancelable(false)
                .setView(R.layout.activity_login)
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                setContentView(R.layout.activity_pick_point);
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
        */