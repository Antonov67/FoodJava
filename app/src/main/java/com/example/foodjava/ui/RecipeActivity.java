package com.example.foodjava.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.foodjava.controller.Api;
import com.example.foodjava.controller.RecipeAdapter;
import com.example.foodjava.controller.RetrofitConnection;
import com.example.foodjava.controller.Utils;
import com.example.foodjava.model.MyResponse;
import com.example.foodkotlin.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeActivity extends AppCompatActivity {

    ViewPager2 pager;
    EditText searchField;
    RecipeAdapter adapter;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        getSupportActionBar().hide();

        pager = findViewById(R.id.pager);
        searchField = findViewById(R.id.search_field);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        Api apiService = RetrofitConnection.getInstance().getRetrofit().create(Api.class);


        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Call<MyResponse> call = apiService.getRecipes(Utils.type, Utils.appID, Utils.appKey, editable.toString());
                progressBar.setVisibility(View.VISIBLE);
                call.enqueue(new Callback<MyResponse>() {
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
        });






    }
}