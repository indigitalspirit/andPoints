package com.mobile.user.pickpoint;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by user on 28.09.16.
 */
public class LoginHeadPiece extends AppCompatActivity implements XmlParser.GetDataListener, View.OnClickListener {


    //  EditText loginEdit;
    // Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnDel, btnOk;
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnDel, btnOk;
    EditText loginEdit;
    String tag = "Edit";
    String PB_key = null, encryptedJSONstring = null, userLogin = null;
    Boolean authorized = false;
    SharedPreferences sp;
    private TextView selection;
    Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_head_piece);
        setContentView(R.layout.keyboard);


        try {

            Boolean startParsing = false;

            startParsing = isNetworkAvailable(this);

            if (startParsing) {
                new XmlParser(this).execute();
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), this.getString(R.string.no_internet_connection),
                        Toast.LENGTH_SHORT);

                toast.show();


            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btn7 = (Button) findViewById(R.id.btn7);
        btn8 = (Button) findViewById(R.id.btn8);
        btn9 = (Button) findViewById(R.id.btn9);
        btn0 = (Button) findViewById(R.id.btn0);
        btnDel = (Button) findViewById(R.id.btnDel);
        btnOk = (Button) findViewById(R.id.btnOk);
        loginEdit = (EditText) findViewById(R.id.editTextPasswd);


        // присвоим обработчик кнопке OK (btnOk)
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn0.setOnClickListener(this);
        btnOk.setOnClickListener(this);
        btnDel.setOnClickListener(this);

        // Intent intent = new Intent(PickPointActivity.this, LoginHeadPiece.class);
        //startActivity(intent);
        /**** 28.09 ***/


        //  Intent intent = new Intent(LoginHeadPiece.this, PickPointActivity.class);
        //  startActivity(intent);


        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setLogo(R.drawable.icon);

        // setSupportActionBar(toolbar);
    }


    public static boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (null != activeNetwork) {
                if ((activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) || (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE))
                    return true;
            }


        } catch (Exception e) {
            return false;
        }
        return false;
    }


    @Override
    public void onGetDataComplete(String result, String classTag) throws IOException, JSONException {

        if (result == null) {

            Toast toast = Toast.makeText(getApplicationContext(), this.getString(R.string.no_internet_connection),
                    Toast.LENGTH_SHORT);

            toast.show();
        } else {

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
                        Intent intent = new Intent(LoginHeadPiece.this, PickPointActivity.class);
                        startActivity(intent);

                        //ArrayList addresses = (ArrayList) responseArray.get(1);

                        //setContentView(R.layout.activity_pick_point);
                        //setContentView(R.layout.menu);

                        //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                        //  toolbar.setLogo(R.drawable.icon);

                        //setSupportActionBar(toolbar);
                        // setAllButtonsToClickable();
                        //myLoginDialog.dismiss();

                        //spinner =  (Spinner) findViewById(R.id.address_spinner);
                        //spinner.setOnItemSelectedListener(this);

                        //  ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                        //         R.layout.support_simple_spinner_dropdown_item, addresses);
                        // dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        // spinner.setAdapter(dataAdapter);


                    } else if (code.toString().contentEquals("FAIL")) {

                        Log.i("NOT AUTHORISED ", code.toString());

                        Toast toast_2 = Toast.makeText(getApplicationContext(), this.getString(R.string.wrong_login),
                                Toast.LENGTH_SHORT);

                        toast_2.show();

                        // myLoginDialog.show(fm, "edit");

                        //myLoginDialog.setCancelable(false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }
    }











     /*
        try {

            Boolean startParsing = false;

            startParsing = isNetworkAvailable(this);

            if (startParsing) {
               // new XmlParser(this).execute();


            } else {
                Toast toast = Toast.makeText(getApplicationContext(), this.getString(R.string.no_internet_connection),
                        Toast.LENGTH_SHORT);

                toast.show();


            }
        }
            catch (Exception e) {
            e.printStackTrace();
        }


    }
    */


    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.btn1:
                setDigitToEditText(1);
                break;

            case R.id.btn2:
                setDigitToEditText(2);
                break;

            case R.id.btn3:
                setDigitToEditText(3);
                break;

            case R.id.btn4:
                setDigitToEditText(4);
                break;

            case R.id.btn5:
                setDigitToEditText(5);
                break;

            case R.id.btn6:
                setDigitToEditText(6);
                break;

            case R.id.btn7:
                setDigitToEditText(7);
                break;

            case R.id.btn8:
                setDigitToEditText(8);
                break;

            case R.id.btn9:
                setDigitToEditText(9);
                break;

            case R.id.btn0:
                setDigitToEditText(0);
                break;

            case R.id.btnOk:
                //setDigitToEditText();
                String login;
                login = loginEdit.getText().toString();
                OnFinishEdit(login);
                break;

            case R.id.btnDel:
                //setDigitToEditText(5);
                break;

            default:
                break;
        }

    }


    public void OnFinishEdit(String login) {
        Log.i("LOGIN", login);
        userLogin = login;

        CreateEncryptedJSON encryptedJSONcontent = new CreateEncryptedJSON();
        try {
            encryptedJSONstring = encryptedJSONcontent.JsonSerialaizer(userLogin, PB_key);
            Log.i("Created JSON object", encryptedJSONstring);
            new BackgroundTask(this).execute("http://nastya.boincfast.ru/reciever_json.php", encryptedJSONstring);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void setDigitToEditText(int digit) {
        Editable oldText;
        String text;

        oldText = loginEdit.getText();
        text = oldText.toString();

        loginEdit.setText("");
        loginEdit.append(text + Integer.toString(digit));
       // loginEdit.moveCursorToVisibleOffset();

    }

    /*
    public void onGetDataComplete(String result, String classTag) throws IOException, JSONException {

        if (classTag.contentEquals("xml")) {
            PB_key = result;
            Log.i("RESULT ", result);
        } else {
            if (classTag.contentEquals("json")) {
                CharSequence code = null;
                ArrayList responseArray = new ArrayList();
                Log.i("RESULT JSON", result);


                try {

                    responseArray = new JsonParser().ParseJsonString(result);

                    code = (CharSequence) responseArray.get(0);


                    if (code.toString().contentEquals("OK")) {
                        Boolean authorized = true;
                        Log.i("AUTHORISED ", code.toString());

                        ArrayList addresses = (ArrayList) responseArray.get(1);

                        //setContentView(R.layout.activity_pick_point);
                        setContentView(R.layout.menu);

                        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                        // toolbar.setLogo(R.drawable.icon);

                        //setSupportActionBar(toolbar);
                        //setAllButtonsToClickable();
                        //myLoginDialog.dismiss();

                        //spinner =  (Spinner) findViewById(R.id.address_spinner);
                        // spinner.setOnItemSelectedListener(this);

                        // ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                        //         R.layout.support_simple_spinner_dropdown_item, addresses);
                        // dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        //  spinner.setAdapter(dataAdapter);


                    } else if (code.toString().contentEquals("FAIL")) {

                        Log.i("NOT AUTHORISED ", code.toString());

                        Toast toast = Toast.makeText(getApplicationContext(), this.getString(R.string.wrong_login),
                                Toast.LENGTH_SHORT);

                        toast.show();

                        // myLoginDialog.show(fm, "edit");

                        // myLoginDialog.setCancelable(false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


    }
    */


    // setContentView(R.layout.keyboard);






        /*
        // создаем обработчик нажатия
        View.OnClickListener oclBtnOk = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Меняем текст в TextView (tvOut)
                loginEdit.setText("1");
                Log.i("LoginDialog", "Нажата кнопка ОК");
            }
        };
        */


}
