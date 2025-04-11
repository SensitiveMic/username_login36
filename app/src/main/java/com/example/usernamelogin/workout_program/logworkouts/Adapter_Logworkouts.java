package com.example.usernamelogin.workout_program.logworkouts;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usernamelogin.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Adapter_Logworkouts extends RecyclerView.Adapter<Adapter_Logworkouts.MyViewHolder> {

    private List<Exercise> exercises;
    private Map<Integer, Map<Integer, EditText>> editTextMap = new HashMap<>();
    private Map<Integer, Map<Integer, String>> textViewMap = new HashMap<>();

    public Adapter_Logworkouts(List<Exercise> exercises) {
        this.exercises = exercises;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_listofthwworkouts_wrkt_prgrm, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        int secondpos = position;

        Exercise exercise = exercises.get(position);
        holder.tvExerciseName.setText(exercise.getExerciseName());
        holder.containerLayout.removeAllViews();

        Map<Integer, EditText> setEditTexts = new HashMap<>();
        editTextMap.put(position, setEditTexts);

        // Map to store KGTEXT values
        Map<Integer, String> setTextViewValues = new HashMap<>();
        textViewMap.put(position, setTextViewValues);

        for (int i = 0; i < exercise.getSets().size(); i++) {
            Set set = (Set) exercise.getSets().get(i);

            // Dynamically create a layout for each set
            LinearLayout setLayout = new LinearLayout(holder.itemView.getContext());
            setLayout.setOrientation(LinearLayout.HORIZONTAL);
            setLayout.setBackgroundResource(R.drawable.border_wrkt_prgrm);
            setLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            setLayout.setPadding(10, 10, 10, 10);

            // Add "Set" TextView
            TextView setNumberView = new TextView(holder.itemView.getContext());
            setNumberView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            setNumberView.setText("Set " + (i + 1));
            setNumberView.setTextSize(16);
            setNumberView.setTextColor(Color.BLACK);
            setNumberView.setTypeface(null, Typeface.BOLD);

            // Add "Weight" TextView
            TextView KGTEXT = new TextView(holder.itemView.getContext());
            KGTEXT.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            KGTEXT.setText("KG:");
            KGTEXT.setTextColor(Color.BLACK);
            KGTEXT.setTypeface(null, Typeface.BOLD);


            // Add "Weight" TextView
            TextView weightView = new TextView(holder.itemView.getContext());
            weightView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            weightView.setText(set.getWeight());
            weightView.setTextColor(Color.BLACK);
            // Store "Weight" TextView value
            setTextViewValues.put(i, set.getWeight());

            TextView REPSS = new TextView(holder.itemView.getContext());
            REPSS.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            REPSS.setText("REPS:");
            REPSS.setTextColor(Color.BLACK);
            KGTEXT.setTypeface(null, Typeface.BOLD);

            // Add "Reps" EditText
            EditText repsEditText = new EditText(holder.itemView.getContext());
            repsEditText.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            repsEditText.setHint(set.getReps());
            repsEditText.setText(set.getReps());
            repsEditText.setGravity(Gravity.CENTER);
            repsEditText.setTextColor(Color.BLACK);
            repsEditText.setBackgroundResource(R.drawable.border_wrkt_prgrm);
            repsEditText.setInputType(InputType.TYPE_CLASS_NUMBER);

            // Add to the layout
            setEditTexts.put(i, repsEditText);

            setLayout.addView(setNumberView);
            setLayout.addView(KGTEXT);
            setLayout.addView(weightView);
            setLayout.addView(REPSS);
            setLayout.addView(repsEditText);

            holder.containerLayout.addView(setLayout);
        }
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public Map<Integer, Map<Integer, String>> getTextViewValues() {
        return textViewMap;
    }
    public Map<Integer, Map<Integer, String>> getEditTextValues() {
            Map<Integer, Map<Integer, String>> values = new HashMap<>();
            for (Map.Entry<Integer, Map<Integer, EditText>> entry : editTextMap.entrySet()) {
            int exerciseIndex = entry.getKey();
            Map<Integer, String> setValues = new HashMap<>();
            for (Map.Entry<Integer, EditText> setEntry : entry.getValue().entrySet()) {
                int setIndex = setEntry.getKey();
                EditText editText = setEntry.getValue();
                setValues.put(setIndex, editText.getText().toString());
            }
            values.put(exerciseIndex, setValues);
        }
        return values;
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvExerciseName;
        LinearLayout containerLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
                    tvExerciseName = itemView.findViewById(R.id.exercise_name);
            containerLayout = itemView.findViewById(R.id.container_layout);
        }
    }
}
