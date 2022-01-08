package com.robin.tolist;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.robin.tolist.databinding.ActivityHomeBinding;
import com.robin.tolist.db.DatabaseManager;
import com.robin.tolist.model.Task;

import java.util.ArrayList;

public class CompletedTaskActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //it will binding your layout to your binding object
        binding=ActivityHomeBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        binding.btnAddTask.setVisibility(View.GONE);
        setContentView(view);
        getSupportActionBar().setTitle("Completed Task");
        dbManager=new DatabaseManager(this);
        dbManager.open();
        setList();
    }

    private void setList() {
        try {
            ArrayList<Task> tasks=dbManager.queryCompleted();
            binding.rvTasks.setAdapter(new CompletedItemAdapter(tasks,this));
            Log.d("no of tasks added",tasks.size()+"");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
