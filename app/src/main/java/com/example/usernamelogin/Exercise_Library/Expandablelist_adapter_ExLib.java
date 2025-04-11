package com.example.usernamelogin.Exercise_Library;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.usernamelogin.R;

import java.util.HashMap;
import java.util.List;
public class Expandablelist_adapter_ExLib extends BaseExpandableListAdapter {
    private Context context;
    private List<String> categoryList;
    private HashMap<String, List<Exercise_selecting2ExLib>> exerciseMap;
    private HashMap<Integer, Boolean> selectedExercises = new HashMap<>();

    public Expandablelist_adapter_ExLib(Context context, List<String> categoryList, HashMap<String, List<Exercise_selecting2ExLib>> exerciseMap) {
        this.context = context;
        this.categoryList = categoryList;
        this.exerciseMap = exerciseMap;
        this.selectedExercises = selectedExercises;
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
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_group_wrkout_prgrm2_2, null);
        }
        TextView textView = convertView.findViewById(R.id.listHeader);
        textView.setText(categoryTitle);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Exercise_selecting2ExLib exercise = (Exercise_selecting2ExLib) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_item_wrkout_prgram2_2, null);
        }

        TextView exerciseName = convertView.findViewById(R.id.exerciseName);
        exerciseName.setText(exercise.getExerciseName());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}