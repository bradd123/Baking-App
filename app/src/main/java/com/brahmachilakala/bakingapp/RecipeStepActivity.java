package com.brahmachilakala.bakingapp;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class RecipeStepActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step);

        ArrayList<Step> steps = (ArrayList<Step>) getIntent().getSerializableExtra("steps");
        int position = getIntent().getIntExtra("position", 0);

        RecipeStepFragment fragment = RecipeStepFragment.newInstance(steps, position);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_recipe_step, fragment)
                .commit();
    }
}
