package com.brahmachilakala.bakingapp;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeDetailFragment extends Fragment {
    private TextView tvRecipeIngredients;
    private RecyclerView rvSteps;
    private StepsAdapter mStepsAdapter;

    private GestureDetector mGestureDetector;

    private Recipe mRecipe;
    private IntentSendListener mListener;

    public interface IntentSendListener {
        public void startIntent(ArrayList<Step> steps, int position);
    }


    public RecipeDetailFragment() {
        // Required empty public constructor
    }

    public static RecipeDetailFragment newInstance(Recipe recipe) {
        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("recipe", recipe);
        recipeDetailFragment.setArguments(args);
        return recipeDetailFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof IntentSendListener) {
            mListener = (IntentSendListener) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement RecipeDetailFragment.IntentSendListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecipe = (Recipe) getArguments().getSerializable("recipe");

        tvRecipeIngredients = (TextView) view.findViewById(R.id.tv_recipe_ingredients);
        tvRecipeIngredients.setText(Ingredient.convertIngredientsToString(mRecipe.getIngredients()));

        rvSteps = (RecyclerView) view.findViewById(R.id.rvSteps);
        mStepsAdapter = new StepsAdapter(Step.getStepShortDescriptions(mRecipe.getSteps()));
        rvSteps.setAdapter(mStepsAdapter);

        rvSteps.setLayoutManager(new LinearLayoutManager(getActivity()));

        mGestureDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {
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

                    mListener.startIntent(mRecipe.getSteps(), position);

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
