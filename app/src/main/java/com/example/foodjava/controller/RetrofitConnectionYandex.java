package com.example.foodjava.controller;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConnectionYandex {

    private static final String BASE_URL = "https://translate.api.cloud.yandex.net/translate/v2";
    static Retrofit retrofit;
    static {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private static RetrofitConnectionYandex instance;
    private RetrofitConnectionYandex(){};
    public static RetrofitConnectionYandex getInstance(){
        if (instance == null){
            instance = new RetrofitConnectionYandex();
        }
        return instance;
    }

    public Retrofit getRetrofit(){
        return retrofit;
    }
}
