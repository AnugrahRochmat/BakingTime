package com.example.android.bakingtime;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingtime.adapter.StepAdapter;
import com.example.android.bakingtime.model.Recipe;

/**
 * Created by Anugrah on 8/16/17.
 */

public class RecipeStepsFragment extends Fragment implements StepAdapter.OnStepAdapterListener {

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

        stepAdapter = new StepAdapter(recipe.getSteps(), this);
        recipeStepsRecyclerView.setAdapter(stepAdapter);
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

}