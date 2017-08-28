package com.example.android.bakingtime.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingtime.R;
import com.example.android.bakingtime.adapter.StepAdapter;
import com.example.android.bakingtime.model.Recipe;

/**
 * Created by Anugrah on 8/16/17.
 */

public class RecipeStepsFragment extends android.support.v4.app.Fragment implements StepAdapter.OnStepAdapterListener {

    private static final String BUNDLE_RECYCLER_LAYOUT = "BUNDLE_RECYCLER_LAYOUT";
    //private static final String SAVED_STEPS_KEY = "SAVED_STEPS_KEY";
    private static final String SAVED_RECIPES = "SAVED_RECIPES";
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

    public static RecipeStepsFragment newInstance(Recipe recipe){
        RecipeStepsFragment fragment = new RecipeStepsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(SAVED_RECIPES, recipe);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(SAVED_RECIPES)) {
            recipe = getArguments().getParcelable(SAVED_RECIPES);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_steps, container, false);
        recipeStepsRecyclerView = rootView.findViewById(R.id.steps_recycler_view);
        setupRecyclerView();

        return rootView;
    }

    private void setupRecyclerView(){
        stepAdapter = new StepAdapter(recipe.getSteps(), this);
        recipeStepsRecyclerView.setAdapter(stepAdapter);
        recipeStepsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recipeStepsRecyclerView.setHasFixedSize(true);
    }

//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        recipeStepsRecyclerView.setLayoutManager(layoutManager);
//
//        //stepAdapter = new StepAdapter(recipe.getSteps(), this);
//        stepAdapter = new StepAdapter(new ArrayList<Step>(), this);
//        recipeStepsRecyclerView.setAdapter(stepAdapter);
//
//
//         /**
//         * savedInstanceState
//         */
//        if (savedInstanceState != null) {
//            if (savedInstanceState.containsKey(SAVED_STEPS_KEY)){
//                List<Step> steps = savedInstanceState.getParcelableArrayList(SAVED_STEPS_KEY);
//                stepAdapter.setStepsData(steps);
//            }
//        } else {
//            stepAdapter.setStepsData(recipe.getSteps());
//        }
//
//    }
//
//    public void setRecipe(Recipe recipe) {
//        this.recipe = recipe;
//    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        ArrayList<Step> stepsSaved = new ArrayList<>(stepAdapter.getSteps());
//        if (stepsSaved != null && !stepsSaved.isEmpty()) {
//            outState.putParcelableArrayList(SAVED_STEPS_KEY, stepsSaved);
//        }
        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT, recipeStepsRecyclerView.getLayoutManager().onSaveInstanceState());
    }


    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if(savedInstanceState != null)
        {
            Parcelable savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
            recipeStepsRecyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
        }
    }


}