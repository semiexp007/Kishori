package com.uietsocial.kishori.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uietsocial.kishori.FirebaseData;
import com.uietsocial.kishori.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class graph extends Fragment {

    BarChart barChart;


   private Map<String, Integer>type1;
  private   Map<String,Integer>type2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_graph, container, false);
        barChart = view.findViewById(R.id.barChart);
        type1=new HashMap<>();
        type2=new HashMap<>();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Issues");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                type1.clear();
                for(DataSnapshot snapshot1:snapshot.getChildren()) {
                    String isu = snapshot1.getKey();
                    String Value = snapshot1.getValue().toString();
                    int val = Integer.parseInt(Value);
                    type1.put(isu, val);


                }
                ArrayList<String> listOfKeys
                        = new ArrayList<String>(type1.keySet());
                DatabaseReference reference1=FirebaseDatabase.getInstance().getReference().child("SolvedIssues");
                reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        type2.clear();
                        for(DataSnapshot snapshot1:snapshot.getChildren())
                        {
                            String isu=snapshot1.getKey();
                            String Value=snapshot1.getValue().toString();
                            int val=Integer.parseInt(Value);
                            type2.put(isu,val);

                        }

                            ArrayList<BarEntry> barEntries1 = new ArrayList<>();


                            int i=1;
                            for (Map.Entry<String, Integer> me : type1.entrySet())
                            {
                                barEntries1.add(new BarEntry(i, me.getValue()));
                                i++;
                            }
                             ArrayList<BarEntry> barEntries2 = new ArrayList<>();



                                i=1;
                            for (Map.Entry<String, Integer> me : type1.entrySet()) {
                                if (type2.containsKey(me.getKey())) {
                                    barEntries2.add(new BarEntry(i, type2.get(me.getKey())));
                                } else {
                                    barEntries2.add(new BarEntry(i, 0));
                                }
                                i++;
                            }


                        BarDataSet barDataSet1 = new BarDataSet(barEntries1, "UNSOLVED");
                        barDataSet1.setColor(Color.RED);
                        BarDataSet barDataSet2 = new BarDataSet(barEntries2, "SOLVED");
                        barDataSet2.setColor(Color.GREEN);
                        BarData data = new BarData(barDataSet1, barDataSet2);
                        barChart.setData(data);


                        XAxis xAxis = barChart.getXAxis();
                        xAxis.setValueFormatter(new IndexAxisValueFormatter(listOfKeys));
                        xAxis.setCenterAxisLabels(true);
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis.setGranularity(1);
                        xAxis.setGranularityEnabled(true);

                        barChart.setDragEnabled(true);
                        barChart.setVisibleXRangeMaximum(4);

                        float barSpace = 0.008f;
                        float groupSpace = 0.80f;
                        data.setBarWidth(0.10f);

                        barChart.getXAxis().setAxisMinimum(0);
                        barChart.getXAxis().setAxisMaximum(0 + barChart.getBarData().getGroupWidth(groupSpace, barSpace) * type1.size());
                        barChart.getAxisLeft().setAxisMinimum(0);

                        barChart.groupBars(0, groupSpace, barSpace);
                        barChart.animateY(2000);
                        barChart.invalidate();


                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });



            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });







        // Creating an ArrayList of keys
        // by passing the keySet





        return view;

    }







}