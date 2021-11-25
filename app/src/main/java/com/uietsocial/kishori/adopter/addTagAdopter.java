package com.uietsocial.kishori.adopter;

import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uietsocial.kishori.BuildConfig;
import com.uietsocial.kishori.Home;
import com.uietsocial.kishori.R;
import com.uietsocial.kishori.Searchadd;
import com.uietsocial.kishori.addTages;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class addTagAdopter extends RecyclerView.Adapter<addTagAdopter.ViewHolder> {

    List<String>mtags;
    addTages context;
    String type;
    public addTagAdopter() {
        if(BuildConfig.DEBUG)
            StrictMode.enableDefaults();
    }
    public addTagAdopter(List<String> mtags, addTages context,String type) {
        this.mtags = mtags;
        this.context = context;
        this.type=type;
    }



    @NonNull
    @NotNull
    @Override
    public addTagAdopter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.tags,parent,false);

        return new addTagAdopter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull addTagAdopter.ViewHolder holder, int position) {
        holder.tagText.setText(mtags.get(position));


    }

    @Override
    public int getItemCount() {
        return mtags.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tagText;
        ImageView addtagImage;
        ProgressBar progress;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tagText=itemView.findViewById(R.id.tagstype);
            addtagImage=itemView.findViewById(R.id.addthistag);
           // progress=itemView.findViewById(R.id.progress);
            addtagImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    String tag=tagText.getText().toString();
                    // updating user's data  of issues
                    FirebaseDatabase database=FirebaseDatabase.getInstance();
                    DatabaseReference reference=database.getReference().child("user").child("Student").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Issue");
                    reference.child(type).child("Tags").child(tag).setValue("1").addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                                                   }
                    });



                    DatabaseReference reference1=database.getReference().child("user").child("Student").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Issue").child(type);
                    reference1.child("Value").setValue("1").addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {


                            //updating graph data

                            DatabaseReference reference2=database.getReference().child("Issues");

                            reference2.child(type).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String data=snapshot.getValue().toString();
                                    int  i=Integer.parseInt(data);

                                   // FirebaseDatabase database4 = FirebaseDatabase.getInstance();
                                    DatabaseReference ref = database.getReference().child("Issues");
                                    i += 1;
                                    data = Integer.toString(i);
                                    ref.child(type).setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {

                                            Toast.makeText(context, "Issue will be solve soon", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }
                    });




                }
            });

        }
    }
}
