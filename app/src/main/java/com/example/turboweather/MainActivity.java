package com.example.turboweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Здесь просиходит вся магия.
 * <p>
 * При открытии данного активити, пользователь увидит погоду по его текущему местоположению,
 * а именно состояние погоды, название города, температуру в Цельсиях.
 * <p>
 * Здесь находится кнопка перехода на страницу поиска погоды по городу.
 *<p>
 *{@link String APP_ID} - ID приложение по которому сайт openweathermap.org передает состояние погоды.
 * <p>
 *{@link String WEATHER_URL} - URL сайта openweathermap.org, который передает состояние погоды.
 *<p>
 *{@link String Location_Provider} - переменная, получающая местоположение пользователя.
 * <p>
 *{@link String NameofCity, weatherState, Temperature} - название города, состояние погоды и температура соответственно.
 *<p>
 *{@link String mweatherIcon} - иконка состояния погоды.
 * <p>
 *{@link String Background} - цвет фона.
 * @see cityFinder
 *
 */
public class MainActivity extends AppCompatActivity {
    final String APP_ID = "dab3af44de7d24ae7ff86549334e45bd";
    final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather";

    final long MIN_TIME = 5000;
    final float MIN_DISTANCE = 1000;
    final int REQUEST_CODE = 101;


    String Location_Provider = LocationManager.GPS_PROVIDER;

    TextView NameofCity, weatherState, Temperature, feelslike,Humidity , pressure;
    ImageView mweatherIcon;
    ImageView Background;

    RelativeLayout mCityFinder;


    LocationManager mLocationManager;
    LocationListener mLocationListner;
    /**
     * При создании активити, приложение показывает состояние погоды в городе.
     * <p>
     * Если пользователь только зашел в приложение, вызывается метод {@link String getWeatherForCurrentLocation()}
     * <p>
     * Если пользователь перешел на данное активити из активити {@link cityFinder}, вызывается метод getWeatherForNewCity("Введеный пользователем город")
     * @param savedInstanceState параметр для сохранения состояния, связанного с текущим экземпляром Activity, например текущей информации о навигации или выборе, так что, если Android уничтожает и воссоздает Activity, он может вернуться, как было раньше.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent mIntent=getIntent();
        String city= mIntent.getStringExtra("City");
        if(city!=null)
        {
            getWeatherForNewCity(city);
        }
        else
        {
            getWeatherForCurrentLocation();
        }

        weatherState = findViewById(R.id.weatherCondition);
        Temperature = findViewById(R.id.temperature);
        mweatherIcon = findViewById(R.id.weatherIcon);
        mCityFinder = findViewById(R.id.cityFinder);
        NameofCity = findViewById(R.id.cityName);
        Background = findViewById(R.id.background);
        feelslike = findViewById(R.id.feelslike);
        Humidity = findViewById(R.id.humidity);
        pressure = findViewById(R.id.pressure);

        mCityFinder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, cityFinder.class);
                startActivity(intent);
            }
        });
    }

    /**
     *
     * Метод для получения погоды в городе, введенным пользователем, собирающий и
     * отправляющий объект {@link Object params} в метод {@link Void letsdoSomeNetworking}
     * <p>
     * {@link String APP_ID} - ID приложение по которому сайт openweathermap.org передает состояние погоды.
     * <p>
     * @param  city  название города, введенное пользователем
     */
    public void getWeatherForNewCity(String city)
    {
        RequestParams params = new RequestParams();
        params.put("q", city);
        params.put("appid", APP_ID);
        letsdoSomeNetworking(params);
    }

