package com.example.sfilatov96.myapplication;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import ru.mail.weather.lib.City;
import ru.mail.weather.lib.Weather;
import ru.mail.weather.lib.WeatherStorage;
import ru.mail.weather.lib.WeatherUtils;

public class MyService extends IntentService {
    public final static String ACTION = "com.example.sfilatov96.myapplication.action.weather";
    public MyService() {
        super("MyService");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        WeatherStorage weatherStorage = WeatherStorage.getInstance(this);

        try {
            City city = weatherStorage.getCurrentCity();
            Weather weather = WeatherUtils.getInstance().loadWeather(city);
            weatherStorage.saveWeather(city, weather);
            Intent newIntent = new Intent(ACTION);
            sendBroadcast(newIntent);
        } catch (IOException e) {
            Log.d("ERROR","error");
        }


    }
}
