package com.example.foodjava.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TranslateResponse {

    @SerializedName("translations")
    @Expose
    public List<Translation> translations;

}
