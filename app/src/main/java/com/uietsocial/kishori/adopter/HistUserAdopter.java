package com.uietsocial.kishori.adopter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uietsocial.kishori.Chat.ChatActivity;
import com.uietsocial.kishori.R;
import com.uietsocial.kishori.model.Message;
import com.uietsocial.kishori.model.User;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HistUserAdopter extends RecyclerView.Adapter<HistUserAdopter.ViewHolder> {
    Context context;
    List<User> userslist;
    boolean isChat;
     int read=0;
    public HistUserAdopter(List<User> userslist, Context context,boolean isChat) {
        this.context = context;
        this.userslist = userslist;
        this.isChat=isChat;
    }
    @NotNull
    @Override
    public HistUserAdopter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_history_profile,parent,false);
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


        holder.mstatus.setText(users.getStatus());

        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("chats");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(DataSnapshot s2:snapshot.getChildren())
                {
                    Message message=s2.getValue(Message.class);

                    if(message.getSenderId().equals(users.getUid()) && message.getReceiverId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()) && message.getRead().equals("false") )
                    {
                        read++;
                    }
                }
                if(read!=0)
                {
                    holder.mread.setVisibility(View.VISIBLE);
                }
                else
                {
                    holder.mread.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });





        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context,ChatActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
        ImageView mprofilepic,mread;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            mname=itemView.findViewById(R.id.namehis);
            mprofilepic=itemView.findViewById(R.id.profilepichis);
            mstatus=itemView.findViewById(R.id.status);
            mread=itemView.findViewById(R.id.imageunread);


        }
    }

}
