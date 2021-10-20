package com.uietsocial.kishori;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uietsocial.kishori.adopter.myIssueAdopter;
import com.uietsocial.kishori.fragments.student;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class showingStudentsProfile extends AppCompatActivity {
    ImageView imageView;
    RecyclerView recyclerView;
    ArrayList<String>isslist;
    myIssueAdopter adapter;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showing_students_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);

        imageView=findViewById(R.id.studpic);
        textView=findViewById(R.id.notedata);
        isslist=new ArrayList<>();

        recyclerView=findViewById(R.id.problems);
        adapter=new myIssueAdopter(showingStudentsProfile.this,isslist);
        recyclerView.setLayoutManager(new LinearLayoutManager(showingStudentsProfile.this));
        recyclerView.setAdapter(adapter);

        String url=getIntent().getStringExtra("Url");
        String uid=getIntent().getStringExtra("UserUid");
        Glide.with(showingStudentsProfile.this).load(url).into(imageView);


        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("user").child("Student").child(uid).child("Issue");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
              isslist.clear();
              for(DataSnapshot snapshot1:snapshot.getChildren()){
                  if(!snapshot1.getValue().toString().equals("0"))
                  {
                      isslist.add(snapshot1.getKey());
                  }
              }
               adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }
    public boolean onSupportNavigateUp() {

        finish();
        return super.onSupportNavigateUp();
    }
}