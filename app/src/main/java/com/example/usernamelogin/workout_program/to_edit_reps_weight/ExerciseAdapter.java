package com.example.usernamelogin.workout_program.to_edit_reps_weight;

import android.content.Context;
import android.text.InputType;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usernamelogin.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {
        private List<Modelclass_forexercises> exerciseList;
        private Context context;
    private Map<Integer, List<EditText>> weightEditTextsMap = new HashMap<>();
    private Map<Integer, List<EditText>> repsEditTextsMap = new HashMap<>();

        public ExerciseAdapter(List<Modelclass_forexercises> exerciseList,Context context) {
            this.exerciseList = exerciseList;
            this.context = context;
        }

        @NonNull
        @Override
        public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_list_chosenitem_wrkt_prgrm, parent, false);
            return new ExerciseViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
            holder.textView.setText(exerciseList.get(position).getExercise_name());
            List<Integer> setNumbers = new ArrayList<>();
            setNumbers.add(1); // Start with one set
            List<EditText> weightEditTexts = new ArrayList<>();
            List<EditText> repsEditTexts = new ArrayList<>();

            weightEditTextsMap.put(position, weightEditTexts);
            repsEditTextsMap.put(position, repsEditTexts);

            holder.addSetButton.setOnClickListener(v -> {
                int newSetNumber = setNumbers.size() ;
                setNumbers.add(newSetNumber);

                // Create new LinearLayout for the set
                LinearLayout newSetLayout = createNewSetLayout(
                        holder.itemView.getContext(),
                        newSetNumber,
                        weightEditTexts,
                        repsEditTexts
                );

                // Add the dynamically created layout to the container
                holder.containerLayout.addView(newSetLayout);
            });
        }
    public Map<Integer, Pair<List<String>, List<String>>> getAllData() {
        Map<Integer, Pair<List<String>, List<String>>> allData = new HashMap<>();

        for (int position : weightEditTextsMap.keySet()) {
            List<String> weightValues = new ArrayList<>();
            List<String> repsValues = new ArrayList<>();

            for (EditText weightEditText : weightEditTextsMap.get(position)) {
                weightValues.add(weightEditText.getText().toString().trim());
            }
            for (EditText repsEditText : repsEditTextsMap.get(position)) {
                repsValues.add(repsEditText.getText().toString().trim());
            }

            allData.put(position, new Pair<>(weightValues, repsValues));
        }

        return allData;
    }
        @Override
        public int getItemCount() {
            return exerciseList.size();
        }
    private LinearLayout createNewSetLayout(
            Context context,
            int setNumber,
            List<EditText> weightEditTexts,
            List<EditText> repsEditTexts
    ) {
        // Parent LinearLayout for the new set
        LinearLayout newSetLayout = new LinearLayout(context);
        newSetLayout.setOrientation(LinearLayout.HORIZONTAL);
        newSetLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        newSetLayout.setPadding(10, 10, 10, 10);

        // TextView for set number
        TextView setNumberView = new TextView(context);
        setNumberView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        setNumberView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        setNumberView.setText(String.valueOf(setNumber)); // Set the incremented set number
        setNumberView.setTextColor(ContextCompat.getColor(context, R.color.black));
        setNumberView.setTextSize(16);

        // EditText for weight input
        EditText weightEditText = new EditText(context);
        weightEditText.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        weightEditText.setPadding(12, 12, 12, 12);
        weightEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        weightEditText.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        weightEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        weightEditTexts.add(weightEditText); // Add to the list

        // TextView for "KG"
        TextView kgTextView = new TextView(context);
        kgTextView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        kgTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        kgTextView.setText("KG");
        kgTextView.setTextColor(ContextCompat.getColor(context, R.color.black));
        kgTextView.setTextSize(16);

        // EditText for reps input
        EditText repsEditText = new EditText(context);
        repsEditText.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        repsEditText.setPadding(12, 12, 12, 12);
        repsEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        repsEditText.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        repsEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        repsEditTexts.add(repsEditText); // Add to the list

        // TextView for "REPS"
        TextView repsTextView = new TextView(context);
        repsTextView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        repsTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        repsTextView.setText("REPS");
        repsTextView.setTextColor(ContextCompat.getColor(context, R.color.black));
        repsTextView.setTextSize(16);

        // Add all views to the new LinearLayout
        newSetLayout.addView(setNumberView);
        newSetLayout.addView(weightEditText);
        newSetLayout.addView(kgTextView);
        newSetLayout.addView(repsEditText);
        newSetLayout.addView(repsTextView);

        return newSetLayout;
    }

       public static class ExerciseViewHolder extends RecyclerView.ViewHolder {
            TextView textView;
           LinearLayout containerLayout;
           Button addSetButton;

            ExerciseViewHolder(View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.exercise_name);
                containerLayout = itemView.findViewById(R.id.container_layout);
                addSetButton = itemView.findViewById(R.id.button_addset);
            }
        }
    }

