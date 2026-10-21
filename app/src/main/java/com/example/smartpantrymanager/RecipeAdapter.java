package com.example.smartpantrymanager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private final ArrayList<Recipe> recipeList;
    public interface OnRecipeClickListener {
        void onRecipeClick(Recipe recipe);
    }

    private OnRecipeClickListener listener;

    public void setOnRecipeClickListener(OnRecipeClickListener listener) {
        this.listener = listener;
    }
    public RecipeAdapter(ArrayList<Recipe> recipeList){
        this.recipeList = recipeList;
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }
    public static class RecipeViewHolder extends RecyclerView.ViewHolder{
        TextView recipeName;
        TextView recipeDescription;

        public RecipeViewHolder (@NonNull View itemView){
            super(itemView);

            recipeName= itemView.findViewById(R.id.recipeName);
            recipeDescription = itemView.findViewById(R.id.recipeDescription);
        }

    }
    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, parent,false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position){
        Recipe recipe = recipeList.get(position);
        holder.recipeName.setText(recipe.getName());
        holder.recipeDescription.setText(R.string.recipe_description);
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onRecipeClick(recipe);
            }
        });
    }

}
