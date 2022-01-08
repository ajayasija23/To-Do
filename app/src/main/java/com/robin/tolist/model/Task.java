package com.robin.tolist.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Task implements Parcelable {
    private int id;
    private String task;
    private String date;
    private String details;
    private String priority;
    private String status;

    public Task(String task, String date, String details, String priority, String status) {
        this.task = task;
        this.date = date;
        this.details = details;
        this.priority = priority;
        this.status = status;
    }

    public Task(int id, String task, String date, String details, String priority, String status) {
        this.id = id;
        this.task = task;
        this.date = date;
        this.details = details;
        this.priority = priority;
        this.status = status;
    }

    protected Task(Parcel in) {
        id = in.readInt();
        task = in.readString();
        date = in.readString();
        details = in.readString();
        priority = in.readString();
        status = in.readString();
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(task);
        dest.writeString(date);
        dest.writeString(details);
        dest.writeString(priority);
        dest.writeString(status);
    }
}
