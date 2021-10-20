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
import com.uietsocial.kishori.model.Imagepost;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class showpostAdapter extends RecyclerView.Adapter<showpostAdapter.ViewHolder> {
    Context context;
    List<Imagepost>mposts;

    public showpostAdapter(Context context, List<Imagepost> mposts) {
        this.context = context;
        this.mposts = mposts;
    }

    @NonNull
    @NotNull
    @Override
    public showpostAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.showpost,parent,false);
        return new showpostAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull showpostAdapter.ViewHolder holder, int position) {
        Imagepost imagepost=mposts.get(position);
        holder.musemane.setText(imagepost.getName());
        holder.mdate.setText(imagepost.getDate());
        holder.mcomment.setText(imagepost.getText());
        Glide.with(context).load(imagepost.getUrl()).into(holder.mpostpic);
        Glide.with(context).load(imagepost.getUserpic()).into(holder.muserpic);

    }

    @Override
    public int getItemCount() {
        return mposts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView muserpic,mpostpic;
        TextView musemane,mdate,mcomment;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            muserpic=itemView.findViewById(R.id.userpic);
            mpostpic=itemView.findViewById(R.id.postpic);
            musemane=itemView.findViewById(R.id.postedbyname);
            mdate=itemView.findViewById(R.id.postedondate);
            mcomment=itemView.findViewById(R.id.postComment);
        }
    }
}