    /**
     * Метод для получения погоды в городе по умолчанию(текущий город пользователя)
     * <p>
     * {@link String LocationListener} - Для получения данных о текущей геопозиции и ее изменении
     */
    private void getWeatherForCurrentLocation() {

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLocationListner = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                String Latitude = String.valueOf(location.getLatitude());
                String Longitude = String.valueOf(location.getLongitude());

                RequestParams params = new RequestParams();
                params.put("lat" ,Latitude);
                params.put("lon",Longitude);
                params.put("appid",APP_ID);
                letsdoSomeNetworking(params);

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                //not able to get location
            }
        };


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
            return;
        }
        mLocationManager.requestLocationUpdates(Location_Provider, MIN_TIME, MIN_DISTANCE, mLocationListner);
    }

    /**
     * Метод для получения информации о разрещении отслеживания геолокации пользователя
     * <p>
     * @param requestCode Помогает идентифицировать, с какого Intent получен код и различать его от остальных
     * @param permissions Позволяет получить список прав приложения
     * @param grantResults Полученный результат от пользователя
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if(requestCode==REQUEST_CODE)
        {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(MainActivity.this,"Местоположение получено",Toast.LENGTH_SHORT).show();
                getWeatherForCurrentLocation();
            }
            else
            {
                //user denied the permission
            }
        }


    }
    /*
    _______Cmon do some Networking________
    ⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠿⠟⠛⠻⠿⢿⣿⣿⣿⣿⣿⣿
    ⣿⣿⣿⣿⣿⣿⣿⣿⠟⠋⢀⣠⡤⠤⢭⣛⡉⢀⡤⠴⠶⠶⡶⢆⣉⠙⠻⢿⣿⣿
    ⣿⣿⣿⣿⣿⡿⠋⠀⠠⠞⠉⢀⣀⡤⣤⣤⣭⠭⣤⡤⣽⣿⢿⠿⠛⠛⠳⣌⢻⣿
    ⣿⣿⣿⣿⢿⡅⠀⠀⠀⠀⣶⠿⠛⠉⣡⠤⠭⠭⠷⡔⠚⠛⠉⠉⠉⢛⢛⢏⣿⣿
    ⣿⣿⣿⠃⠘⠇⠀⠀⠀⠀⠈⣿⠉⠁⢒⣒⣶⠶⡦⠒⠒⠒⠚⠓⠛⠉⡿⣿⣿⣿
    ⣿⣿⡏⠀⠀⠀⠀⠀⠀⠀⠀⣟⠀⢉⣉⣠⣤⣤⢶⣒⣠⣭⣭⣭⣭⣉⡗⣼⣿⣿
    ⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⡟⠀⠛⠿⠭⠶⠖⠛⠉⠀⠈⠁⠈⠉⠛⢣⣿⣿⣿
    ⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⣿⣿
    ⣿⡏⣶⣦⣄⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣀⣀⣀⣤⣤⣶⣿⣿⣜⠿⣿⣿
    ⣿⢣⣿⣿⣿⣿⣿⣿⣿⣶⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⡝⣿
     */
    private void letsdoSomeNetworking(RequestParams params)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(WEATHER_URL,params,new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Toast.makeText(MainActivity.this,"Успешно",Toast.LENGTH_SHORT).show();

                weatherData weatherD=weatherData.fromJson(response);
                updateUI(weatherD);


                // super.onSuccess(statusCode, headers, response);
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(MainActivity.this,"Город введен неправильно",Toast.LENGTH_SHORT).show();
            }
        });



    }

    public  void updateUI(weatherData weather){


        Temperature.setText(weather.getmTemperature());
        NameofCity.setText(weather.getMcity());
        Humidity.setText("Влажность: " +weather.getmHumidity() +"%");
        weatherState.setText(weather.getmWeatherType());
        feelslike.setText("Ощущается как: " + weather.getmFeelsLike());
        int resourceID=getResources().getIdentifier(weather.getMicon(),"drawable",getPackageName());
        int backgroundID=getResources().getIdentifier(weather.getMback(),"drawable",getPackageName());
        mweatherIcon.setImageResource(resourceID);
        Background.setImageResource(backgroundID);
        pressure.setText("Давление: "+weather.getmPressure() +" мм рт. ст.");

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mLocationManager!=null)
        {
            mLocationManager.removeUpdates(mLocationListner);
        }
    }
}
