package com.uietsocial.kishori;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.uietsocial.kishori.adopter.IssueAdopter;
import com.uietsocial.kishori.model.issueName;

import java.util.ArrayList;
import java.util.List;

public class Searchadd extends AppCompatActivity {
    FirebaseDatabase database;
    List<String> listIssue;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    ProgressDialog progressDialog;
    IssueAdopter adpter;
    AppCompatAutoCompleteTextView search_bar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchadd);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listIssue=new ArrayList<>();
        adpter=new IssueAdopter(Searchadd.this,listIssue);
        search_bar=findViewById(R.id.search_bar);
        recyclerView=findViewById(R.id.raiseissues);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setAdapter(adpter);


        database=FirebaseDatabase.getInstance();

        DatabaseReference reference=database.getReference().child("Issues");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                for(DataSnapshot snapshot1: snapshot.getChildren())
                { listIssue.add(snapshot1.getKey());}

                adpter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               filter(s.toString());
            }
        });

    }
    @Override
    public boolean onSupportNavigateUp() {

    // startActivity(new Intent( Searchadd.this,Home.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
        return super.onSupportNavigateUp();
    }


    private void filter(String s) {

        Query reference=database.getReference().child("Issues");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                listIssue.clear();
                for(DataSnapshot snap:snapshot.getChildren())
                {
                    String iss=snap.getKey();
                    if(iss.toLowerCase().contains(s.toLowerCase()))
                    {
                        listIssue.add(iss);
                    }

                }
                adpter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });


    }





}