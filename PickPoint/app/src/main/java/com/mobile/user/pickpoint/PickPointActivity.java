package com.mobile.user.pickpoint;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.user.pickpoint.LoginDialog.EditAuthDialogListener;
import com.mobile.user.pickpoint.XmlParser.GetDataListener;


import com.mobile.user.pickpoint.XmlParser;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class PickPointActivity extends AppCompatActivity implements EditAuthDialogListener, GetDataListener, AdapterView.OnItemSelectedListener {


    Button deliveryBtn, recievingBtn, returningBtn, reportsBtn;
    String tag = "Edit";
    String PB_key = null, encryptedJSONstring = null, userLogin = null;
    Boolean authorized = false;
    SharedPreferences sp;
    private TextView selection;
    Spinner spinner;



    FragmentManager fm = getSupportFragmentManager();
    LoginDialog myLoginDialog = new LoginDialog();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        try {

            new XmlParser(this).execute();

        } catch (Exception e) {
            e.printStackTrace();
        }

        myLoginDialog.show(fm, "edit");
        myLoginDialog.setCancelable(false);

    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
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
    public void OnFinishEditDialog(String login, Boolean correct) {
        Log.i("LOGIN", login);
        userLogin = login;

        CreateEncryptedJSON encryptedJSONcontent = new CreateEncryptedJSON();
        try {
            encryptedJSONstring = encryptedJSONcontent.JsonSerialaizer(userLogin, PB_key);
            Log.i("Created JSON object", encryptedJSONstring);
            new BackgroundTask(this).execute("http://82.196.66.12:12173/reciever.php", encryptedJSONstring);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }



    @Override
    public void onGetDataComplete(String result, String classTag) throws IOException, JSONException {

        if (classTag.contentEquals("xml")) {
            PB_key = result;
            Log.i("RESULT ", result);
        } else if (classTag.contentEquals("json")) {
            CharSequence code = null;
            ArrayList responseArray = new ArrayList();
            Log.i("RESULT JSON", result);


            try {

                responseArray = new JsonParser().ParseJsonString(result);

                code = (CharSequence) responseArray.get(0);


                if (code.toString().contentEquals("OK")) {
                    authorized = true;
                    Log.i("AUTHORISED ", code.toString());

                    ArrayList addresses = (ArrayList) responseArray.get(1);

                    //setContentView(R.layout.activity_pick_point);
                    setContentView(R.layout.menu);
                    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                    setSupportActionBar(toolbar);

                   // FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                  //  fab.setOnClickListener(new View.OnClickListener() {
                   //     @Override
                  //      public void onClick(View view) {
                    //        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                  //                  .setAction("Action", null).show();
                  //      }
                //    });



                    setAllButtonsToClickable();
                    myLoginDialog.dismiss();


                    spinner =  (Spinner) findViewById(R.id.address_spinner);


                    spinner.setOnItemSelectedListener(this);

                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                            R.layout.support_simple_spinner_dropdown_item, addresses);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(dataAdapter);


                } else if (code.toString().contentEquals("FAIL")) {

                    Log.i("NOT AUTHORISED ", code.toString());

                    Toast toast = Toast.makeText(getApplicationContext(), this.getString(R.string.wrong_login) ,
                            Toast.LENGTH_SHORT);

                    toast.show();

                    myLoginDialog.show(fm, "edit");

                    myLoginDialog.setCancelable(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }


}




/***********************************************************************/

    //public void onGetDataComplete(String result) {
        //PB_key = result;
    //    Log.i("RESULT BG", result);
        //return PB_key;

        //try {
        /*
            CreateEncryptedJSON encryptedJSONcontent = new CreateEncryptedJSON();
        try {
            encryptedJSONstring = encryptedJSONcontent.JsonSerialaizer("Hello", PB_key);
            Log.i("Created JSON object", encryptedJSONstring);
            new BackgroundTask().execute("http://82.196.66.12:12173/reciever.php", encryptedJSONstring);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    */

   // }




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