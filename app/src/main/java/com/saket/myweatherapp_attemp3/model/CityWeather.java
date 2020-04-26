package com.saket.myweatherapp_attemp3.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sshriwas on 2020-04-18
 */
public class CityWeather {

    @SerializedName("weather_state_name")
    private String weather_name;
    private String max_temp;
    private String min_temp;
    @SerializedName("the_temp")
    private String current_temp;
    private String wind_speed;
    private String humidity;

    public String getWeather_name() {
        return weather_name;
    }

    public void setWeather_name(String weather_name) {
        this.weather_name = weather_name;
    }

    public String getMax_temp() {
        return max_temp;
    }

    public void setMax_temp(String max_temp) {
        this.max_temp = max_temp;
    }

    public String getMin_temp() {
        return min_temp;
    }

    public void setMin_temp(String min_temp) {
        this.min_temp = min_temp;
    }

    public String getCurrent_temp() {
        return current_temp;
    }

    public void setCurrent_temp(String current_temp) {
        this.current_temp = current_temp;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(String wind_speed) {
        this.wind_speed = wind_speed;
    }
}
