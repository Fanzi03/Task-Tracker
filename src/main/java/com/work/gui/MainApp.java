package com.work.gui;

import com.work.controller.TaskViewController;
import com.work.service.TaskManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
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

    public static void main(String[] args) {
        launch(args);
    }
}
