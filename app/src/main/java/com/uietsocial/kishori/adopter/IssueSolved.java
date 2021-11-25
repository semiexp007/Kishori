package com.uietsocial.kishori.adopter;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uietsocial.kishori.R;
import com.uietsocial.kishori.Searchadd;
import com.uietsocial.kishori.treatmets;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class IssueSolved extends RecyclerView.Adapter<IssueSolved.ViewHolder> {


  Context mcontext;
    List<String> mlistIssue;
    List<String> listcount;
    List<String>mtype;

    public IssueSolved(Context mcontext, List<String> mlistIssue, List<String> listcount,List<String>mtype) {
        this.mcontext= mcontext;
        this.mlistIssue = mlistIssue;
        this.listcount=listcount;
        this.mtype=mtype;
    }

    @Override
    public IssueSolved.ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mcontext).inflate(R.layout.solved_item,parent,false);
        return new IssueSolved.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.issuedis.setText(mlistIssue.get(position));
        holder.counter.setText(listcount.get(position));
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

        return mlistIssue.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView issuedis,counter;
        ImageView treat;

        public ViewHolder(@NonNull  View itemView) {
            super(itemView);
            issuedis=itemView.findViewById(R.id.issue_solved);
            counter=itemView.findViewById(R.id.counter);
            treat=itemView.findViewById(R.id.treatsolved);
            treat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent =new Intent(mcontext, treatmets.class);
                    intent.putExtra("issue",issuedis.getText().toString());
                    mcontext.startActivity(intent);

                }
            });
        }
    }

}
