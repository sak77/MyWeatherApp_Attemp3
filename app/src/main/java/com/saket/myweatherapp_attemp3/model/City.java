package com.saket.myweatherapp_attemp3.model;

/**
 * Created by sshriwas on 2020-04-18
 */
public class City {
    private String title;
    private String woeid;
    private CityWeather mCityWeather;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWoeid() {
        return woeid;
    }

    public void setWoeid(String woeid) {
        this.woeid = woeid;
    }

    public CityWeather getCityWeather() {
        return mCityWeather;
    }

    public void setCityWeather(CityWeather cityWeather) {
        mCityWeather = cityWeather;
    }
}
