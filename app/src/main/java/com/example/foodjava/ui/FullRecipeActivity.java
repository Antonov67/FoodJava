package com.example.foodjava.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.foodkotlin.R;

public class FullRecipeActivity extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_recipe);
        getSupportActionBar().hide();

        webView = findViewById(R.id.web_view);

        String url = getIntent().getStringExtra("url");
        //Toast.makeText(this, url, Toast.LENGTH_SHORT).show();
        webView.loadUrl(url);

    }
}