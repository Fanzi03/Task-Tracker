package com.work.service;

import com.work.domain.Task;
import com.work.storage.TaskStorage;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import java.util.UUID;

public class TaskManager {
    private final TaskStorage storage;
    private final FilteredList<Task> filteredTasks;
    private final SortedList<Task> sortedTasks;

    public TaskManager() {
        this.storage = new TaskStorage();
        this.filteredTasks = new FilteredList<>(storage.getTasks());
        this.sortedTasks = new SortedList<>(filteredTasks);
    }

    public ObservableList<Task> getAllTasks() {
        return storage.getTasks();
    }

    public FilteredList<Task> getFilteredTasks() {
        return filteredTasks;
    }

    public SortedList<Task> getSortedTasks() {
        return sortedTasks;
    }

    public void addTask(String title, String description) {
        Task task = new Task(UUID.randomUUID().toString(), title, description, false);
        storage.addTask(task);
    }

    public void updateTask(String id, Task updatedTask) {
        storage.updateTask(id, updatedTask);
    }

    public void removeTask(String id) {
        storage.removeTask(id);
    }

    public void setFilter(String searchText) {
        filteredTasks.setPredicate(task -> {
            if (searchText == null || searchText.isEmpty()) {
                return true;
            }
            return task.getTitle().toLowerCase().contains(searchText.toLowerCase()) ||
                   task.getDescription().toLowerCase().contains(searchText.toLowerCase());
        });
    }

    public void setSort(String sortBy) {
        sortedTasks.setComparator((t1, t2) -> {
            return switch (sortBy) {
                case "Title" -> t1.getTitle().compareToIgnoreCase(t2.getTitle());
                case "Status" -> Boolean.compare(t1.isCompleted(), t2.isCompleted());
                default -> 0;
            };
        });
    }
}
