package com.robin.tolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.robin.tolist.databinding.ActivityHomeBinding;
import com.robin.tolist.db.DatabaseManager;
import com.robin.tolist.model.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityHomeBinding binding;
    private DatabaseManager dbManager;
    private ArrayList<Task> expired;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //it will binding your layout to your binding object
        binding=ActivityHomeBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);

        //create dbManager to manipulate db
        dbManager=new DatabaseManager(this);
        dbManager.open();
        setList();
        setListeners();
    }

    private void setList() {
        //set data in recycler view
       try {
           ArrayList<Task> tasks=dbManager.queryPending();
           tasks=arrangeList(tasks);
           binding.rvTasks.setAdapter(new ListItemAdapter(tasks,this,dbManager,expired));
       } catch (Exception e) {
           e.printStackTrace();
       }
    }

    private ArrayList<Task> arrangeList(ArrayList<Task> tasks) {
        //return the list setting expired task in the end of list
        ArrayList<Task> nonExpired=new ArrayList<>();
        expired=new ArrayList<>();
        for (Task task:tasks){
            if (isDateBeforeToday(task.getDate())){
                expired.add(task);
            }
            else{
                nonExpired.add(task);
            }

        }
        nonExpired.addAll(expired);
        return nonExpired;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //create over flow menu in top right corner
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //move to completed task on click overflowed menus
        startActivity(new Intent(this,CompletedTaskActivity.class));
        return true;
    }

    private void setListeners() {
        binding.btnAddTask.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //on click add task
        Intent intent=new Intent(this,AddTaskActivity.class);
        startActivity(intent);
    }

    public static boolean isDateBeforeToday(String dateString) {
        //check if date of task is before todays date
        SimpleDateFormat format=new SimpleDateFormat("d-M-yyyy");
        Date date=null;
        boolean result=false;
        try {
            date=format.parse(dateString);
            String today=format.format(new Date());
            result =date.before(format.parse(today));
        } catch (ParseException e) {
            Log.d("expired",e.getMessage());
        }
        return result;
    }
}