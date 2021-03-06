package com.example.android.bakingtime.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.android.bakingtime.fragment.StepDetailFragment;
import com.example.android.bakingtime.model.Step;

import java.util.List;

/**
 * Created by Anugrah on 8/18/17.
 */

public class StepDetailPagerAdapter extends FragmentPagerAdapter{

    Context context;
    List<Step> steps;

    public StepDetailPagerAdapter(FragmentManager fragmentManager, Context context, List<Step> steps) {
        super(fragmentManager);
        this.context = context;
        this.steps = steps;
    }

    @Override
    public Fragment getItem(int position) {
        Step step = steps.get(position);
        String videoURL = step.getVideoURL(); // Video Url
        String description = step.getDescription();
        return StepDetailFragment.newInstance(videoURL, description);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Step step = steps.get(position);
        return step.getShortDescription();
    }

    @Override
    public int getCount() {
        return steps.size();
    }

    public void setSteps(List<Step> steps){
        this.steps = steps;
        notifyDataSetChanged();
    }

    public List<Step> getSteps(){
        return steps;
    }
}
