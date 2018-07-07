package com.example.beyza.calendar_android.feature;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.Toast;

public class PopupActivity extends AppCompatActivity {

    private EditText et_title,et_content,et_loc;
    private String title,content,location;
    private Button add,cancel;
    private Button remind_me,repeat;
    private String currentSelectedDate;
    private String currentSelectedReminderIndex;
    private String currentSelectedRepeaterIndex;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        MainActivity dateGetter = new MainActivity();

        setContentView(R.layout.popup_screen);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.8), (int)(height*.6));

        et_title = (EditText)findViewById(R.id.event_title);
        et_content = (EditText)findViewById(R.id.event_content);
        et_loc = (EditText)findViewById(R.id.event_loc);

        currentSelectedDate = dateGetter.getDate();  //getting the date from calendarView

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
        //database'e eklenecek bilgiler buradan alınır;
        // reminderlar ve repeatler henuz alınmadı alınacak.

        add = (Button)findViewById(R.id.add_btn);
        add.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {

                title = et_title.getText().toString();
                content = et_content.getText().toString();
                location = et_loc.getText().toString();
                currentSelectedReminderIndex = Popup_Reminder.reminderIndex;    //gets selected reminder from radio boxes
                currentSelectedRepeaterIndex = Popup_Repeat.repeaterIndex;      //gets selected repeat interval from radio boxes




            }

        });

    }

}
