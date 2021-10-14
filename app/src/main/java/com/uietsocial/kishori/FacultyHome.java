package com.uietsocial.kishori;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.OnDisconnect;
import com.google.firebase.messaging.FirebaseMessaging;
import com.uietsocial.kishori.fragments.chats;
import com.uietsocial.kishori.fragments.chats2;
import com.uietsocial.kishori.fragments.facultyProfile;
import com.uietsocial.kishori.fragments.graph;
import com.uietsocial.kishori.fragments.member;
import com.uietsocial.kishori.fragments.profile;
import com.uietsocial.kishori.fragments.student;

import java.util.HashMap;

public class FacultyHome extends AppCompatActivity {
    String usercat;
Fragment selected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_home);
        FirebaseMessaging.getInstance().subscribeToTopic(FirebaseAuth.getInstance().getCurrentUser().getUid());
        usercat=getIntent().getStringExtra("usercat");
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomnevigationfac);
        bottomNavigationView.setSelectedItemId(R.id.student);
        Fragment defaultFragment=new student();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem item) {
                Fragment selectedFragment=null;
                switch (item.getItemId())
                {
                    case R.id.student:
                        selectedFragment=new student();
                        break;
                    case R.id.graph2:
                        selectedFragment=new graph();
                        break;
                    case R.id.chats2:
                        selectedFragment=new chats2();
                        break;
                    case R.id.profilefaculty:
                        selectedFragment=new facultyProfile();
                        break;

                }
                selected=selectedFragment;
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2,selectedFragment).commit();
                return true;
            }
        });


        DatabaseReference online_status_all_users = FirebaseDatabase.getInstance().getReference().child("user").child("Faculty").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("status");

        //on each user's device when connected they should indicate e.g. `linker` should tell everyone he's snooping around
        online_status_all_users.setValue("Online");
        //also when he's not doing any snooping or if snooping goes bad he should also tell


        OnDisconnect onDisconnectRef = online_status_all_users.onDisconnect();
        onDisconnectRef.setValue("Offline");

    }



}