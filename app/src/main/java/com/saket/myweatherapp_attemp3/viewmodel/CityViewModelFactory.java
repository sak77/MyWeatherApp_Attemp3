package com.saket.myweatherapp_attemp3.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.saket.myweatherapp_attemp3.repositiory.CityWeatherRepository;

/**
 * Created by sshriwas on 2020-04-19
 */
public class CityViewModelFactory {

    public static CityViewModel getInstance(ViewModelStoreOwner owner) {
        return new ViewModelProvider(owner, new MyViewModelFactory()).get(CityViewModel.class);
    }

    static class MyViewModelFactory implements ViewModelProvider.Factory {

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            //add dependencies for ViewModel
            CityWeatherRepository cityWeatherRepository = new CityWeatherRepository();
            return (T) new CityViewModel(cityWeatherRepository);
        }
    }
}
