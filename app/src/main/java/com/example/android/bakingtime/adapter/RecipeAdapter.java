package com.example.android.bakingtime.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingtime.R;
import com.example.android.bakingtime.activity.RecipeStepsActivity;
import com.example.android.bakingtime.model.Recipe;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

/**
 * Created by Anugrah on 8/16/17.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<Recipe> recipes;
    private Context context;

    /**
     * ViewHolder to contain a view for list item of recipe
     */
    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView recipeImage;
        public final TextView recipeName;

        public RecipeViewHolder(View view) {
            super(view);
            recipeImage = view.findViewById(R.id.recipe_image);
            recipeName = view.findViewById(R.id.recipe_name);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Recipe recipe = recipes.get(getAdapterPosition());

            Intent intentToStartRecipeStepsActivity = new Intent(view.getContext(), RecipeStepsActivity.class);
            intentToStartRecipeStepsActivity.putExtra("recipe", recipe);

            view.getContext().startActivity(intentToStartRecipeStepsActivity);
        }
    }

    /**
     * RecipeAdapter class constructor
     */
    public RecipeAdapter(List<Recipe> recipes, Context context) {
        this.recipes = recipes;
        this.context = context;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutInflateRecipe = R.layout.list_recipes;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutInflateRecipe, viewGroup, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, final int position) {
        Recipe recipe = recipes.get(position);

        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.BLACK)
                .borderWidthDp(2)
                .cornerRadiusDp(20)
                .oval(false)
                .build();

        if (TextUtils.isEmpty(recipe.getImage())) {
            Picasso.with(context).load(R.drawable.placeholder).fit().transform(transformation).into(holder.recipeImage);
        } else {
            Picasso.with(context).load(recipe.getImage()).placeholder(R.drawable.placeholder).fit().transform(transformation).into(holder.recipeImage);
        }

        holder.recipeName.setText(recipe.getName());
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void setRecipesData(List<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }
}