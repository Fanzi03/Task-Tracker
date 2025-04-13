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

    // 👇 ВОТ ЭТО НУЖНО ДОБАВИТЬ:
    public void setTaskManager(TaskManager manager) {
        this.manager = manager;
        taskList.setAll(manager.getAllTasks()); // Загружаем задачи
    }

    @FXML
    private void initialize() {
        titleColumn.setCellValueFactory(data -> data.getValue().titleProperty());
        descriptionColumn.setCellValueFactory(data -> data.getValue().descriptionProperty());
        statusColumn.setCellValueFactory(data -> data.getValue().completedProperty());
        statusColumn.setCellFactory(tc -> new CheckBoxTableCell<>());

        SortedList<Task> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(taskTable.comparatorProperty());
        taskTable.setItems(sortedList);

        sortBox.getItems().addAll("Title", "Status");

        // сортировка
        sortBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            sortedList.setComparator((t1, t2) -> {
                return switch (newVal) {
                    case "Title" -> t1.getTitle().compareToIgnoreCase(t2.getTitle());
                    case "Status" -> Boolean.compare(t1.isCompleted(), t2.isCompleted());
                    default -> 0;
                };
            });
        });

        // фильтрация
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            filteredList.setPredicate(task -> {
                if (newVal == null || newVal.isEmpty()) return true;
                return task.getTitle().toLowerCase().contains(newVal.toLowerCase());
            });
        });

        // кнопки
        addBtn.setOnAction(e -> onAdd());
        editBtn.setOnAction(e -> onEdit());
        deleteBtn.setOnAction(e -> onDelete());
    }

    private void onAdd() {
        TaskForm form = new TaskForm(null);
        Optional<Task> result = form.showAndWait();
        result.ifPresent(task -> {
            task.setId(UUID.randomUUID().toString());
            manager.addTask(task);
            taskList.setAll(manager.getAllTasks());
        });
    }

    private void onEdit() {
        Task selected = taskTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            alert("Choose a task to edit");
            return;
        }

        TaskForm form = new TaskForm(selected);
        Optional<Task> result = form.showAndWait();
        result.ifPresent(edited -> {
            selected.setTitle(edited.getTitle());
            selected.setDescription(edited.getDescription());
            selected.setCompleted(edited.isCompleted());

            manager.updateTask(selected.getId(), selected);
            taskList.setAll(manager.getAllTasks());
        });
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
}
