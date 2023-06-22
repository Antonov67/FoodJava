package com.example.foodjava.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LanguageResponse {

    @SerializedName("languageCode")
    @Expose
    public String languageCode;

}