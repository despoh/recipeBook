package com.example.recipebook;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {


    private List<CookingRecipe> recipes;
    private LayoutInflater inflater;

    RecycleViewAdapter(Context context, List<CookingRecipe> recipes){
        this.inflater = LayoutInflater.from(context);
        this.recipes = recipes;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycleview_row, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdapter.ViewHolder holder, final int position) {
        final CookingRecipe recipe = recipes.get(position);
        holder.title.setText(recipe.getTitle());
        String stars ="";
        for(int i =0;i< recipe.getRating();i++){
            stars += "⭐";
        }
        holder.rating.setText(stars);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),RecipeDetailActivity.class);
                intent.putExtra("recipeId",recipe.getId());
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title ;
        TextView rating;

        ViewHolder(View view) {

            super(view);

            title = (TextView) view.findViewById(R.id.recipe_title);
            rating = (TextView) view.findViewById(R.id.recipe_rating);
        }


    }
}
