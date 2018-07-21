package com.example.beyza.calendar_android.feature;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    // Database'den çekilen veriler burada listelenecek
    @Override
    protected void onCreate(final Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.events);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .6));

        contactList = new ArrayList<>();
        events = (ListView) findViewById(R.id.eventList);

        new GetContacts().execute();

    }//oncreate


    private class GetContacts extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(DrawerActivity.this, "Json Data is downloading...", Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "https://immense-coast-39524.herokuapp.com/calendars";
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if(jsonStr != null) {
                try {
               /* URL url = new URL("https://immense-coast-39524.herokuapp.com/calendars");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                Log.e(TAG, "response from url :" + url.toString());*/

                    JSONObject jsonObj = new JSONObject(url.toString());

                    JSONArray contacts = jsonObj.getJSONArray("");

                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        String title = c.getString("title");

                        HashMap<String, String> contact = new HashMap<>();

                        contact.put("title", title);
                        contactList.add(contact);

                        // HATA VERİYORRR !!!!

                    }

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
            else{

                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ListAdapter adapter = new SimpleAdapter(DrawerActivity.this, contactList,
                    R.layout.list_item, new String[]{"title"},
                    new int[]{R.id.title});
            events.setAdapter(adapter);
        }


    }//class

}


