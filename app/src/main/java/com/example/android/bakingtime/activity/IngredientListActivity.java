package com.example.android.bakingtime.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.android.bakingtime.fragment.IngredientListFragment;
import com.example.android.bakingtime.R;
import com.example.android.bakingtime.model.Ingredient;
import com.example.android.bakingtime.model.Recipe;

import java.util.List;

/**
 * Created by Anugrah on 8/16/17.
 */

public class IngredientListActivity extends AppCompatActivity {

    private List<Ingredient> ingredients;
    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_list);

        IngredientListFragment ingredientListFragment = new IngredientListFragment();
        getFragmentManager().beginTransaction().add(R.id.ingredients_container, ingredientListFragment).commit();

        Bundle data = getIntent().getExtras();
        if ( data != null) {
            recipe = data.getParcelable("recipe");
            ingredients = recipe.getIngredients();
            ingredientListFragment.setIngredients(ingredients);
        } else {
            finish();
        }
        if (!getResources().getBoolean(R.bool.isTablet)){
            setTitle(recipe.getName() + " (Ingredients)");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}