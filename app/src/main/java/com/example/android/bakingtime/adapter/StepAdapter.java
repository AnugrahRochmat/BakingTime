package com.example.android.bakingtime.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingtime.R;
import com.example.android.bakingtime.StepDetailActivity;
import com.example.android.bakingtime.model.Step;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

/**
 * Created by Anugrah on 8/16/17.
 */


public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {

    public List<Step> steps;
    private Context context;

    public class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView stepImage;
        public final TextView shortDescription;

        public StepViewHolder(View view){
            super(view);
            stepImage = view.findViewById(R.id.step_image);
            shortDescription = view.findViewById(R.id.step_short_description);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Step step = steps.get(getAdapterPosition());

            Intent intentToStepDetailActivity = new Intent(view.getContext(), StepDetailActivity.class);
            intentToStepDetailActivity.putExtra("step", step);

            view.getContext().startActivity(intentToStepDetailActivity);
        }
    }

    public StepAdapter(List<Step> steps, Context context) {
        this.steps = steps;
        this.context = context;
    }

    @Override
    public StepViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutInflaterStep = R.layout.list_steps;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutInflaterStep, viewGroup, false);
        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepViewHolder holder, int position) {
        Step step = steps.get(position);

        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.BLACK)
                .borderWidthDp(2)
                .cornerRadiusDp(20)
                .oval(false)
                .build();

        if (step.getThumbnailURL() == null || step.getThumbnailURL().isEmpty() || !isImageExtension(step.getThumbnailURL())) {
            Picasso.with(context).load(R.drawable.placeholder).fit().transform(transformation).into(holder.stepImage);
        } else {
            Picasso.with(context).load(step.getThumbnailURL()).placeholder(R.drawable.placeholder).fit().transform(transformation).into(holder.stepImage);
        }
        holder.shortDescription.setText(step.getShortDescription());
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    public void setStepsData(List<Step> steps) {
        this.steps = steps;
        notifyDataSetChanged();
    }

    public List<Step> getSteps() {
        return steps;
    }

    public boolean isImageExtension(String steThumbUrl){
        if (steThumbUrl.endsWith(".jpg") || steThumbUrl.endsWith(".png")) {
            return true;
        }
        return false;
    }

}