package com.example.usernamelogin.NonMemberUser.Reservations;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.usernamelogin.NonMemberUser.NonMemberUSER;
import com.example.usernamelogin.NonMemberUser.Reservations.Date_picking_res.Abstract_Date_picking_res;
import com.example.usernamelogin.NonMemberUser.Reservations.Gymchoosing.Reservation_DialogList;
import com.example.usernamelogin.NonMemberUser.Reservations.Gymchoosing.interface_for_recyclerviewAdapter;
import com.example.usernamelogin.R;
import com.example.usernamelogin.RegisterandLogin.Login;
import com.example.usernamelogin.RegisterandLogin.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_current_reservations_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_current_reservations_Fragment extends Fragment implements interface_for_recyclerviewAdapter, interface_refresh_Reservations {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_current_reservations_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Current_reservations.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_current_reservations_Fragment newInstance(String param1, String param2) {
        fragment_current_reservations_Fragment fragment = new fragment_current_reservations_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    ImageView add_res, ref_resh;
    RecyclerView recyclerView;
    recyclerviewAdapter myadapter;
    Integer sizeof;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_current_reservations, container, false);

        add_res = view.findViewById(R.id.add_res);
        ref_resh = view.findViewById(R.id.refresh);
        recyclerView = view.findViewById(R.id.userList);

    refresh_res_list();
        ref_resh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh_res_list();
            }
        });
        add_res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_list_gym();
            }
        });

        return view;
    }

    public void refresh_res_list(){

        DatabaseReference dbweww = FirebaseDatabase.getInstance()
                .getReference("Reservations/Pending_Requests");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<recycleviewReservationlist> list = new ArrayList<>();
        myadapter = new recyclerviewAdapter(getContext(),list, (interface_for_recyclerviewAdapter) this,this::deletebuttonclciked);
        recyclerView.setAdapter(myadapter);
        dbweww.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot1:snapshot.getChildren()){
                    for(DataSnapshot dataSnapshot:dataSnapshot1.getChildren()){

                        String username = dataSnapshot.child("user").getValue(String.class);

                        if (username != null && username.equals(NonMemberUSER.ProfileContents[0])) {
                            recycleviewReservationlist res_list = dataSnapshot.getValue(recycleviewReservationlist.class);
                            list.add(res_list);
                        }
                    }
                }
                myadapter.notifyDataSetChanged();
               Reservations reservations = (Reservations) getActivity();
                int notifcounter = myadapter.getItemCount();

                if (notifcounter > 0) {
                    reservations.updateNotificationCount(notifcounter);
                }
                else{
                    reservations.updateNotificationBadge(notifcounter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public void onItemClick1(int position) {
        dialog_current_user_res();
    }
    public void dialog_current_user_res(){
        dialog_fornonmember_res_pending listDialog = new dialog_fornonmember_res_pending(getContext()) {
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
            }
        } ;
        listDialog.show();
    }
    public void dialog_list_gym(){

        Reservation_DialogList listDialog = new Reservation_DialogList(getContext()) {

            @Override
            public void onItemClick1(int position, String gymkey, String ownerkey) {

                new Abstract_Date_picking_res(getActivity()) {

                    @Override
                    public void onDateSelected(int year, int month, int dayOfMonth) {
                        Calendar today = Calendar.getInstance();
                        today.set(Calendar.HOUR_OF_DAY, 0);
                        today.set(Calendar.MINUTE, 0);
                        today.set(Calendar.SECOND, 0);
                        today.set(Calendar.MILLISECOND, 0);

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month); // Note: 0-based (January = 0)
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        // Get day name
                        String[] dayNames = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
                        String day = dayNames[calendar.get(Calendar.DAY_OF_WEEK) - 1];
                        // Format selected date
                        String selectedDate = String.format("%d/%d/%d", month + 1, dayOfMonth, year);

                        Calendar selected = Calendar.getInstance();
                        selected.set(year, month, dayOfMonth); // month is 0-based
                        if (selected.before(today)) {
                            Toast.makeText(getContext(), "Cannot pick a past date", Toast.LENGTH_SHORT).show();
                        } else {
                            DatabaseReference databaseReferenceNon = FirebaseDatabase.getInstance()
                                    .getReference("Users")
                                    .child("Gym_Owner").child(ownerkey).child("Gym").child(gymkey).child(day);
                            Log.d("Selected_date_TAG", "onDateSelected: " + ownerkey + " " + gymkey + " " + day);
                            databaseReferenceNon.child("day_active").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        Boolean dayActive = snapshot.getValue(Boolean.class);
                                        if (dayActive != null && dayActive == true){
                                            new AlertDialog.Builder(getActivity())
                                                    .setTitle("Confirm Date Selection")
                                                    .setMessage("Are you sure you want to select this date?")
                                                    .setPositiveButton("Yes", (dialog, which) -> {
                                                        Toast.makeText(getContext(), "Selected: " + selectedDate + " (" + day + ")", Toast.LENGTH_SHORT).show();
                                                        Log.d("Selected_date_TAG", "onDateSelected: " + selectedDate + " " + day);
                                                         reservethis(selectedDate); // Your existing method
                                                        dismiss();
                                                    })
                                                    .setNegativeButton("Cancel", null)
                                                    .show();
                                        }else {
                                            Toast.makeText(getContext(), "Gym Closed for this Day", Toast.LENGTH_SHORT).show();

                                        }
                                    } else {
                                        Log.d("FirebaseDayActive", "day_active not found");
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Log.e("FirebaseDayActive", "Error: " + error.getMessage());
                                }
                            });

                        }




                    }
                }.show();


            }

            @Override
            public void onCreate(Bundle savedInstanceState)
            {
                super.onCreate(savedInstanceState);

                // Set up any additional dialog features
            }
        };
        // Show the dialog
        listDialog.show();
    }

    private void reservethis(String selecteddate){
       DatabaseReference GymRef = FirebaseDatabase.getInstance().getReference("Reservations").child("Pending_Requests").child(Reservations.gymnamefromresdialoggymlist);
       DatabaseReference GymRef_ID = GymRef.push();

        String userid = Login.key;     // user id
        String Res_id =  GymRef_ID.getKey();    // gym key
        String DatetoDB = selecteddate;   // date of reservation
        String UsernamefromDB = NonMemberUSER.ProfileContents[0];  //user username
        String gym_nam = Reservations.gymnamefromresdialoggymlist;    // gymname
        String contctnum = Reservations.gym_contact_numberforview;    // gym contact numb
        Log.d("TAG110", "contactnum: " + contctnum);

        Reservationdate mser = new Reservationdate(userid, contctnum, Res_id,gym_nam,null,UsernamefromDB, DatetoDB);
        GymRef_ID.setValue(mser).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Toast.makeText(getActivity(),"Successful",Toast.LENGTH_SHORT).show();
                getActivity().recreate();
            }
        });
    }
    @Override
    public void deletebuttonclciked() {
        refresh_res_list();
    }


}