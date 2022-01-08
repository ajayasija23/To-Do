package com.robin.tolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.robin.tolist.db.DatabaseManager;
import com.robin.tolist.model.Task;

import java.util.ArrayList;
import java.util.List;

public class CompletedItemAdapter extends RecyclerView.Adapter<CompletedItemAdapter.MyViewHolder> {

    private List<Task> data;
    private Context context;

    public CompletedItemAdapter(List<Task> data, Context context) {
        this.data = data;
        this.context = context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvTask.setText(data.get(position).getTask());
        holder.tvDate.setText(data.get(position).getDate());
        holder.switchMaterial.setChecked(true);
        holder.switchMaterial.setEnabled(false);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTask,tvDate;
        private SwitchMaterial switchMaterial;
        private MaterialCardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTask=itemView.findViewById(R.id.tvTask);
            tvDate=itemView.findViewById(R.id.tvDate);
            switchMaterial=itemView.findViewById(R.id.switchMaterial);
            cardView=itemView.findViewById(R.id.cardItem);
        }
    }
}
