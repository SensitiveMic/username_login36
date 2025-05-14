package com.example.usernamelogin.Staff;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.usernamelogin.NonMemberUser.Reservations.interface_refresh_Reservations;
import com.example.usernamelogin.R;
import com.example.usernamelogin.RegisterandLogin.Login;
import com.example.usernamelogin.RegisterandLogin.MainActivity;
import com.example.usernamelogin.Staff.Membership_req_management.Membership_requests_main;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_pending#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_pending extends Fragment implements recyclerViewInterface_staff1, interface_refresh_Reservations{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_pending.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_pending newInstance(String param1, String param2) {
        Fragment_pending fragment = new Fragment_pending();
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
    RecyclerView recyclerView;
    Adapter_staff_reservation myadapter1;
    Button membershipreq_btn;
    AlertDialog.Builder builder;
    public static String res_name;
    String snapshotkey;
    ArrayList<newHelper_reservation_staff> combinedList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pending, container, false);
        recyclerView = view.findViewById(R.id.staff_res_list);
        membershipreq_btn = view.findViewById(R.id.membership_req_btn);

        membershipreq_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(getActivity(), Membership_requests_main.class);
            }
        });

        populateList();


        return view;
    }

    private void populateList(){


        myadapter1 = new Adapter_staff_reservation((recyclerViewInterface_staff1) this, getContext(), combinedList);

        DatabaseReference db1 = FirebaseDatabase.getInstance().getReference("Reservations/Pending_Requests")
                .child(Login.key_Gym_);
        Query wew = db1;

        recyclerView.setAdapter(myadapter1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        db1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
                Date today = new Date();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                   String key = snapshot1.getKey().toString();
                   String dateStr = snapshot1.child("date").getValue().toString();

                    try {
                        Date storedDate = sdf.parse(dateStr);

                        if (storedDate != null && storedDate.before(today)) {
                            // Date has passed, delete this key and its children
                          snapshot1.getRef().removeValue();
                        }

                    } catch (ParseException e) {
                        e.printStackTrace(); // Optional: handle or log parse error
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        wew.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                combinedList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    newHelper_reservation_staff item = dataSnapshot.getValue(newHelper_reservation_staff.class);
                    combinedList.add(item);

                }
                myadapter1.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG", "Database error: " + error.getMessage());
            }
        });
    }

    public static void redirectActivity(Activity activity, Class secondActivity) {
        Intent intent = new Intent(activity, secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);

    }

    @Override
   public void onItemClick1(int position) {
        builder = new AlertDialog.Builder(getContext());
        Log.d("TAG121", "Button clicked" );
        builder.setTitle("Accept Reservation?")
                .setMessage("Do you want to Accept the Gym Reservation? ")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        moveitem();

                        Log.d("TAG121", "Successful item move method launch " );

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }
    public void moveitem() {

        FirebaseDatabase databaseLogin = FirebaseDatabase.getInstance();
        DatabaseReference myRefLogin = databaseLogin.getReference("Reservations");
        Log.d("TAG121", "theID " + res_name );
        Log.d("TAG121", "the Gym name " + Login.key_Gym_ );
        Query checkUser = myRefLogin.child("Pending_Requests")
                .child(Login.key_Gym_)
                .orderByChild("res_id")
                .equalTo(res_name);
        Log.d("TAG121", "1st phase initiated " );
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    for (DataSnapshot gk : snapshot.getChildren()) {
                        snapshotkey = gk.getKey();

                        Log.d("TAG121", "push id of the clicked item : " + snapshotkey);

                        String date = snapshot.child(snapshotkey).child("date").getValue(String.class);
                        String time = snapshot.child(snapshotkey).child("time").getValue(String.class);
                        String username = snapshot.child(snapshotkey).child("user").getValue(String.class);
                        String res_id = snapshot.child(snapshotkey).child("res_id").getValue(String.class);
                        String userid = snapshot.child(snapshotkey).child("userid").getValue(String.class);

                        //move to Accepted Reservations
                        DatabaseReference myRefres = databaseLogin.getReference("Reservations/Accepted")
                                .child(Login.key_Gym_).child(snapshotkey);
                        //move to nonmembers paid reservations
                        DatabaseReference non_mem_paid_display = databaseLogin.getReference("Users/Non-members")
                                .child(userid).child("Non_member_Gym_res").push();

                        Helper_reservation_add_nonmember_paid topaid_diisplay = new Helper_reservation_add_nonmember_paid(date, time, username, res_id,Login.key_Gym_);
                        Helper_reservation_staff_accepted reservation = new Helper_reservation_staff_accepted (date, time, username, res_id);

                        non_mem_paid_display.setValue(topaid_diisplay).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){

                                    Log.d("TAG121", "Successful adding of paid res in non_member user " );

                                }
                            }
                        });
                        myRefres.setValue(reservation).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    nextset();

                                } else {
                                    // Handle the case where setting the value in Firebase failed
                                    Log.e("TAG121", "Failed to set value in Firebase");
                                }
                            }
                        });


                    }
                } else {
                    Log.e("TAG121", "Snapshot does not exist");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
}
private void nextset(){

    DatabaseReference myRefres1 = FirebaseDatabase.getInstance().getReference("Reservations/Pending_Requests")
            .child(Login.key_Gym_).child(snapshotkey);

    myRefres1.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
            if (task.isSuccessful()) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Notify your adapters that the dataset has changed

                        // Show an AlertDialog after the moveitem() finishes running
                        new AlertDialog.Builder(getContext())
                                .setTitle("Move Successful")
                                .setMessage("The item has been moved successfully click yes to refresh list")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        Intent intent = new Intent(requireActivity(), Staff_main.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        getActivity().startActivity(intent);


                                        Log.d("TAG121", "Successful refresh click launch " );

                                    }
                                })
                                .show();
                    }

                }, 500); // 1 seconds delay
            } else {
                // Handle the case where removal from Firebase failed
                Log.e("TAG121", "Failed to remove item from Firebase");
            }
        }
    });
}

    @Override
    public void deletebuttonclciked() {

    }
}