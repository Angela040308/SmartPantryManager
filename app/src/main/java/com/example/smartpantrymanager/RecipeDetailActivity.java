package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recipe_detail);
        int recipeId = getIntent().getIntExtra("recipeId", -1);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        Recipe recipe = databaseHelper.getRecipeById(recipeId);

        databaseHelper.close();
        TextView recipeName = findViewById(R.id.recipeDetailTitle);
        TextView recipeIngredients = findViewById(R.id.recipeIngredients);
        TextView recipeInstructions = findViewById(R.id.recipeMethod);

        if (recipe != null) {
            recipeName.setText(recipe.getName());
            recipeIngredients.setText(recipe.getIngredients());
            recipeInstructions.setText(recipe.getInstructions());
        }
        else {
            recipeName.setText(R.string.recipe_not_found);
            recipeIngredients.setText("");
            recipeInstructions.setText("");
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}