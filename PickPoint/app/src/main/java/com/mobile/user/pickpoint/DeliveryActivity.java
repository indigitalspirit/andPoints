package com.mobile.user.pickpoint;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

public class DeliveryActivity extends AppCompatActivity {

    EditText codeEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);





        codeEditor =  (EditText) findViewById(R.id.code_editor);

        codeEditor.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        Log.i("TEST", "Done pressed!");
                        String entered_code = codeEditor.getText().toString();
                        try {

                            checkingCode(entered_code);

                        } catch (Exception e) {

                        }
                    }

                    return true;
                }
                return false;
            }
        });


    }

    public void checkingCode(String codeFromEditor) throws IOException {

        try {
            //String entered_code = codeEditor.getText().toString();

            if(!codeFromEditor.contentEquals("1")) {

                Toast toast = Toast.makeText(getApplicationContext(), this.getString(R.string.wrong_delivery_code) ,
                        Toast.LENGTH_LONG);

                toast.show();
                codeEditor.setText("");
            }
            else if (codeFromEditor.contentEquals("1")) {

                //  Toast toast = Toast.makeText(getApplicationContext(), this.getString(R.string.wrong_delivery_code) ,
                //    Toast.LENGTH_LONG);

                //toast.show();
                Intent intent = new Intent(DeliveryActivity.this, ParcelScanInfoActivity.class);
                startActivity(intent);
            }

            // if(!entered_code.isEmpty()) {

            //Intent intent = new Intent(DeliveryActivity.this, ParcelScanInfoActivity.class);
            //startActivity(intent);
            // }


        }
        catch (Exception e) {

            e.printStackTrace();
        }

    }


    public void showDeliveryInfo(View view) throws IOException
    {




        Boolean right_code = checkCode(codeEditor);


        //Intent intent = new Intent(PickPointActivity.this, DeliveryActivity.class);
        //startActivity(intent);
    }

    private Boolean checkCode(EditText codeEditor) throws IOException {

        try {
            String entered_code = codeEditor.getText().toString();


            if(!entered_code.contentEquals("1")) {

                Toast toast = Toast.makeText(getApplicationContext(), this.getString(R.string.wrong_delivery_code) ,
                        Toast.LENGTH_LONG);

                toast.show();
                codeEditor.setText("");
            }
            else if (entered_code.contentEquals("1")) {

              //  Toast toast = Toast.makeText(getApplicationContext(), this.getString(R.string.wrong_delivery_code) ,
                    //    Toast.LENGTH_LONG);

                //toast.show();
                Intent intent = new Intent(DeliveryActivity.this, ParcelScanInfoActivity.class);
                 startActivity(intent);
            }

           // if(!entered_code.isEmpty()) {

            //Intent intent = new Intent(DeliveryActivity.this, ParcelScanInfoActivity.class);
            //startActivity(intent);
           // }


        }
        catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

   // @Override
    /*
    public boolean onKey(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            Log.i("TEST", "Done pressed!");

            return true;
        }
        return false;
    }
    */

}
