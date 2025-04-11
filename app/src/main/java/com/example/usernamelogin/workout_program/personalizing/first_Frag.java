package com.example.usernamelogin.workout_program.personalizing;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.usernamelogin.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link first_Frag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class first_Frag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public first_Frag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment first_Frag.
     */
    // TODO: Rename and change types and number of parameters
    public static first_Frag newInstance(String param1, String param2) {
        first_Frag fragment = new first_Frag();
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
    public interface OnNextButtonListener {
        void nextbtn();  // This method will be called in the activity
    }
    Button fitnessgoal, buildmuscle, stayfit;
    private OnNextButtonListener callback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first__wrkt_prgrm, container, false);


        fitnessgoal = view.findViewById(R.id.button1);
        buildmuscle = view.findViewById(R.id.button2);
        stayfit = view.findViewById(R.id.button3);


        buildmuscle.setOnClickListener(v -> nextfrag());


        return view;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            // Step 2: Ensure the activity implements the interface
            callback = (OnNextButtonListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnNextButtonListener");
        }
    }
    public void nextfrag(){
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_1_id, new Building_muscle_main_frag());
        transaction.addToBackStack(null);  // Allows back navigation
        transaction.commit();

        callback.nextbtn();

    }
}