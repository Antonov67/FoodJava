package com.example.foodjava.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.foodjava.controller.Api;
import com.example.foodjava.controller.ApiYandex;
import com.example.foodjava.controller.RecipeAdapter;
import com.example.foodjava.controller.RetrofitConnection;
import com.example.foodjava.controller.RetrofitConnectionYandex;
import com.example.foodjava.controller.Utils;
import com.example.foodjava.model.LanguageResponse;
import com.example.foodjava.model.MyResponse;
import com.example.foodjava.model.TranslateRequest;
import com.example.foodjava.model.TranslateResponse;
import com.example.foodkotlin.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeActivity extends AppCompatActivity {

    ViewPager2 pager;
    EditText searchField;
    RecipeAdapter adapter;
    ProgressBar progressBar;
    MaterialButton searchButton;

    String sourceLanguage = "ru"; // язык ввода по умолчанию, позже мы его определим
    List<String> list = new ArrayList();
    String translatedQueryText = "";  //переведенный на английский текст запроса


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        getSupportActionBar().hide();

        pager = findViewById(R.id.pager);
        searchButton = findViewById(R.id.search_button);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.item_decorator));
        pager.addItemDecoration(itemDecorator);
        searchField = findViewById(R.id.search_field);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);


        ApiYandex apiYandex = RetrofitConnectionYandex.getInstance().getRetrofit().create(ApiYandex.class);


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //определим язык введенного текста и переведем его на английский, чтобы сделать запрос
                LanguageResponse languageResponse = new LanguageResponse();
                Call<LanguageResponse> responseCall = apiYandex.getLanguageCode(Utils.token, searchField.getText().toString(), Utils.folderId);
                responseCall.enqueue(new Callback<LanguageResponse>() {
                    @Override
                    public void onResponse(Call<LanguageResponse> call, Response<LanguageResponse> response) {
                        if (response.isSuccessful()) {
                            sourceLanguage = response.body().languageCode; //запишем определившийся язык ввода в переменную
                            //Toast.makeText(RecipeActivity.this, response.body().languageCode, Toast.LENGTH_SHORT).show();

                            //переведем введенный текст на английский, чтобы позже использовать его для запроса рецепта
                            //Создадим запрос

                            list.clear();
                            list.add(searchField.getText().toString());
                            TranslateRequest translateRequest = new TranslateRequest(sourceLanguage, list);
                            Call<TranslateResponse> translateResponseCall = apiYandex.translate(Utils.token, translateRequest);
                            translateResponseCall.enqueue(new Callback<TranslateResponse>() {
                                @Override
                                public void onResponse(Call<TranslateResponse> call, Response<TranslateResponse> response) {
                                    if (response.isSuccessful()){
                                        translatedQueryText = response.body().translations.get(0).text; //заберем переведенный на английский текст запроса
                                        //Toast.makeText(RecipeActivity.this, response.body().translations.get(0).text, Toast.LENGTH_SHORT).show();

                                        //получим рецепты по переведенному на ангилйский запросу
                                        Api apiService = RetrofitConnection.getInstance().getRetrofit().create(Api.class);
                                        Call<MyResponse> call2 = apiService.getRecipes(Utils.type, Utils.appID, Utils.appKey, translatedQueryText);
                                        progressBar.setVisibility(View.VISIBLE);
                                        call2.enqueue(new Callback<MyResponse>() {
                                            @Override
                                            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {

                                                adapter = new RecipeAdapter(response.body());
                                                pager.setAdapter(adapter);
                                                progressBar.setVisibility(View.INVISIBLE);
                                                //Toast.makeText(RecipeActivity.this, response.body().hits.get(0).recipe.label + "калории: " + response.body().hits.get(0).recipe.calories + " " + response.body().hits.get(0).recipe.ingredientLines.toString() + " " + response.body().hits.get(0).recipe.co2EmissionsClass, Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onFailure(Call<MyResponse> call, Throwable t) {
                                                Toast.makeText(RecipeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onFailure(Call<TranslateResponse> call, Throwable t) {

                                }
                            });



                        }

                    }

                    @Override
                    public void onFailure(Call<LanguageResponse> call, Throwable t) {
                        Toast.makeText(RecipeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });





            }
        });

    }
}