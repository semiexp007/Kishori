package com.uietsocial.kishori;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

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
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.uietsocial.kishori.fragments.chats;
import com.uietsocial.kishori.fragments.graph;
import com.uietsocial.kishori.fragments.member;
import com.uietsocial.kishori.fragments.profile;

import java.lang.reflect.Member;

import javax.net.ssl.SSLEngineResult;

public class Home extends AppCompatActivity {
     FirebaseUser authuser;
     FirebaseAuth auth;
     Fragment select;

String usercat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);




        usercat=getIntent().getStringExtra("usercat");
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomnevigation);
        bottomNavigationView.setSelectedItemId(R.id.members);
        Fragment defaultFragment=new member();

        FirebaseMessaging.getInstance().subscribeToTopic(FirebaseAuth.getInstance().getCurrentUser().getUid());

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem item) {
                Fragment selectedFragment=defaultFragment;
                switch (item.getItemId())
                {
                    case R.id.members:
                        selectedFragment=new member();
                        break;
                    case R.id.graph:
                        selectedFragment=new graph();
                        break;
                    case R.id.chats:
                        selectedFragment=new chats();
                        break;
                    case R.id.profile:
                        selectedFragment=new profile();
                        break;

                }

                select=selectedFragment;
               getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.additem,menu);
        MenuItem menuItem=menu.findItem(R.id.issue);
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                startActivity(new Intent(Home.this,Searchadd.class));
                finish();


                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

}