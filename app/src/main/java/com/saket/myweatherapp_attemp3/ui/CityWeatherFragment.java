package com.saket.myweatherapp_attemp3.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.saket.myweatherapp_attemp3.MainActivity;
import com.saket.myweatherapp_attemp3.R;
import com.saket.myweatherapp_attemp3.model.City;
import com.saket.myweatherapp_attemp3.viewmodel.CityViewModel;
import com.squareup.picasso.Picasso;

/**
 * Created by sshriwas on 2020-04-18
 */
public class CityWeatherFragment extends Fragment {

    static CityWeatherFragment newInstance() {
        
        Bundle args = new Bundle();
        
        CityWeatherFragment fragment = new CityWeatherFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_city_weather, container, false);
        TextView txtWeatherName = root.findViewById(R.id.txtWeatherName);
        TextView txtCurrentTemp = root.findViewById(R.id.txtCurrentTemp);
        ImageView imgWeatherIcon = root.findViewById(R.id.imageView);
        CityViewModel cityViewModel = new ViewModelProvider(requireActivity()).get(CityViewModel.class);
        City selectedCity = cityViewModel.getSelectedCity();

        Picasso.get()
                .load("https://www.metaweather.com/static/img/weather/png/64/clear.png")
                .into(imgWeatherIcon);
        txtWeatherName.setText(selectedCity.getCityWeather().getWeather_name());
        txtCurrentTemp.setText(selectedCity.getCityWeather().getCurrent_temp());
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        ((MainActivity)getActivity()).displayNavIcon();
        super.onActivityCreated(savedInstanceState);
    }
}
