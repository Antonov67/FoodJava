package com.example.foodjava.controller;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.foodjava.model.MyResponse;
import com.example.foodjava.model.Recipe;
import com.example.foodjava.ui.FullRecipeActivity;
import com.example.foodkotlin.R;
import com.squareup.picasso.Picasso;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.OnboardingViewHolder> {
    private MyResponse onboardingItems;



    public RecipeAdapter(MyResponse onboardingItems) {

        this.onboardingItems = onboardingItems;
    }

    @NonNull
    @Override
    public OnboardingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OnboardingViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_layout, parent, false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull OnboardingViewHolder holder, int position) {
        holder.setOnboardingData(onboardingItems.hits.get(position).recipe);
    }

    @Override
    public int getItemCount() {
        return onboardingItems.hits.size();
    }

    class OnboardingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textZagolovok;
        private TextView textOpisanie;
        private TextView textCalorii;
        private TextView textRating;
        private TextView textUrl;
        private ImageView image;

        private final Context context;
        private String url;

        OnboardingViewHolder(@NonNull View itemView) {
            super(itemView);
            textZagolovok = itemView.findViewById(R.id.text_title);
            textOpisanie = itemView.findViewById(R.id.text_descr);
            textCalorii = itemView.findViewById(R.id.text_calorii);
            textRating = itemView.findViewById(R.id.text_rating);
            image = itemView.findViewById(R.id.image);
            textUrl = itemView.findViewById(R.id.text_url);
            image.setOnClickListener(this);
            context = itemView.getContext();

        }

        void setOnboardingData(Recipe recipe) {
            textZagolovok.setText(recipe.label);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ингридиенты:\n");
            for (String s: recipe.ingredientLines) {
                stringBuilder.append(s).append("\n");
            }
            textOpisanie.setText(stringBuilder);
            textUrl.setText("url:\n" + recipe.url);
            url = recipe.url;
            textCalorii.setText("калории:\n " + recipe.calories);
            textRating.setText("рейтинг: \n" + recipe.co2EmissionsClass);
            //здесь нужна библиотека Picasso для загрузки картинки по url в наш imageView
            Picasso.get().load(recipe.image).into(image);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(new Intent(context, FullRecipeActivity.class));
            intent.putExtra("url", url);
            context.startActivity(intent);
        }
    }


}