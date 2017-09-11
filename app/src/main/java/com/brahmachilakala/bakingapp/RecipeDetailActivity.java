package com.brahmachilakala.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class RecipeDetailActivity extends AppCompatActivity {
    private TextView tvRecipeIngredients;
    private RecyclerView rvSteps;
    private StepsAdapter mStepsAdapter;

    private GestureDetector mGestureDetector;

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

        mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        rvSteps.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View childView = rv.findChildViewUnder(e.getX(), e.getY());

                if (childView != null && mGestureDetector.onTouchEvent(e)) {
                    int position = rv.getChildAdapterPosition(childView);

                    Intent recipeStepActivity = new Intent(RecipeDetailActivity.this, RecipeStepActivity.class);
                    recipeStepActivity.putExtra("steps", mRecipe.getSteps());
                    recipeStepActivity.putExtra("position", position);
                    startActivity(recipeStepActivity);

                    return true;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

    }


}
