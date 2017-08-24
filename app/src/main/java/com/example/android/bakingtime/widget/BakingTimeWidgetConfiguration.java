package com.example.android.bakingtime.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.example.android.bakingtime.R;
import com.example.android.bakingtime.activity.MainActivity;
import com.example.android.bakingtime.model.Recipe;
import com.example.android.bakingtime.rest.ApiClient;
import com.example.android.bakingtime.rest.ApiInterface;
import com.google.gson.GsonBuilder;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Created by Anugrah on 8/23/17.
 */

public class BakingTimeWidgetConfiguration extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private int appWidgetId;

    private RecyclerView recyclerView;
    private RecipeWidgetAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        if (data != null) {
            appWidgetId = data.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        setContentView(R.layout.baking_time_widget_configure);
        recyclerView = (RecyclerView) findViewById(R.id.recipes_widget_recycler_view);
        adapter = new RecipeWidgetAdapter(new ArrayList<Recipe>());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        new FetchRecipesTask().execute();

    }

    @SuppressWarnings("deprecation")
    private void updateWidget(Recipe recipe){
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

        RemoteViews remoteViews = new RemoteViews(this.getPackageName(), R.layout.baking_time_widget);
        remoteViews.setTextViewText(R.id.widget_header, recipe.getName() + " ingredients");

        Intent intent = new Intent(this, WidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

        String strRecipe = new GsonBuilder().create().toJson(recipe);

        intent.putExtra(WidgetDataProvider.RECIPE, strRecipe);
        remoteViews.setRemoteAdapter(appWidgetId, R.id.widget_list, intent);

        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);

        Intent resultValue = new Intent(this, WidgetService.class);
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

        setResult(RESULT_OK, resultValue);

        finish();
    }

    public class FetchRecipesTask extends AsyncTask<Void, Void, List<Recipe>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
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
            if (recipes != null) {
                adapter.setRecipesData(recipes);
            }
        }
    }

    class RecipeWidgetAdapter extends RecyclerView.Adapter<RecipeWidgetAdapter.RecipeViewHolder>{

        private List<Recipe> recipes;
        private Context context;

        public RecipeWidgetAdapter(List<Recipe> recipes){
            this.recipes = recipes;
        }

        class RecipeViewHolder extends RecyclerView.ViewHolder{
            public final ImageView recipeImage;
            public final TextView recipeName;

            public RecipeViewHolder(View view) {
                super(view);
                recipeImage = view.findViewById(R.id.recipe_image);
                recipeName = view.findViewById(R.id.recipe_name);
            }
        }

        @Override
        public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            context = parent.getContext();
            View view = LayoutInflater.from(context).inflate(R.layout.list_recipes_for_widgets, parent, false);
            RecipeViewHolder recipeViewHolder = new RecipeViewHolder(view);
            return recipeViewHolder ;
        }

        @Override
        public void onBindViewHolder(RecipeViewHolder holder, int position) {
            final Recipe recipe = recipes.get(position);

            Transformation transformation = new RoundedTransformationBuilder()
                    .borderColor(Color.BLACK)
                    .borderWidthDp(1)
                    .cornerRadiusDp(0)
                    .oval(false)
                    .build();

            if (recipe.getImage() == null || recipe.getImage().isEmpty()) {
                Picasso.with(context).load(R.drawable.placeholder).fit().transform(transformation).into(holder.recipeImage);
            } else {
                Picasso.with(context).load(recipe.getImage()).placeholder(R.drawable.placeholder).fit().transform(transformation).into(holder.recipeImage);
            }

            holder.recipeName.setText(recipe.getName());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateWidget(recipe);
                }
            });
        }

        @Override
        public int getItemCount() {
            if (recipes != null)
                return recipes.size();
            else return 0;
        }

        public void setRecipesData(List<Recipe> recipes) {
            this.recipes = recipes;
            notifyDataSetChanged();
        }
    }
}
