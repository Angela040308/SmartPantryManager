package com.example.smartpantrymanager;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME ="SmartPantry.db"; //sets the name of the local database file
    private static final int DATABASE_VERSION=1;

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
                "ingredients Text NOT NULL, "+
                "instructions TEXT NOT NULL)";
        db.execSQL(createRecipesTable);
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
    public long addRecipe(String name, String ingredients, String instructions){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("ingredients",ingredients);
        values.put("instructions", instructions);
        return db.insert("recipes", null, values);
    }
    @Override //handles database structure changes in future version
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
