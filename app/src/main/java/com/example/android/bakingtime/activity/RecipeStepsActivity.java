package com.example.android.bakingtime.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.android.bakingtime.R;
import com.example.android.bakingtime.adapter.StepDetailPagerAdapter;
import com.example.android.bakingtime.fragment.IngredientListFragment;
import com.example.android.bakingtime.fragment.RecipeStepsFragment;
import com.example.android.bakingtime.model.Recipe;
import com.example.android.bakingtime.model.SelectedPosition;
import com.example.android.bakingtime.model.Step;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anugrah on 8/16/17.
 */

public class RecipeStepsActivity extends AppCompatActivity  {

    private static final String SAVED_STEPS_PAGER_KEY = "SAVED_STEPS_PAGER_KEY";
    private Recipe recipe;

    private Button ingredientsButton;

    private ViewPager viewPager;
    private StepDetailPagerAdapter stepDetailPagerAdapter;

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe_steps);

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

        /**
         * Call Steps Fragment
         */
        RecipeStepsFragment recipeStepsFragment = new RecipeStepsFragment();
        getFragmentManager().beginTransaction().add(R.id.recipe_steps_container, recipeStepsFragment).commit();
        recipeStepsFragment.setRecipe(recipe);

        /**
         * Check wether screen device on tablet or phone
         */
        if(getResources().getBoolean(R.bool.isTablet)){
            // Tablet is True
            /**
             * Call ingredient List Fragment
             */
            IngredientListFragment ingredientListFragment = new IngredientListFragment();
            getFragmentManager().beginTransaction().add(R.id.ingredients_container, ingredientListFragment).commit();
            ingredientListFragment.setIngredients(recipe.getIngredients());

            /**
             * Call detail of steps fragment
             */
            viewPager = (ViewPager)findViewById(R.id.view_pager);
            stepDetailPagerAdapter = new StepDetailPagerAdapter(getSupportFragmentManager(), this, new ArrayList<Step>());
            viewPager.setAdapter(stepDetailPagerAdapter);

            /**
             * savedInstanceState
             */
            if (savedInstanceState != null) {
                if (savedInstanceState.containsKey(SAVED_STEPS_PAGER_KEY)){
                    List<Step> steps = savedInstanceState.getParcelableArrayList(SAVED_STEPS_PAGER_KEY);
                    stepDetailPagerAdapter.setSteps(steps);
                }
            } else {
                stepDetailPagerAdapter.setSteps(recipe.getSteps());
            }
        } else {
            // Tablet is False
            ingredientsButton = (Button) findViewById(R.id.recipe_ingredients_button);
            ingredientsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!getResources().getBoolean(R.bool.isTablet)) {
                        Intent intentToStartIngredientActivity = new Intent(view.getContext(), IngredientListActivity.class);
                        intentToStartIngredientActivity.putExtra("recipe", recipe);

                        view.getContext().startActivity(intentToStartIngredientActivity);
                    }
                }
            });
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SelectedPosition selectedPosition) {
        viewPager.setCurrentItem(selectedPosition.getPosition());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ArrayList<Step> stepsPagerSaved = new ArrayList<>(stepDetailPagerAdapter.getSteps());
        if (stepsPagerSaved != null && !stepsPagerSaved.isEmpty()) {
            outState.putParcelableArrayList(SAVED_STEPS_PAGER_KEY, stepsPagerSaved);
        }
    }
}