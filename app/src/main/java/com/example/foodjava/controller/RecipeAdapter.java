package com.example.foodjava.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.foodjava.model.MyResponse;
import com.example.foodjava.model.Recipe;
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

    class OnboardingViewHolder extends RecyclerView.ViewHolder {
        private TextView textZagolovok;
        private TextView textOpisanie;
        private TextView textCalorii;
        private TextView textRating;
        private ImageView image;

        OnboardingViewHolder(@NonNull View itemView) {
            super(itemView);
            textZagolovok = itemView.findViewById(R.id.text_title);
            textOpisanie = itemView.findViewById(R.id.text_descr);
            textCalorii = itemView.findViewById(R.id.text_calorii);
            textRating = itemView.findViewById(R.id.text_rating);
            image = itemView.findViewById(R.id.image);
        }

        void setOnboardingData(Recipe recipe) {
            textZagolovok.setText(recipe.label);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ингридиенты:\n");
            for (String s: recipe.ingredientLines) {
                stringBuilder.append(s).append("\n");
            }
            textOpisanie.setText(stringBuilder);
            textCalorii.setText("калории:\n " + recipe.calories);
            textRating.setText("рейтинг: \n" + recipe.co2EmissionsClass);
            //здесь нужна библиотека Picasso для загрузки картинки по url в наш imageView
            Picasso.get().load(recipe.image).into(image);
        }
    }
}