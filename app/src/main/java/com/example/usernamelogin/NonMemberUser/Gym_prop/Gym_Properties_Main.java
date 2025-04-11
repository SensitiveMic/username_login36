package com.example.usernamelogin.NonMemberUser.Gym_prop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.usernamelogin.NonMemberUser.NonMemberUSER;
import com.example.usernamelogin.NonMemberUser.Profile;
import com.example.usernamelogin.R;
import com.example.usernamelogin.RegisterandLogin.Login;
import com.example.usernamelogin.NonMemberUser.Reservations.Reservations;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Gym_Properties_Main extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, reservations, profile, gym_membership;
    public static String[] ProfileContents;
    public static String[] Gym_package_selected;
    FrameLayout frameLayout;
    TabLayout tabLayout;
    public static int storedposition = -1;
    public static int notificationCount_gym_badge;
    TextView badge;
    PendingGymApplications_fragment pendingGymApplicationsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym_properties_main);

        someMethod();

        drawerLayout = findViewById(R.id.home_layout);
        menu = findViewById(R.id.nav_menu);
        home = findViewById(R.id.Home_navdrawer);
        reservations = findViewById(R.id.Reservations_navdrawer);
        profile = findViewById(R.id.Profile_navdrawer);
        gym_membership = findViewById(R.id.Gym_navdrawer);

        frameLayout = (FrameLayout) findViewById(R.id.framelayout1);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.framelayout1, new Apply_a_gym_fragment())
                .addToBackStack(null)
                .commit();

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                Fragment fragment = null;
                String tag = null;

                switch (tab.getPosition()) {
                    case 0:
                        tag = "ApplyGymFragment";
                        fragment = getSupportFragmentManager().findFragmentByTag(tag);
                        if (fragment == null) {
                            fragment = new Apply_a_gym_fragment();
                        }
                        break;
                    case 1:
                        tag = "PendingGymApplicationsFragment";
                        fragment = getSupportFragmentManager().findFragmentByTag(tag);

                        if (fragment == null) {
                            fragment = new PendingGymApplications_fragment();
                        }
                        break;
                }

                // Replace the fragment and add it to the back stack with a tag
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.framelayout1, fragment, tag)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        TabLayout.Tab tab1 = tabLayout.newTab().setText("Apply Membership");
        tabLayout.addTab(tab1);

        TabLayout.Tab tab2 = tabLayout.newTab().setText("Pending Gym Applications");
        tabLayout.addTab(tab2);

        View customTabView = LayoutInflater.from(this).inflate(R.layout.custom_tab_with_badge, null);
        tab2.setCustomView(customTabView);

        badge = customTabView.findViewById(R.id.notificationBadge);


        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNavbar(drawerLayout);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Gym_Properties_Main.this, NonMemberUSER.class);
            }
        });
        reservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Gym_Properties_Main.this, Reservations.class);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Gym_Properties_Main.this, Profile.class);
            }
        });
        gym_membership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeNavbar(drawerLayout);
            }
        });

        callApprecview();
    }
    public void callApprecview() {

        Fragment retrievedfrag = getSupportFragmentManager().findFragmentByTag("PendingGymApplicationsFragment");
        if(retrievedfrag == null){
            pendingGymApplicationsFragment = new PendingGymApplications_fragment();
            // Optionally add it to the FragmentManager if you plan to use it later
            getSupportFragmentManager().beginTransaction()
                    .add(pendingGymApplicationsFragment, "PendingGymApplicationsFragment")
                    .commit();
            // Since the fragment is added asynchronously, you can't immediately call its methods
            getSupportFragmentManager().executePendingTransactions(); // Ensure all transactions are completed
        }else {
            pendingGymApplicationsFragment = (PendingGymApplications_fragment) retrievedfrag;
            pendingGymApplicationsFragment.refreshgym();
        }


    }

    public void updateNotificationCount(int count) {
        notificationCount_gym_badge = count;
        updateNotificationBadge(notificationCount_gym_badge);
    }

    public void updateNotificationBadge(int count) {

        if (count > 0) {
            badge.setVisibility(View.VISIBLE);
            badge.setText(String.valueOf(count));
        } else {
            badge.setVisibility(View.GONE);
        }

        Log.d("TAG77", "THISIS notif coun2 =" + count);
    }

    public void usertoolbarname(Context context, TextView usernamebar, TextView username_nav) {
        DatabaseReference databaseReferenceNon = FirebaseDatabase.getInstance().getReference("Users").child("Non-members");
        Query checkname = databaseReferenceNon.orderByChild("username");
        String pushkey = Login.key;
        Log.d("TAG3", pushkey);

        checkname.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ProfileContents = new String[3];
                ProfileContents[0] = dataSnapshot.child(pushkey).child("username").getValue(String.class);
                ProfileContents[1] = dataSnapshot.child(pushkey).child("email").getValue(String.class);
                ProfileContents[2] = dataSnapshot.child(pushkey).child("password").getValue(String.class);

                // Update UI elements using the provided context and TextViews

                usernamebar.setText(ProfileContents[0]);
                username_nav.setText(ProfileContents[0]);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("MainActivity_wrkt_prgrm", "Failed to read value.", databaseError.toException());
            }
        });
    }
    public void someMethod() {
        // Call usertoolbarname() with appropriate arguments
        usertoolbarname(getApplicationContext(),
                findViewById(R.id.textView2),
                findViewById(R.id.username_nav));
    }
    public static void openNavbar(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);
    }
    public static void closeNavbar(DrawerLayout drawerLayout){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
    public static void redirectActivity(Activity activity, Class secondActivity){
        Intent intent = new Intent(activity, secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }
    @Override
    protected void onPause(){
        super.onPause();
        closeNavbar(drawerLayout);
    }
    public void switchfrag(){

        // Mimic a tab click to ensure the TabLayout changes accordingly
        TabLayout.Tab tab = tabLayout.getTabAt(1); // 1 corresponds to the second tab
        if (tab != null) {
            tab.select(); // This will mimic the user clicking on the second tab
        }

        Fragment find= getSupportFragmentManager().findFragmentByTag("PendingGymApplicationsFragment");;
        if(find == null){

            pendingGymApplicationsFragment = new PendingGymApplications_fragment();
            find = pendingGymApplicationsFragment; // Assign newly created fragment to 'find'
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.framelayout1, find, "PendingGymApplicationsFragment")
                .addToBackStack(null)
                .commit();
        pendingGymApplicationsFragment = (PendingGymApplications_fragment) find;
        pendingGymApplicationsFragment.refreshgym();
    }


}