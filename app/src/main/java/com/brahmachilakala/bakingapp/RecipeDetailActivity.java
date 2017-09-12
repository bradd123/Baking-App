package com.brahmachilakala.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class RecipeDetailActivity extends AppCompatActivity implements RecipeDetailFragment.IntentSendListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Recipe recipe = (Recipe) getIntent().getSerializableExtra("recipe");

        RecipeDetailFragment fragment = RecipeDetailFragment.newInstance(recipe);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_recipe_detail, fragment)
                .commit();

    }


    @Override
    public void startIntent(ArrayList<Step> steps, int position) {
        Intent recipeStepActivity = new Intent(RecipeDetailActivity.this, RecipeStepActivity.class);
        recipeStepActivity.putExtra("steps", steps);
        recipeStepActivity.putExtra("position", position);
        startActivity(recipeStepActivity);
    }
}
