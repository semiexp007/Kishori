package com.uietsocial.kishori.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uietsocial.kishori.R;

import org.jetbrains.annotations.NotNull;


public class NoticeFragment extends Fragment {

  TextView mnotice;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_notice, container, false);
        mnotice=view.findViewById(R.id.notes);
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
        reference.child("Notice").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

            if(snapshot.exists()) {
                String date = snapshot.child("date").getValue().toString();

                String note = snapshot.child("note").getValue().toString();
                mnotice.setText( System.getProperty("line.separator") + System.getProperty("line.separator") +
                        "Date : " + date + System.getProperty("line.separator") +
                                System.getProperty("line.separator")+
                        "Dear students ," + System.getProperty("line.separator") + note+
                        System.getProperty("line.separator")+
                        System.getProperty("line.separator")+System.getProperty("line.separator")
                         + "Thank you" + System.getProperty("line.separator") +
                        "Health Club.");
            }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        return view;
    }
}