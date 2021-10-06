package com.uietsocial.kishori.adopter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.uietsocial.kishori.Chat.ChatActivity;
import com.uietsocial.kishori.R;
import com.uietsocial.kishori.model.User;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HistUserAdopter extends RecyclerView.Adapter<HistUserAdopter.ViewHolder> {
    Context context;
    List<User> userslist;
   boolean isChat;
    public HistUserAdopter(List<User> userslist, Context context) {
        this.context = context;
        this.userslist = userslist;
        this.isChat=isChat;
    }
    @NotNull
    @Override
    public HistUserAdopter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.chat_history_profile,parent,false);
        return new HistUserAdopter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull HistUserAdopter.ViewHolder holder, int position) {
        User users=userslist.get(position);
        holder.mname.setText(users.getName());


        if(!users.getProfilePicUrl().equals("default"))
        {
            Glide.with(context).load(users.getProfilePicUrl()).into(holder.mprofilepic);
        }




        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context,ChatActivity.class);
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
        TextView mname,mstatus;
        ImageView mprofilepic;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            mname=itemView.findViewById(R.id.namehis);
            mprofilepic=itemView.findViewById(R.id.profilepichis);
            mstatus=itemView.findViewById(R.id.status);



        }
    }

}
