package com.uietsocial.kishori.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.uietsocial.kishori.R;
import com.uietsocial.kishori.help;
import com.uietsocial.kishori.vcmessage;


public class aboutFragment extends Fragment {
      Button mmessage,mhelp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_about, container, false);
        mhelp=view.findViewById(R.id.helpform);
        mmessage=view.findViewById(R.id.vcmessage);
        mmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             startActivity(new Intent(getContext(), vcmessage.class));


            }
        });
        mhelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), help.class));
            }
        });
        return view;
    }
}