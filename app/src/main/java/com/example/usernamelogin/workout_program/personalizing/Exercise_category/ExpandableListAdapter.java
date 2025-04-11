package com.example.usernamelogin.workout_program.personalizing.Exercise_category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.usernamelogin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> categoryList;
    private HashMap<String, List<Exercise_selecting>> exerciseMap;
    private HashMap<Integer, Boolean> selectedExercises = new HashMap<>();
    public ExpandableListAdapter(Context context, List<String> categoryList
            , HashMap<String, List<Exercise_selecting>> exerciseMap) {
        this.context = context;
        this.categoryList = categoryList;
        this.exerciseMap = exerciseMap;
    }
    @Override
    public int getGroupCount() {
        return categoryList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return exerciseMap.get(categoryList.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return categoryList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return exerciseMap.get(categoryList.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String categoryTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group_wrkt_prgrm, null);
        }
        TextView textView = convertView.findViewById(R.id.listHeader);
        textView.setText(categoryTitle);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Exercise_selecting exercise = (Exercise_selecting) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_wrkt_prgrm, null);
        }

        TextView exerciseName = convertView.findViewById(R.id.exerciseName);
        CheckBox checkBox = convertView.findViewById(R.id.checkbox);

        exerciseName.setText(exercise.getExerciseName());

        // Important: Remove any existing listener before setting checked state
        checkBox.setOnCheckedChangeListener(null);
        checkBox.setChecked(selectedExercises.containsKey(exercise.getExerciseId()));

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedExercises.put(exercise.getExerciseId(), true);
            } else {
                selectedExercises.remove(exercise.getExerciseId());
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public String getSelectedExercisesAsJson() {
        JSONArray jsonArray = new JSONArray();
        for (Map.Entry<Integer, Boolean> entry : selectedExercises.entrySet()) {
            if (entry.getValue()) {
                Exercise_selecting exercise = findExerciseById(entry.getKey());
                if (exercise != null) {
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("exerciseName", exercise.getExerciseName());
                        jsonObject.put("sets", null); // Default value
                        jsonObject.put("reps", null); // Default value
                        jsonObject.put("weight", null); // Default value
                        jsonObject.put("exerciseIntensity",exercise.getExerciseIntensity());
                        jsonObject.put("recognized_recom_weight",exercise.getRecognized_weight_recomm());
                        jsonObject.put("beginner_bw_indicator", exercise.getBeginnerBW());

                        jsonArray.put(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return jsonArray.toString();
    }

    private Exercise_selecting findExerciseById(int exerciseId) {
        for (List<Exercise_selecting> exerciseList : exerciseMap.values()) {
            for (Exercise_selecting exercise : exerciseList) {
                if (exercise.getExerciseId() == exerciseId) {
                    return exercise;
                }
            }
        }
        return null; // Return null if not found
    }

}
