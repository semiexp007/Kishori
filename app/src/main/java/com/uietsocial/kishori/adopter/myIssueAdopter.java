package com.uietsocial.kishori.adopter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uietsocial.kishori.Chat.MessageAdopter;
import com.uietsocial.kishori.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class myIssueAdopter extends RecyclerView.Adapter<myIssueAdopter.ViewHolder> {
    Context context;
    List<String> myissue;


    public myIssueAdopter(Context context, List<String> myissue) {
        this.context = context;
        this.myissue = myissue;
    }

    @NonNull
    @NotNull
    @Override
    public myIssueAdopter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.myissues,parent,false);
        return new myIssueAdopter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull myIssueAdopter.ViewHolder holder, int position) {

        holder.missutext.setText("  ※•   "+myissue.get(position));

    }

    @Override
    public int getItemCount() {
        return myissue.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView missutext;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            missutext=itemView.findViewById(R.id.myiis);
        }
    }
}
