package com.work.gui;

import com.work.controller.TaskController;
import com.work.service.TaskManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        TaskView view = new TaskView();

        // Загружаем задачи из TaskManager
        TaskManager manager = new TaskManager();

        new TaskController(view, manager); // ← подключаем контроллер

        Scene scene = new Scene(view.getRoot(),600,400);
        stage.setTitle("Task Tracker - JavaFX");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
