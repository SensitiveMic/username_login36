package com.example.usernamelogin.workout_program.personalizing;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.usernamelogin.R;
import com.example.usernamelogin.workout_program.personalizing.Exercise_category.Exercise_categories_frag;
import com.example.usernamelogin.workout_program.personalizing.Exercise_category.selected_exercises.Selected_Exercise_frag;


public class Personalizing_main extends AppCompatActivity implements first_Frag.OnNextButtonListener {
FrameLayout frameLayout1;
Button nextbtn, prevbtn;
public static int Fitness_value,Strength_value,Endurance_value,Energy_level,userWeight;
    private int currentFragmentIndex = 0;  // Track current fragment
    private final Fragment[] fragments = new Fragment[]{
            new first_Frag(),
            new Building_muscle_main_frag(),
            new Exercise_categories_frag(),
            new Selected_Exercise_frag()
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_personalizing_main_wrkt_prgrm);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

      frameLayout1 = (FrameLayout) findViewById(R.id.fragment_1_id);
        first_Frag fragment1 = new first_Frag();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_1_id,fragment1)
                .commit();

        nextbtn = findViewById(R.id.next_button1);
        nextbtn.setOnClickListener(v -> showFragment(currentFragmentIndex + 1));
        prevbtn= findViewById(R.id.prev_button1);
        prevbtn.setOnClickListener(v -> showFragment(currentFragmentIndex - 1));
      /*      */

    }
    public void nextbtn(){
        currentFragmentIndex = currentFragmentIndex + 1;
        updateButtonVisibility();

    }
    private void showFragment(int index) {
        if (index < 0 || index >= fragments.length) return;

        currentFragmentIndex = index;

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_1_id, fragments[currentFragmentIndex])
                .commit();

        updateButtonVisibility();
    }
    private void updateButtonVisibility() {
        prevbtn.setVisibility(currentFragmentIndex == 0 ? View.GONE : View.VISIBLE);
        nextbtn.setVisibility(currentFragmentIndex == fragments.length - 1 ? View.GONE : View.VISIBLE);
    }
}