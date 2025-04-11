package com.example.usernamelogin.workout_program.workouts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class KeyAdapter extends RecyclerView.Adapter<KeyAdapter.KeyViewHolder> {

private final List<String> keyList;
interface_clickcustomW onclick;

public KeyAdapter(List<String> keyList,interface_clickcustomW onclick) {
    this.keyList = keyList;
    this.onclick = onclick;
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

public static class KeyViewHolder extends RecyclerView.ViewHolder {
    TextView textView;

    public KeyViewHolder(@NonNull View itemView, interface_clickcustomW onclick) {
        super(itemView);
        textView = itemView.findViewById(android.R.id.text1);
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