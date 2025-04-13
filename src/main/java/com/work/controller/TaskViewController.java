package com.work.controller;

import com.work.domain.Task;
import com.work.gui.TaskForm;
import com.work.service.TaskManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public class TaskViewController {

    @FXML private TableView<Task> taskTable;
    @FXML private TableColumn<Task, String> titleColumn;
    @FXML private TableColumn<Task, String> descriptionColumn;
    @FXML private TableColumn<Task, Boolean> statusColumn;
    @FXML private TextField searchField;
    @FXML private ComboBox<String> sortBox;
    @FXML private Button addBtn;
    @FXML private Button editBtn;
    @FXML private Button deleteBtn;

    private final ObservableList<Task> taskList = FXCollections.observableArrayList();
    private final FilteredList<Task> filteredList = new FilteredList<>(taskList, p -> true);
    private TaskManager manager;

    public void setTaskManager(TaskManager manager) {
        this.manager = manager;
        taskList.setAll(manager.getAllTasks());
    }

    @FXML
    private void initialize() {
        // Настройка колонок таблицы
        titleColumn.setCellValueFactory(data -> data.getValue().titleProperty());
        descriptionColumn.setCellValueFactory(data -> data.getValue().descriptionProperty());
        statusColumn.setCellValueFactory(data -> data.getValue().completedProperty());
        statusColumn.setCellFactory(tc -> new CheckBoxTableCell<>());

        // Настройка сортировки
        SortedList<Task> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(taskTable.comparatorProperty());
        taskTable.setItems(sortedList);

        // Настройка ComboBox для сортировки
        sortBox.getItems().addAll("Title", "Status");
        sortBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            sortedList.setComparator((t1, t2) -> {
                return switch (newVal) {
                    case "Title" -> t1.getTitle().compareToIgnoreCase(t2.getTitle());
                    case "Status" -> Boolean.compare(t1.isCompleted(), t2.isCompleted());
                    default -> 0;
                };
            });
        });

        // Настройка поиска
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            filteredList.setPredicate(task -> {
                if (newVal == null || newVal.isEmpty()) return true;
                return task.getTitle().toLowerCase().contains(newVal.toLowerCase());
            });
        });

        // Настройка кнопок
        addBtn.setOnAction(e -> onAdd());
        editBtn.setOnAction(e -> onEdit());
        deleteBtn.setOnAction(e -> onDelete());
    }

    private void onAdd() {
        try {
            TaskForm form = new TaskForm(null);
            Optional<Task> result = form.showAndWait();
            result.ifPresent(task -> {
                task.setId(UUID.randomUUID().toString());
                manager.addTask(task);
                taskList.setAll(manager.getAllTasks());
            });
        } catch (IOException e) {
            showError("Failed to create task form", e);
        }
    }

    private void onEdit() {
        Task selected = taskTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            alert("Choose a task to edit");
            return;
        }

        try {
            TaskForm form = new TaskForm(selected);
            Optional<Task> result = form.showAndWait();
            result.ifPresent(edited -> {
                selected.setTitle(edited.getTitle());
                selected.setDescription(edited.getDescription());
                selected.setCompleted(edited.isCompleted());
                manager.updateTask(selected.getId(), selected);
                taskList.setAll(manager.getAllTasks());
            });
        } catch (IOException e) {
            showError("Failed to create edit form", e);
        }
    }

    private void onDelete() {
        Task selected = taskTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            alert("Choose a task to delete");
            return;
        }

        manager.removeTask(selected.getId());
        taskList.setAll(manager.getAllTasks());
    }

    private void alert(String message) {
        new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK).showAndWait();
    }

    private void showError(String message, Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }
}


