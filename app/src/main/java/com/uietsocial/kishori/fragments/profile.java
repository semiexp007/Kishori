package com.uietsocial.kishori.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.uietsocial.kishori.adopter.IssueSolved;
import com.uietsocial.kishori.adopter.IssueUnsolved;
import com.uietsocial.kishori.SetProfilePic;
import com.uietsocial.kishori.studentLogin;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class profile extends Fragment {
    FirebaseDatabase database;
TextView mtotal,msolved,munsolved,mname,mroll;
ImageView mprofileimage;
CardView cardView;
Button mEdit,msolvedbar,munsolvedbar,mlogout;
RecyclerView r1,r2;
IssueSolved adptersolved;
IssueUnsolved adpterunsolved;

    private List<String> mlistissue;
    private List<String> mCount;
    private List<String> mlistissueun;
    private List<String> t;
    private List<String>t2;
    int ans=0,sol=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View v=inflater.inflate(R.layout.fragment_profile, container, false);
       database=FirebaseDatabase.getInstance();

       mtotal=v.findViewById(R.id.total);
       msolved=v.findViewById(R.id.solved);
       munsolved=v.findViewById(R.id.Unsolved);
       mname=v.findViewById(R.id.name);
       mroll=v.findViewById(R.id.roll);
       mprofileimage=v.findViewById(R.id.profilepic);
       mEdit=v.findViewById(R.id.edit);
  mlogout=v.findViewById(R.id.logout);
       msolvedbar=v.findViewById(R.id.solveditem);
       munsolvedbar=v.findViewById(R.id.unsolveditem);
       mlistissue=new ArrayList<>();
       mCount=new ArrayList<>();
       mlistissueun=new ArrayList<>();
       t=new ArrayList<>();
        t2=new ArrayList<>();
       r1=v.findViewById(R.id.recsolveditem);
       r2=v.findViewById(R.id.recunsolveditem);
        r1.setHasFixedSize(true);
        r1.setLayoutManager(new LinearLayoutManager(getContext()));
        adptersolved=new IssueSolved(getContext(), mlistissue, mCount,t2);
        r1.setAdapter(adptersolved);
        r2.setLayoutManager(new LinearLayoutManager(getContext()));
        adpterunsolved=new IssueUnsolved(getContext(), mlistissueun,t);
        r2.setAdapter(adpterunsolved);





        r1.setVisibility(View.VISIBLE);
        r2.setVisibility(View.GONE);

        msolvedbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.GONE);
                msolvedbar.setBackgroundColor(getResources().getColor(R.color.purple_500));
                munsolvedbar.setBackgroundColor(getResources().getColor(R.color.white));
            }
        });

        munsolvedbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                r1.setVisibility(View.GONE);
                r2.setVisibility(View.VISIBLE);
                munsolvedbar.setBackgroundColor(getResources().getColor(R.color.purple_500));
                msolvedbar.setBackgroundColor(getResources().getColor(R.color.white));

            }
        });
        readSolved();
        readUnsolved();


       //retrieving data from student database
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference reference=database.getReference().child("user").child("Student").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {

                    String name=snapshot.child("name").getValue().toString();
                    String roll=snapshot.child("id").getValue().toString();
                    String profileUrl=snapshot.child("profileImageUrl").getValue().toString();
                    mname.setText(name);
                    mroll.setText(roll);
                    if(!profileUrl.equals("default"))
                    {

                        Glide.with(profile.this).load(profileUrl).into(mprofileimage);

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {


            }
        });
   // reading total issue raised by the person
        readTotal();

        //reading data of solved issue;
        readTotalUsolved();

        //editing Image activity
        mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),SetProfilePic.class);
                intent.putExtra("usercat","Student");
                startActivity(intent);


            }
        });

       mlogout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               FirebaseAuth.getInstance().signOut();
               startActivity(new Intent(getContext(), studentLogin.class).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
               getActivity().finish();
           }
       });



        return v;

    }

    private void readTotalUsolved() {
        DatabaseReference ref2=database.getReference().child("user").child("Student").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Issue");
        ref2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                int t=0;
                if(snapshot.exists()) {
                    for (DataSnapshot sp : snapshot.getChildren()) {
                        String data = sp.child("Value").getValue().toString();
                        int i = Integer.parseInt(data);
                        t += i;
                    }
                }
                String b=Integer.toString(t+ans);
                String c=Integer.toString(ans);
                mtotal.setText(b);
                msolved.setText(c);

                munsolved.setText(Integer.toString(t));

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void readTotal() {
        DatabaseReference ref = database.getReference().child("user").child("Student").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Solved");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                int t=0,sol=0;
                if(snapshot.exists()) {
                    for (DataSnapshot sp : snapshot.getChildren()) {
                        String data = sp.child("Value").getValue().toString();
                        int i = Integer.parseInt(data);
                        t += i;
                    }
                }
                ans=t;
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }




    private void readUnsolved() {

        DatabaseReference ref5 = database.getReference().child("user").child("Student").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Issue");
        ref5.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                if(snapshot.exists()) {
                    mlistissueun.clear();
                    t.clear();
                    for (DataSnapshot s1 : snapshot.getChildren()) {
                        String name = s1.getKey();
                        String c = s1.child("Value").getValue().toString();
                        String c2;

                        if (!c.equals("0")) {
                            mlistissueun.add(name);
                            DatabaseReference ref5 = database.getReference().child("user").child("Student").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Issue");
                            ref5.child(name).child("Tags").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                        String k = snapshot1.getValue().toString();
                                        if (!k.equals("0")) {
                                            t.add(snapshot1.getKey());
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                }
                            });
                        }


                    }
                }
                adpterunsolved.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }


    private void readSolved() {
        DatabaseReference ref4 = database.getReference().child("user").child("Student").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Solved");
        ref4.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    mlistissue.clear();
                    t2.clear();
                    for (DataSnapshot s1 : snapshot.getChildren()) {
                        String name = s1.getKey();
                        String c = s1.child("Value").getValue().toString();
                        if (!c.equals("0")) {
                            mlistissue.add(name);
                            mCount.add(c);
                            DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("user").child("Student").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Solved").child(name)
                                    .child("Tags");
                            dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                        t2.add(snapshot1.getKey());
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                }
                            });
                        }
                    }
                }
                adptersolved.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }


}