package com.saket.myweatherapp_attemp3.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saket.myweatherapp_attemp3.R;
import com.saket.myweatherapp_attemp3.model.City;
import com.saket.myweatherapp_attemp3.viewmodel.CityViewModel;
import com.saket.myweatherapp_attemp3.viewmodel.CityViewModelFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sshriwas on 2020-04-18
 */
public class CityListFragment extends Fragment implements CityListAdapter.CityClickListener {

    RecyclerView recCityList;
    List<City> mCityList = new ArrayList<>();
    CityListAdapter cityListAdapter;

    public static CityListFragment newInstance() {
        
        Bundle args = new Bundle();
        
        CityListFragment fragment = new CityListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_city_list, container, false);
        recCityList = root.findViewById(R.id.recCityList);
        recCityList.setLayoutManager(new LinearLayoutManager(getContext()));
        cityListAdapter = new CityListAdapter(mCityList, this);
        recCityList.setAdapter(cityListAdapter);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getCityInfo();
        super.onActivityCreated(savedInstanceState);
    }

    void getCityInfo() {
        if (mCityList.size() == 0) {
            String[] arrCityNames = getResources().getStringArray(R.array.city_names);

            CityViewModel cityViewModel = CityViewModelFactory.getInstance(requireActivity());
            //cityViewModel.getCityDetails(arrCityNames);
            cityViewModel.getRxCityDetails(arrCityNames);

            cityViewModel.getCitiesLiveData().observe(getViewLifecycleOwner(), new Observer<List<City>>() {
                @Override
                public void onChanged(List<City> cities) {
                    //update list
                    mCityList.clear();
                    mCityList.addAll(cities);
                    cityListAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public void onCityClicked(City city) {
        CityViewModel cityViewModel = new ViewModelProvider(requireActivity()).get(CityViewModel.class);
        cityViewModel.setSelectedCity(city);
        //Go to City weather fragment
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, CityWeatherFragment.newInstance());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
