package com.example.android.bakingtime;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.bakingtime.adapter.RecipeAdapter;
import com.example.android.bakingtime.model.Recipe;
import com.example.android.bakingtime.rest.ApiClient;
import com.example.android.bakingtime.rest.ApiInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ProgressBar progressBar;
    private TextView errorMessage;

    private RecyclerView recyclerView;
    private RecipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        errorMessage = (TextView) findViewById(R.id.error_message);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        recyclerView = (RecyclerView) findViewById(R.id.recipes_recycler_view);

        /**
         * Check wether screen device on tablet or phone
         */
        if(getResources().getBoolean(R.bool.isTablet)) {
            // tablet device
            GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
            recyclerView.setLayoutManager(layoutManager);
        } else {
            // phone device
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
        }

        recyclerView.setHasFixedSize(true);
        adapter = new RecipeAdapter(new ArrayList<Recipe>(), getApplicationContext());
        recyclerView.setAdapter(adapter);

        new FetchRecipesTask().execute();
    }

    private void showErrorMessage() {
        recyclerView.setVisibility(View.INVISIBLE);
        errorMessage.setVisibility(View.VISIBLE);
    }

    public class FetchRecipesTask extends AsyncTask<Void, Void, List<Recipe>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Recipe> doInBackground(Void... params) {

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<Recipe>> call = apiService.getRecipes();

            try {
                List<Recipe> recipes = call.execute().body();
                return recipes;
            } catch (IOException e) {
                Log.e(TAG, "A problem occured ", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Recipe> recipes) {
            progressBar.setVisibility(View.GONE);
            if (recipes != null) {
                adapter.setRecipesData(recipes);
            } else {
                showErrorMessage();
            }
        }
    }

    // TODO implement SaveInstaceState
}