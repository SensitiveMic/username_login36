package com.example.usernamelogin.Coach;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usernamelogin.R;

import java.util.ArrayList;

public class recyclerViewAdapter_coach_main_pending_req extends RecyclerView.Adapter<recyclerViewAdapter_coach_main_pending_req.MyViewHoldeR> {
     private final interface_click_pendingresfrm_gym_members recyclerViewInterface;
    Context context;
    ArrayList<Model_class_pendingresdisplay> list;

    private static final int VIEW_TYPE_ONE = 1;  //accepted
    private static final int VIEW_TYPE_TWO = 2;  // pending
    private static final int VIEW_TYPE_THREE = 3; // denied


    public recyclerViewAdapter_coach_main_pending_req(Context context, ArrayList<Model_class_pendingresdisplay> list ,interface_click_pendingresfrm_gym_members recyclerViewInterface ) {
        this.context = context;
        this.list = list;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @Override
    public int getItemViewType(int position) {
        Model_class_pendingresdisplay item = list.get(position);
        return item.getViewType(); // Return the viewType from the data model
    }

    @NonNull
    @Override
    public MyViewHoldeR onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        if (viewType == VIEW_TYPE_ONE) {
            itemView = LayoutInflater.from(context).inflate(R.layout.item_list_pending_coach_res_accepted, parent, false);
        } else if (viewType == VIEW_TYPE_TWO) {
            itemView = LayoutInflater.from(context).inflate(R.layout.item_list_pending_coach_res, parent, false);
        }
        else {
            // Handle default case or throw an exception for unknown viewType
            throw new IllegalArgumentException("Invalid viewType: " + viewType);
        }
        return new MyViewHoldeR(itemView , recyclerViewInterface );
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHoldeR holder, int position) {
        Model_class_pendingresdisplay req_list = list.get(position);
        holder.Member_name.setText(req_list.getFullname());
        holder.datesent.setText(req_list.getDate_sent());
        holder.member_pushid.setText(req_list.getMember_push_id());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHoldeR extends RecyclerView.ViewHolder {

        TextView Member_name, datesent, member_pushid;

        public MyViewHoldeR(@NonNull View itemView , interface_click_pendingresfrm_gym_members recyclerViewInterface ) {
            super(itemView);

            Member_name = itemView.findViewById(R.id.dbmembername);
            datesent = itemView.findViewById(R.id.dbdatesent);
            member_pushid = itemView.findViewById(R.id.memberpushidfrmdb);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewInterface != null) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            String text2 = Member_name.getText().toString();
                            String text = member_pushid.getText().toString();
                            Coach_main.member_pushid = text;
                            Coach_main.member_name = text2;
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }


                }
            });
        }
    }
}