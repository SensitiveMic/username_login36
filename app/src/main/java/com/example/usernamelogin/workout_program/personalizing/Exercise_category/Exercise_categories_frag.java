package com.example.usernamelogin.workout_program.personalizing.Exercise_category;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.usernamelogin.workout_program.personalizing.Exercise_category.selected_exercises.Selected_Exercise_frag;
import com.example.usernamelogin.workout_program.personalizing.Personalizing_main;
import com.example.usernamelogin.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Exercise_categories_frag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Exercise_categories_frag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Exercise_categories_frag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_third.
     */
    // TODO: Rename and change types and number of parameters
    public static Exercise_categories_frag newInstance(String param1, String param2) {
        Exercise_categories_frag fragment = new Exercise_categories_frag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    ExpandableListView expandableListView;
    private ExpandableListAdapter adapter;
    private List<String> categoryList;
    private HashMap<String, List<Exercise_selecting>> exerciseMap;
    Button trialdone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.exercise_categories_frag_wrkt_prgrm, container, false);
        expandableListView = view.findViewById(R.id.expandableListView);

        categoryList = new ArrayList<>();
        exerciseMap = new HashMap<>();

        loadExercisesFromJson();
        adapter = new ExpandableListAdapter(getContext(), categoryList, exerciseMap);
        expandableListView.setAdapter(adapter);

        trialdone = view.findViewById(R.id.donetrialbuttonwew);
        trialdone.setOnClickListener(v -> thebutton());
        return view;
    }
    private void thebutton() {

        String selectedExercisesJson = adapter.getSelectedExercisesAsJson();
        Log.d("TAGSelectedExercises", selectedExercisesJson);

        // Create the new fragment and pass the data
        Selected_Exercise_frag displayFragment = new Selected_Exercise_frag();
        Bundle bundle = new Bundle();
        bundle.putString("selected_exercises", selectedExercisesJson);
        displayFragment.setArguments(bundle);

        // Navigate to the next fragment
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_1_id, displayFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    private void loadExercisesFromJson() {
        try {

            Exercise_selecting exercise = null;

            InputStream inputStream = requireContext().getAssets().open("exercises.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer, "UTF-8");

            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                int id = obj.getInt("exercise_id");
                String name = obj.getString("exercise_name");
                String intensity = obj.getString("exercise_intensity");
                String muscleGroup = obj.getString("muscle_location");
                String description = obj.getString("exercise_descrp");

                JSONArray weightRecommendations = obj.getJSONArray("weight_recommendations");
                Double recognized_weight_recomm = 0.0;
                boolean beginnerString = false;

                JSONObject recommendation = weightRecommendations.getJSONObject(0);
                String fitnessKey = Personalizing_main.Fitness_value < 4 ? "beginner" :
                            Personalizing_main.Fitness_value == 4 ? "intermediate" : "advanced";

                    Object beginnerValue = recommendation.get(fitnessKey);

                        if (beginnerValue instanceof String) {
                            beginnerString = true;
                            String beginner_String = recommendation.getString(fitnessKey);
                            Log.d("TAGSelectedExercises","BW usage?: " + beginner_String);

                        } else {
                            recognized_weight_recomm = recommendation.getDouble(fitnessKey);
                            Log.d("TAGSelectedExercises","is it in decimal?: " + recognized_weight_recomm);
                        }


                exercise = new Exercise_selecting(id, name, intensity
                        , muscleGroup, description, recognized_weight_recomm, beginnerString);



                if (!exerciseMap.containsKey(muscleGroup)) {
                    exerciseMap.put(muscleGroup, new ArrayList<>());
                    categoryList.add(muscleGroup);
                }
                exerciseMap.get(muscleGroup).add(exercise);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}