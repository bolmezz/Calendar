package com.example.beyza.calendar_android.feature;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PopupActivity extends AppCompatActivity {

    private EditText et_title,et_content,et_loc;
    private String title,content,location;
    private Button add,cancel;
    private Button remind_me,repeat,time;
    private String currentDate;
    private String currentSelectedDate;
    private String currentSelectedReminderIndex;
    private String currentSelectedRepeaterIndex;
    private String start_t, finish_t;
    private TimePicker start_Time,finish_Time;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        MainActivity dateGetter = new MainActivity();

        setContentView(R.layout.popup_screen);

        et_title = (EditText)findViewById(R.id.event_title);
        et_content = (EditText)findViewById(R.id.event_content);
        et_loc = (EditText)findViewById(R.id.event_loc);

        currentDate = dateGetter.getDate();  //getting the date from calendarView

        // reminder set edilmesi ;
        remind_me = (Button) findViewById(R.id.remind_btn);
        remind_me.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {


                    startActivity(new Intent(PopupActivity.this, Popup_Reminder.class));
                   // Toast.makeText(PopupActivity.this, remind_me.getText(), Toast.LENGTH_SHORT).show();

            }

        });



        // repeat ;

        repeat = (Button) findViewById(R.id.repeat_btn);
        repeat.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {


                startActivity(new Intent(PopupActivity.this, Popup_Repeat.class));

                // Toast.makeText(PopupActivity.this, repeat.getText(), Toast.LENGTH_SHORT).show();

            }

        });




        start_Time = (TimePicker) findViewById(R.id.startTime);
        start_Time.setIs24HourView(true);

        finish_Time = (TimePicker) findViewById(R.id.endTime);
        finish_Time.setIs24HourView(true);




        //cancel butonuna basılınca popup ekrandan çıkıyor;

        cancel = (Button) findViewById(R.id.cancel_btn);
        cancel.setOnClickListener(new View.OnClickListener(){


           @Override
           public void onClick(View view) {

               finish();

           }

        }
        );



        //add butonuna basılınca event database'e eklenir;


        add = (Button)findViewById(R.id.add_btn);
        add.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                sendPost();
                Toast.makeText(PopupActivity.this, "Event Created!", Toast.LENGTH_SHORT).show();
                finish();
            }

        });

    }//oncreate

    //database'e alınan verileri gönderir
    public void sendPost() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {


                    title = et_title.getText().toString();
                    content = et_content.getText().toString();
                    location = et_loc.getText().toString();
                    currentSelectedReminderIndex = Popup_Reminder.reminderIndex;    //gets selected reminder from radio boxes
                    currentSelectedRepeaterIndex = Popup_Repeat.repeaterIndex;      //gets selected repeat interval from radio boxes
                    currentSelectedDate = currentDate;  //current date

                    // event'in başlangıç ve bitiş saatleri alınıyor;

                    int hour1 = start_Time.getCurrentHour();
                    int min1 = start_Time.getCurrentMinute();
                    int hour2 = finish_Time.getCurrentHour();
                    int min2 = finish_Time.getCurrentMinute();
                    start_t = hour1+":"+min1;
                    finish_t = hour2+":"+min2;

                    if(currentSelectedRepeaterIndex.equals("Every day")) {
                        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(currentSelectedDate);

                        for (int i = 0; i <10;i++) {


                            URL url = new URL("https://immense-coast-39524.herokuapp.com/calendars");
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("POST");
                            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                            conn.setRequestProperty("Accept","application/json");
                            conn.setDoOutput(true);
                            conn.setDoInput(true);

                            JSONObject jsonParam = new JSONObject();
                            jsonParam.put("title", title);
                            jsonParam.put("content", content);
                            jsonParam.put("location", location);
                            jsonParam.put("start_date", date1);
                            jsonParam.put("start_time", start_t);
                            jsonParam.put("end_date", date1);
                            jsonParam.put("end_time", finish_t);
                            jsonParam.put("repeat", currentSelectedRepeaterIndex);
                            jsonParam.put("reminder", currentSelectedReminderIndex);


                            Log.i("JSON", jsonParam.toString());
                            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                            //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                            os.writeBytes(jsonParam.toString());

                            date1 = addDay(date1, 1);

                            Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                            Log.i("MSG" , conn.getResponseMessage());

                            conn.disconnect();


                        }

                    }
                    else if(currentSelectedRepeaterIndex.equals("Every week")) {


                        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(currentSelectedDate);

                        for (int i = 0; i < 10; i++) {
                            URL url = new URL("https://immense-coast-39524.herokuapp.com/calendars");
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("POST");
                            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                            conn.setRequestProperty("Accept", "application/json");
                            conn.setDoOutput(true);
                            conn.setDoInput(true);

                            JSONObject jsonParam = new JSONObject();
                            jsonParam.put("title", title);
                            jsonParam.put("content", content);
                            jsonParam.put("location", location);
                            jsonParam.put("start_date", date1);
                            jsonParam.put("start_time", start_t);
                            jsonParam.put("end_date", date1);
                            jsonParam.put("end_time", finish_t);
                            jsonParam.put("repeat", currentSelectedRepeaterIndex);
                            jsonParam.put("reminder", currentSelectedReminderIndex);


                            Log.i("JSON", jsonParam.toString());
                            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                            //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                            os.writeBytes(jsonParam.toString());

                            date1 = addDay(date1, 7);

                            Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                            Log.i("MSG", conn.getResponseMessage());

                            conn.disconnect();


                        }
                    }
                    else if(currentSelectedRepeaterIndex.equals("Every 2 weeks")) {


                        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(currentSelectedDate);

                        for (int i = 0; i < 10; i++) {
                            URL url = new URL("https://immense-coast-39524.herokuapp.com/calendars");
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("POST");
                            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                            conn.setRequestProperty("Accept", "application/json");
                            conn.setDoOutput(true);
                            conn.setDoInput(true);

                            JSONObject jsonParam = new JSONObject();
                            jsonParam.put("title", title);
                            jsonParam.put("content", content);
                            jsonParam.put("location", location);
                            jsonParam.put("start_date", date1);
                            jsonParam.put("start_time", start_t);
                            jsonParam.put("end_date", date1);
                            jsonParam.put("end_time", finish_t);
                            jsonParam.put("repeat", currentSelectedRepeaterIndex);
                            jsonParam.put("reminder", currentSelectedReminderIndex);


                            Log.i("JSON", jsonParam.toString());
                            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                            //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                            os.writeBytes(jsonParam.toString());

                            date1 = addDay(date1, 14);

                            Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                            Log.i("MSG", conn.getResponseMessage());

                            conn.disconnect();


                        }
                    }
                    else if(currentSelectedRepeaterIndex.equals("Every month")) {


                        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(currentSelectedDate);

                        for (int i = 0; i < 10; i++) {
                            URL url = new URL("https://immense-coast-39524.herokuapp.com/calendars");
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("POST");
                            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                            conn.setRequestProperty("Accept", "application/json");
                            conn.setDoOutput(true);
                            conn.setDoInput(true);

                            JSONObject jsonParam = new JSONObject();
                            jsonParam.put("title", title);
                            jsonParam.put("content", content);
                            jsonParam.put("location", location);
                            jsonParam.put("start_date", date1);
                            jsonParam.put("start_time", start_t);
                            jsonParam.put("end_date", date1);
                            jsonParam.put("end_time", finish_t);
                            jsonParam.put("repeat", currentSelectedRepeaterIndex);
                            jsonParam.put("reminder", currentSelectedReminderIndex);


                            Log.i("JSON", jsonParam.toString());
                            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                            //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                            os.writeBytes(jsonParam.toString());

                            date1 = addMonth(date1, 1);

                            Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                            Log.i("MSG", conn.getResponseMessage());

                            conn.disconnect();


                        }
                    }
                    else if(currentSelectedRepeaterIndex.equals("Every year")) {
                        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(currentSelectedDate);

                        for (int i = 0; i < 10; i++) {


                            URL url = new URL("https://immense-coast-39524.herokuapp.com/calendars");
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("POST");
                            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                            conn.setRequestProperty("Accept", "application/json");
                            conn.setDoOutput(true);
                            conn.setDoInput(true);

                            JSONObject jsonParam = new JSONObject();
                            jsonParam.put("title", title);
                            jsonParam.put("content", content);
                            jsonParam.put("location", location);
                            jsonParam.put("start_date", date1);
                            jsonParam.put("start_time", start_t);
                            jsonParam.put("end_date", date1);
                            jsonParam.put("end_time", finish_t);
                            jsonParam.put("repeat", currentSelectedRepeaterIndex);
                            jsonParam.put("reminder", currentSelectedReminderIndex);


                            Log.i("JSON", jsonParam.toString());
                            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                            //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                            os.writeBytes(jsonParam.toString());

                            date1 = addYear(date1, 1);

                            Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                            Log.i("MSG", conn.getResponseMessage());

                            conn.disconnect();


                        }
                    }
                    else if(currentSelectedRepeaterIndex.equals("Never")){


                        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(currentSelectedDate);

                            URL url = new URL("https://immense-coast-39524.herokuapp.com/calendars");
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("POST");
                            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                            conn.setRequestProperty("Accept", "application/json");
                            conn.setDoOutput(true);
                            conn.setDoInput(true);

                            JSONObject jsonParam = new JSONObject();
                            jsonParam.put("title", title);
                            jsonParam.put("content", content);
                            jsonParam.put("location", location);
                            jsonParam.put("start_date", date1);
                            jsonParam.put("start_time", start_t);
                            jsonParam.put("end_date", date1);
                            jsonParam.put("end_time", finish_t);
                            jsonParam.put("repeat", currentSelectedRepeaterIndex);
                            jsonParam.put("reminder", currentSelectedReminderIndex);


                            Log.i("JSON", jsonParam.toString());
                            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                            //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                            os.writeBytes(jsonParam.toString());

                            Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                            Log.i("MSG", conn.getResponseMessage());

                            conn.disconnect();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }
    public static Date addDay(Date date, int i) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, i);
        return cal.getTime();

    }
    public static Date addMonth(Date date, int i) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, i);
        return cal.getTime();

    }
    public static Date addYear(Date date, int i) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, i);
        return cal.getTime();

    }
}
