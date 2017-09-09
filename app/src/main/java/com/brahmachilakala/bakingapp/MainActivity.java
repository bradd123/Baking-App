package com.brahmachilakala.bakingapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvRecipes;
    private RecipesAdapter mRecipesAdapter;
    ArrayList<Recipe> mRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new RecipeTask().execute("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json");

    }

    public void loadRecyclerView() {
        rvRecipes = (RecyclerView) findViewById(R.id.rvRecipes);
        mRecipesAdapter = new RecipesAdapter(mRecipes);

        rvRecipes.setAdapter(mRecipesAdapter);
        rvRecipes.setLayoutManager(new LinearLayoutManager(this));
    }

    private class RecipeTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                InputStream in = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                StringBuilder stringBuilder = new StringBuilder();

                String line = "";
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }

                return stringBuilder.toString();


            } catch (Exception e) {
                Log.i("MainActivity", "Error in fetching contents : " + e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONArray jsonArray = new JSONArray(s);
                mRecipes = Recipe.fromRecipeJson(jsonArray);
                loadRecyclerView();
            } catch (Exception e) {
                Log.i("MainActivity", "Error in parsing JSON in Recipe Task" + e.getMessage());
            }
        }
    }
}
