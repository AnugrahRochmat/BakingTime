package com.example.android.bakingtime.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingtime.R;
import com.example.android.bakingtime.activity.StepDetailActivity;
import com.example.android.bakingtime.model.SelectedPosition;
import com.example.android.bakingtime.model.Step;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anugrah on 8/16/17.
 */


public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {

    public List<Step> steps;
    private Context context;

    private OnStepAdapterListener onStepAdapterListener;

    public interface OnStepAdapterListener{
        void onViewSelected(int position);
    }

    public class StepViewHolder extends RecyclerView.ViewHolder {
        public final ImageView stepImage;
        public final TextView shortDescription;

        public StepViewHolder(View view){
            super(view);
            stepImage = view.findViewById(R.id.step_image);
            shortDescription = view.findViewById(R.id.step_short_description);
        }
    }

    public StepAdapter(List<Step> steps, OnStepAdapterListener onStepAdapterListener) {
        this.steps = steps;
        this.onStepAdapterListener = onStepAdapterListener;
    }

    @Override
    public StepViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        context = viewGroup.getContext();
        int layoutInflaterStep = R.layout.list_steps;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutInflaterStep, viewGroup, false);
        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepViewHolder holder, final int position) {
        final Step step = steps.get(position);

        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.BLACK)
                .borderWidthDp(2)
                .cornerRadiusDp(20)
                .oval(false)
                .build();

        if (TextUtils.isEmpty(step.getThumbnailURL()) || !isImageExtension(step.getThumbnailURL())) {
            Picasso.with(context).load(R.drawable.placeholder).fit().transform(transformation).into(holder.stepImage);
        } else {
            Picasso.with(context).load(step.getThumbnailURL()).placeholder(R.drawable.placeholder).fit().transform(transformation).into(holder.stepImage);
        }

        holder.shortDescription.setText(step.getShortDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onStepAdapterListener.onViewSelected(position);
                if (context.getResources().getBoolean(R.bool.isTablet)) {
                    // tablet is true
                    EventBus.getDefault().post(new SelectedPosition(position));
                } else {
                    // tablet is false
                    Intent intentToStepDetailActivity = new Intent(context, StepDetailActivity.class);

                    ArrayList<Step> steps1 = new ArrayList<Step>();
                    steps1.addAll(steps);
                    intentToStepDetailActivity.putParcelableArrayListExtra("steps1", steps1);

                    context.startActivity(intentToStepDetailActivity);
                }
            }
        });
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