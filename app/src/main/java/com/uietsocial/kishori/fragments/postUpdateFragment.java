package com.uietsocial.kishori.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uietsocial.kishori.R;
import com.uietsocial.kishori.adopter.showfac_postAdopter;
import com.uietsocial.kishori.adopter.showpostAdapter;
import com.uietsocial.kishori.model.Imagepost;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class postUpdateFragment extends Fragment {


    RecyclerView mrecposts;
    ArrayList<Imagepost> mpost;
  showpostAdapter adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view =inflater.inflate(R.layout.fragment_post_update, container, false);
       mrecposts=view.findViewById(R.id.recpostupdate);
       mpost=new ArrayList<>();
       adapter=new showpostAdapter(getContext(),mpost);
        LinearLayoutManager manager=new LinearLayoutManager(getContext());
       mrecposts.setLayoutManager(manager);
       manager.setStackFromEnd(true);
       mrecposts.setHasFixedSize(true);
       mrecposts.setAdapter(adapter);

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
        reference.child("posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1: snapshot.getChildren()){
                    String name=snapshot1.child("nameofuser").getValue().toString();
                    String posturl=snapshot1.child("postImageUrl").getValue().toString();
                    String comment=snapshot1.child("comment").getValue().toString();
                    String date=snapshot1.child("date").getValue().toString();
                    String userImageUrl=snapshot1.child("userImageUrl").getValue().toString();
                    Imagepost img=new Imagepost(name,posturl,comment,date,userImageUrl,null);

                    mpost.add(img);
                }
               adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        return view;
    }
}