package com.uietsocial.kishori.adopter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uietsocial.kishori.R;
import com.uietsocial.kishori.model.treatmentmodel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class treatmentAdapter extends RecyclerView.Adapter<treatmentAdapter.ViewHolder> {

    Context context;
    List<treatmentmodel>mtreat;
    public treatmentAdapter(Context context, List<treatmentmodel> mtreat) {
        this.context = context;
        this.mtreat = mtreat;
    }



    @NonNull
    @NotNull
    @Override
    public treatmentAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.tretshowing,parent,false);

        return new treatmentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull treatmentAdapter.ViewHolder holder, int position) {
        treatmentmodel tr=mtreat.get(position);
        holder.cat.setText(tr.getType());
        holder.symp.setText(tr.getSymp());
        holder.treat.setText(tr.getTreat());

    }

    @Override
    public int getItemCount() {
        return mtreat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView cat,symp,treat;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            cat=itemView.findViewById(R.id.Type);
            symp=itemView.findViewById(R.id.symp);
            treat=itemView.findViewById(R.id.treatment);

        }
    }
}
