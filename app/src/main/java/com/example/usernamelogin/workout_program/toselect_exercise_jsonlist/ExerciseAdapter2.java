package com.example.usernamelogin.workout_program.toselect_exercise_jsonlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usernamelogin.workout_program.to_edit_reps_weight.Modelclass_forexercises;
import com.example.usernamelogin.R;

import java.util.ArrayList;
import java.util.List;

public class ExerciseAdapter2 extends RecyclerView.Adapter<ExerciseAdapter2.ExerciseViewHolder> {
private List<Modelclass_forexercises> exerciseList;
private Context context;

public ExerciseAdapter2(Context context, List<Modelclass_forexercises> exerciseList) {
    this.context = context;
    this.exerciseList = exerciseList;
}

@NonNull
@Override
public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.item_exercise_wrkt_prgrm, parent, false);
    return new ExerciseViewHolder(view);
}

@Override
public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
    Modelclass_forexercises exercise = exerciseList.get(position);
    holder.exerciseName.setText(exercise.getExercise_name());

    // Show selection state   basically checks if the isSelected is true or otherwise
    holder.checkMark.setVisibility(exercise.isSelected() ? View.VISIBLE : View.INVISIBLE);

    // Handle item clicks   basically when an itemview is clicked it makes the current
    // boolean value of the isSelected() the opposite of it .
    holder.itemView.setOnClickListener(v -> {
        exercise.setSelected(!exercise.isSelected());
        notifyItemChanged(position); // Update the item view
    });
}

@Override
public int getItemCount() {
    return exerciseList.size();
}

// the method below basically adds the isSelected() which are true to the "selected" list.
public List<Modelclass_forexercises> getSelectedItems() {
    List<Modelclass_forexercises> selected = new ArrayList<>();
    for (Modelclass_forexercises exercise : exerciseList) {
        if (exercise.isSelected()) {
            selected.add(exercise);
        }
    }
    return selected;
}

static class ExerciseViewHolder extends RecyclerView.ViewHolder {
    TextView exerciseName;
    ImageView checkMark;

    public ExerciseViewHolder(@NonNull View itemView) {
        super(itemView);
        exerciseName = itemView.findViewById(R.id.exercise_name);
        checkMark = itemView.findViewById(R.id.check_mark); // ImageView for checkmark

    }
}
}