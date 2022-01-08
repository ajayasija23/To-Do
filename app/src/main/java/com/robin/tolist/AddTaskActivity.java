package com.robin.tolist;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.robin.tolist.databinding.ActivityAddTaskBinding;
import com.robin.tolist.db.DatabaseManager;
import com.robin.tolist.model.Task;

import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private ActivityAddTaskBinding binding;
    private DatabaseManager dbManager;
    private Task task;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddTaskBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);
        setData();
        dbManager=new DatabaseManager(this);
        dbManager.open();
        binding.tvDate.setOnClickListener(this);
    }

    private void setData() {
        getSupportActionBar().setTitle("Add Task");
        Intent intent=getIntent();
        task=intent.getParcelableExtra("parcel");
        if (task!=null){
            binding.etTask.setText(task.getTask());
            binding.tvDate.setText(task.getDate());
            getSupportActionBar().setTitle("Edit Task");
            binding.etTaskDetails.setText(task.getDetails());
            if (task.getPriority().equalsIgnoreCase("important"))
                binding.cbImportant.setChecked(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_done, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (isValid()){
            addTaskInDb();
        }
        return true;
    }

    private void addTaskInDb() {
        Task task=new Task(
            binding.etTask.getText().toString(),
            binding.tvDate.getText().toString(),
            binding.etTaskDetails.getText().toString(),
            binding.cbImportant.isChecked()?binding.cbImportant.getText().toString():"Not Important",
            "pending"
        );
        if (this.task==null){
            long rows=dbManager.insert(task);
            if(rows>0){
                startActivity(new Intent(this,HomeActivity.class));
                finish();
            }
        }else {
            task.setId(this.task.getId());
            long rows=dbManager.updateTask(task);
            if(rows>0){
                startActivity(new Intent(this,HomeActivity.class));
                finish();
            }
        }
    }

    private boolean isValid() {
        boolean valid=true;
        if (binding.tvDate.getText().toString().isEmpty()){
            valid=false;
            binding.tvDate.setError("Select a valid date");
        }
        if (binding.etTask.getText().toString().isEmpty()){
            valid=false;
            binding.etTask.setError("Enter the task you have to do");
        }
        return valid;
    }

    @Override
    public void onClick(View v) {
        Calendar calendar=Calendar.getInstance();
        int date=calendar.get(Calendar.DATE);
        int month=calendar.get(Calendar.MONTH);
        int year=calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog=new DatePickerDialog(this,this,year,month,date);
       // datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        binding.tvDate.setText(dayOfMonth+"-"+(month+1)+"-"+year);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManager.close();
    }
}
