package com.work.gui;

import com.work.domain.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.UUID;

public class TaskController {

    private final TaskView view;

    public TaskController(TaskView view) {
        this.view = view;
        initListeners();
    }

    private void initListeners() {
        view.getAddBtn().setOnAction(e -> onAdd());
        view.getEditBtn().setOnAction(e -> onEdit());
        view.getDeleteBtn().setOnAction(e -> onDelete());
    }

    private void onAdd() {
        Task newTask = new Task(UUID.randomUUID().toString(), "New task", "Description...", false);
        view.getTaskList().add(newTask);
    }

    private void onEdit() {
        Task selected = view.getSelectedTask();
        if(selected != null) {
            selected.setTitle(selected.getTitle() + " Edited");
            view.refreshTable();
        } else {
            alert("Choose the task for edition");
        }
    }

    private void onDelete() {
        Task selected = view.getSelectedTask();
        if(selected != null) {
            view.getTaskList().remove(selected);
        }else{
            alert("Choose the task for deletion");
        }
    }

    private void alert(String message) {
       new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK).showAndWait();
    }
}
