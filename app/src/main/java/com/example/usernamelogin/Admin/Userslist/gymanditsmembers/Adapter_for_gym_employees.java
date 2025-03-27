package com.example.usernamelogin.Admin.Userslist.gymanditsmembers;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usernamelogin.Member.Reservation.Model_class_Coach_list;
import com.example.usernamelogin.R;

import java.util.ArrayList;
import java.util.Arrays;

public class Adapter_for_gym_employees extends RecyclerView.Adapter<Adapter_for_gym_employees.MyViewHolder> implements givetoadapterinterface {
    private Context context;
    private ArrayList<Model_class_Coach_list> list;
    private givetoroots itemclicker;
    private String[] arrayReceiver;
    public Adapter_for_gym_employees(Context context
            , ArrayList<Model_class_Coach_list> list
            ,givetoroots itemclicker ) {
        this.context = context;
        this.list = list;
        this.itemclicker = itemclicker;
    }

    @NonNull
    @Override
    public Adapter_for_gym_employees.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_list_admin_employeelistname,parent,false);
        return new Adapter_for_gym_employees.MyViewHolder(v,itemclicker);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_for_gym_employees.MyViewHolder holder, int position) {
        Model_class_Coach_list fromusers = list.get(position);
        holder.employeename.setText(fromusers.getUsername());
        if (arrayReceiver != null) {
            holder.setArray(arrayReceiver);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void clickeditem2(String[] array) {
        if(array == null){
            Log.d("TAGARRAYFIND", "novalue in adapter ");
        }else {
            Log.d("TAGARRAYFIND", "hasvalue in adapter ");
            arrayReceiver = array;
        }
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView employeename;
        String[] arrayreceiver1;
        String[] updatedarray;

        public MyViewHolder(@NonNull View itemView,givetoroots itemclicker ) {
            super(itemView);
            employeename = itemView.findViewById(R.id.Employee_Name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(itemclicker !=null) {
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION){
                            String employeenamefromrec = employeename.getText().toString();
                            arrayreceiver1 = Dialog_for_staffandcoach.extradirect;
                            updatedarray = Arrays.copyOf(arrayreceiver1, arrayreceiver1.length +1);
                            updatedarray[updatedarray.length - 1] = employeenamefromrec;

                            itemclicker.clickeditem(pos, updatedarray);

                        }
                    }
                }
            });
        }
        public void setArray(String[] array) {
            arrayreceiver1 = array;
        }

    }

}
