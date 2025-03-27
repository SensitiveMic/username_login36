package com.example.usernamelogin.Member.Reservation.Coach_Res_Scheduler;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.usernamelogin.Coach.Coach_main;
import com.example.usernamelogin.Member.Member_main;
import com.example.usernamelogin.Member.Reservation.Coach_Reservation_main;
import com.example.usernamelogin.Member.Reservation.Current_Coach_Res.Modelclass_for_current_member_res_accepted;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HealthandAvailabilityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HealthandAvailabilityFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HealthandAvailabilityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HealthandAvailabilityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HealthandAvailabilityFragment newInstance(String param1, String param2) {
        HealthandAvailabilityFragment fragment = new HealthandAvailabilityFragment();
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
    // Define a callback interface
    public interface OnFragmentInteractionListener {
        void onFragmentChange(String tabTitle);
    }

    private OnFragmentInteractionListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }
    Button button_prev, proceedtosend;
    EditText Medicalhist, REcentinj;
    private SharedViewModel viewModel;
    String FN, Agg,Sx, FG, CF, PD;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_healthand_availability, container, false);

        proceedtosend = view.findViewById(R.id.proceedsenttocoach);
        Medicalhist = view.findViewById(R.id.MedicalHist);
        REcentinj = view.findViewById(R.id.RecentSurg_inj);

        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        viewModel.getFullName().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                FN = s;
                // Use the FullName value here

            }
        });

        viewModel.getAge().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Agg = s;
                // Use the FullName value here

            }
        });
        viewModel.getSex().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Sx = s;
                // Use the FullName value here

            }
        });
        viewModel.getFitnessGoals().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                FG = s;
                // Use the FullName value here

            }
        });
        viewModel.getCurrentFitness().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                CF = s;
                // Use the FullName value here

            }
        });
        viewModel.getPreferredDays().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                PD = s;
                // Use the FullName value here

            }
        });

        proceedtosend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Recent_injuries = REcentinj.getText().toString();
                String Medical_history =  Medicalhist.getText().toString();

                Date date = new Date();

                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy HH:mm", Locale.getDefault());
                String currentdateofsent = dateFormat.format(date);

                DatabaseReference Tocoach = FirebaseDatabase.getInstance()
                        .getReference("Users").child("Gym_Owner").child(Coach_Reservation_main.key_Gym_Coach1)
                        .child("Gym").child(Coach_Reservation_main.key_Gym_Coach3).child("Coach");

                DatabaseReference Toserver = FirebaseDatabase.getInstance()
                        .getReference("Users").child("Non-members").child(Login.key)
                        .child("Coach_Reservation").child("Reservation_Applications").child(Coach_Reservation_main.picked_coach);

                DatabaseReference add_as_pending_reservation = FirebaseDatabase.getInstance()
                        .getReference("Users/Non-members")
                        .child(Login.key)
                        .child("Coach_Reservation").child("Current_Accepted_Res").push();

                Modelclass_for_current_member_res_accepted forpendingres = new Modelclass_for_current_member_res_accepted(Coach_Reservation_main.picked_coach,null,null,2);

                Model_Class_healthandavail_procd_snd Appdetails = new Model_Class_healthandavail_procd_snd(FN,Agg,Sx,FG,CF,PD,Medical_history,Recent_injuries,Coach_res_form_main.mobilenumberfromdb);
                Log.d("TAGmobile", "mobile number: " + Coach_res_form_main.mobilenumberfromdb );
                Toserver.setValue(Appdetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {


                    }
                });

                Query checkUser = Tocoach.orderByChild("username")
                        .equalTo(Coach_Reservation_main.picked_coach);

                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){

                            for(DataSnapshot gk: snapshot.getChildren()) {
                                String key = gk.getKey();

                                DatabaseReference insidecoach = Tocoach.child(key).child("pending_res").push();

                                Map<String, Object> sent_res_app = new HashMap<>();
                                sent_res_app.put("Fullname",FN);
                                sent_res_app.put("Date_sent",currentdateofsent );
                                sent_res_app.put("Member_push_id",Login.key);
                                sent_res_app.put("viewType", 2);

                                insidecoach.setValue(sent_res_app).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        Toast.makeText(getActivity(),"Application Sent!",Toast.LENGTH_SHORT).show();

                                        Coach_Reservation_main.picked_coach = null;
                                        Intent intent = new Intent(getActivity(), Member_main.class);
                                        startActivity(intent);

                                    }
                                });

                                Log.d("TAG20", "onDataChange: succesful sent");
                                break;
                            }

                        }




                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                add_as_pending_reservation.setValue(forpendingres);


            }
        });

        button_prev = view.findViewById(R.id.prev_frag12);

        button_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new PersonalandFitnessFragment();

                getParentFragmentManager().beginTransaction().replace(R.id.framelayout1, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();

                if (mListener != null) {
                    mListener.onFragmentChange("Personal and Fitness"); // Pass the new tab title
                }
            }
        });



        return view;
    }
}