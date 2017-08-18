package com.example.android.bakingtime;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.android.bakingtime.adapter.StepDetailPagerAdapter;
import com.example.android.bakingtime.model.Step;

import java.util.List;

/**
 * Created by Anugrah on 8/16/17.
 */

public class StepDetailActivity extends AppCompatActivity {

    private List<Step> steps;

    private ViewPager viewPager;
    private StepDetailPagerAdapter stepDetailPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_of_step);
        viewPager = (ViewPager)findViewById(R.id.view_pager);

        Bundle data = getIntent().getExtras();
        if ( data != null) {
            steps = data.getParcelableArrayList("steps1");
        } else {
            finish();
        }
        if (!getResources().getBoolean(R.bool.isTablet)){
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            String recipeName = prefs.getString("recipe_name", null);
            setTitle(recipeName);
        }

        // view pager adapter
        stepDetailPagerAdapter = new StepDetailPagerAdapter(getSupportFragmentManager(), this, steps);
        viewPager.setAdapter(stepDetailPagerAdapter);
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