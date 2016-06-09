package com.mobile.user.pickpoint;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * Created by user on 02.06.16.
 */
public class JsonParser {


    private static final String TAG = CreateEncryptedJSON.class.getSimpleName();
    private static final int KEY_SIZE = 1024;
    private String iv = "fedcba9876543210";//Dummy iv (CHANGE IT!)
    private String SecretKey = "0123456789abcdef";//Dummy secretKey (CHANGE IT!)



    String deserializeJson(String jsonData) throws JSONException {
        //String jsonData = response.body().string();
        JSONObject jsonObject = new JSONObject(jsonData);
        String codecheck = jsonObject.getString("codecheck");
        // JSONObject jObj = new JSONObject(jsonData);


        return codecheck;
    }

    /*_____________________________________JSON SERIALAIZE START_____________________________*/
    protected ArrayList ParseJsonString(String jsonString) throws IllegalArgumentException, IllegalStateException, IOException, JSONException {


        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            String codecheck = jsonObject.getString("codecheck");

            ArrayList jsonResultList = new ArrayList();
            jsonResultList.add(codecheck);

            if(codecheck.contentEquals("OK")) {
                String user_id = jsonObject.getString("id");
                String msg = jsonObject.getString("message");

                // = new JSONObject(); // we need another object to store the address

                JSONObject jsonSettings = jsonObject.getJSONObject("settings");
                String update_period = jsonSettings.getString("update_period");

                JSONArray jArray = jsonObject.getJSONArray("address");

                System.out.println("*****JARRAY*****" + jArray.length());


                ArrayList jsonResultListPoint = new ArrayList();

                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json_data = jArray.getJSONObject(i);

                    String name = json_data.getString("name");
                    String city = json_data.getString("city");
                    String street = json_data.getString("street");

                    String hole_address = name+' '+city+' '+street;

                    jsonResultListPoint.add(hole_address);
                 //   jsonResultListPoint.add(city);
                 //   jsonResultListPoint.add(street);
                  //  jsonResultList.add(codecheck);

                }
            /*
            JSONObject jsonAddress = jsonObject.getJSONObject("address");

            String name = jsonAddress.getString("name");
            String city = jsonAddress.getString("city");
            String street = jsonAddress.getString("street");
               */



                jsonResultList.add(jsonResultListPoint);


                Log.i("JS parser user_id ", user_id);
                Log.i("JS parser msg ", msg);
                Log.i("JS parser upd ", update_period);
                //if(codecheck.contentEquals("OK")) {

                // Log.i("CODECHECK ", "OK");
                //String message = jsonObject.getString("message");


                // }
            }
            return jsonResultList;


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;


    }
}
