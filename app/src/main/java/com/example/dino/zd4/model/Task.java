package com.example.dino.zd4.model;

import com.example.dino.zd4.R;

import java.io.Serializable;

/**
 * Created by Dino on 11/05/2018.
 */

public class Task implements Serializable {

    private static int sID = 0;

    int ID;
    private String Title;
    private String Description;
    private boolean isCompleted;
    private TaskPriority Priority;


    public Task(String title, String description, TaskPriority priority) {
        Title = title;
        Description = description;
        Priority = priority;
        isCompleted = false;
        ID=sID++;
    }

    public static int getsID() {
        return sID;
    }

    public static void setsID(int sID) {
        Task.sID = sID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public int getPriority() {
        int intPriority = R.color.taskPriority_Unknown;
        switch (this.Priority)
        {
            case LOW: intPriority = R.color.taskpriority_low;
                break;
            case MEDIUM: intPriority = R.color.taskpriority_medium;
                break;
            case HIGH: intPriority = R.color.taskpriority_high;
                break;
        }
        return intPriority;
    }

    public void setPriority(TaskPriority priority) {
        Priority = priority;
    }
}
