package com.example.usernamelogin.Coach;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usernamelogin.Coach.Snd_wrkout.Select_wrkout_2_snd_frag;
import com.example.usernamelogin.R;

import java.util.ArrayList;

public class recyclerViewAdapter_Active_reservations extends RecyclerView.Adapter<recyclerViewAdapter_Active_reservations.MYVIEWHOLDEr> {
    Context context;
    ArrayList<Model_class_for_active_reservations> list;
    private FragmentManager fragmentManager;
    interface_longclick_Adapter_ACtive_res interfaceLongclickAdapterACtiveRes;

    public recyclerViewAdapter_Active_reservations(Context context,
                                                   ArrayList<Model_class_for_active_reservations> list,
                                                   FragmentManager fragmentManager
                        ,interface_longclick_Adapter_ACtive_res interfaceLongclickAdapterACtiveRes) {
        this.context = context;
        this.list = list;
        this.fragmentManager = fragmentManager;
        this.interfaceLongclickAdapterACtiveRes = interfaceLongclickAdapterACtiveRes;
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

        return new MYVIEWHOLDEr(itemView,interfaceLongclickAdapterACtiveRes);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerViewAdapter_Active_reservations.MYVIEWHOLDEr holder, int position) {
        Model_class_for_active_reservations thelist = list.get(position);
        holder.Membername.setText((thelist.getFullname()));
        holder.Coachingdate.setText((thelist.getDate_sent()));
        holder.AgreedTime.setText((thelist.getMeet_time()));

        holder.add_custom_wrkout.setOnClickListener(v -> {
            // Show a Toast with some information (e.g., member's name or any other relevant data)
            Coach_main.activeres_member_pushid = thelist.getPushId();

            Log.d("TAG_MAIN_Selected_ex_1", "The user's PUSH ID!: " +Coach_main.activeres_member_pushid );
            Toast.makeText(v.getContext(), "Adding custom workout for " + thelist.getFullname(), Toast.LENGTH_SHORT).show();

            Select_wrkout_2_snd_frag displayFragment = new Select_wrkout_2_snd_frag();

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.framelayout3, displayFragment);
            transaction.addToBackStack(null);
            transaction.commit();

            // You can also implement any other actions you want to perform when the button is clicked.
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MYVIEWHOLDEr extends RecyclerView.ViewHolder{

        TextView Membername, Coachingdate, AgreedTime;
        Button add_custom_wrkout;
        public MYVIEWHOLDEr(@NonNull View itemView, interface_longclick_Adapter_ACtive_res interfaceLongclickAdapterACtiveRes) {
            super(itemView);

            Membername = itemView.findViewById(R.id.dbmembername);
            Coachingdate = itemView.findViewById(R.id.dbdatesent);
            AgreedTime = itemView.findViewById(R.id.dbtimesent);
            add_custom_wrkout = itemView.findViewById(R.id.Add_cstm_wo_btn);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                          String text = Membername.getText().toString();
                            Coach_main.selected_longclick = text;

                            interfaceLongclickAdapterACtiveRes.onitemLonclick(pos);
                        }


                    return true;
                }
            });
        }
    }

}