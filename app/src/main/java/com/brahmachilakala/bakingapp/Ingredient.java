package com.brahmachilakala.bakingapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by brahma on 08/09/17.
 */

public class Ingredient {
    private int mQuantity;
    private String mMeasure;
    private String mIngredient;

    public int getQuantity() {
        return mQuantity;
    }

    public void setQuantity(int quantity) {
        this.mQuantity = quantity;
    }

    public String getMeasure() {
        return mMeasure;
    }

    public void setMeasure(String measure) {
        this.mMeasure = measure;
    }

    public String getIngredient() {
        return mIngredient;
    }

    public void setIngredient(String ingredient) {
        this.mIngredient = ingredient;
    }

    public static Ingredient fromIngredientJson(JSONObject jsonObject) {
        Ingredient ingredient = new Ingredient();

        try {
            ingredient.mQuantity = jsonObject.getInt("quantity");
            ingredient.mMeasure = jsonObject.getString("measure");
            ingredient.mIngredient = jsonObject.getString("ingredient");

        } catch (Exception e) {
            Log.i("Ingredient", "Error in parsing JSON in Ingredient" + e.getMessage());
        }

        return ingredient;
    }

    public static ArrayList<Ingredient> fromIngredientJson(JSONArray jsonArray) {
        ArrayList<Ingredient> ingredients = new ArrayList<>();

        for(int i = 0; i<jsonArray.length(); i++) {

            try {
                ingredients.add(fromIngredientJson(jsonArray.getJSONObject(i)));
            } catch (Exception e) {
                Log.i("Ingredient", "Error in gettting JSON object" + e.getMessage());
            }
        }
        return ingredients;
    }
}
