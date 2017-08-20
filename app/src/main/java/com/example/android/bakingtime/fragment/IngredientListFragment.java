package com.example.android.bakingtime.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingtime.R;
import com.example.android.bakingtime.adapter.IngredientAdapter;
import com.example.android.bakingtime.model.Ingredient;

import java.util.List;

/**
 * Created by Anugrah on 8/16/17.
 */

public class IngredientListFragment extends Fragment {

    private List<Ingredient> ingredients;

    private RecyclerView ingredientRecyclerView;
    private IngredientAdapter ingredientAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ingredient_list, container, false);
        ingredientRecyclerView = rootView.findViewById(R.id.ingredients_recycler_view);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        ingredientRecyclerView.setLayoutManager(layoutManager);

        ingredientAdapter = new IngredientAdapter(ingredients);
        ingredientRecyclerView.setAdapter(ingredientAdapter);
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}