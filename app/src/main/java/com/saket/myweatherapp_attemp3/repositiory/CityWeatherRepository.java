package com.saket.myweatherapp_attemp3.repositiory;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.saket.myweatherapp_attemp3.model.City;
import com.saket.myweatherapp_attemp3.model.CityWeather;
import com.saket.myweatherapp_attemp3.network.CityWeatherService;
import com.saket.myweatherapp_attemp3.network.RetrofitAPIClient;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by sshriwas on 2020-04-20
 * The class responsible for Data operations
 */
public class CityWeatherRepository {

    private static final String TAG = CityWeatherRepository.class.getName();

    private CityWeatherService mCityWeatherService;

    public CityWeatherRepository() {
        //mCityList = new ArrayList<>();
        Retrofit retrofitClient = RetrofitAPIClient.getInstance();
        mCityWeatherService = retrofitClient.create(CityWeatherService.class);
    }

    public void getCityWoeId(final String cityName, MutableLiveData<City> cityMutableLiveData) {
        //MutableLiveData<City> mCityMutableLiveData = new MutableLiveData<>();
        Call<City[]> cityCall = mCityWeatherService.getCityInfo(cityName);
        cityCall.enqueue(new Callback<City[]>() {
            @Override
            public void onResponse(Call<City[]> call, Response<City[]> response) {
                if (response.isSuccessful()) {
                    City[] arrCityResponse = response.body();
                    City city = arrCityResponse[0];
                    //Take woeid and make another api request for weather details.
                    getCityWeatherInfo(city, cityMutableLiveData);
                }
            }

            @Override
            public void onFailure(Call<City[]> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void getCityWeatherInfo(final City city, MutableLiveData<City> cityMutableLiveData) {
        Call<CityWeather[]> cityWeatherCallable = mCityWeatherService.getCityWeather(city.getWoeid());
        cityWeatherCallable.enqueue(new Callback<CityWeather[]>() {
            @Override
            public void onResponse(Call<CityWeather[]> call, Response<CityWeather[]> response) {
                if (response.isSuccessful()) {
                    CityWeather[] arrCityWeather = response.body();
                    CityWeather cityWeather = arrCityWeather[0];
                    //Update City weather
                    city.setCityWeather(cityWeather);
                    cityMutableLiveData.setValue(city);
                    //updateCityListInfo(city);
                }
            }

            @Override
            public void onFailure(Call<CityWeather[]> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void updateCityListInfo(City city) {
        //mCityList.add(city);
        //mCityMutableLiveData.setValue(city);
        //I tried adding directly to live data list but it did not trigger the observer
        /*if (mCitiesLiveData.getValue() == null ) {
            mCitiesLiveData.setValue(mCityList);
        }
        mCitiesLiveData.getValue().add(city);*/
    }


    public Observable<City> getRxCityWeatherInfo(String[] arrCityNames) {
        List<Observable<City>> lstCityObservables = new ArrayList<>();
        for (String cityName: arrCityNames) {
            //just calling the getRxCityInfo() method does not acutally make the api call.
            // The API call only happens when you subscribe to the observable
            Observable<City> observable1 = mCityWeatherService.getRxCityInfo(cityName)
                    .map(new Function<City[], City>() {
                        @Override
                        public City apply(City[] cities) throws Exception {
                            return cities[0];
                        }
                    });
            lstCityObservables.add(observable1);
        }

        //Observable.merge will subscribe to each observable (.i.e. execute the API request)
        // and then return the response from different observables in the list.
        //Unlike this, Observable.fromIterable() simply emits the individual observables without subscribing to them.
        return Observable.merge(lstCityObservables)
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<City, ObservableSource<City>>() {
                    @Override
                    public ObservableSource<City> apply(City city) throws Exception {
                        getRxCityWeather(city.getWoeid())
                                .subscribe(new Consumer<CityWeather[]>() {
                                    @Override
                                    public void accept(CityWeather[] cityWeathers)
                                            throws Exception {
                                        city.setCityWeather(cityWeathers[0]);
                                    }
                                });

                        return Observable.create(new ObservableOnSubscribe<City>() {
                            @Override
                            public void subscribe(ObservableEmitter<City> emitter)
                                    throws Exception {
                                emitter.onNext(city);
                            }
                        });
                    }
                });
    }


    private Observable<CityWeather[]> getRxCityWeather(String woeid) {
        //
        return mCityWeatherService.getRxCityWeather(woeid);
    }
}
