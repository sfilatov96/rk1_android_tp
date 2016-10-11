package com.example.sfilatov96.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import ru.mail.weather.lib.*;


public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        City curr_city = WeatherStorage.getInstance(SecondActivity.this).getCurrentCity();
        if(curr_city == null ) {
            setContentView(R.layout.activity_main);
            Button button1 = (Button) findViewById(R.id.btn_first);
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SecondActivity.this, SecondActivty.class);
                    WeatherStorage.getInstance(SecondActivity.this).setCurrentCity(City.VICE_CITY);
                    startActivity(intent);
                }
            });
            Button button2 = (Button) findViewById(R.id.btn_second);
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SecondActivity.this, SecondActivty.class);
                    WeatherStorage.getInstance(SecondActivity.this).setCurrentCity(City.RACCOON_CITY);
                    startActivity(intent);
                }
            });
            Button button3 = (Button) findViewById(R.id.btn_third);
            button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SecondActivity.this, SecondActivty.class);
                    WeatherStorage.getInstance(SecondActivity.this).setCurrentCity(City.SPRINGFIELD);
                    startActivity(intent);
                }
            });
            Button button4 = (Button) findViewById(R.id.btn_fourth);
            button4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SecondActivity.this, SecondActivty.class);
                    WeatherStorage.getInstance(SecondActivity.this).setCurrentCity(City.SILENT_HILL);
                    startActivity(intent);
                }
            });
            Button button5 = (Button) findViewById(R.id.btn_fifth);
            button5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SecondActivity.this, SecondActivty.class);
                    WeatherStorage.getInstance(SecondActivity.this).setCurrentCity(City.SOUTH_PARK);
                    startActivity(intent);
                }
            });
        } else {
            Intent intent = new Intent(SecondActivity.this, SecondActivty.class);
            startActivity(intent);
        }
    }
}
