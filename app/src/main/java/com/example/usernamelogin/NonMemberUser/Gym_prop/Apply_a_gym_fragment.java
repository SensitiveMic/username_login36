package com.example.usernamelogin.NonMemberUser.Gym_prop;

import android.app.Activity;
import android.content.DialogInterface;
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

import com.example.usernamelogin.NonMemberUser.Model_class_membershipReq;
import com.example.usernamelogin.NonMemberUser.NonMemberUSER;
import com.example.usernamelogin.NonMemberUser.dialogbox.DialogList;
import com.example.usernamelogin.R;
import com.example.usernamelogin.RegisterandLogin.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Apply_a_gym_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Apply_a_gym_fragment extends Fragment implements interface_gym_list {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Apply_a_gym_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Apply_a_gym_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Apply_a_gym_fragment newInstance(String param1, String param2) {
        Apply_a_gym_fragment fragment = new Apply_a_gym_fragment();
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


    RecyclerView gymdisplay;
    int positionfrmdb;
    Gym_Properties_Main switcheroo ;
    public static String Gymnameformembershipreq ;
    String gymnamefromposition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_apply_a_gym_fragment, container, false);





        if (getActivity() instanceof Gym_Properties_Main) {
            switcheroo = (Gym_Properties_Main) getActivity();
        } else {
            Log.e("Apply_a_gym_fragment", "Error: Activity is not an instance of Gym_Properties_Main");
        }
        gymdisplay = view.findViewById(R.id.recyclerView_gymdisplay);

        populate_gym_list();




        return view;
    }

    private void populate_gym_list() {
        // Reference to the Gym_Owner node in your database
        DatabaseReference checkname = FirebaseDatabase.getInstance()
                .getReference("Users")
                .child("Gym_Owner");

        Log.d("TAG6", "Starting gym list population!");

        // Setup RecyclerView
        gymdisplay.setHasFixedSize(true);
        gymdisplay.setLayoutManager(new LinearLayoutManager(getContext()));

        // Create a list to hold gym data
        ArrayList<Helper_class_for_gym_list> list = new ArrayList<>();
        Adapter_nonmember_gymlist adapter = new Adapter_nonmember_gymlist(this,getContext(), list);
        gymdisplay.setAdapter(adapter);

        // Firebase listener
        checkname.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot underGym_Owner : snapshot.getChildren()) {
                    String key_Gym_Staff1 = underGym_Owner.getKey();
                    Log.d("TAG6", "1st key: " + key_Gym_Staff1);
                    String username = underGym_Owner.child("gym_owner_username").getValue(String.class);
                    for (DataSnapshot underGym : underGym_Owner.getChildren()) {
                        String key_Gym_Staff2 = underGym.getKey();
                        Log.d("TAG6", "2nd key: " + key_Gym_Staff2);

                        // Log.d("TAG6", "the username of the owner: " + username);
                        for (DataSnapshot underGymchildwew : underGym.getChildren()) {
                            String key_Gym_Staff3 = underGymchildwew.getKey();
                            Log.d("TAG6", "3rd key: " + key_Gym_Staff3);
                            String GymNAME1 = underGymchildwew.child("gym_name").getValue(String.class);
                            Log.d("TAG6", "Gym-Name " + GymNAME1);
                            String Gym_dsc2 = underGymchildwew.child("gym_descrp").getValue(String.class);
                            Log.d("TAG6", "Gym-description " + Gym_dsc2);

                            Helper_class_for_gym_list res_list1 = underGymchildwew.getValue(Helper_class_for_gym_list.class);

                            res_list1.setGym_owner_username(username);
                            res_list1.setGym_name(GymNAME1);

                            list.add(res_list1);


                        }
                        break;
                    }

                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled event
            }
        });


    }

    @Override
    public void onItemClick(int position)  {

        Gym_Properties_Main.storedposition = position;
        DatabaseReference storetheposition = FirebaseDatabase.getInstance().getReference("Users")
                .child("Non-members").child(Login.key).child("positionstored");

        storetheposition.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    boolean positionFound = false;
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        positionfrmdb = dataSnapshot.child("pos").getValue(Integer.class);
                        gymnamefromposition = dataSnapshot.child("gym_name").getValue(String.class);
                        String pushIdtoremove = dataSnapshot.getKey();
                        Log.d("TAG9", "position value in int : " + positionfrmdb);

                        if (position == positionfrmdb) {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());

                            builder1.setTitle("Already Sent a membership request")
                                    .setMessage("Would you like to remove request ?")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            DatabaseReference removeapplication = FirebaseDatabase.getInstance().getReference()
                                                    .child("Membership_Request")
                                                    .child(gymnamefromposition);

                                            Query finduser = removeapplication.orderByChild("username")
                                                    .equalTo(NonMemberUSER.ProfileContents[0]);

                                            finduser.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    if(snapshot.exists()){
                                                        String application_key = "";
                                                        for(DataSnapshot snapshot1 :snapshot.getChildren()){
                                                            String user_username = snapshot1.child("username").getValue(String.class);
                                                                application_key =  snapshot1.getKey();
                                                            Log.d("TAG9 ", "snapshot username from memberships: " + user_username);
                                                                Log.d("TAG9 ", "snapshotkey from memberships: " + application_key);
                                                                DatabaseReference remaval = removeapplication.child(application_key);
                                                                 remaval.removeValue();
                                                                break;
                                                            }


                                                        }   else {
                                                        Log.d("TAG9 ", "No recognized snapshot");
                                                    }


                                                    }



                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });

                                            DatabaseReference removeal = storetheposition.child(pushIdtoremove);


                                            removeal.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        AlertDialog.Builder builder2 = new AlertDialog.Builder(getContext());

                                                        builder2.setTitle("Succesfully removed Membership Request")
                                                                .setCancelable(true)
                                                                .show();
                                                    }
                                                }
                                            });
                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();

                                        }
                                    })
                                    .show();
                            positionFound = true;
                            // AlertDialog logic
                            break;
                        }
                    }
                    if (!positionFound) {
                        EmployeeListDialough();
                    }
                }else {
                    EmployeeListDialough();
                    Log.d("TAG9", "EmplyeeListDialoguh start!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled event
            }
        });

    }
    public void EmployeeListDialough() {

        DialogList listDialog = new DialogList(getContext()) {
            @Override
            public void onItemClick1(int position) {
                //redirectActivity(Gym_Properties_Main.this, NonMemberUSER.class);

                //  Log.d("TAG8", "onClick: " +Gym_package_selected[0] );
                //  Log.d("TAG8", "onClick: " +Gym_package_selected[1] );
                MembershipReq();
                dismiss();
                Log.d("TAG8","Minecraft Clicked!");
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
    private void MembershipReq(){

        Log.d("TAG8","Minecraft Start!");

        String Nonmember_Username = NonMemberUSER.ProfileContents[0];
        Log.d("TAG8","Username" + Nonmember_Username);

        String Package_name = Gym_Properties_Main.Gym_package_selected[0];
        Log.d("TAG8","Package_name" + Package_name);

        String Package_Price = Gym_Properties_Main.Gym_package_selected[1];
        Log.d("TAG8","Package_Price" + Package_Price);

        String Package_duration = Gym_Properties_Main.Gym_package_selected[3];

        String request_TimeandDate = createLog();
        Log.d("TAG8","Time and date" + request_TimeandDate);

        Log.d("TAG8", "Gymname: " + Gymnameformembershipreq);

        Model_class_membershipReq list = new Model_class_membershipReq(Nonmember_Username, Package_name,Package_Price
                , request_TimeandDate,Package_duration );
        DatabaseReference databaseReferenceNon = FirebaseDatabase.getInstance()
                .getReference("Membership_Request")
                .child(Gymnameformembershipreq)
                .push();

        Log.d("TAG8","Initialization complete!" );
        AlertDialog.Builder makingsaure = new AlertDialog.Builder(getContext());

        makingsaure.setTitle("Are you sure you want to Apply?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseReference storetheposition = FirebaseDatabase.getInstance().getReference("Users")
                                .child("Non-members").child(Login.key).child("positionstored").push();

                        HashMap<String, Object> storepos_gymname = new HashMap<>();
                        storepos_gymname.put("pos",Gym_Properties_Main.storedposition);
                        storepos_gymname.put("gym_name",Gymnameformembershipreq);

                        storetheposition.updateChildren(storepos_gymname);


                        databaseReferenceNon.setValue(list).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setTitle("Membership Request Sent")
                                        .setMessage("Click Yes to Continue ")
                                        .setCancelable(true)
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Gym_Properties_Main switcheroo = (Gym_Properties_Main) getActivity();
                                                switcheroo.switchfrag();
                                                Log.d("TAG121", "Successful item move method launch " );

                                            }
                                        })
                                        .show();
                            }
                        });
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();

    }

    public String createLog() {
        // Get the current date and time
        Date currentDate = new Date();

        // Define the date and time format
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        // Format the date and time
        String formattedDateTime = dateTimeFormat.format(currentDate);

        // Log the formatted date and time
        Log.d("TAG8", "Current date and time: " + formattedDateTime);

        return(formattedDateTime);
    }
    public static void redirectActivity(Activity activity, Class secondActivity){
        Intent intent = new Intent(activity, secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }




}