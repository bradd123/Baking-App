package com.brahmachilakala.bakingapp;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    private RecyclerView rvRecipes;
    private RecipesAdapter mRecipesAdapter;
    ArrayList<Recipe> mRecipes;
    private GestureDetector mGestureDetector;

    private SendIntentListener mListener;


    public MainFragment() {
        // Required empty public constructor
    }

    public interface SendIntentListener {
        public void startIntent(Recipe recipe);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof SendIntentListener) {
            mListener = (SendIntentListener) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement MainFragment.SendIntentListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvRecipes = (RecyclerView) view.findViewById(R.id.rvRecipes);
        new RecipeTask().execute("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json");
    }

    public void loadRecyclerView() {
        mRecipesAdapter = new RecipesAdapter(mRecipes);

        rvRecipes.setAdapter(mRecipesAdapter);
        rvRecipes.setLayoutManager(new LinearLayoutManager(getActivity()));

        mGestureDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        rvRecipes.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View childView = rv.findChildViewUnder(e.getX(), e.getY());

                if (childView != null && mGestureDetector.onTouchEvent(e)) {
                    int position = rv.getChildAdapterPosition(childView);

                    mListener.startIntent(mRecipes.get(position));

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
