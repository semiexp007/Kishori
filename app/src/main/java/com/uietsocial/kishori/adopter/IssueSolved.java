package com.uietsocial.kishori.adopter;

import android.content.Context;
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

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class IssueSolved extends RecyclerView.Adapter<IssueSolved.ViewHolder> {


  Context mcontext;
    List<String> mlistIssue;
    List<String> listcount;

    public IssueSolved(Context mcontext, List<String> mlistIssue, List<String> listcount) {
        this.mcontext= mcontext;
        this.mlistIssue = mlistIssue;
        this.listcount=listcount;
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
    }



    @Override
    public int getItemCount() {

        return mlistIssue.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView issuedis,counter;


        public ViewHolder(@NonNull  View itemView) {
            super(itemView);
            issuedis=itemView.findViewById(R.id.issue_solved);
            counter=itemView.findViewById(R.id.counter);


        }
    }

}
