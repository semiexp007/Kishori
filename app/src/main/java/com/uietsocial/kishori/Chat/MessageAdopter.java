package com.uietsocial.kishori.Chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.uietsocial.kishori.R;
import com.uietsocial.kishori.model.Message;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MessageAdopter extends RecyclerView.Adapter{

Context context;
List<Message>messages;
final  int Item_sent=1;
    final  int Item_received=2;
    public MessageAdopter(Context context,List<Message>messages){
        this.context=context;
        this.messages=messages;
    }

    @NonNull
    @NotNull
    @Override

    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        if(viewType==Item_sent)
        {
            View view =LayoutInflater.from(context).inflate(R.layout.item_send,parent,false);
            return new SentViewHolder(view);
        }
        else
        {
            View view =LayoutInflater.from(context).inflate(R.layout.item_received,parent,false);
            return new ReceiverViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {

        Message message=messages.get(position);
        if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(message.getSenderId()))
        {
            return Item_sent;
        }
        else
            return Item_received;



    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {

        Message message=messages.get(position);

        if(holder.getClass()== SentViewHolder.class)
        {
           SentViewHolder viewHolder=(SentViewHolder)holder;
          viewHolder.mes.setText(message.getMessage());

        }
        else
        {
            ReceiverViewHolder viewHolder=(ReceiverViewHolder)holder;
            viewHolder.mes2.setText(message.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class SentViewHolder extends RecyclerView.ViewHolder{
        TextView mes;
        public SentViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            mes=itemView.findViewById(R.id.showMessage);

        }
    }

    public class ReceiverViewHolder extends RecyclerView.ViewHolder{
        TextView mes2;
        public ReceiverViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            mes2=itemView.findViewById(R.id.showMessage2);
        }
    }


}
