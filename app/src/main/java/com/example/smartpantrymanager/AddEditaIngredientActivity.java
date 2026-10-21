package com.example.smartpantrymanager;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddEditaIngredientActivity extends AppCompatActivity {
private DatabaseHelper databaseHelper;
private EditText ingredientName;
private EditText ingredientQuantity;
private EditText ingredientUnit;
private EditText expiryDate;
private Button saveIngredientButton;
private Button cancelButton;
private int ingredientId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_edita_ingredient);
        databaseHelper = new DatabaseHelper(this);
        ingredientName = findViewById(R.id.editIngredientName);
        ingredientQuantity = findViewById(R.id.editIngredientQuantity);
        ingredientUnit = findViewById(R.id.editIngredientUnit);
        expiryDate = findViewById(R.id.editExpiryDate);
        saveIngredientButton = findViewById(R.id.saveIngredientButton);
        cancelButton = findViewById(R.id.cancelButton);
        ingredientId = getIntent().getIntExtra("ingredientId", -1);
        if (ingredientId != -1){
            Ingredient ingredient = databaseHelper.getIngredientById(ingredientId);
            if (ingredient != null){
                ingredientName.setText(ingredient.getName());
                ingredientQuantity.setText(ingredient.getQuantity());
                ingredientUnit.setText(ingredient.getUnit());
                expiryDate.setText(ingredient.getExpiryDate());
            }
        }
        saveIngredientButton.setOnClickListener(v-> {
            String name = ingredientName.getText().toString().trim();
            String quantityText = ingredientQuantity.getText().toString().trim();
            String unit = ingredientUnit.getText().toString().trim();
            String expiry = expiryDate.getText().toString().trim();

            if(name.isEmpty() || quantityText.isEmpty() || unit.isEmpty()){
                Toast.makeText(this,"Please fill in all required fields", Toast.LENGTH_SHORT).show();
                return ;
            }
            double quantity;
            try {
                quantity = Double.parseDouble(quantityText);
            }catch (NumberFormatException e){
                Toast.makeText(this,"Please enter a valid quantity", Toast.LENGTH_SHORT).show();
                return;
            }
            if (quantity <=0){
                Toast.makeText(this, "Quantity must be greater than zero", Toast.LENGTH_SHORT).show();
                return;
            }
            boolean success;
            if (ingredientId == -1){
                long result = databaseHelper.addIngredient(name,quantity, unit, expiry.isEmpty() ?null:expiry);
                success = result !=-1;
            } else {
                success = databaseHelper.updateIngredient(ingredientId, name, quantity, unit, expiry.isEmpty() ?null:expiry);
            }
            if (success){
                Toast.makeText(this, "Ingredient saved successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this,"Failed to save ingredient", Toast.LENGTH_SHORT).show();
            }
        });
        cancelButton.setOnClickListener(v-> finish());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}