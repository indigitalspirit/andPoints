package com.mobile.user.pickpoint;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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


public class PickPointActivity extends FragmentActivity implements EditAuthDialogListener, GetDataListener {


    Button deliveryBtn, recievingBtn, returningBtn, reportsBtn;
    String tag = "Edit";
    String PB_key = null, encryptedJSONstring = null, userLogin = null;
    Boolean authorized = false;

    FragmentManager fm = getSupportFragmentManager();
    LoginDialog myLoginDialog = new LoginDialog();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        try {
            //xml = encryptedXml.GenerateXMLString(textToEncode);
            //   Log.i("XML", xml);

            /*
            new XmlParser(new XmlParser.AsyncResponse() {
                @Override
                public void processFinish(String output) {
                    //key = output;
                    Log.i("XMLPARSER", output);
                }
            }).execute();
            */
            new XmlParser(this).execute();

        } catch (Exception e) {
            e.printStackTrace();
        }



        myLoginDialog.show(fm, "edit");
        myLoginDialog.setCancelable(false);
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
    public void OnFinishEditDialog(String login, Boolean correct) {
        Log.i("LOGIN", login);
        userLogin = login;
        //String key = null;
        //key = onGetDataComplete(String result);

        CreateEncryptedJSON encryptedJSONcontent = new CreateEncryptedJSON();
        try {
            encryptedJSONstring = encryptedJSONcontent.JsonSerialaizer(userLogin, PB_key);
            Log.i("Created JSON object", encryptedJSONstring);
            new BackgroundTask(this).execute("http://82.196.66.12:12173/reciever.php", encryptedJSONstring);

        } catch (Exception e) {
            e.printStackTrace();
        }

        //if(authorized) {
        //  setContentView(R.layout.activity_pick_point);
        //  setAllButtonsToClickable();
        // }


        //deliveryBtn = (Button) findViewById(R.id.deliveryButton);

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
                //code = new JsonParser().ParseJsonString(result);

                responseArray = new JsonParser().ParseJsonString(result);
                //editResult.setText((CharSequence) catnamesList.get(0));
                code = (CharSequence) responseArray.get(0);



                //if (code.contentEquals("OK")) {
                if (code.toString().contentEquals("OK")) {
                    authorized = true;
                    Log.i("AUTHORISED ", code.toString());
                    //myLoginDialog.dismiss();
                    ArrayList addresses = (ArrayList) responseArray.get(1);

                    setContentView(R.layout.activity_pick_point);
                    setAllButtonsToClickable();
                    TextView viewResult;
                    viewResult = (TextView) findViewById(R.id.address_view);
                    viewResult.setText(addresses.get(0).toString() + " " + addresses.get(1).toString() + " " + addresses.get(2).toString());


                } else if (code.toString().contentEquals("FAIL")) {

                    Log.i("NOT AUTHORISED ", code.toString());
                    myLoginDialog.show(fm, "edit");
                    myLoginDialog.setCancelable(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

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