package com.work.gui;

import com.work.controller.TaskFormController;
import com.work.domain.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class TaskForm {
    private final Stage stage;
    private final TaskFormController controller;

    public TaskForm(Task task) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/templates/TaskForm.fxml"));
        Parent root = fxmlLoader.load();
        controller = fxmlLoader.getController();
        controller.setTask(task);

        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        stage.setTitle(task == null ? "New Task" : "Edit Task");
    }

    public Optional<Task> showAndWait() {
        stage.showAndWait();
        return controller.isConfirmed() ? Optional.of(controller.getResult()) : Optional.empty();
    }
}
