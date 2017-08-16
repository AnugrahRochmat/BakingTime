package com.example.android.bakingtime;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.android.bakingtime.model.Recipe;

/**
 * Created by Anugrah on 8/16/17.
 */

public class RecipeStepsActivity extends AppCompatActivity {

    private Recipe recipe;

    private Button ingredientsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe_steps);
        ingredientsButton = (Button) findViewById(R.id.recipe_ingredients_button);

        Bundle data = getIntent().getExtras();
        if ( data != null) {
            recipe = data.getParcelable("recipe");
        } else {
            finish();
        }
        setTitle(recipe.getName());


        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);;
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("recipe_name", recipe.getName());
        editor.commit();

        RecipeStepsFragment recipeStepsFragment = new RecipeStepsFragment();
        getFragmentManager().beginTransaction().add(R.id.recipe_steps_container, recipeStepsFragment).commit();
        recipeStepsFragment.setRecipe(recipe);

        if(getResources().getBoolean(R.bool.isTablet)){
            // Tablet is True
            if (savedInstanceState == null) {
                IngredientListFragment ingredientListFragment = new IngredientListFragment();
                getFragmentManager().beginTransaction().add(R.id.ingredients_container, ingredientListFragment).commit();
                ingredientListFragment.setIngredients(recipe.getIngredients());

//                StepDetailFragment stepDetailFragment = new StepDetailFragment();
//                getFragmentManager().beginTransaction().add(R.id.detail_step_container, stepDetailFragment).commit();
//                stepDetailFragment.setStep(recipe.);
            }
        }

        ingredientsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!getResources().getBoolean(R.bool.isTablet)) {
                    // Tablet is false
                    Intent intentToStartIngredientActivity = new Intent(view.getContext(), IngredientListActivity.class);
                    intentToStartIngredientActivity.putExtra("recipe", recipe);

                    view.getContext().startActivity(intentToStartIngredientActivity);
                }
            }
        });

    }


//    @Override
//    protected void onCreate( Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_view_recipe_steps);
//
//        /**
//         * getIntent from MainActivity
//         */
//        Bundle data = getIntent().getExtras();
//        if ( data != null) {
//            recipe = data.getParcelable("recipe");
//            steps = recipe.getSteps();
//
//            setTitle(recipe.getName());
//        } else {
//            finish();
//        }
//
//        /**
//         * Ingredients Button
//         */
//        ingredientsButton = (Button) findViewById(R.id.recipe_ingredients_button);
//        ingredientsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intentToStartIngredientActivity = new Intent(view.getContext(), IngredientListActivity.class);
//                intentToStartIngredientActivity.putExtra("recipe", recipe);
//
//                view.getContext().startActivity(intentToStartIngredientActivity);
//            }
//        });
//
//        /**
//         * Steps Recycler View
//         */
//        recyclerView = (RecyclerView) findViewById(R.id.steps_recycler_view);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setHasFixedSize(true);
//
//        stepAdapter = new StepAdapter(new ArrayList<Step>(), getApplicationContext());
//        recyclerView.setAdapter(stepAdapter);
//
//        stepAdapter.setStepsData(steps);
//
//    }

}