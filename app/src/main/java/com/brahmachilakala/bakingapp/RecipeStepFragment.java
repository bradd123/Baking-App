package com.brahmachilakala.bakingapp;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeStepFragment extends Fragment {
    private static final String TAG = RecipeStepActivity.class.getSimpleName();

    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mExoPlayerView;
    private TextView tvStepDescription;
    private static MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;
    private ArrayList<Step> mSteps;
    private int position;
    private Button mPrevBtn;
    private Button mNextBtn;


    public RecipeStepFragment() {
        // Required empty public constructor
    }

    public static RecipeStepFragment newInstance(ArrayList<Step> steps, int position) {
        RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
        Bundle args = new Bundle();
        args.putSerializable("steps", steps);
        args.putInt("position", position);
        recipeStepFragment.setArguments(args);
        return recipeStepFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_step, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mExoPlayerView = (SimpleExoPlayerView) view.findViewById(R.id.playerView);
        mSteps = (ArrayList<Step>) getArguments().getSerializable("steps");
        position = getArguments().getInt("position", 0);

        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory adaptiveTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(adaptiveTrackSelectionFactory);
        mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);
        mExoPlayerView.setPlayer(mExoPlayer);

        tvStepDescription = (TextView) view.findViewById(R.id.tv_step_description);

        mPrevBtn = (Button) view.findViewById(R.id.bt_prev);
        mNextBtn = (Button) view.findViewById(R.id.bt_next);

        mPrevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position--;
                playVideo(mSteps.get(position));
            }
        });

        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position++;
                playVideo(mSteps.get(position));
            }
        });

        playVideo(mSteps.get(position));
    }

    private void setupButtons() {
        if (position <= 0) {
            mPrevBtn.setEnabled(false);
        }
        if (position >= mSteps.size()) {
            mNextBtn.setEnabled(false);
        }
    }

    private void playVideo(Step step) {
        setupButtons();
        Uri videoUri = Uri.parse(step.getVideoUrl());
        tvStepDescription.setText(step.getDescription());

        DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter();
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getActivity(),
                Util.getUserAgent(getActivity(), "BakingApp"), defaultBandwidthMeter);
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        MediaSource videoSource = new ExtractorMediaSource(videoUri,
                dataSourceFactory, extractorsFactory, null, null);
        mExoPlayer.prepare(videoSource);
        mExoPlayer.setPlayWhenReady(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    private void releasePlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

}
