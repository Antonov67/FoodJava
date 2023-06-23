package com.example.foodjava.model;

import com.example.foodjava.controller.Utils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TranslateRequest {

    @SerializedName("sourceLanguageCode")
    @Expose
    public String sourceLanguageCode;
    @SerializedName("targetLanguageCode")
    @Expose
    public String targetLanguageCode;
    @SerializedName("texts")
    @Expose
    public List<String> texts;
    @SerializedName("folderId")
    @Expose
    public String folderId;
    @SerializedName("speller")
    @Expose
    public Boolean speller;

    public TranslateRequest(String sourceLanguageCode, List<String> texts) {
        this.sourceLanguageCode = sourceLanguageCode;
        this.targetLanguageCode = Utils.language;
        this.texts = texts;
        this.folderId = Utils.folderId;
        this.speller = true;
    }
}