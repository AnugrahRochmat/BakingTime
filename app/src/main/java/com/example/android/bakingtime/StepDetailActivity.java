package com.example.android.bakingtime;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.android.bakingtime.model.Step;

/**
 * Created by Anugrah on 8/16/17.
 */

public class StepDetailActivity extends AppCompatActivity {

    private Step step;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_of_step);

        StepDetailFragment stepDetailFragment = new StepDetailFragment();
        getFragmentManager().beginTransaction().add(R.id.detail_step_container, stepDetailFragment).commit();

        Bundle data = getIntent().getExtras();
        if ( data != null) {
            step = data.getParcelable("step");
            stepDetailFragment.setStep(step);
        } else {
            finish();
        }
        if (!getResources().getBoolean(R.bool.isTablet)){
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            String recipeName = prefs.getString("recipe_name", null);
            setTitle(recipeName);
        }
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