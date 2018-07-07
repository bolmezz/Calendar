package com.example.beyza.calendar_android.feature;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Popup_Reminder extends AppCompatActivity{

    private RadioGroup reminder;
    private RadioButton remind_me;
    private Button ok,cancel;
    public static String reminderIndex;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);


        setContentView(R.layout.reminder_screen);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.8), (int)(height*.6));



        //reminder set edilmesi;

        reminder = (RadioGroup)findViewById(R.id.remind_btn);
        reminder.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {

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



        //ok butonuna basılınca ne surede bir tekrarlanacak set edilir;

        ok = (Button)findViewById(R.id.ok_btn);
        ok.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                int selectedId = reminder.getCheckedRadioButtonId();
                remind_me = (RadioButton) findViewById(selectedId);
                setReminderIndex(remind_me.getText().toString());
                finish();
            }

        });

    }

    private void setReminderIndex(String a){
        reminderIndex = a;
    }

    public String getReminderIndex(){
        return reminderIndex;
    }
}
