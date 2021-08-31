package com.timespace.notesapp.ui.space;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.timespace.notesapp.R;
import com.timespace.notesapp.common.Common;


public class MySpacesActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tv_sports, tv_all,tv_events,tv_music,tv_holiday;
    TextView tv_panchanga,tv_local_events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_spaces);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.white));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        tv_panchanga = findViewById(R.id.tv_panchanga);
        tv_local_events = findViewById(R.id.tv_local_events);

        tv_sports=findViewById(R.id.tv_sports);
        tv_all=findViewById(R.id.tv_all);
        tv_events=findViewById(R.id.tv_events);
        tv_music=findViewById(R.id.tv_music);
        tv_holiday=findViewById(R.id.tv_holiday);
        tv_sports.setOnClickListener(this);
        tv_all.setOnClickListener(this);
        tv_events.setOnClickListener(this);
        tv_holiday.setOnClickListener(this);
        tv_music.setOnClickListener(this);


        tv_panchanga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MySpacesActivity.this, EventDetailsActivity.class);
//                startActivity(intent);
//                Intent intent = new Intent(MySpacesActivity.this, AllEventsListActivity.class);
//                Common.spaceType = "1";
//                startActivity(intent);
            }
        });

        tv_local_events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MySpacesActivity.this, AllEventsListActivity.class);
//                Common.spaceType = "2";
//                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_all:
                if(Common.btn_all==1){
                    Common.btn_all = 0;
                    tv_all.setBackgroundResource(R.drawable.school_button_white);
                    tv_all.setTextColor(getResources().getColor(R.color.black));
                    //
                    Common.btn_sports = 1;
                    tv_sports.setBackgroundResource(R.drawable.button_selector);
                    tv_sports.setTextColor(getResources().getColor(R.color.white));
                    //
                    Common.btn_event = 1;
                    tv_events.setBackgroundResource(R.drawable.button_selector);
                    tv_events.setTextColor(getResources().getColor(R.color.white));
                    //
                    Common.btn_holiday = 1;
                    tv_holiday.setBackgroundResource(R.drawable.button_selector);
                    tv_holiday.setTextColor(getResources().getColor(R.color.white));
                    //
                    Common.btn_music = 1;
                    tv_music.setBackgroundResource(R.drawable.button_selector);
                    tv_music.setTextColor(getResources().getColor(R.color.white));
                    break;
                }
                else {
                    Common.btn_all = 1;
                    tv_all.setBackgroundResource(R.drawable.button_selector);
                    tv_all.setTextColor(getResources().getColor(R.color.white));
                    //
                    Common.btn_sports = 0;
                    tv_sports.setBackgroundResource(R.drawable.school_button_white);
                    tv_sports.setTextColor(getResources().getColor(R.color.black));
                    //
                    Common.btn_event = 0;
                    tv_events.setBackgroundResource(R.drawable.school_button_white);
                    tv_events.setTextColor(getResources().getColor(R.color.black));
                    //
                    Common.btn_holiday = 0;
                    tv_holiday.setBackgroundResource(R.drawable.school_button_white);
                    tv_holiday.setTextColor(getResources().getColor(R.color.black));
                    //
                    Common.btn_music = 0;
                    tv_music.setBackgroundResource(R.drawable.school_button_white);
                    tv_music .setTextColor(getResources().getColor(R.color.black));
                    break;
                }

            case R.id.tv_sports:
                if(Common.btn_sports==1){
                    Common.btn_sports = 0;
                    tv_sports.setBackgroundResource(R.drawable.school_button_white);
                    tv_sports.setTextColor(getResources().getColor(R.color.black));
                    //
                    Common.btn_all = 1;
                    tv_all.setBackgroundResource(R.drawable.button_selector);
                    tv_all.setTextColor(getResources().getColor(R.color.white));

                }
                else {
                    Common.btn_sports = 1;
                    tv_sports.setBackgroundResource(R.drawable.button_selector);
                    tv_sports.setTextColor(getResources().getColor(R.color.white));

                    Common.btn_all = 0;
                    tv_all.setBackgroundResource(R.drawable.school_button_white);
                    tv_all.setTextColor(getResources().getColor(R.color.black));
                    break;
                }
            case R.id.tv_events:
                if(Common.btn_event==1){
                    Common.btn_event = 0;
                    tv_events.setBackgroundResource(R.drawable.school_button_white);
                    tv_events.setTextColor(getResources().getColor(R.color.black));
                    //
                    Common.btn_all = 1;
                    tv_all.setBackgroundResource(R.drawable.button_selector);
                    tv_all.setTextColor(getResources().getColor(R.color.white));
                }
                else {
                    Common.btn_event = 1;
                    tv_events.setBackgroundResource(R.drawable.button_selector);
                    tv_events.setTextColor(getResources().getColor(R.color.white));
                    Common.btn_all = 0;
                    tv_all.setBackgroundResource(R.drawable.school_button_white);
                    tv_all.setTextColor(getResources().getColor(R.color.black));
                    break;
                }
            case R.id.tv_music:
                if(Common.btn_music==1){
                    Common.btn_music = 0;
                    tv_music.setBackgroundResource(R.drawable.school_button_white);
                    tv_music.setTextColor(getResources().getColor(R.color.black));
                    //
                    Common.btn_all = 1;
                    tv_all.setBackgroundResource(R.drawable.button_selector);
                    tv_all.setTextColor(getResources().getColor(R.color.white));

                }
                else {
                    Common.btn_music = 1;
                    tv_music.setBackgroundResource(R.drawable.button_selector);
                    tv_music.setTextColor(getResources().getColor(R.color.white));

                    Common.btn_all = 0;
                    tv_all.setBackgroundResource(R.drawable.school_button_white);
                    tv_all.setTextColor(getResources().getColor(R.color.black));
                    break;
                }
            case R.id.tv_holiday:
                if(Common.btn_holiday==1){
                    Common.btn_holiday = 0;
                    tv_holiday.setBackgroundResource(R.drawable.school_button_white);
                    tv_holiday.setTextColor(getResources().getColor(R.color.black));
                    //
                    Common.btn_all = 1;
                    tv_all.setBackgroundResource(R.drawable.button_selector);
                    tv_all.setTextColor(getResources().getColor(R.color.white));
                }
                else {
                    Common.btn_holiday = 1;
                    tv_holiday.setBackgroundResource(R.drawable.button_selector);
                    tv_holiday.setTextColor(getResources().getColor(R.color.white));
                    Common.btn_all = 0;
                    tv_all.setBackgroundResource(R.drawable.school_button_white);
                    tv_all.setTextColor(getResources().getColor(R.color.black));
                    break;
                }
        }

    }
}