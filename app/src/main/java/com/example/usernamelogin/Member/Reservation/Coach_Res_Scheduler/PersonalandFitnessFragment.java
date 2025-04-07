package com.example.usernamelogin.Member.Reservation.Coach_Res_Scheduler;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.usernamelogin.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PersonalandFitnessFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonalandFitnessFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PersonalandFitnessFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PersonalandFitnessFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PersonalandFitnessFragment newInstance(String param1, String param2) {
        PersonalandFitnessFragment fragment = new PersonalandFitnessFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private SharedViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
    }

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


    EditText FullName, Age, FitnessGoals, PreferredDays;

    String[] items = {"Male","Female"};

    Integer[] fitnessrate = {1, 2, 3, 4, 5};

    AutoCompleteTextView auto_complet;
    AutoCompleteTextView fitrate;

    ArrayAdapter<String> adapteritems;
    ArrayAdapter<Integer> adapterforfitlevel;
    String selectedfromdropdownlist1;
    String getSelectedfromdropdownlist_fitnesslevel;
    Button changefragment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_personaland_fitness, container, false);

        View view = inflater.inflate(R.layout.fragment_personaland_fitness, container, false);

        FullName = view.findViewById(R.id.fullnamemem);
        Age = view.findViewById(R.id.editTextAGeNumber);
        FitnessGoals = view.findViewById(R.id.fitnessgoalsmem);

        PreferredDays = view.findViewById(R.id.availability_gym_mem);


        changefragment = view.findViewById(R.id.next_frag1);

        auto_complet = (AutoCompleteTextView) view.findViewById(R.id.auto_complete);
        adapteritems = new ArrayAdapter<>(requireActivity(),R.layout.list_items_main,items);

        adapterforfitlevel = new ArrayAdapter<>(requireActivity(),R.layout.list_items_main,fitnessrate);
        fitrate = (AutoCompleteTextView) view.findViewById(R.id.fitrateautocomplete);
        fitrate.setAdapter(adapterforfitlevel);

        auto_complet.setAdapter(adapteritems);

        auto_complet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedfromdropdownlist1 = parent.getItemAtPosition(position).toString();
                Toast.makeText(getActivity().getApplicationContext(),"itemL " +selectedfromdropdownlist1,Toast.LENGTH_SHORT).show();
            }
        });

        fitrate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getSelectedfromdropdownlist_fitnesslevel = parent.getItemAtPosition(position).toString();
            }
        });

        changefragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new HealthandAvailabilityFragment();

                getParentFragmentManager().beginTransaction().replace(R.id.framelayout1, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();

                if (mListener != null) {
                    mListener.onFragmentChange("Health and Availability"); // Pass the new tab title
                }
                String FN, Agg,Sx, FG,CF,PD;

                FN = FullName.getText().toString();
                Agg = Age.getText().toString();
                Sx = selectedfromdropdownlist1;
                FG = FitnessGoals.getText().toString();
                CF = getSelectedfromdropdownlist_fitnesslevel.toString();
                PD = PreferredDays.getText().toString();

                Log.d("TAG19", FN);

                viewModel.setSex(Sx);
                viewModel.setFullName(FN);
                viewModel.setAge(Agg);
                viewModel.setFitnessGoals(FG);
                viewModel.setCurrentFitness(CF);
                viewModel.setPreferredDays(PD);
            }
        });

        return view;
    }
}