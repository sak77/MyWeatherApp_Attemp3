package com.saket.myweatherapp_attemp3.network;

import com.saket.myweatherapp_attemp3.model.City;
import com.saket.myweatherapp_attemp3.model.CityWeather;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by sshriwas on 2020-04-18
 */
public interface CityWeatherService {

    @GET("location/search/")
    Call<City[]> getCityInfo(@Query("query") String cityName);

    @GET("location/{woeid}/2020/4/20/")
    Call<CityWeather[]> getCityWeather(@Path("woeid") String woeid);

    //Rxjava
    @GET("location/search/")
    Observable<City[]> getRxCityInfo(@Query("query") String cityName);

    @GET("location/{woeid}/2020/4/23/")
    Observable<CityWeather[]> getRxCityWeather(@Path("woeid") String woeid);

}
