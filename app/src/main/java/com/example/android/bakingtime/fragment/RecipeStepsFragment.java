package com.example.android.bakingtime.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingtime.R;
import com.example.android.bakingtime.adapter.StepAdapter;
import com.example.android.bakingtime.model.Recipe;
import com.example.android.bakingtime.model.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anugrah on 8/16/17.
 */

public class RecipeStepsFragment extends Fragment implements StepAdapter.OnStepAdapterListener {

    private static final String SAVED_STEPS_KEY = "SAVED_STEPS_KEY";
    private Recipe recipe;

    private RecyclerView recipeStepsRecyclerView;
    private StepAdapter stepAdapter;

    private int stepsPosition;

    public RecipeStepsFragment(){
    }

    @Override
    public void onViewSelected(int position) {
        stepsPosition = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_steps, container, false);
        recipeStepsRecyclerView = rootView.findViewById(R.id.steps_recycler_view);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recipeStepsRecyclerView.setLayoutManager(layoutManager);

        //stepAdapter = new StepAdapter(recipe.getSteps(), this);
        stepAdapter = new StepAdapter(new ArrayList<Step>(), this);
        recipeStepsRecyclerView.setAdapter(stepAdapter);


         /**
         * savedInstanceState
         */
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(SAVED_STEPS_KEY)){
                List<Step> steps = savedInstanceState.getParcelableArrayList(SAVED_STEPS_KEY);
                stepAdapter.setStepsData(steps);
            }
        } else {
            stepAdapter.setStepsData(recipe.getSteps());
        }

    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ArrayList<Step> stepsSaved = new ArrayList<>(stepAdapter.getSteps());
        if (stepsSaved != null && !stepsSaved.isEmpty()) {
            outState.putParcelableArrayList(SAVED_STEPS_KEY, stepsSaved);
        }
    }

}