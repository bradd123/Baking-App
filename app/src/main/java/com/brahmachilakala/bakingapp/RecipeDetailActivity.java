package com.brahmachilakala.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

public class RecipeDetailActivity extends AppCompatActivity {
    private TextView tvRecipeIngredients;
    private RecyclerView rvSteps;
    private StepsAdapter mStepsAdapter;

    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        mRecipe = (Recipe) getIntent().getSerializableExtra("recipe");

        tvRecipeIngredients = (TextView) findViewById(R.id.tv_recipe_ingredients);
        tvRecipeIngredients.setText(Ingredient.convertIngredientsToString(mRecipe.getIngredients()));

        rvSteps = (RecyclerView) findViewById(R.id.rvSteps);
        mStepsAdapter = new StepsAdapter(Step.getStepShortDescriptions(mRecipe.getSteps()));
        rvSteps.setAdapter(mStepsAdapter);

        rvSteps.setLayoutManager(new LinearLayoutManager(this));

    }


}
