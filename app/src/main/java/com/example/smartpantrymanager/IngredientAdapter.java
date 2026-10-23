package com.example.smartpantrymanager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import android.widget.Toast;
import android.widget.Button;
import android.content.Intent;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>{

    private final ArrayList<Ingredient> ingredientList;
    private final DatabaseHelper databaseHelper;

    public IngredientAdapter (ArrayList<Ingredient> ingredientList, DatabaseHelper databaseHelper){
        this.ingredientList = ingredientList;
        this.databaseHelper = databaseHelper;
    }
    public static class IngredientViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView quantity;
        TextView expiry;
        Button deleteButton;
        Button editButton;

        public IngredientViewHolder(@NonNull View itemView){
            super(itemView);


            name= itemView.findViewById(R.id.itemIngredientName);
            quantity = itemView.findViewById(R.id.itemingredientQuantity);
            expiry = itemView.findViewById(R.id.itemingredientExpiryy);
            deleteButton = itemView.findViewById(R.id.deleteingredientButton);
            editButton = itemView.findViewById(R.id.editingredientButton);
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
        holder.itemView.setOnClickListener(v->{
            Intent intent = new Intent(v.getContext(), AddEditaIngredientActivity.class);
            intent.putExtra("ingredientId", ingredient.getId());
            v.getContext().startActivity(intent);
        });
        holder.deleteButton.setOnClickListener(v-> {
            boolean deleted = databaseHelper.deleteIngredient(ingredient.getId());

            if(deleted) {
                int currentPosition = holder.getBindingAdapterPosition();

                if (currentPosition != RecyclerView.NO_POSITION) {
                    ingredientList.remove(currentPosition);
                    notifyItemRemoved(currentPosition);
                }
                Toast.makeText(v.getContext(), "Ingredient deleted successfully", Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(v.getContext(), "Failed to delete ingredient", Toast.LENGTH_SHORT).show();
            }
        });

        holder.name.setText(ingredient.getName());
        holder.quantity.setText(
                holder.itemView.getContext().getString(
                        R.string.ingredient_quantity,
                        ingredient.getQuantity(),
                        ingredient.getUnit()
                )
        );

        if (ingredient.getExpiryDate() == null || ingredient.getExpiryDate().isEmpty()){
            holder.expiry.setText(R.string.expiry_not_provided);
        }else {
            holder.expiry.setText(holder.itemView.getContext().getString(
                    R.string.expiry_date,ingredient.getExpiryDate()
            ));

        }
        holder.editButton.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), AddEditaIngredientActivity.class);
            intent.putExtra("ingredientId", ingredient.getId());
            v.getContext().startActivity(intent);
        });
    }
    @Override
    public int getItemCount(){
        return ingredientList.size();
    }
}
