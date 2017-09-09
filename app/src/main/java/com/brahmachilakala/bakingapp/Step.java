package com.brahmachilakala.bakingapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by brahma on 08/09/17.
 */

public class Step {
    private int mId;
    private String mShortDescription;
    private String mDescription;
    private String mVideoUrl;
    private String mThumbnailUrl;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getShortDescription() {
        return mShortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.mShortDescription = shortDescription;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public String getVideoUrl() {
        return mVideoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.mVideoUrl = videoUrl;
    }

    public String getThumbnailUrl() {
        return mThumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.mThumbnailUrl = thumbnailUrl;
    }

    public static Step fromStepJson(JSONObject jsonObject) {
        Step step = new Step();

        try {
            step.mId = jsonObject.getInt("id");
            step.mShortDescription = jsonObject.getString("shortDescription");
            step.mDescription = jsonObject.getString("description");
            step.mVideoUrl = jsonObject.getString("videoURL");
            step.mThumbnailUrl = jsonObject.getString("thumbnailURL");
        } catch (Exception e) {
            Log.i("Step", "Error in parsing Step" + e.getMessage());
        }

        return step;
    }

    public static ArrayList<Step> fromStepJson(JSONArray jsonArray) {
        ArrayList<Step> steps = new ArrayList<>();

        for (int i = 0; i<jsonArray.length(); i++) {
            try {
                steps.add(fromStepJson(jsonArray.getJSONObject(i)));
            } catch (Exception e) {
                Log.i("Step", "Error in parsing steps" + e.getMessage());
            }
        }

        return steps;
    }
}
