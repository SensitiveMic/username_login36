package com.example.usernamelogin.NonMemberUser.Reservations;

import static androidx.core.app.ActivityCompat.recreate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usernamelogin.NonMemberUser.Reservations.Gymchoosing.interface_for_recyclerviewAdapter;
import com.example.usernamelogin.R;
import com.example.usernamelogin.RegisterandLogin.Login;
import com.example.usernamelogin.RegisterandLogin.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class recyclerviewAdapter extends RecyclerView.Adapter<recyclerviewAdapter.MyViewHolder> {
    private final interface_refresh_Reservations imageClickListener;
    private final interface_for_recyclerviewAdapter gym_interface_clicking;
    Context context;
    ArrayList<recycleviewReservationlist> list;


    public recyclerviewAdapter(Context context, ArrayList<recycleviewReservationlist> list
            , interface_for_recyclerviewAdapter gym_interface_clicking
            ,interface_refresh_Reservations imageclicklisterner) {
        this.context = context;
        this.list = list;
        this.gym_interface_clicking = gym_interface_clicking;
        this.imageClickListener = imageclicklisterner;
    }

    @NonNull
    @Override
    public recyclerviewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_current_res, parent, false);
        return new MyViewHolder(v, gym_interface_clicking);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerviewAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        recycleviewReservationlist user = list.get(position);

        holder.Date.setText(user.getDate());
        holder.User.setText(user.getUser());
        holder.Gym.setText(user.getGym());
        holder.gymnum.setText(user.getGymnum());
        holder.itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("CANCEL RESERVATION");
                builder.setMessage("Do you want to proceed cancelling your reservation?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("TAG12", "minecraft initiated!");
                        String user1 = user.getUser();
                        String gymnum = user.getGym();
                        Log.d("TAG12", "Gym initiated! " +gymnum);
                        DatabaseReference reservationofgymnonmember = FirebaseDatabase.getInstance()
                                .getReference("Reservations/Pending_Requests")
                                .child(gymnum)
                                .child(user.getRes_id());

                        reservationofgymnonmember.removeValue();
                        imageClickListener.deletebuttonclciked();

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();



            }
        });


    }

    @Override
    public int getItemCount() {


        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Date;
        TextView User;
        TextView Gym;
        TextView gymnum;
        ImageView itemImage;
        public MyViewHolder(@NonNull View itemView, interface_for_recyclerviewAdapter recycleViewInterface) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.trashcandelete);
            Date = itemView.findViewById(R.id.dbdate);
            User = itemView.findViewById(R.id.dbuser);
            Gym = itemView.findViewById(R.id.dbgym_named);
            gymnum = itemView.findViewById(R.id.dbgym_num);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recycleViewInterface != null) {
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION) {
                            String text = gymnum.getText().toString();
                            Reservations.mobilenumberofgyminres = text;



                            recycleViewInterface.onItemClick1(pos);
                        }
                    }
                }
            });
        }
    }

}
