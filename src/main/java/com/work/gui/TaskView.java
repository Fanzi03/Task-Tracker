package com.work.gui;

import com.work.domain.Task;
import com.work.service.TaskManager;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;

public class TaskView extends VBox {
    private final TaskManager manager;
    private final TextField titleField;
    private final TextField descriptionField;
    private final ListView<Task> taskListView;
    private final TextField searchField;
    private final ComboBox<String> sortBox;

    public TaskView(TaskManager manager) {
        this.manager = manager;
        this.setSpacing(20);
        this.setPadding(new Insets(20));

        // Заголовок
        Label titleLabel = new Label("Task Manager");
        titleLabel.getStyleClass().add("title-label");

        // Форма добавления задачи
        VBox addTaskForm = new VBox(10);
        addTaskForm.getStyleClass().add("pane");
        Label addTaskLabel = new Label("Add New Task");
        addTaskLabel.getStyleClass().add("label");

        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);

        titleField = new TextField();
        descriptionField = new TextField();
        Button addButton = new Button("Add Task");

        formGrid.add(new Label("Title:"), 0, 0);
        formGrid.add(titleField, 1, 0, 2, 1);
        formGrid.add(new Label("Description:"), 0, 1);
        formGrid.add(descriptionField, 1, 1, 2, 1);
        formGrid.add(addButton, 1, 2);

        addTaskForm.getChildren().addAll(addTaskLabel, formGrid);

        // Список задач
        VBox taskListBox = new VBox(10);
        taskListBox.getStyleClass().add("pane");
        Label taskListLabel = new Label("Tasks");
        taskListLabel.getStyleClass().add("label");

        // Поиск и сортировка
        HBox searchSortBox = new HBox(10);
        searchField = new TextField();
        searchField.setPromptText("Search tasks...");
        sortBox = new ComboBox<>();
        sortBox.getItems().addAll("None", "Title", "Status");
        sortBox.setValue("None");
        searchSortBox.getChildren().addAll(searchField, sortBox);

        taskListView = new ListView<>();
        taskListView.setPrefHeight(300);

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(javafx.geometry.Pos.CENTER_RIGHT);
        Button markCompletedButton = new Button("Mark as Completed");
        Button deleteButton = new Button("Delete Task");
        buttonBox.getChildren().addAll(markCompletedButton, deleteButton);

        taskListBox.getChildren().addAll(taskListLabel, searchSortBox, taskListView, buttonBox);

        // Добавляем все компоненты в основной контейнер
        this.getChildren().addAll(titleLabel, addTaskForm, taskListBox);

        // Инициализация данных
        taskListView.setItems(manager.getSortedTasks());

        // Обработчики событий
        addButton.setOnAction(e -> handleAddTask());
        markCompletedButton.setOnAction(e -> handleMarkAsCompleted());
        deleteButton.setOnAction(e -> handleDeleteTask());
        searchField.textProperty().addListener((obs, oldVal, newVal) -> manager.setFilter(newVal));
        sortBox.valueProperty().addListener((obs, oldVal, newVal) -> manager.setSort(newVal));
    }

    private void handleAddTask() {
        String title = titleField.getText().trim();
        String description = descriptionField.getText().trim();

        if (!title.isEmpty()) {
            manager.addTask(title, description);
            titleField.clear();
            descriptionField.clear();
        } else {
            showAlert("Error", "Title cannot be empty");
        }
    }

    private void handleMarkAsCompleted() {
        Task selectedTask = taskListView.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            Task updatedTask = new Task(
                selectedTask.getId(),
                selectedTask.getTitle(),
                selectedTask.getDescription(),
                !selectedTask.isCompleted()
            );
            manager.updateTask(selectedTask.getId(), updatedTask);
        } else {
            showAlert("Error", "Please select a task to mark as completed");
        }
    }

    private void handleDeleteTask() {
        Task selectedTask = taskListView.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            manager.removeTask(selectedTask.getId());
        } else {
            showAlert("Error", "Please select a task to delete");
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
