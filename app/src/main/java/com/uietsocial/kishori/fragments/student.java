package com.uietsocial.kishori.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uietsocial.kishori.R;


public class student extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view =inflater.inflate(R.layout.fragment_student, container, false);

        return view;
    }
}