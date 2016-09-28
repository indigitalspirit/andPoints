package com.mobile.user.pickpoint;

import android.content.DialogInterface;
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
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by user on 28.09.16.
 */
public class LoginHeadPiece extends AppCompatActivity implements XmlParser.GetDataListener, View.OnClickListener {


     EditText loginEdit;
     Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnDel, btnOk;
     String PB_key = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_head_piece);
        setContentView(R.layout.keyboard);


        loginEdit = (EditText) findViewById(R.id.password);

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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setLogo(R.drawable.icon);

        setSupportActionBar(toolbar);



        try {

            new XmlParser(this).execute();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

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
                break;

            case R.id.btnDel:
                //setDigitToEditText(5);
                break;

            default:
                break;
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


}
