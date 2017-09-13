package com.brahmachilakala.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements MainFragment.SendIntentListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_main, new MainFragment())
                .commit();

    }

    @Override
    public void startIntent(Recipe recipe) {
        Intent recipeDetailActivity = new Intent(MainActivity.this, RecipeDetailActivity.class);
        recipeDetailActivity.putExtra("recipe", recipe);
        startActivity(recipeDetailActivity);
    }
}
