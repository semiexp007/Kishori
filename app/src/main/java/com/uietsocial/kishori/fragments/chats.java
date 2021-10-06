package com.uietsocial.kishori.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.uietsocial.kishori.R;
import com.uietsocial.kishori.adopter.HistUserAdopter;
import com.uietsocial.kishori.model.User;
import com.uietsocial.kishori.model.Message;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class chats extends Fragment {

   RecyclerView recyclerView;
   FirebaseDatabase database;
   List<User> muser;
   List<String>userList;
   HistUserAdopter adapter;
   DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_chats, container, false);

        recyclerView=view.findViewById(R.id.recHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        userList=new ArrayList<>();
        muser=new ArrayList<>();

        FirebaseMessaging.getInstance().subscribeToTopic(FirebaseAuth.getInstance().getCurrentUser().getUid());


        reference=FirebaseDatabase.getInstance().getReference().child("chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                userList.clear();
                for(DataSnapshot snapshot1:snapshot.getChildren())
                {
                    Message message=snapshot1.getValue(Message.class);
                    if(message.getSenderId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                    {
                        int f=0;
                        for(String s:userList)
                        {
                            if(s.equals(message.getReceiverId()))
                            {
                                f=1;
                            }
                        }
                        if(f==0){
                            userList.add(message.getReceiverId());
                        }
                    }
                    if(message.getReceiverId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                    {
                        int f=0;
                        for(String s:userList)
                        {
                            if(s.equals(message.getSenderId()))
                            {
                                f=1;
                            }
                        }
                        if(f==0){
                            userList.add(message.getSenderId());
                        }
                    }
                }
                readChats();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


        return view;
    }



    private void readChats() {
        muser=new ArrayList<>();
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference();
        ref.child("user").child("Faculty").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
               muser.clear();
                for(DataSnapshot snapshot1:snapshot.getChildren())
                {

                    String name=snapshot1.child("name").getValue().toString();
                    String profilepic=snapshot1.child("profileImageUrl").getValue().toString();
                    String uUid=snapshot1.getKey();

                    User user=new User(name,null,null,profilepic,uUid,null);


                    for(String id:userList)
                    {
                        if(user.getUid().equals(id))
                        {

                                muser.add(user);

                        }
                    }
                }
                adapter=new HistUserAdopter(muser,getContext());
                recyclerView.setAdapter(adapter);



            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }




}