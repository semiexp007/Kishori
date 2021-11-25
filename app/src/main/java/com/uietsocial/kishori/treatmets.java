package com.uietsocial.kishori;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Adapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uietsocial.kishori.adopter.treatmentAdapter;
import com.uietsocial.kishori.model.treatmentmodel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class treatmets extends AppCompatActivity {
    RecyclerView recyclerView;
    treatmentAdapter adapter;
    List<treatmentmodel> mtreat;
    TextView problem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatmets);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        problem=findViewById(R.id.treatcat);
        String s=getIntent().getStringExtra("issue");
        problem.setText(s);
        mtreat=new ArrayList<>();
        adapter= new treatmentAdapter(treatmets.this,mtreat);
        recyclerView=findViewById(R.id.rectret);
        recyclerView.setLayoutManager( new LinearLayoutManager(treatmets.this));
        recyclerView.setAdapter(adapter);
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
        reference.child("Treatments").child(s).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    for(DataSnapshot snapshot1:snapshot.getChildren())
                    {
                        String type=snapshot1.getKey();
                        String symp=snapshot1.child("Symp").getValue().toString();
                        String treat=snapshot1.child("Treat").getValue().toString();
                        treatmentmodel t=new treatmentmodel(type,symp,treat);
                        mtreat.add(t);
                    }
                    adapter.notifyDataSetChanged();
                }
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