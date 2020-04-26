package com.saket.myweatherapp_attemp3.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sshriwas on 2020-04-18
 */
public class RetrofitAPIClient {

    private RetrofitAPIClient(){};

    private static Retrofit mRetrofit;

    public static Retrofit getInstance() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl("https://www.metaweather.com/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return mRetrofit;
    }
}
