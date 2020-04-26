package com.saket.myweatherapp_attemp3.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saket.myweatherapp_attemp3.R;
import com.saket.myweatherapp_attemp3.model.City;

import java.util.List;

/**
 * Created by sshriwas on 2020-04-18
 */
public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.CityViewHolder> {

    public interface CityClickListener {
        void onCityClicked(City city);
    }

    List<City> mCityList;
    CityClickListener mCityClickListener;

    public CityListAdapter(List<City> cities, CityClickListener cityClickListener) {
        this.mCityList = cities;
        this.mCityClickListener = cityClickListener;
    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_list_item,parent,false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {
        holder.bindView(mCityList.get(position));
    }

    @Override
    public int getItemCount() {
        return mCityList.size();
    }


    class CityViewHolder extends RecyclerView.ViewHolder {

        TextView txtCityName;
        TextView txtMaxTemp;
        TextView txtMinTemp;
        CityViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCityName = itemView.findViewById(R.id.txtCityName);
            txtMaxTemp = itemView.findViewById(R.id.txtMaxTemp);
            txtMinTemp = itemView.findViewById(R.id.txtMinTemp);
        }

        void bindView(final City city) {
            txtCityName.setText(city.getTitle());
            txtMaxTemp.setText(city.getCityWeather().getMax_temp());
            txtMinTemp.setText(city.getCityWeather().getMin_temp());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCityClickListener.onCityClicked(city);
                }
            });
        }
    }
}
