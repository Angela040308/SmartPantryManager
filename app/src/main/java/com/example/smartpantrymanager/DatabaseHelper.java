package com.example.smartpantrymanager;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DatabaseHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME ="SmartPantry.db"; //sets the name of the local database file
    private static final int DATABASE_VERSION=2;

    public DatabaseHelper(Context context){ //connects the helper to apps context
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }
    @Override //will create database table when the database is first created
    public void onCreate(SQLiteDatabase db){
        String createTable = "CREATE TABLE ingredients ("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "name TEXT NOT NULL, "+
                "quantity REAL NOT NULL, "+
                "unit TEXT NOT NULL, "+
                "expiryDate TEXT)";

        db.execSQL(createTable);
        String createRecipesTable = "CREATE TABLE recipes ("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "name TEXT NOT NULL, "+
                "ingredients TEXT NOT NULL, "+
                "instructions TEXT NOT NULL)";
        db.execSQL(createRecipesTable);
        seedRecipes(db);
    }
    public long addIngredient(String name, double quantity, String unit, String expiryDate){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("quantity",quantity);
        values.put("unit", unit);
        values.put("expiryDate",expiryDate);

        return db.insert("ingredients",null,values);


    }
    public ArrayList<Ingredient> getAllIngredient(){
        ArrayList<Ingredient> ingredientList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM ingredients",null);

        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString (cursor.getColumnIndexOrThrow("name"));
                double quantity = cursor.getDouble(cursor.getColumnIndexOrThrow("quantity"));
                String unit = cursor.getString (cursor.getColumnIndexOrThrow("unit"));
                String expiryDate = cursor.getString (cursor.getColumnIndexOrThrow("expiryDate"));
                Ingredient ingredient = new Ingredient(id, name, String.valueOf(quantity), unit, expiryDate);
                ingredientList.add(ingredient);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return ingredientList;
    }
    public Ingredient getIngredientById(int id ){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM ingredients WHERE id= ?",
                new String []{String.valueOf(id)}
        );
        Ingredient ingredient = null;
        if (cursor.moveToFirst()){
            String name = cursor.getString (cursor.getColumnIndexOrThrow("name"));
            double quantity = cursor.getDouble(cursor.getColumnIndexOrThrow("quantity"));
            String unit = cursor.getString (cursor.getColumnIndexOrThrow("unit"));
            String expiryDate = cursor.getString (cursor.getColumnIndexOrThrow("expiryDate"));
            ingredient = new Ingredient(id, name, String.valueOf(quantity), unit, expiryDate);
        }
        cursor.close();
        return ingredient;

    }

    public boolean updateIngredient(int id, String name, double quantity, String unit, String expiryDate){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("quantity",quantity);
        values.put("unit", unit);
        values.put("expiryDate",expiryDate);
        int result = db.update(
                "ingredients",
                values,
                "id = ?",
                new String[]{String.valueOf(id)}
        );
        return result>0;
    }
    public boolean deleteIngredient(int id){
        SQLiteDatabase db= this.getWritableDatabase();
        int result = db.delete(
                "ingredients",
                "id = ?",
                new String []{String.valueOf(id)}
        );
        return result >0;
    }
    public long addRecipe(SQLiteDatabase db, String name, String ingredients, String instructions){
        //SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("ingredients",ingredients);
        values.put("instructions", instructions);
        return db.insert("recipes", null, values);
    }
    private void seedRecipes (SQLiteDatabase db){
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM recipes", null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        if (count >0){
            return;
        }
        addRecipe(db,"Scrambled Eggs",
                "Egg:2:pcs;Milk:30:ml;Butter:10:g",
                "Beat the eggs and milk together.Melt the butter in a pan. Add the egg mixture and stir until cooked "
        );
        addRecipe(db ,"Pancakes",
                "Flour:100:g;Milk:150:ml;Egg:1:pcs",
                "Beat eggs with milk.Melt butter in a pan. Stir eggs until cooked"
        );
        addRecipe(db,"Cheese Omelette",
                "Egg:2:pcs;Cheese:30:g;Butter:5:g",
                "Beat eggs. Cook in butter, add cheese and fold." );

        addRecipe(db, "Banana Smoothie",
                "Banana:1:pcs;Milk:200:ml",
                "Blend the banana and milk until smooth.");

        addRecipe(db,"French Toast",
                "Bread:2:pcs;Egg:1:pcs;Milk:50:ml",
                "Dip bread in beaten egg and milk. Fry until golden." );
        addRecipe(db, "Tomato Pasta",
                "Pasta:100:g;Tomato:2:pcs;Onion:1:pcs",
                "Boil pasta. Cook chopped tomato and onion, then combine.");

        addRecipe(db, "Garlic Butter Pasta",
                "Pasta:100:g;Butter:15:g;Garlic:2:g",
                "Boil pasta. Melt butter, add garlic, and mix with pasta.");

        addRecipe(db, "Potato Mash",
                "Potato:200:g;Milk:50:ml;Butter:10:g",
                "Boil potatoes until soft. Mash with milk and butter." );

        addRecipe(db, "Garden Salad",
                "Lettuce:50:g;Tomato:1:pcs;Cucumber:1:pcs",
                "Wash and chop the vegetables, then mix together.");

        addRecipe(db,"Egg Sandwich",
                "Bread:2:pcs;Egg:2:pcs;Butter:5:g",
                "Cook the eggs. Spread butter on bread and add the eggs." );

        addRecipe(db, "Apple Banana Bowl",
                "Apple:1:pcs;Banana:1:pcs",
                "Wash and chop the fruit. Serve together.");

        addRecipe(db,"Cheese Toast",
                "Bread:2:pcs;Cheese:40:g;Butter:5:g",
                "Add cheese to bread and toast until melted." );

        addRecipe(db,"Carrot Salad",
                "Carrot:100:g;Lemon:10:ml",
                "Grate the carrot and mix with lemon juice." );

        addRecipe(db,"Milk Porridge",
                "Oats:50:g;Milk:200:ml",
                "Cook oats with milk over low heat, stirring until thick." );

        addRecipe(db, "Tomato Omelette",
                "Egg:2:pcs;Tomato:1:pcs;Onion:1:pcs",
                "Beat eggs. Cook chopped tomato and onion, add eggs and cook.");
    }
    public ArrayList<Recipe> getAllRecipes(){
        ArrayList<Recipe> recipeList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM recipes",null);
        if(cursor.moveToFirst ()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String ingredients = cursor.getString(cursor.getColumnIndexOrThrow("ingredients"));
                String instructions = cursor.getString(cursor.getColumnIndexOrThrow("instructions"));
                Recipe recipe = new Recipe(id, name, ingredients, instructions);
                recipeList.add(recipe);
            }
            while (cursor.moveToNext()) ;
        }
        cursor.close();
        return recipeList;

    }
    public Recipe getRecipeById(int recipeId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM recipes WHERE id = ?",
                new String[]{String.valueOf(recipeId)}
        );

        Recipe recipe = null;

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String ingredients = cursor.getString(cursor.getColumnIndexOrThrow("ingredients"));
            String instructions = cursor.getString(cursor.getColumnIndexOrThrow("instructions"));

            recipe = new Recipe(id, name, ingredients, instructions);
        }

        cursor.close();
        return recipe;
    }
    public ArrayList<Recipe> getMatchingRecipes() {
        ArrayList<Recipe> matchingRecipes = new ArrayList<>();
        ArrayList<Ingredient> pantryItems = getAllIngredient();
        ArrayList<Recipe> allRecipes = getAllRecipes();

        Map<String, Double> pantryQuantities = new HashMap<>();
        Map<String, String> pantryUnits = new HashMap<>();

        for (Ingredient item : pantryItems) {
            String name = item.getName().trim().toLowerCase(Locale.ROOT);
            String unit = getBaseUnit(normalizeUnit(item.getUnit()));
            double quantity = convertToBaseUnit(
                    Double.parseDouble(item.getQuantity()), unit);


            Double currentQuantity = pantryQuantities.get(name);
            pantryQuantities.put(
                    name, (currentQuantity == null ? 0.0 : currentQuantity) + quantity
            );

            pantryUnits.put(name, unit);

        }

        for (Recipe recipe : allRecipes) {
            boolean canMakeRecipe = true;
            String[] requiredIngredients = recipe.getIngredients().split(";");

            for (String required : requiredIngredients) {
                String[] details = required.split(":");

                if (details.length != 3) {
                    canMakeRecipe = false;
                    break;
                }

                String name = details[0].trim().toLowerCase(Locale.ROOT);
                String requiredUnit = getBaseUnit(normalizeUnit(details[2]));
                double requiredQuantity = convertToBaseUnit(
                        Double.parseDouble(details[1].trim()), requiredUnit);

                Double availableQuantity = pantryQuantities.get(name);
                String availableUnit = pantryUnits.get(name);

                if (availableQuantity == null
                        || availableUnit == null
                        || !availableUnit.equals(requiredUnit)
                        || availableQuantity < requiredQuantity) {
                    canMakeRecipe = false;
                    break;
                }
            }

            if (canMakeRecipe) {
                matchingRecipes.add(recipe);
            }
        }

        return matchingRecipes;
    }

    private String normalizeUnit (String unit){
        switch(unit.trim().toLowerCase(Locale.ROOT)){
            case "gram":
            case "grams":
            case "g":
                return "g";

            case "kilogram":
            case "kilograms":
            case"kg":
                return "kg";

            case "milliliter":
            case "milliliters":
            case "millilitres":
            case "millilitre":
            case "ml":
                return "ml";

            case "liter":
            case "liters":
            case "litre":
            case "litres":
            case "l":
                return "l";

            case "pieces":
            case "piece":
            case "pcs":
                return "pcs";

            default:
                return unit.trim().toLowerCase(Locale.ROOT);

        }
    }
    private String getBaseUnit(String unit) {
        switch (normalizeUnit(unit)) {
            case "kg":
            case "g":
                return "g";

            case "l":
            case "ml":
                return "ml";

            default:
                return normalizeUnit(unit);
        }
    }
    private double convertToBaseUnit(double quantity, String unit) {
        switch (normalizeUnit(unit)) {
            case "kg":
            case "l":
                return quantity * 1000;

            case "g":
            case "ml":
            case "pcs":
            default:
                return quantity;
        }
    }

    @Override //handles database structure changes in future version
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        if(oldVersion <2){
            String createRecipesTable =" CREATE TABLE IF NOT EXISTS recipes("+
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "name TEXT NOT NULL, "+
                    "ingredients TEXT NOT NULL, "+
                    "instructions TEXT NOT NULL) ";
            db.execSQL(createRecipesTable);
            seedRecipes(db);
        }
    }
}
