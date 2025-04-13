package com.work.service;


import com.work.controller.TaskController;
import com.work.controller.TaskViewController;
import com.work.gui.TaskView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class TaskControllerInitializer {

    public TaskController createController() {
        // Создаём менеджер задач
        TaskManager manager = new TaskManager();

        // Создаём View и сразу загружаем в него задачи
        TaskView view = new TaskView();
        view.getTaskList().setAll(manager.getAllTasks());

        // Создаём контроллер с уже готовыми зависимостями
        return new TaskController(view, manager);
    }

    public void init (Stage stage) throws IOException {
            // Загружаем FXML
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/templates/TaskView.fxml"));
            Parent root = fxmlLoader.load();


            // Получаем контроллер и передаём ему TaskManager
            TaskViewController controller = fxmlLoader.getController();
            controller.setTaskManager(new TaskManager());

            Scene scene = new Scene(root);
            stage.setTitle("Task Tracker");
            stage.setScene(scene);
            stage.show();
    }
}

