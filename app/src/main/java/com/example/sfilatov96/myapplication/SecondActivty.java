package com.example.sfilatov96.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ru.mail.weather.lib.*;


public class SecondActivty extends AppCompatActivity {
    private TextView status;
    private final String MAIN_INTENT = "com.example.sfilatov96.myapplication.action.weather";
    private final String TAG = "BroadCast";
    private BroadcastReceiver broadcastReceiver;
    private WeatherStorage weatherStorage;
    Button fone;
    Button no_fone;
    Button city_button;

    City city = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_activty);
        weatherStorage = WeatherStorage.getInstance(SecondActivty.this);
        fone = (Button) findViewById(R.id.btn_fone);
        no_fone = (Button) findViewById(R.id.btn_not_fone);
        city_button = (Button) findViewById(R.id.btn_city);
        status = (TextView) findViewById(R.id.weather_string);
        city_button.setText(weatherStorage.getCurrentCity().name());
        city_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivty.this, MainActivity.class);
                startActivity(intent);
            }
        });
        fone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecondActivty.this, MyService.class);
                WeatherUtils.getInstance().schedule(SecondActivty.this, intent);
                Log.d(TAG,"START");
            }
        });
        no_fone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivty.this, MyService.class);
                WeatherUtils.getInstance().unschedule(SecondActivty.this, intent);
                Log.d(TAG, "STOP");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCurrWeather();
        IntentFilter filter = new IntentFilter(MAIN_INTENT);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG,"onReceive");
                getCurrWeather();
            }
        };
        registerReceiver(broadcastReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    private void getCurrWeather(){

        city = weatherStorage.getCurrentCity();
        Weather weather = weatherStorage.getLastSavedWeather(city);
        if (weather != null) {

            status.setText(weather.getTemperature() + "C " + weather.getDescription());
            no_fone.setClickable(Boolean.TRUE);
        } else {
            no_fone.setClickable(Boolean.FALSE);
            status.setText("Пока погоды нет, нажмите на  обновить");
        }
    }
}