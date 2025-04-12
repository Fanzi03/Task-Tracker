package com.work.controller;

import com.work.domain.Task;
import com.work.gui.TaskForm;
import com.work.gui.TaskView;
import com.work.service.TaskManager;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import lombok.Getter;

import java.util.Optional;
import java.util.UUID;

@Getter
public class TaskController {

    private final TaskView view;
    private final TaskManager manager;

    public TaskController(TaskView view, TaskManager manager) {
        this.view = view;
        this.manager = manager;
        initListeners();

        // сортировка
        SortedList<Task> sortedList = new SortedList<>(view.getFilteredList());
        view.getTable().setItems(sortedList);

        view.getSortBox().valueProperty().addListener((obs, oldVal, newVal) -> {
            sortedList.setComparator((t1, t2) ->{
                return switch (newVal){
                    case "Title" -> t1.getTitle().compareToIgnoreCase(t2.getTitle());
                    case "Status" -> Boolean.compare(t1.isCompleted(), t2.isCompleted());
                    default -> 0;
                };
            });
            view.getTable().sort();
        });
    }

    private void initListeners() {
        view.getAddBtn().setOnAction(e -> onAdd());
        view.getEditBtn().setOnAction(e -> onEdit());
        view.getDeleteBtn().setOnAction(e -> onDelete());

        view.getSearchField().textProperty().addListener((obs, oldVal, newVal) -> {
            view.getFilteredList().setPredicate(task ->{
                if(newVal == null || newVal.isEmpty()) return true;
                return task.getTitle().toLowerCase().contains(newVal.toLowerCase());
            });
        });
    }

    private void onAdd() {
        TaskForm form = new TaskForm(null);
        Optional<Task> result = form.showAndWait();
        result.ifPresent(task -> {
            task.setId(UUID.randomUUID().toString());

            // добавляем в менеджер (и сохраняем)
            manager.addTask(task);

            // обновляем UI
            view.getTaskList().setAll(manager.getAllTasks());
            view.refreshTable();
        });
    }

    private void onEdit() {
        Task selected = view.getSelectedTask();
        if (selected == null) {
            alert("Choose the task for edition");
            return;
        }

        TaskForm form = new TaskForm(selected);
        Optional<Task> result = form.showAndWait();
        result.ifPresent(edited -> {
            selected.setTitle(edited.getTitle());
            selected.setDescription(edited.getDescription());
            selected.setCompleted(edited.isCompleted());

            manager.updateTask(selected.getId(), selected); // сохраняем изменения

            // 🔄 Обновляем UI из TaskManager
            view.getTaskList().setAll(manager.getAllTasks());
            view.refreshTable();
        });
    }
    private void onDelete() {
        Task selected = view.getSelectedTask();
        if (selected != null) {
            manager.removeTask(selected.getId());           // удаляем из хранилища

            // 🔄 Обновляем UI из TaskManager
            view.getTaskList().setAll(manager.getAllTasks());
            view.refreshTable();
        } else {
            alert("Choose the task for deletion");
        }
    }

    private void alert(String message) {
       new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK).showAndWait();
    }
}
