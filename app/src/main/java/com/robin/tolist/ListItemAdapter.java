package com.robin.tolist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

public class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.MyViewHolder> {

    private final ArrayList<Task> expired;
    private List<Task> data;
    private Context context;
    private DatabaseManager dbManager;

    public ListItemAdapter(List<Task> data, Context context, DatabaseManager dbManager, ArrayList<Task> expired) {
        this.data = data;
        this.context = context;
        this.dbManager= dbManager;
        this.expired=expired;
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
        holder.switchMaterial.setChecked(false);
        if (expired.contains(data.get(position))){
            // task is expired
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.red));
        }

        else if(data.get(position).getPriority().equalsIgnoreCase("important")){
            // task is important
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.yellow));
        }
        else {
            //normal task
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.white));
        }
        holder.switchMaterial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    //mark the task as completed
                    dbManager.updateCompleted(data.get(position));
                    data.remove(data.get(position));
                    notifyDataSetChanged();
                }
            }
        });
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //delete task
                deleteItem(data.get(position));
                return true;
            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //update task
                Intent intent=new Intent(context,AddTaskActivity.class);
                intent.putExtra("parcel",data.get(position));
                context.startActivity(intent);
            }
        });
    }

    private void deleteItem(Task task) {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(R.string.confirm_delete));
        builder.setMessage(context.getResources().getString(R.string.confirm_delete_msg));
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                builder.create().dismiss();
                dbManager.delete(task);
                data.remove(task);
                notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                builder.create().dismiss();
            }
        });
        builder.show();
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
