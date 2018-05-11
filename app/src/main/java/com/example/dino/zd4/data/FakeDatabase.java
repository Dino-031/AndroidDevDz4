package com.example.dino.zd4.data;

import com.example.dino.zd4.model.Task;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dino on 11/05/2018.
 */

public class FakeDatabase {

    private List<Task> Tasks;

    public FakeDatabase() {
        Tasks = new ArrayList<>();
    }

    public void save(Task task) {
        Tasks.add(task);
    }

    public void save(List<Task> tasks){
        Tasks.addAll(tasks);
    }
    public void delete(Task task){
        Tasks.remove(task);
    }
    public int count() {
        return Tasks.size();
    }
    public Task get(int position){
        return Tasks.get(position);
    }
}
