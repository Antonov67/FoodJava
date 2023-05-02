package com.example.foodjava.controller;

import com.example.foodjava.model.MyResponse;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("/api/recipes/v2")
    Call<MyResponse> getRecipes(
            @Query("type") String type,
            @Query("app_id") String appId ,
            @Query("app_key") String appKey,
            @Query("q") String query);
}
