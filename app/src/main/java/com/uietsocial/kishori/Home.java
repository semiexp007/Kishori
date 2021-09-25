package com.uietsocial.kishori;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.uietsocial.kishori.fragments.chats;
import com.uietsocial.kishori.fragments.graph;
import com.uietsocial.kishori.fragments.member;
import com.uietsocial.kishori.fragments.profile;

import java.lang.reflect.Member;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomnevigation);
        bottomNavigationView.setSelectedItemId(R.id.members);
        Fragment defaultFragment=new member();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem item) {
                Fragment selectedFragment=null;
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
               getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
                return true;
            }
        });
    }

}