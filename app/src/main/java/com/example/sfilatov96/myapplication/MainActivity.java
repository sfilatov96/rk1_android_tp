package com.example.sfilatov96.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ru.mail.weather.lib.*;


public class MainActivity extends AppCompatActivity {
    private TextView status;
    final String ACTION = MyService.ACTION;
    private final String TAG = "BroadCast";
    private BroadcastReceiver broadcastReceiver;
    private WeatherStorage weatherStorage;
    Button fone;
    Button no_fone;
    Button city_button;

    City city = null;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        weatherStorage = WeatherStorage.getInstance(MainActivity.this);
        setContentView(R.layout.activity_second_activty);
        if (weatherStorage.getCurrentCity() != null) {

            weatherStorage = WeatherStorage.getInstance(MainActivity.this);
            fone = (Button) findViewById(R.id.btn_fone);
            no_fone = (Button) findViewById(R.id.btn_not_fone);
            city_button = (Button) findViewById(R.id.btn_city);
            status = (TextView) findViewById(R.id.weather_string);
            city_button.setText(weatherStorage.getCurrentCity().name());
            city_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    startActivity(intent);
                }
            });
            fone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, MyService.class);
                    WeatherUtils.getInstance().schedule(MainActivity.this, intent);
                    Log.d(TAG, "START");
                }
            });
            no_fone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, MyService.class);
                    WeatherUtils.getInstance().unschedule(MainActivity.this, intent);
                    Log.d(TAG, "STOP");
                }
            });
        } else {
            Intent intent = new Intent(this, SecondActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCurrWeather();
        IntentFilter filter = new IntentFilter(ACTION);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG,"onReceive");
                getCurrWeather();
            }
        };
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
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