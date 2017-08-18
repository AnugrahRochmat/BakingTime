package com.example.android.bakingtime;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingtime.model.Step;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

/**
 * Created by Anugrah on 8/16/17.
 */

public class StepDetailFragment extends Fragment {

    private Step step;
    private ImageView stepThumbnailImage;
    private TextView stepDescription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);
        stepThumbnailImage = rootView.findViewById(R.id.step_thumbnail_image);
        stepDescription = rootView.findViewById(R.id.step_description);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.BLACK)
                .borderWidthDp(2)
                .cornerRadiusDp(20)
                .oval(false)
                .build();

        if (step.getThumbnailURL() == null || step.getThumbnailURL().isEmpty()) {
            Picasso.with(getActivity()).load(R.drawable.placeholder).fit().transform(transformation).into(stepThumbnailImage);
        } else {
            Picasso.with(getActivity()).load(step.getThumbnailURL()).placeholder(R.drawable.placeholder).fit().transform(transformation).into(stepThumbnailImage);
        }
        // TODO Change thumbnailImage to media player
        // TODO Add Navigation between steps using view pager

        stepDescription.setText(step.getDescription());
        stepDescription.setMovementMethod(new ScrollingMovementMethod());

    }

    public void setStep(Step step) {
        this.step = step;
    }

}