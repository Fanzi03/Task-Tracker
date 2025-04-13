package com.work.storage;

import com.work.domain.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.UUID;

public class TaskStorage {
    private static final String TASKS_FILE = "tasks.ser";
    private final ObservableList<Task> tasks;

    public TaskStorage() {
        this.tasks = FXCollections.observableArrayList();
        loadTasks();
    }

    public ObservableList<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
        saveTasks();
    }

    public void updateTask(String id, Task updatedTask) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId().equals(id)) {
                tasks.set(i, updatedTask);
                saveTasks();
                break;
            }
        }
    }

    public void removeTask(String id) {
        tasks.removeIf(task -> task.getId().equals(id));
        saveTasks();
    }

    private void loadTasks() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(TASKS_FILE))) {
            tasks.addAll((ObservableList<Task>) ois.readObject());
        } catch (FileNotFoundException e) {
            // Файл не существует, создадим новый при сохранении
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Failed to load tasks: " + e.getMessage());
        }
    }

    private void saveTasks() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(TASKS_FILE))) {
            oos.writeObject(tasks);
        } catch (IOException e) {
            System.err.println("Failed to save tasks: " + e.getMessage());
        }
    }
}
