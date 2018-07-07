package com.example.beyza.calendar_android.feature;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Popup_Repeat extends AppCompatActivity {


    private RadioGroup repeat;
    private RadioButton repeat_it;
    private Button ok,cancel;
    public static String repeaterIndex;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);


        setContentView(R.layout.repeat_screen);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .6));





        //repeat set edilmesi;

        repeat = (RadioGroup)findViewById(R.id.repeat_btn);
        repeat.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {

            }

        });



        //cancel butonuna basılınca popup ekrandan çıkıyor;


        cancel = (Button) findViewById(R.id.cancel_btn);
        cancel.setOnClickListener(new View.OnClickListener() {

          @Override
           public void onClick(View view) {


              finish();

              // repeat_it.setChecked(false);

           }

              }
        );


        //ok butonuna basılınca event database'e eklenir;

        ok = (Button) findViewById(R.id.ok_btn);
        ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                int selectedId = repeat.getCheckedRadioButtonId();
                repeat_it = (RadioButton) findViewById(selectedId);
                setRepeaterIndex(repeat_it.getText().toString());
                finish();

            }

        });

    }

    private void setRepeaterIndex(String a){
        repeaterIndex = a;
    }

    public String getRepeaterIndex(){
        return repeaterIndex;
    }

}
