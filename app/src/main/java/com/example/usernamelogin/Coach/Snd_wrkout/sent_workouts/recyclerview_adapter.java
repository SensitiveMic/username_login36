package com.example.usernamelogin.Coach.Snd_wrkout.sent_workouts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usernamelogin.R;

import java.util.List;

public class recyclerview_adapter extends RecyclerView.Adapter<recyclerview_adapter.ViewHolder>{
    private List<workout_name_info_modelclass> workoutList;
    private final click_tosee_interface recyclerview_interface;

    public recyclerview_adapter(click_tosee_interface recyclerview_interface, List<workout_name_info_modelclass> workoutList) {
        this.recyclerview_interface = recyclerview_interface;
        this.workoutList = workoutList;
    }

    @NonNull
    @Override
    public recyclerview_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sentworkouts_layout_items, parent, false);
        return new ViewHolder(view,recyclerview_interface);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerview_adapter.ViewHolder holder, int position) {
        workout_name_info_modelclass workouyt = workoutList.get(position);
        holder.workoutName.setText(workouyt.getWorkoutName());
        holder.parentkey.setText(workouyt.getParentKey());
        holder.membername.setText(workouyt.getMember_username());
    }

    @Override
    public int getItemCount() {
        return workoutList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView workoutName,parentkey, membername;
        public ViewHolder(@NonNull View itemView,click_tosee_interface recyclerview_interface) {
            super(itemView);
            workoutName = itemView.findViewById(R.id.workout_name);
            parentkey = itemView.findViewById(R.id.parent_key);
            membername = itemView.findViewById(R.id.member_usernma);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerview_interface != null) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                                String selectedkey = parentkey.getText().toString();
                                String workout_name = workoutName.getText().toString();
                            recyclerview_interface.onItemClick(pos,selectedkey, workout_name);
                        }
                    }
                }
            });
        }
    }
}
