package com.work.service;

import com.work.domain.Task;
import com.work.storage.TaskStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TaskManager {
    private Map<String, Task> tasks;

    public TaskManager() {
        this.tasks = TaskStorage.loadTasks();
    }

    public void addTask(Task task){
        tasks.put(task.getId(), task);
        TaskStorage.saveTasks(tasks);
    }

    public void removeTask(String id){
        tasks.remove(id);
        TaskStorage.saveTasks(tasks);
    }

    public void updateTask(String id, Task updatedTask){
        if(tasks.containsKey(id))
            tasks.put(id, updatedTask);
        TaskStorage.saveTasks(tasks);
    }

    public Task getTask(String id){
        return tasks.get(id);
    }

    public List<Task> getAllTasks(){
        return new ArrayList<>(tasks.values());
    }
}
