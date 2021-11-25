package com.uietsocial.kishori.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uietsocial.kishori.Home;
import com.uietsocial.kishori.R;
import com.uietsocial.kishori.adopter.UserAdopter;
import com.uietsocial.kishori.model.User;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Map;

public class member extends Fragment {



    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener firebaseAuthStaticListener;
    RecyclerView recyclerView;
    UserAdopter adopter;
    FirebaseDatabase database;
    ArrayList<User> usersArrayList;
    Map<String,String> mp;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v1=inflater.inflate(R.layout.fragment_member, container, false);

        usersArrayList=new ArrayList<>();
        adopter=new UserAdopter(usersArrayList,getContext());
        recyclerView=v1.findViewById(R.id.members);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setAdapter(adopter);
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference reference=database.getReference().child("user").child("Faculty");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(DataSnapshot s1: snapshot.getChildren())
                {
                    String name=s1.child("name").getValue().toString();
                    String id=s1.child("id").getValue().toString();
                    String Url=s1.child("profileImageUrl").getValue().toString();
                    String uid=s1.getKey();
                    User user=new User(name,id,"0",Url,uid,null);
                    usersArrayList.add(user);
                }
                adopter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


        return v1;
    }

}