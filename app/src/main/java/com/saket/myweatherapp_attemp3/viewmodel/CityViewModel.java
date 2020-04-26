package com.saket.myweatherapp_attemp3.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.saket.myweatherapp_attemp3.model.City;
import com.saket.myweatherapp_attemp3.repositiory.CityWeatherRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by sshriwas on 2020-04-18
 * ViewModel is lifecycle aware
 */
public class CityViewModel extends ViewModel {

    private MutableLiveData<City> mSelectedCity = new MutableLiveData<>();
    List<City> mCities;
    //Pass this cityLiveData to the repository and call its setValue for each new City instance.
    MutableLiveData<City> cityLiveData = new MutableLiveData<>();
    //Maps to cityLiveData. This is done only once here. Earlier i put this in the for loop and saw
    //that each new call to map replaced the earlier maps.
    LiveData<List<City>> mCitiesLiveData = Transformations.map(cityLiveData, this::updateCityListData);

    private static final String TAG = CityViewModel.class.getName();

    private CityWeatherRepository mCityWeatherRepository;


    CityViewModel(CityWeatherRepository cityWeatherRepository) {
        this.mCityWeatherRepository = cityWeatherRepository;
    }

    //get city details
    public void getCityDetails(String[] arrCityNames) {
        mCities = new ArrayList<>();
        //Loop through City names
        for (String cityName : arrCityNames) {
            //Passing livedata instance to repository. When we get a City response
            //we invoke setValue() on this instance which will trigger updateCityListData() here
            //earlier i was trying to return a livedata instance from getCityWoeId but it did not
            //work in this case, because it became difficult to map local live data returned here
            //in transformations.map declared at the beginning.
            mCityWeatherRepository.getCityWoeId(cityName, cityLiveData);
        }
    }

    private List<City> updateCityListData(City city) {
        mCities.add(city);
        return mCities;
        //mCities.add(city);
        //mCitiesLiveData.setValue(mCities);
    }

    public City getSelectedCity() {
        return mSelectedCity.getValue();
    }

    public void setSelectedCity(City city) {
        mSelectedCity.setValue(city);
    }

    public LiveData<List<City>> getCitiesLiveData() {
        return mCitiesLiveData;
    }

    public void getRxCityDetails(String[] arrCityNames) {
        mCityWeatherRepository.getRxCityWeatherInfo(arrCityNames)
                .subscribe(new Observer<City>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(City city) {
                Log.d(TAG, "onNext: ");
                mCities.add(city);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
                //Update livedata
            }
        });
    }
}
