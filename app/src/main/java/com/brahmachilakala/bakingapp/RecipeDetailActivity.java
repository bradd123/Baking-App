package com.brahmachilakala.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class RecipeDetailActivity extends AppCompatActivity implements RecipeDetailFragment.IntentSendListener {

    private boolean mTwoPane;
    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        if (findViewById(R.id.fragment_recipe_step) != null) {
            mTwoPane = true;

            Recipe recipe = (Recipe) getIntent().getSerializableExtra("recipe");
            RecipeDetailFragment recipeDetailFragment = RecipeDetailFragment.newInstance(recipe);

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.fragment_recipe_detail, recipeDetailFragment)
                    .commit();

            ArrayList<Step> steps = recipe.getSteps();
            RecipeStepFragment recipeStepFragment = RecipeStepFragment.newInstance(steps, position);
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_recipe_step, recipeStepFragment)
                    .commit();



        } else {
            mTwoPane = false;

            Recipe recipe = (Recipe) getIntent().getSerializableExtra("recipe");

            RecipeDetailFragment fragment = RecipeDetailFragment.newInstance(recipe);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_recipe_detail, fragment)
                    .commit();
        }

    }


    @Override
    public void startIntent(ArrayList<Step> steps, int position) {

        if (mTwoPane) {
            RecipeStepFragment recipeStepFragment = RecipeStepFragment.newInstance(steps, position);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_recipe_step, recipeStepFragment)
                    .commit();
        } else {
            Intent recipeStepActivity = new Intent(RecipeDetailActivity.this, RecipeStepActivity.class);
            recipeStepActivity.putExtra("steps", steps);
            recipeStepActivity.putExtra("position", position);
            startActivity(recipeStepActivity);
        }
    }
}
