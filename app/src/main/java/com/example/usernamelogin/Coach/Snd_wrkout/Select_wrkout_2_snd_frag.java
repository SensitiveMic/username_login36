package com.example.usernamelogin.Coach.Snd_wrkout;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.example.usernamelogin.Coach.Snd_wrkout.Adjust_reps.Adjust_reps_set_weight_frag;
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
 * Use the {@link Select_wrkout_2_snd_frag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Select_wrkout_2_snd_frag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Select_wrkout_2_snd_frag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Select_wrkout_2_snd_frag.
     */
    // TODO: Rename and change types and number of parameters
    public static Select_wrkout_2_snd_frag newInstance(String param1, String param2) {
        Select_wrkout_2_snd_frag fragment = new Select_wrkout_2_snd_frag();
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
    private ExpandableListAdapter_2 adapter;
    private List<String> categoryList;
    private HashMap<String, List<Exercise_selecting_helper>> exerciseMap;
    Button trialdone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_select_wrkout_2_snd, container, false);

        expandableListView = view.findViewById(R.id.expandableListView);

        categoryList = new ArrayList<>();
        exerciseMap = new HashMap<>();
        loadExercisesFromJson();

        adapter = new ExpandableListAdapter_2(getContext(), categoryList, exerciseMap);
        expandableListView.setAdapter(adapter);

        trialdone = view.findViewById(R.id.donetrialbuttonwew);
        trialdone.setOnClickListener(v -> thebutton());

        return view;
    }
    private void thebutton() {

        String selectedExercisesJson = adapter.getSelectedExercisesAsJson();
        Log.d("TAGSelectedExercises", selectedExercisesJson);

        // Create the new fragment and pass the data
        Adjust_reps_set_weight_frag displayFragment = new Adjust_reps_set_weight_frag();
        Bundle bundle_2 = new Bundle();
        bundle_2.putString("selected_exercises", selectedExercisesJson);
        displayFragment.setArguments(bundle_2);

        // Navigate to the next fragment
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.framelayout3, displayFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }
    private void loadExercisesFromJson() {
        try {

            Exercise_selecting_helper exercise = null;

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


                exercise = new Exercise_selecting_helper(id, name, intensity, muscleGroup, description);



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