package com.uietsocial.kishori;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.OnDisconnect;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.uietsocial.kishori.fragments.NoticeFragment;
import com.uietsocial.kishori.fragments.chats;
import com.uietsocial.kishori.fragments.graph;
import com.uietsocial.kishori.fragments.member;
import com.uietsocial.kishori.fragments.profile;

import java.lang.reflect.Member;
import java.util.HashMap;

import javax.net.ssl.SSLEngineResult;

public class Home extends AppCompatActivity {
    FirebaseUser authuser;
    FirebaseAuth auth;
    Fragment select;
    String usercat;

    Fragment selectedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        usercat = getIntent().getStringExtra("usercat");


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnevigation);
        bottomNavigationView.setSelectedItemId(R.id.graph);
        Fragment defaultFragment = new graph();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, defaultFragment).commit();
        FirebaseMessaging.getInstance().subscribeToTopic(FirebaseAuth.getInstance().getCurrentUser().getUid());

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                selectedFragment = defaultFragment;
                switch (item.getItemId()) {
                    case R.id.members:
                        selectedFragment = new member();
                        break;
                    case R.id.graph:
                        selectedFragment = new graph();
                        break;
                    case R.id.chats:
                        selectedFragment = new chats();
                        break;
                    case R.id.profile:
                        selectedFragment = new profile();
                        break;
                    case R.id.news:
                        selectedFragment=new NoticeFragment();
                        break;

                }

                select = selectedFragment;
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                return true;
            }
        });




        DatabaseReference online_status_all_users2 = FirebaseDatabase.getInstance().getReference().child("user").child("Student").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("status");

        //on each user's device when connected they should indicate e.g. `linker` should tell everyone he's snooping around
        online_status_all_users2.setValue("Online");
        //also when he's not doing any snooping or if snooping goes bad he should also tell


        OnDisconnect onDisconnectRef2 = online_status_all_users2.onDisconnect();
        onDisconnectRef2.setValue("Offline");

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.additem, menu);
        MenuItem menuItem = menu.findItem(R.id.issue);
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                startActivity(new Intent(Home.this, Searchadd.class));
                finish();
                return true;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }




}