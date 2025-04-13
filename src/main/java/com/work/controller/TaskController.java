package com.work.controller;

import com.work.domain.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.UUID;

public class TaskController {
    @FXML
    private TextField titleField;
    @FXML
    private TextField descriptionField;
    @FXML
    private ListView<Task> taskListView;

    private ObservableList<Task> tasks;
    private static final String TASKS_FILE = "tasks.ser";

    @FXML
    public void initialize() {
        tasks = FXCollections.observableArrayList();
        loadTasks();
        taskListView.setItems(tasks);
        taskListView.setCellFactory(lv -> new ListCell<Task>() {
            @Override
            protected void updateItem(Task task, boolean empty) {
                super.updateItem(task, empty);
                if (empty || task == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    CheckBox checkBox = new CheckBox(task.getTitle() + " - " + task.getDescription());
                    checkBox.setSelected(task.isCompleted());
                    checkBox.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
                        task.setCompleted(isSelected);
                        saveTasks();
                    });
                    setGraphic(checkBox);
                }
            }
        });
    }

    @FXML
    private void handleAddTask() {
        String title = titleField.getText().trim();
        String description = descriptionField.getText().trim();
        
        if (!title.isEmpty()) {
            Task task = new Task(UUID.randomUUID().toString(), title, description, false);
            tasks.add(task);
            titleField.clear();
            descriptionField.clear();
            saveTasks();
        } else {
            showAlert("Error", "Title cannot be empty");
        }
    }

    @FXML
    private void handleDeleteTask() {
        Task selectedTask = taskListView.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            tasks.remove(selectedTask);
            saveTasks();
        } else {
            showAlert("Error", "Please select a task to delete");
        }
    }

    @FXML
    private void handleMarkAsCompleted() {
        Task selectedTask = taskListView.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            selectedTask.setCompleted(!selectedTask.isCompleted());
            saveTasks();
        } else {
            showAlert("Error", "Please select a task to mark as completed");
        }
    }

    private void loadTasks() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(TASKS_FILE))) {
            tasks.addAll((ObservableList<Task>) ois.readObject());
        } catch (FileNotFoundException e) {
            // Файл не существует, создадим новый при сохранении
        } catch (IOException | ClassNotFoundException e) {
            showAlert("Error", "Failed to load tasks: " + e.getMessage());
        }
    }

    private void saveTasks() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(TASKS_FILE))) {
            oos.writeObject(tasks);
        } catch (IOException e) {
            showAlert("Error", "Failed to save tasks: " + e.getMessage());
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
} 