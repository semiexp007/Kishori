package com.uietsocial.kishori.adopter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.uietsocial.kishori.R;
import com.uietsocial.kishori.fragments.profile;
import com.uietsocial.kishori.treatmets;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class IssueUnsolved extends RecyclerView.Adapter<IssueUnsolved.ViewHolder> {
   Context mcontext;
    List<String> mlistIssueun;
    List<String>mtype;
    List<String>type;

    public IssueUnsolved(Context mcontext, List<String> mlistIssueun,List<String>mtype) {
        this.mcontext= mcontext;
        this.mlistIssueun = mlistIssueun;
        this.mtype=mtype;

    }

    @Override
    public IssueUnsolved.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mcontext).inflate(R.layout.unsoved_item,parent,false);
        return new IssueUnsolved.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.issuedis.setText(mlistIssueun.get(position));
           String s=holder.issuedis.getText().toString();
       // DatabaseReference referece=FirebaseDatabase.getInstance().getReference();
       DatabaseReference referece1=FirebaseDatabase.getInstance().getReference().child("Treatments").child(s);
               referece1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                int p = 0;
                if(snapshot.exists()) {
                   // DatabaseReference referece2=FirebaseDatabase.getInstance().getReference().child("Treatments").child(s);

                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        String name=snapshot1.getKey();
                        if(mtype.contains(name))
                        {
                            p=1;
                        }
                    }
                    if(p!=0)
                    {
                        holder.treat.setVisibility(View.VISIBLE);
                    }

                }


            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });



    }



    @Override
    public int getItemCount() {

        return mlistIssueun.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView issuedis;
        ImageView mdone,treat;


        public ViewHolder(@NonNull  View itemView) {
            super(itemView);
            issuedis=itemView.findViewById(R.id.issue_unsolved);
            mdone=itemView.findViewById(R.id.done);
            treat=itemView.findViewById(R.id.treatunsolved);
            treat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent =new Intent(mcontext, treatmets.class);
                    intent.putExtra("issue",issuedis.getText().toString());
                    mcontext.startActivity(intent);

                }
            });



            mdone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    FirebaseDatabase database2=FirebaseDatabase.getInstance();
                    DatabaseReference ref6=database2.getReference().child("user").child("Student").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Solved").child(issuedis.getText().toString());
                    ref6.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            String data="1";
                            if(snapshot.exists())
                            {
                                data=snapshot.child("Value").getValue().toString();
                                int k=Integer.parseInt(data);
                                data=Integer.toString(k+1);

                            }
                            FirebaseDatabase database3 = FirebaseDatabase.getInstance();
                            DatabaseReference reference=database3.getReference().child("user").child("Student").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Solved").child(issuedis.getText().toString());
                            reference.child("Value").setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {


                                }
                            });

                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });


                    DatabaseReference ref=database2.getReference().child("user").child("Student").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Issue").child(issuedis.getText().toString());
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            String data=snapshot.child("Value").getValue().toString();

                            FirebaseDatabase database3 = FirebaseDatabase.getInstance();
                            DatabaseReference reference=database3.getReference().child("user").child("Student").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Issue").child(issuedis.getText().toString());

                                int k=Integer.parseInt(data);
                                data=Integer.toString(k);
                                reference.child("Value").setValue("0").addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {


                                    }
                                });
                          //setting graph value
                            FirebaseDatabase databasex= FirebaseDatabase.getInstance();
                            DatabaseReference refer=databasex.getReference().child("SolvedIssues").child(issuedis.getText().toString());
                            refer.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                    String data="0";
                                    if(snapshot.exists())
                                    {
                                        data=snapshot.getValue().toString();
                                    }
                                    int  i=Integer.parseInt(data);
                                    FirebaseDatabase database3 = FirebaseDatabase.getInstance();
                                    DatabaseReference refer=database3.getReference().child("SolvedIssues").child(issuedis.getText().toString());
                                    i += 1;
                                    data = Integer.toString(i);
                                    refer.setValue(data);
                                }

                                @Override
                                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                }
                            });









                            //setting tags value to 0
                                DatabaseReference reference1=database3.getReference().child("user").child("Student").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Issue").child(issuedis.getText().toString()).child("Tags");
                                reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                        type=new ArrayList<>();
                                       for(DataSnapshot s2: snapshot.getChildren()){
                                           if(!s2.getValue().toString().equals("0")){

                                               DatabaseReference reference5=database3.getReference().child("user").child("Student").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Issue").child(issuedis.getText().toString()).child("Tags");
                                               reference5.child(s2.getKey()).setValue("0");
                                               DatabaseReference ref6=database3.getReference().child("user").child("Student").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Solved").child(issuedis.getText().toString()).child("Tags");
                                               ref6.child(s2.getKey()).setValue("Solved");
                                           }
                                       }

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


                    Toast.makeText(mcontext,"Happy to help you", Toast.LENGTH_SHORT).show();


                }
            });



        }
    }
}
