package com.uietsocial.kishori;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uietsocial.kishori.adopter.addTagAdopter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class addTages extends AppCompatActivity {
  RecyclerView mrecyclerView;
  addTagAdopter adapter;
  List<String> mtags;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tages);
        String issue=getIntent().getStringExtra("Issue");
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mtags= new ArrayList<>();
        mrecyclerView=findViewById(R.id.recycle_tags);
        adapter=new addTagAdopter(mtags,addTages.this,issue);
        mrecyclerView.setLayoutManager(new LinearLayoutManager(addTages.this));
        mrecyclerView.setHasFixedSize(true);
        mrecyclerView.setAdapter(adapter);

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
          reference.child("IssueWithTags").child(issue).addValueEventListener(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                  mtags.clear();
                  for(DataSnapshot snapshot1:snapshot.getChildren())
                  {
                      String tag=snapshot1.getKey();
                      mtags.add(tag);
                  }
                  adapter.notifyDataSetChanged();
              }

              @Override
              public void onCancelled(@NonNull @NotNull DatabaseError error) {

              }
          });
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

}