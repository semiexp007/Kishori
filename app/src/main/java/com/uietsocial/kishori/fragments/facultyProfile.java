package com.uietsocial.kishori.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uietsocial.kishori.R;
import com.uietsocial.kishori.SetProfilePic;
import com.uietsocial.kishori.studentLogin;

import org.jetbrains.annotations.NotNull;


public class facultyProfile extends Fragment {

    ImageView pro;
    TextView mname,mrole;
    Button mlogout,msave;
  FirebaseAuth auth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View view =inflater.inflate(R.layout.fragment_faculty_profile, container, false);
      auth=FirebaseAuth.getInstance();
      pro=view.findViewById(R.id.faculpro);
      mname=view.findViewById(R.id.namefc);
      mrole=view.findViewById(R.id.role);
      mlogout=view.findViewById(R.id.logout);
      msave=view.findViewById(R.id.save);
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference reference=database.getReference().child("user").child("Faculty").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                if(snapshot.exists())
                {
                    String name=snapshot.child("name").getValue().toString();
                    String role=snapshot.child("id").getValue().toString();
                    String url=snapshot.child("profileImageUrl").getValue().toString();
                    mname.setText(name);
                    mrole.setText(role);
                    if(!url.equals("default"))
                    {
                        Glide.with(facultyProfile.this).load(url).into(pro);
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
       msave.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent=new Intent(getContext(),SetProfilePic.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
               intent.putExtra("usercat","Faculty");
               startActivity(intent);

           }
       });

       mlogout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               auth.signOut();


               startActivity(new Intent(getContext(), studentLogin.class));
               getActivity().finish();
           }
       });

        return view ;
    }



}