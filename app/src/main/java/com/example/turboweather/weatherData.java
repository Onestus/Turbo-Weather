package com.example.turboweather;

import org.json.JSONException;
import org.json.JSONObject;
/**
 * Класс получающий информацию о состоянию погоды и изменяющий поля на странице {@link String MainActivity}
 * @see MainActivity
 */
public class weatherData {

    private String mTemperature, micon, mcity, mWeatherType, mBack, mFeelsLike, mHumidity,mPressure;
    private int mCondition;

    /**
     * Метод получающий JSON объект с информацией о состоянии погоды в городе.
     * <p>
     * Внутри JSON объекта выбираются нужные параметры и их значения передаются параметрам объекта {@link String weatherD}
     * @param jsonObject JSON объект с информацией о состоянии погоды в городе
     * @return {@link String weatherD} - объект с информацией о состоянии погоды в городе
     */
    public static weatherData fromJson(JSONObject jsonObject) {

        try {
            weatherData weatherD = new weatherData();
            weatherD.mcity = jsonObject.getString("name");
            weatherD.mCondition = jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id");
            weatherD.mWeatherType = jsonObject.getJSONArray("weather").getJSONObject(0).getString("main");
            weatherD.micon = updateWeatherIcon(weatherD.mCondition);
            weatherD.mBack = updateBackground(weatherD.mCondition);
            weatherD.mWeatherType=transtaleType(weatherD.mWeatherType);
            double tempResult = jsonObject.getJSONObject("main").getDouble("temp") - 273.15;
            int roundedValue = (int) Math.rint(tempResult);
            weatherD.mTemperature = Integer.toString(roundedValue);
            double tempResultFL = jsonObject.getJSONObject("main").getDouble("feels_like") - 273.15;
            int roundedValueFL = (int) Math.rint(tempResultFL);
            weatherD.mFeelsLike = Integer.toString(roundedValueFL);
            int tempResultH = jsonObject.getJSONObject("main").getInt("humidity");
            int roundedValueH = (int) Math.rint(tempResultH);
            weatherD.mHumidity = Integer.toString(roundedValueH);
            int tempResultP = jsonObject.getJSONObject("main").getInt("pressure");
            int roundedValueP = (int) Math.rint(tempResultP);
            int MMRtStolb = (int) ((roundedValueP*7.5006)/10);
            weatherD.mPressure = Integer.toString(MMRtStolb);


            return weatherD;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }
    /*
    ———————————No switches?———————————
    ⠀⣞⢽⢪⢣⢣⢣⢫⡺⡵⣝⡮⣗⢷⢽⢽⢽⣮⡷⡽⣜⣜⢮⢺⣜⢷⢽⢝⡽⣝
    ⠸⡸⠜⠕⠕⠁⢁⢇⢏⢽⢺⣪⡳⡝⣎⣏⢯⢞⡿⣟⣷⣳⢯⡷⣽⢽⢯⣳⣫⠇
    ⠀⠀⢀⢀⢄⢬⢪⡪⡎⣆⡈⠚⠜⠕⠇⠗⠝⢕⢯⢫⣞⣯⣿⣻⡽⣏⢗⣗⠏⠀
    ⠀⠪⡪⡪⣪⢪⢺⢸⢢⢓⢆⢤⢀⠀⠀⠀⠀⠈⢊⢞⡾⣿⡯⣏⢮⠷⠁⠀⠀
    ⠀⠀⠀⠈⠊⠆⡃⠕⢕⢇⢇⢇⢇⢇⢏⢎⢎⢆⢄⠀⢑⣽⣿⢝⠲⠉⠀⠀⠀⠀
    ⠀⠀⠀⠀⠀⡿⠂⠠⠀⡇⢇⠕⢈⣀⠀⠁⠡⠣⡣⡫⣂⣿⠯⢪⠰⠂⠀⠀⠀⠀
    ⠀⠀⠀⠀⡦⡙⡂⢀⢤⢣⠣⡈⣾⡃⠠⠄⠀⡄⢱⣌⣶⢏⢊⠂⠀⠀⠀⠀⠀⠀
    ⠀⠀⠀⠀⢝⡲⣜⡮⡏⢎⢌⢂⠙⠢⠐⢀⢘⢵⣽⣿⡿⠁⠁⠀⠀⠀⠀⠀⠀⠀
    ⠀⠀⠀⠀⠨⣺⡺⡕⡕⡱⡑⡆⡕⡅⡕⡜⡼⢽⡻⠏⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
    ⠀⠀⠀⠀⣼⣳⣫⣾⣵⣗⡵⡱⡡⢣⢑⢕⢜⢕⡝⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
    ⠀⠀⠀⣴⣿⣾⣿⣿⣿⡿⡽⡑⢌⠪⡢⡣⣣⡟⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
    ⠀⠀⠀⡟⡾⣿⢿⢿⢵⣽⣾⣼⣘⢸⢸⣞⡟⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
    ⠀⠀⠀⠀⠁⠇⠡⠩⡫⢿⣝⡻⡮⣒⢽⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
    —————————————————————————————
     */

    /**
     * Метод переводящий тип погоды с английского на русский язык
     * @param type название типа погоды на английском языке
     * @return название типа погоды на русском языке
     */
    public static String  transtaleType(String type){
        if(type.equals("Thunderstorm"))
        {
            return "Гроза";
        }
        if(type.equals("Drizzle"))
        {
            return "Моросящий дождь";
        }
        if(type.equals("Rain"))
        {
            return "Дождь";
        }
        if(type.equals("Snow"))
        {
            return "Снег";
        }
        if(type.equals("Clear"))
        {
            return "Ясно";
        }

        if(type.equals("Clouds"))
        {
            return "Облачно";
        }
        if(type.equals("Mist"))
        {
            return "Туман";
        }
        if(type.equals("Fog"))
        {
            return "Туман";
        }

        return "Sorry :(";
    }




    /**
     * Метод изменяющий иконку на странице MainActivity, в зависимости от состояния погоды
     * @param condition состояние погоды в числовом значении
     * @return название состояния погоды по которому подбирается иконка
     */
    public static String updateWeatherIcon(int condition)
    {
        if(condition>=0 && condition<=300)
        {
            return "thunderstrom1";
        }
        else if(condition>=300 && condition<500)
        {
            return "lightrain";
        }
        else if(condition>=500 && condition<600)
        {
            return "shower";
        }
        else  if(condition>=600 && condition<=700)
        {
            return "snow1";
        }
        else if(condition>=701 && condition<=771)
        {
            return "fog";
        }

        else if(condition>=772 && condition<800)
        {
            return "overcast";
        }
        else if(condition==800)
        {
            return "sunny";
        }
        else if(condition>=801 && condition<=804)
        {
            return "cloudy";
        }
        else  if(condition>=900 && condition<=902)
        {
            return "thunderstrom";
        }
        if(condition==903)
        {
            return "snow1";
        }
        if(condition==904)
        {
            return "sunny";
        }
        if(condition>=905 && condition<=1000)
        {
            return "thunderstrom";
        }

        return "Sorry :(";
    }
    /*
    ———————————No switches?———————————
    ⠀⣞⢽⢪⢣⢣⢣⢫⡺⡵⣝⡮⣗⢷⢽⢽⢽⣮⡷⡽⣜⣜⢮⢺⣜⢷⢽⢝⡽⣝
    ⠸⡸⠜⠕⠕⠁⢁⢇⢏⢽⢺⣪⡳⡝⣎⣏⢯⢞⡿⣟⣷⣳⢯⡷⣽⢽⢯⣳⣫⠇
    ⠀⠀⢀⢀⢄⢬⢪⡪⡎⣆⡈⠚⠜⠕⠇⠗⠝⢕⢯⢫⣞⣯⣿⣻⡽⣏⢗⣗⠏⠀
    ⠀⠪⡪⡪⣪⢪⢺⢸⢢⢓⢆⢤⢀⠀⠀⠀⠀⠈⢊⢞⡾⣿⡯⣏⢮⠷⠁⠀⠀
    ⠀⠀⠀⠈⠊⠆⡃⠕⢕⢇⢇⢇⢇⢇⢏⢎⢎⢆⢄⠀⢑⣽⣿⢝⠲⠉⠀⠀⠀⠀
    ⠀⠀⠀⠀⠀⡿⠂⠠⠀⡇⢇⠕⢈⣀⠀⠁⠡⠣⡣⡫⣂⣿⠯⢪⠰⠂⠀⠀⠀⠀
    ⠀⠀⠀⠀⡦⡙⡂⢀⢤⢣⠣⡈⣾⡃⠠⠄⠀⡄⢱⣌⣶⢏⢊⠂⠀⠀⠀⠀⠀⠀
    ⠀⠀⠀⠀⢝⡲⣜⡮⡏⢎⢌⢂⠙⠢⠐⢀⢘⢵⣽⣿⡿⠁⠁⠀⠀⠀⠀⠀⠀⠀
    ⠀⠀⠀⠀⠨⣺⡺⡕⡕⡱⡑⡆⡕⡅⡕⡜⡼⢽⡻⠏⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
    ⠀⠀⠀⠀⣼⣳⣫⣾⣵⣗⡵⡱⡡⢣⢑⢕⢜⢕⡝⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
    ⠀⠀⠀⣴⣿⣾⣿⣿⣿⡿⡽⡑⢌⠪⡢⡣⣣⡟⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
    ⠀⠀⠀⡟⡾⣿⢿⢿⢵⣽⣾⣼⣘⢸⢸⣞⡟⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
    ⠀⠀⠀⠀⠁⠇⠡⠩⡫⢿⣝⡻⡮⣒⢽⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
    —————————————————————————————
     */

    /**
     * Метод изменяющий цвет фона страницы MainActivity, в зависимости от состояния погоды
     * @param condition состояние погоды в числовом значении
     * @return название состояния погоды по которому подбирается цвет фона
     *
     */
    public static String updateBackground(int condition)
    {
        if(condition>=0 && condition<=300)//thunder
        {
            return "background_dark";
        }
        else if(condition>=300 && condition<500)//lightrain
        {
            return "background_darkgrey";
        }
        else if(condition>=500 && condition<600)//rain
        {
            return "background_darkdarkgrey";
        }
        else  if(condition>=600 && condition<=700)//snow
        {
            return "background_darklight";
        }
        else if(condition>=701 && condition<=771) //fog
        {
            return "background_grey";
        }

        else if(condition>=772 && condition<800)//overcast
        {
            return "background_darkgrey";
        }
        else if(condition==800)//sunny
        {
            return "background_light";
        }
        else if(condition>=801 && condition<=804)//cloudy
        {
            return "background_darkgrey";
        }
        else  if(condition>=900 && condition<=902)//thunder
        {
            return "background_darkdarkgrey";
        }
        if(condition==903) //snow
        {
            return "background_darklight";
        }
        if(condition==904)//sunny
        {
            return "background_light";
        }
        if(condition>=905 && condition<=1000)//thunder
        {
            return "background_darkdarkgrey";
        }

        return "Sorry :(";
    }




    /**
     * Метод возвращающий значение параметра температуры в градусах Цельсия
     * @return значение параметра температуры в градусах Цельсия
     */
    public String getmTemperature() {
        return mTemperature+"°C";
    }

    /**
     * Метод возвращающий значение параметра названия рисунка погоды
     * @return значение параметра названия рисунка погоды
     */
    public String getMicon() {
        return micon;
    }

    /**
     * Метод возвращающий значение параметра названия города
     * @return значение параметра названия города
     */
    public String getMcity() {
        return mcity;
    }

    /**
     * Метод возвращающий значение параметра названия цвета фона
     * @return значение параметра названия цвета фона
     */
    public String getMback() {
        return mBack;
    }

    /**
     * Метод возвращающий значение параметра названия типа погоды
     * @return значение параметра названия типа погоды
     */
    public String getmWeatherType() {
        return mWeatherType;
    }

    /**
     * Метод возвращающий значение параметра температуры по ощущениям в градусах Цельсия
     * @return значение параметра температуры по ощущениям в градусах Цельсия
     */
    public String getmFeelsLike() {
        return mFeelsLike+"°C";
    }

    /**
     * Метод возвращающий значение параметра влажности в числовом значении
     * @return значение параметра влажности в числовом значении
     */
    public String getmHumidity() {
        return mHumidity;
    }

    /**
     *Метод возвращающий значение параметра давления в числовом значении
     * @return значение параметра давления в числовом значении
     */
    public String getmPressure() {
        return mPressure;
    }
}
