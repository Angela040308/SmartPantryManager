package com.example.smartpantrymanager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>{
    private ArrayList<Ingredient> ingredientList;
    public IngredientAdapter (ArrayList<Ingredient> ingredientList){
        this.ingredientList=ingredientList;
    }
    public static class IngredientViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView quantity;
        TextView expiry;

        public IngredientViewHolder(@NonNull View itemView){
            super(itemView);

            name= itemView.findViewById(R.id.itemIngredientName);
            quantity = itemView.findViewById(R.id.itemingredientQuantity);
            expiry = itemView.findViewById(R.id.itemingredientExpiryy);
        }
    }
    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredient_item,parent,false);
        return new IngredientViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position){
        Ingredient ingredient = ingredientList.get(position);

        holder.name.setText(ingredient.getName());
        holder.quantity.setText(ingredient.getQuantity()+ " "+ ingredient.getUnit());
        holder.expiry.setText(ingredient.getExpiryDate());

        if (ingredient.getExpiryDate() == null || ingredient.getExpiryDate().isEmpty()){
            holder.expiry.setText("Expiry date: Not provided");
        }else {
            holder.expiry.setText("Expiry date" + ingredient.getExpiryDate());
        }
    }
    @Override
    public int getItemCount(){
        return ingredientList.size();
    }
}
