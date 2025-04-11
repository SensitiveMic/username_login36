package com.example.usernamelogin.workout_program.personalizing;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.usernamelogin.R;
import com.example.usernamelogin.workout_program.personalizing.Exercise_category.Exercise_categories_frag;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Building_muscle_main_frag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Building_muscle_main_frag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Building_muscle_main_frag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Second_Frag.
     */
    // TODO: Rename and change types and number of parameters
    public static Building_muscle_main_frag newInstance(String param1, String param2) {
        Building_muscle_main_frag fragment = new Building_muscle_main_frag();
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
    EditText weight_edittext;
    Button done_sec_frag;
    String value_1,value_2,value_3,value_4;
    TextView radioValue1,radioValue2,radioValue3,radioValue4;
    private first_Frag.OnNextButtonListener callback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.building_muscle_main_frag_wrkt_prgrm, container, false);
        RadioGroup radioGroup1,radioGroup2,radioGroup3,radioGroup4;
        radioGroup1 = view.findViewById(R.id.radioGroup1);

        radioValue1 = view.findViewById(R.id.radioValue1);
        radioValue2 = view.findViewById(R.id.radioValue_2);
        radioValue3 = view.findViewById(R.id.radioValue_3);
        radioValue4 = view.findViewById(R.id.radioValue_4);

        weight_edittext = view.findViewById(R.id.userinputweight);

        radioGroup1.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selectedButton = view.findViewById(checkedId);
            value_1 = selectedButton.getText().toString();
            radioValue1.setText("Selected Value: " + value_1);
            radioValue1.setTextColor(Color.BLACK);
        });
        radioGroup2 = view.findViewById(R.id.radioGroup);
        radioGroup2.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selectedButton = view.findViewById(checkedId);
            value_2 = selectedButton.getText().toString();
            radioValue2.setText("Selected Value: " + value_2);
            radioValue2.setTextColor(Color.BLACK);
        });
        radioGroup3 = view.findViewById(R.id.radioGroup3);
        radioGroup3.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selectedButton = view.findViewById(checkedId);
            value_3 = selectedButton.getText().toString();
            radioValue3.setText("Selected Value: " + value_3);
            radioValue3.setTextColor(Color.BLACK);
        });
        radioGroup4 = view.findViewById(R.id.radioGroup4);
        radioGroup4.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selectedButton = view.findViewById(checkedId);
            value_4 = selectedButton.getText().toString();
            radioValue4.setText("Selected Value: " + value_4);
            radioValue4.setTextColor(Color.BLACK);
        });

        done_sec_frag = view.findViewById(R.id.donebutton_secondfrag);

        done_sec_frag.setOnClickListener(v->collectuserinput());

        return view;
    }
    private void collectuserinput(){
       String userweight = weight_edittext.getText().toString().trim();
        int userweight_int = 0;
        boolean isValid = true;

       if (weight_edittext == null){
           weight_edittext.setText("*");
            weight_edittext.setTextColor(Color.RED);
       }
       if(value_1 == null){
           radioValue1.setText("Error: Please select an option for Group 1");
           radioValue1.setTextColor(Color.RED);
           isValid = false;
       }
       if(value_2 == null){
           radioValue2.setText("Error: Please select an option for Group 2");
           radioValue2.setTextColor(Color.RED);
            isValid = false;
        }
       if(value_3 == null){
           radioValue3.setText("Error: Please select an option for Group 3");
           radioValue3.setTextColor(Color.RED);
            isValid = false;
        }
       if(value_4 == null){
           radioValue4.setText("Error: Please select an option for Group 4");
           radioValue4.setTextColor(Color.RED);
            isValid = false;
        }
        if (isValid) {
         userweight_int = Integer.parseInt(userweight);
            Personalizing_main.Fitness_value = Integer.parseInt(value_1);
            Personalizing_main.Strength_value = Integer.parseInt(value_2);
            Personalizing_main.Endurance_value = Integer.parseInt(value_3);
            Personalizing_main.Energy_level = Integer.parseInt(value_4);
            Personalizing_main.userWeight = userweight_int;
            Log.d("TAGMUS", "isvallid!@ ");

        }
        nextfrag2();

    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            // Step 2: Ensure the activity implements the interface
            callback = (first_Frag.OnNextButtonListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnNextButtonListener");
        }
    }
    public void nextfrag2() {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_1_id, new Exercise_categories_frag());
        transaction.addToBackStack(null);  // Allows back navigation
        transaction.commit();

        callback.nextbtn();
    }
}