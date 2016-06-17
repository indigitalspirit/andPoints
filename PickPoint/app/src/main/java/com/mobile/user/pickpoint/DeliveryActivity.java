package com.mobile.user.pickpoint;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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








    }

    public void showDeliveryInfo(View view) throws IOException
    {

        codeEditor =  (EditText) findViewById(R.id.code_editor);

        Boolean right_code = checkCode(codeEditor);



        //Intent intent = new Intent(PickPointActivity.this, DeliveryActivity.class);
       // startActivity(intent);
    }

    private Boolean checkCode(EditText codeEditor) throws IOException {

        try {
            String entered_code = codeEditor.getText().toString();

            if(entered_code.contentEquals("-1")) {

                Toast toast = Toast.makeText(getApplicationContext(), this.getString(R.string.wrong_delivery_code) ,
                        Toast.LENGTH_SHORT);

                toast.show();
            }
            else if (entered_code.contentEquals("1")) {
                Intent intent = new Intent(DeliveryActivity.this, ParcelInfoActivity.class);
                 startActivity(intent);
            }

        }
        catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }


}
