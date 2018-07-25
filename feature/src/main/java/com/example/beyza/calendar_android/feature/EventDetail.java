package com.example.beyza.calendar_android.feature;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class EventDetail extends AppCompatActivity {

    TextView text ;
    Button edit,delete;
    String s;
    ProgressDialog pDialog;

    protected void onCreate(final Bundle savedInstanceState) {

        SearchActivity sa = new SearchActivity();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.events);

        s = sa.getSearch();

       text = (TextView)findViewById(R.id.textView2);
       text.setText(s);
       delete = (Button) findViewById(R.id.delete_btn);
       edit = (Button) findViewById(R.id.edit_btn);

       new EventDetail.GetContacts().execute("https://immense-coast-39524.herokuapp.com/calendars");



        delete.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {



            }

        });

       edit.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {


            }

        });



    }//oncreate



    private class GetContacts extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EventDetail.this);
            pDialog.setMessage("Searching..");
            pDialog.show();

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

            Log.d("postExecute'tan gelen",s);
            try {
                JSONArray ja = new JSONArray(s);
                int count =ja.length();

                for(int i =0; i<count;i++){
                    JSONObject jo = ja.getJSONObject(i);

                    if(jo.getString("title").equalsIgnoreCase(s)) {

                        text.append("Title: " + jo.getString("title") + "\n");
                        text.append("Content: " + jo.getString("content") + "\n");
                        text.append("Location: " + jo.getString("location") + "\n");
                        text.append("Start Date: " + jo.getString("start_date") + "\n");
                        text.append("Start Time: " + jo.getString("start_time") + "\n");
                        text.append("End Date: " + jo.getString("end_date") + "\n");
                        text.append("End Time: " + jo.getString("end_time") + "\n");
                        text.append("Repeat: " + jo.getString("repeat") + "\n");
                        text.append("Reminer: " + jo.getString("reminder") + "\n");
                        text.append("\n\n");

                    }
                    else
                    {
                        text.setText("Event has not found !");
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            pDialog.dismiss();

        }


    }//class




}
