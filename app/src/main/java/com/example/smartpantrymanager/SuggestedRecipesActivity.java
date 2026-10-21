package com.example.smartpantrymanager;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;

import java.util.ArrayList;

public class SuggestedRecipesActivity extends AppCompatActivity {
    private RecyclerView suggestedRecipesRecyclerView;
    private RecipeAdapter recipeAdapter;
    private DatabaseHelper databaseHelper;
    private ArrayList<Recipe> recipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_suggested_recipes);
        databaseHelper = new DatabaseHelper(this);
        suggestedRecipesRecyclerView = findViewById(R.id.suggestedRecipesRecyclerView);
        suggestedRecipesRecyclerView.setLayoutManager( new LinearLayoutManager(this));
        recipeList = databaseHelper.getMatchingRecipes();
        recipeAdapter = new RecipeAdapter(recipeList);
        suggestedRecipesRecyclerView.setAdapter(recipeAdapter);
        recipeAdapter.setOnRecipeClickListener(recipe -> {
            Intent intent = new Intent(SuggestedRecipesActivity.this,
                    RecipeDetailActivity.class);
            intent.putExtra("recipeId", recipe.getId());
            startActivity(intent);
        });
        recipeAdapter.setOnRecipeClickListener(recipe -> {
            Intent intent = new Intent(SuggestedRecipesActivity.this,
                    RecipeDetailActivity.class);
            intent.putExtra("recipeId", recipe.getId());
            startActivity(intent);
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}