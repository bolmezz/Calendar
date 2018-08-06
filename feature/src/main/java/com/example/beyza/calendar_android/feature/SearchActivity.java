package com.example.beyza.calendar_android.feature;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class SearchActivity extends AppCompatActivity {

    Button search ;
    EditText sword;
    public static String  search_word;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.search_screen);


        sword = (EditText) findViewById(R.id.word);



        search = (Button) findViewById(R.id.search_btn);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setSearch(sword.getText().toString());
                startActivity(new Intent(SearchActivity.this, EventDetail.class));
            }
        });



    }//oncreate

    public String getSearch()
    {

    return search_word;

    }

    public void setSearch(String s)
    {
        search_word = s;
    }


}


