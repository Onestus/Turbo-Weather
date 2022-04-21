package com.example.turboweather;

import androidx.appcompat.app.AppCompatActivity;

import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
 <p>
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

    TextView NameofCity, weatherState, Temperature;
    ImageView mweatherIcon;
    ImageView Background;

    RelativeLayout mCityFinder;


    LocationManager mLocationManager;
    LocationListener mLocationListner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}