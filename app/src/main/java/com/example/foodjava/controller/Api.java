package com.example.foodjava.controller;

import com.example.foodjava.model.LanguageResponse;
import com.example.foodjava.model.MyResponse;
import com.example.foodjava.model.TranslateResponse;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {


    //поиск рецепта по ингредиентам
    @GET("/api/recipes/v2")
    Call<MyResponse> getRecipes(
            @Query("type") String type,
            @Query("app_id") String appId ,
            @Query("app_key") String appKey,
            @Query("q") String query);

    //методы для работы с Яндекс переводчиком

    //определение языка исходного текста
    @POST("/detect")
    Call<LanguageResponse> getLanguageCode(@Query("text") String text, @Query("folderId") String folderId);

    //перевод текста
    @POST("2/translate")
    Call<TranslateResponse> changePassword(@Header("Authorization") String token, @Body TranslateRequest request);


}
