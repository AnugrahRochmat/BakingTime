package com.example.android.bakingtime.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingtime.R;
import com.example.android.bakingtime.model.Ingredient;

import java.util.List;

/**
 * Created by Anugrah on 8/16/17.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    public List<Ingredient> ingredients;

    public class IngredientViewHolder extends RecyclerView.ViewHolder {
        public final TextView ingredientName;
        public final TextView ingredientMeasureQuantity;

        public IngredientViewHolder(View view){
            super(view);
            ingredientName = view.findViewById(R.id.ingredient_name);
            ingredientMeasureQuantity = view.findViewById(R.id.ingredient_quantity_measure);
        }
    }

    public IngredientAdapter(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutInflaterIngredient = R.layout.list_ingredients;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutInflaterIngredient, viewGroup, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);

        holder.ingredientName.setText(ingredient.getIngredient());
        holder.ingredientMeasureQuantity.setText(ingredient.getQuantity() + " " + ingredient.getMeasure());
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public void setIngredientsData(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

}