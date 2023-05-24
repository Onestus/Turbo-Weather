package com.example.turboweather;

import static org.junit.Assert.*;

import org.junit.Test;

public class weatherDataTest {

    @Test
    public void updateWeatherIcon() {
        weatherData weatherdata = new weatherData();
        assertEquals("lightrain", weatherdata.updateWeatherIcon(122414));
        assertEquals("shower", weatherdata.updateWeatherIcon(235235));
        assertEquals("fog", weatherdata.updateWeatherIcon(-293529));
    }

    @Test
    public void updateBackground() {
        weatherData weatherdata = new weatherData();
        assertEquals("background_light", weatherdata.updateBackground(3747343));
        assertEquals("Sorry :(", weatherdata.updateBackground(-5469646));
        assertEquals("background_dark", weatherdata.updateBackground(333333333));
    }
}