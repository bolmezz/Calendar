package com.example.beyza.calendar_android.feature;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


public class DrawerActivity extends AppCompatActivity {
    private static final String TAG = DrawerActivity.class.getSimpleName();
    ProgressDialog dialog;
    String urls = "https://immense-coast-39524.herokuapp.com/calendars";
    Button evnt;
    ProgressDialog pDialog;
    int secilen;
    ListView events;
    ArrayList<HashMap<String, String>> contactList;
    TextView titleT ;

    // Database'den çekilen veriler burada listelenecek
    @Override
    protected void onCreate(final Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.item_list);

        titleT = (TextView) findViewById(R.id.title);


        new GetContacts().execute("https://immense-coast-39524.herokuapp.com/calendars");

    }//oncreate


    private class GetContacts extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DrawerActivity.this);
            pDialog.show();
            Toast.makeText(DrawerActivity.this, "Json Data is downloading...", Toast.LENGTH_LONG).show();

        }

        protected String doInBackground(String...params) {


            HttpURLConnection connection = null;

            BufferedReader br = null;

                try {


                  URL url = new URL(params[0]);
                  connection =(HttpURLConnection) url.openConnection();
                  connection.connect();
                    InputStream is = connection.getInputStream();
                    br = new BufferedReader(new InputStreamReader(is));
                    String satir;
                    String dosya ="";
                    while((satir = br.readLine()) != null){
                        Log.d("satir:",satir);
                        dosya +=satir;
                    }
                    return dosya;


                }  catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return "hata";

        }


        protected void onPostExecute(String s) {

           // super.onPostExecute(s);
             Log.d("postExecute'tan gelen",s);

             pDialog.dismiss();
            try {


                JSONArray ja = new JSONArray(s);
                int count =ja.length();

                for(int i =0; i<count;i++){


                }


                JSONObject jo = ja.getJSONObject(2);


                titleT.append(jo.getString("title"));


            } catch (JSONException e) {
                e.printStackTrace();
            }




        }


    }//class

}


