package com.zephyr.weatherforecast;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Zephyr on 1/3/2015.
 */
public class WeatherClient {

    HttpURLConnection urlConnection = null;
    BufferedReader reader = null;
    String JsonStr = null;
    private static String API_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?mode=json&cnt=7&q=";

    public String getForecastJsonStr(String LOG_TAG, Context context, String location, String unit, String mode, int numDays){

         try {

            URL url= new URL(API_URL+location);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            Log.d(LOG_TAG, "inputStream " + inputStream);

            if (inputStream == null) {
                JsonStr = null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                JsonStr = null;
            }
            JsonStr = buffer.toString();

            SharedPreferences settings = context.getSharedPreferences("weatherData",0);

            SharedPreferences.Editor  editor = settings.edit();

            editor.putString("JsonStr", JsonStr);

            editor.commit();
            Log.d(LOG_TAG, "forecastJsonStr "+JsonStr);

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            SharedPreferences preferences = context.getSharedPreferences("weatherData",0);
            JsonStr = preferences.getString("JsonStr", "");
            //JsonStr = null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                    return  null;
                }
            }
            return JsonStr;
        }
    }
}
