package com.uietsocial.kishori.adopter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;
import android.os.Bundle;

import com.uietsocial.kishori.R;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.uietsocial.kishori.Chat.ChatActivity;
import com.uietsocial.kishori.Home;
import com.uietsocial.kishori.R;
import com.uietsocial.kishori.model.User;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class UserAdopter extends RecyclerView.Adapter<UserAdopter.ViewHolder> {

  Context context;
    ArrayList<User>userslist;
    String UserUid;
    public UserAdopter( ArrayList<User> userslist,Context context) {
        this.context = context;
        this.userslist = userslist;
    }



    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.profilegrid,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        User users=userslist.get(position);
        holder.mname.setText(users.getName());
        holder.mroll.setText(users.getId());
        holder.Issuecount.setText("Rating : "+ users.getIssue());

        if(!users.getProfilePicUrl().equals("default"))
        {
            Glide.with(context).load(users.getProfilePicUrl()).into(holder.mprofilepicfac);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context,ChatActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("Username",users.getName());
                intent.putExtra("UserUid",users.getUid());
                intent.putExtra("UserName","someone");
                context.startActivity(intent);
            }
        });
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
