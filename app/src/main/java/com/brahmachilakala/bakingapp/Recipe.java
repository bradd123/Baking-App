package com.brahmachilakala.bakingapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by brahma on 08/09/17.
 */

public class Recipe {

    private int mId;
    private String mName;
    private ArrayList<Ingredient> mIngredients;
    private ArrayList<Step> mSteps;
    private int mServings;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public ArrayList<Ingredient> getIngredients() {
        return mIngredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.mIngredients = ingredients;
    }

    public ArrayList<Step> getSteps() {
        return mSteps;
    }

    public void setSteps(ArrayList<Step> steps) {
        this.mSteps = steps;
    }

    public int getServings() {
        return mServings;
    }

    public void setServings(int servings) {
        this.mServings = servings;
    }

    public static Recipe fromRecipeJson(JSONObject jsonObject) {
        Recipe recipe = new Recipe();

        try {
            recipe.mId = jsonObject.getInt("id");
            recipe.mName = jsonObject.getString("name");
            recipe.mIngredients = Ingredient.fromIngredientJson(jsonObject.getJSONArray("ingredients"));
            recipe.mSteps = Step.fromStepJson(jsonObject.getJSONArray("steps"));
            recipe.mServings = jsonObject.getInt("servings");
        } catch (Exception e) {
            Log.i("Recipe", "Error in parsing Recipe" + e.getMessage());
        }

        return recipe;
    }

    public static ArrayList<Recipe> fromRecipeJson(JSONArray jsonArray) {
        ArrayList<Recipe> recipes = new ArrayList<>();

        for (int i = 0; i<jsonArray.length(); i++) {
            try {
                recipes.add(fromRecipeJson(jsonArray.getJSONObject(i)));
            } catch (Exception e) {
                Log.i("Recipes", "Error in parsing Recipes" + e.getMessage());
            }
        }

        return recipes;
    }
}
