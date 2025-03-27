package com.example.usernamelogin.Coach;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usernamelogin.Member.Reservation.Current_Coach_Res.Modelclass_for_current_member_res_accepted;
import com.example.usernamelogin.R;

import java.util.ArrayList;

public class recyclerViewAdapter_Active_reservations extends RecyclerView.Adapter<recyclerViewAdapter_Active_reservations.MYVIEWHOLDEr> {
    Context context;
    ArrayList<Model_class_for_active_reservations> list;

    public recyclerViewAdapter_Active_reservations(Context context, ArrayList<Model_class_for_active_reservations> list) {
        this.context = context;
        this.list = list;
    }

    public int getItemViewType(int position) {
        Model_class_for_active_reservations item = list.get(position);
        return item.getViewType(); // Return the viewType from the data model
    }

    @NonNull
    @Override
    public recyclerViewAdapter_Active_reservations.MYVIEWHOLDEr onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(context).inflate(R.layout.item_list_pending_coach_res_accepted, parent, false);

        return new MYVIEWHOLDEr(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerViewAdapter_Active_reservations.MYVIEWHOLDEr holder, int position) {
        Model_class_for_active_reservations thelist = list.get(position);
        holder.Membername.setText((thelist.getFullname()));
        holder.Coachingdate.setText((thelist.getDate_sent()));
        holder.AgreedTime.setText((thelist.getMeet_time()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MYVIEWHOLDEr extends RecyclerView.ViewHolder{

        TextView Membername, Coachingdate, AgreedTime;
        public MYVIEWHOLDEr(@NonNull View itemView) {
            super(itemView);

            Membername = itemView.findViewById(R.id.dbmembername);
            Coachingdate = itemView.findViewById(R.id.dbdatesent);
            AgreedTime = itemView.findViewById(R.id.dbtimesent);
        }
    }

}
