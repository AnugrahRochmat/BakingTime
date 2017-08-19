package com.example.android.bakingtime;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

/**
 * Created by Anugrah on 8/16/17.
 */

public class StepDetailFragment extends Fragment{

    private TextView stepDescription;

    private SimpleExoPlayer simpleExoPlayer;
    private SimpleExoPlayerView simpleExoPlayerView;
    private boolean playWhenReady = true;
    private int currentWindow;
    private long playBackPosition;

    private String videoUrl;
    private String description;

    public static StepDetailFragment newInstance(String videoUrl, String description){
        StepDetailFragment f = new StepDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("video_url", videoUrl);
        bundle.putString("description", description);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey("video_url") && getArguments().containsKey("description")) {
            Bundle bundle = getArguments();
            videoUrl = bundle.getString("video_url");
            description = bundle.getString("description");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);

        simpleExoPlayerView = rootView.findViewById(R.id.step_detail_video);

        stepDescription = rootView.findViewById(R.id.step_description);
        stepDescription.setText(description);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23){
            iniPlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 || simpleExoPlayer == null){
            iniPlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23){
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23){
            releasePlayer();
        }
    }

    private void iniPlayer(){
        if (videoUrl == null) {
            return;
        } else {
            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getActivity()), new DefaultTrackSelector(), new DefaultLoadControl());

            simpleExoPlayerView.setPlayer(simpleExoPlayer);

            simpleExoPlayer.setPlayWhenReady(playWhenReady);
            simpleExoPlayer.seekTo(currentWindow, playBackPosition);

            Uri uri = Uri.parse(videoUrl);
            MediaSource mediaSource = buildMediaSource(uri);
            simpleExoPlayer.prepare(mediaSource);
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource(uri, new DefaultHttpDataSourceFactory("ua"), new DefaultExtractorsFactory(), null, null);
    }

    private void releasePlayer(){
        if (simpleExoPlayer != null){
            playBackPosition = simpleExoPlayer.getCurrentPosition();
            currentWindow = simpleExoPlayer.getCurrentWindowIndex();
            playWhenReady = simpleExoPlayer.getPlayWhenReady();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }

}