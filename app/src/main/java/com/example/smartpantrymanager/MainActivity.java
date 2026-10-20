package com.example.smartpantrymanager;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import java.util.ArrayList;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;


public class MainActivity extends AppCompatActivity {

    private final ArrayList<Ingredient> ingredientList = new ArrayList<>();
    private DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        databaseHelper= new DatabaseHelper(this);
        RecyclerView pantryRecyclerView = findViewById(R.id.pantryRecyclerview);
        pantryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ingredientList.addAll(databaseHelper.getAllIngredient());
        IngredientAdapter adapter = new IngredientAdapter(ingredientList, databaseHelper);
        pantryRecyclerView.setAdapter(adapter);
        EditText ingredientName = findViewById(R.id.ingredientName);
        EditText ingredientQuantity = findViewById(R.id.ingredientQuantity);
        EditText ingredientUnit = findViewById(R.id.ingredientUnit);
        EditText expiryDate = findViewById(R.id.expiryDate);
        Button addIngredientButton = findViewById(R.id.addIngredientButton);
        addIngredientButton.setOnClickListener(v -> {
                    if (ingredientName.getText().toString().isEmpty()) {
                        Toast.makeText(this, "Enter ingredient name", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String name = ingredientName.getText().toString();

                    if (ingredientQuantity.getText().toString().isEmpty()) {
                        Toast.makeText(this, "Enter the quantity", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String quantity = ingredientQuantity.getText().toString();

                    if (ingredientUnit.getText().toString().isEmpty()) {
                        Toast.makeText(this, "Enter the unit", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String unit = ingredientUnit.getText().toString();
                    String expiry = expiryDate.getText().toString();
                    double quantityValue;
                    try {
                          quantityValue= Double.parseDouble(quantity);
                          if(quantityValue <=0){
                              Toast.makeText(this,"Quantity must be greater than zero", Toast.LENGTH_SHORT).show();
                              return;
                          }
                    } catch(NumberFormatException e){
                        Toast.makeText(this,"Enter a valid number for quantity", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    long saved = databaseHelper.addIngredient(name,quantityValue, unit, expiry);
                    if(saved !=-1){
                        Ingredient ingredient = new Ingredient ((int)saved ,name, quantity, unit, expiry);
                        ingredientList.add(ingredient);
                        adapter.notifyItemInserted(ingredientList.size() -1);
                        Toast.makeText(this, "Ingredient added successfully", Toast.LENGTH_SHORT).show();
                        ingredientName.setText("");
                        ingredientQuantity.setText("");
                        ingredientUnit.setText("");
                        expiryDate.setText("");
                    } else {
                        Toast.makeText(this, "Failed to save ingredient",Toast.LENGTH_SHORT).show();
                    }
                });
        Button clearButton = findViewById(R.id.clearButton);
        clearButton.setOnClickListener(v->{
            ingredientName.setText("");
            ingredientQuantity.setText("");
            ingredientUnit.setText("");
            expiryDate.setText("");
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    }