package com.uietsocial.kishori.adopter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.uietsocial.kishori.R;
import com.uietsocial.kishori.model.User;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class StudentAdopter extends RecyclerView.Adapter<StudentAdopter.ViewHolder>  {





    Context context;
    ArrayList<User> userslist;
    String UserUid;
    public StudentAdopter( ArrayList<User> userslist,Context context) {
        this.context = context;
        this.userslist = userslist;
    }

    @NonNull
    @NotNull
    @Override
    public StudentAdopter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.profilegrid,parent,false);
        return new StudentAdopter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull StudentAdopter.ViewHolder holder, int position) {
        User users=userslist.get(position);
        holder.mname.setText(users.getName());
        holder.mroll.setText(users.getId());
        if(!users.getProfilePicUrl().equals("default"))
        {
            Glide.with(context).load(users.getProfilePicUrl()).into(holder.mprofilepicfac);
        }
    }

    @Override
    public int getItemCount() {
        return userslist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mname,mroll,Issuecount;
        ImageView mprofilepicfac;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            mname=itemView.findViewById(R.id.name);
            mroll=itemView.findViewById(R.id.id_no);
            Issuecount=itemView.findViewById(R.id.issuecount);
            mprofilepicfac=itemView.findViewById(R.id.imageViewprofilepicgrid);
        }
    }
}
