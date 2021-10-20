package com.uietsocial.kishori.adopter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.uietsocial.kishori.R;
import com.uietsocial.kishori.model.Imagepost;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class showfac_postAdopter extends RecyclerView.Adapter<showfac_postAdopter.ViewHolder> {
    Context context;
    List<Imagepost> mImgpost;

    public showfac_postAdopter(Context context, List<Imagepost> mImgpost) {
        this.context = context;
        this.mImgpost = mImgpost;
    }

    @NonNull
    @NotNull
    @Override
    public showfac_postAdopter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.imagewalapost,parent,false);
        return new showfac_postAdopter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull showfac_postAdopter.ViewHolder holder, int position) {
       Imagepost imagepost=mImgpost.get(position);
        Glide.with(context).load(imagepost.getUrl()).into(holder.mpostImg);

    }

    @Override
    public int getItemCount() {
        return mImgpost.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mpostImg;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            mpostImg=itemView.findViewById(R.id.imgwlapost);
        }
    }
}
