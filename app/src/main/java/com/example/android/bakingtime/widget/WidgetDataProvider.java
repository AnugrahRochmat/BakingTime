package com.example.android.bakingtime.widget;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingtime.R;
import com.example.android.bakingtime.model.Ingredient;
import com.example.android.bakingtime.model.Recipe;
import com.google.gson.GsonBuilder;

import java.util.List;

/**
 * Created by Anugrah on 8/23/17.
 */

public class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {

    public static final String RECIPE = "com.example.android.bakingtime.widget.WidgetDataProvider.RECIPE";

    private List<Ingredient> ingredients;
    //private List<String> collection = new ArrayList<>();

    private Context context;
    private Intent intent;

    public WidgetDataProvider(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
    }

    private void initData() {
        String strRecipe = intent.getStringExtra(RECIPE);
        Recipe recipe = new GsonBuilder().create().fromJson(strRecipe, Recipe.class);
        ingredients = recipe.getIngredients();
//        collection.clear();
//        for (int i=1; i <= 10; i++) {
//            collection.add("ListView Item " + i);
//        }
    }

    @Override
    public void onCreate() {
        initData();
    }

    @Override
    public void onDataSetChanged() {
        initData();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        //return collection.size();
        return ingredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Ingredient ingredient = ingredients.get(position);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), android.R.layout.simple_list_item_1);
        remoteViews.setTextViewText(android.R.id.text1, String.format(context.getString(R.string.ingredients_detail), ingredient.getQuantity(), ingredient.getMeasure(), ingredient.getIngredient()));
        remoteViews.setTextColor(android.R.id.text1, ContextCompat.getColor(context, R.color.scampi));
        return remoteViews;
//        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), android.R.layout.simple_list_item_1);
//        remoteViews.setTextViewText(android.R.id.text1, collection.get(position));
//        remoteViews.setTextColor(android.R.id.text1, Color.RED);
//        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
