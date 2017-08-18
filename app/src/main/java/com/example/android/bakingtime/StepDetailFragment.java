package com.example.android.bakingtime;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingtime.model.Step;
import com.squareup.picasso.Picasso;

/**
 * Created by Anugrah on 8/16/17.
 */

public class StepDetailFragment extends android.support.v4.app.Fragment {

    private Step step;
    private ImageView stepThumbnailImage;
    private TextView stepDescription;

    private String thumbImage;
    private String description;

    public static StepDetailFragment newInstance(String thumbImage, String description){
        StepDetailFragment f = new StepDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("thumb_image", thumbImage);
        bundle.putString("description", description);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey("thumb_image") && getArguments().containsKey("description")) {
            Bundle bundle = getArguments();
            thumbImage = bundle.getString("thumb_image");
            description = bundle.getString("description");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);
        stepThumbnailImage = rootView.findViewById(R.id.step_thumbnail_image);
        stepDescription = rootView.findViewById(R.id.step_description);

        Picasso.with(getActivity()).load(R.drawable.placeholder).into(stepThumbnailImage);
        stepDescription.setText(description);

        return rootView;
    }

}