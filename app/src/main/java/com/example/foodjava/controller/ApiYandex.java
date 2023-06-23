package com.example.foodjava.controller;

import com.example.foodjava.model.LanguageResponse;
import com.example.foodjava.model.TranslateRequest;
import com.example.foodjava.model.TranslateResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiYandex {


   //методы для работы с Яндекс переводчиком

    //определение языка исходного текста
    @POST("/translate/v2/detect")
    Call<LanguageResponse> getLanguageCode(@Header("Authorization") String token, @Query("text") String text, @Query("folderId") String folderId);

    //перевод текста
    @POST("/translate/v2/translate")
    Call<TranslateResponse> translate(@Header("Authorization") String token, @Body TranslateRequest request);


}
