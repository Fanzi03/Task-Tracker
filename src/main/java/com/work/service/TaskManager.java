package com.work.service;

import com.work.domain.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskManager {
    private Map<String, Task> tasks = new HashMap<>();

    public void add(Task task){
        tasks.put(task.getId(), task);
    }

    public void removeTask(String id){
        tasks.remove(id);
    }

    public void updateTask(String id, Task updatedTask){
        tasks.put(id, updatedTask);
    }

    public Task getTask(Task task){
        return tasks.get(task.getId());
    }

    public List<Task> getTasks(){
        return new ArrayList<>(tasks.values());
    }
}
