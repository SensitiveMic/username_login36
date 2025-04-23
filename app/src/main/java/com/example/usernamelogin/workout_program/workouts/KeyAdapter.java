package com.example.usernamelogin.workout_program.workouts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class KeyAdapter extends RecyclerView.Adapter<KeyAdapter.KeyViewHolder> {
Context context;
private static List<String> keyList;
interface_clickcustomW onclick;

public KeyAdapter(List<String> keyList,interface_clickcustomW onclick,Context context) {
    this.keyList = keyList;
    this.onclick = onclick;
    this.context = context;
}

@NonNull
@Override
public KeyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
    return new KeyViewHolder(view,onclick);
}

@Override
public void onBindViewHolder(@NonNull KeyViewHolder holder, int position) {
    String key = keyList.get(position);
    holder.textView.setText(key);
}

@Override
public int getItemCount() {
    return keyList.size();
}
    private void showDeleteDialog(String key, int pos) {
        new AlertDialog.Builder(context)
                .setTitle("Delete Workout")
                .setMessage("Are you sure you want to delete this workout?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    if (context instanceof User_workouts) {
                        ((User_workouts) context).deleteWorkoutFromJson(key);
                        keyList.remove(pos);
                        notifyItemRemoved(pos);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

public class KeyViewHolder extends RecyclerView.ViewHolder {
    TextView textView;

    public KeyViewHolder(@NonNull View itemView, interface_clickcustomW onclick) {
        super(itemView);
        textView = itemView.findViewById(android.R.id.text1);

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    showDeleteDialog(keyList.get(pos), pos);
                }
                return true;
            }
        });

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onclick != null){
                    int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION){
                        String text = textView.getText().toString();
                        User_workouts.selectedCustomWOrk = text;
                        onclick.onclick(pos);

                    }
                }
            }
        });
    }
}
}