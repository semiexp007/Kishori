package com.uietsocial.kishori.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.uietsocial.kishori.adopter.showfac_postAdopter;
import com.uietsocial.kishori.model.Imagepost;
import com.uietsocial.kishori.postActivity;
import com.uietsocial.kishori.studentLogin;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class facultyProfile extends Fragment {

    ImageView pro,mpost;
    TextView mname,mrole;
    Button mlogout,msave,mphoto,mtext;
  FirebaseAuth auth;
  RecyclerView mrecphoto,mrecText;
  ArrayList<Imagepost>imgpost;
  showfac_postAdopter adopter;
  String url;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View view =inflater.inflate(R.layout.fragment_faculty_profile, container, false);
      auth=FirebaseAuth.getInstance();
      pro=view.findViewById(R.id.faculpro);
      mname=view.findViewById(R.id.namefc);
      mpost=view.findViewById(R.id.post);
      mrole=view.findViewById(R.id.role);
      mlogout=view.findViewById(R.id.logout);
      mphoto=view.findViewById(R.id.pics);
      mtext=view.findViewById(R.id.Texts);
      mrecphoto=view.findViewById(R.id.recpics);
      mrecText=view.findViewById(R.id.recText);
      imgpost=new ArrayList<>();
      adopter=new showfac_postAdopter(getContext(),imgpost);
     mrecphoto.setLayoutManager(new GridLayoutManager(getContext(),3));
        mrecphoto.setHasFixedSize(true);
     mrecphoto.setAdapter(adopter);
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
                    url=snapshot.child("profileImageUrl").getValue().toString();
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


               startActivity(new Intent(getContext(), studentLogin.class).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
               getActivity().finish();
           }
       });
        mrecphoto.setVisibility(View.VISIBLE);
        mrecText.setVisibility(View.GONE);
       mphoto.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               mrecphoto.setVisibility(View.VISIBLE);
               mrecText.setVisibility(View.GONE);

               mphoto.setBackgroundColor(getResources().getColor(R.color.purple_500));
               mtext.setBackgroundColor(getResources().getColor(R.color.white));


           }
       });
        mtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mrecphoto.setVisibility(View.GONE);
                mrecText.setVisibility(View.VISIBLE);

                mtext.setBackgroundColor(getResources().getColor(R.color.purple_500));
                mphoto.setBackgroundColor(getResources().getColor(R.color.white));
            }
        });
        mpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getContext(), postActivity.class);
                intent.putExtra("name",mname.getText().toString());
                intent.putExtra("userImageUrl",url);
                startActivity(intent);
            }
        });
        posts();

        return view ;
    }

    private void posts() {
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference();
        reference.child("posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
               imgpost.clear();
                for(DataSnapshot snapshot1:snapshot.getChildren())
                {
                    String postedby=snapshot1.child("postedby").getValue().toString();
                    String post=snapshot1.child("postImageUrl").getValue().toString();
                    Imagepost img=new Imagepost(null,post,null,null,null,null);
                    if(postedby.equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                    {

                        imgpost.add(img);
                    }
                }
                adopter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }


}