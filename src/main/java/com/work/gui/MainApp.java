package com.work.gui;

import com.work.controller.TaskController;
import com.work.service.TaskControllerInitializer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        // Используем инициализатор для создания контроллера и его зависимостей
        TaskControllerInitializer initializer = new TaskControllerInitializer();
        TaskController controller = initializer.createController();

        // Получаем View из контроллера и запускаем сцену
       TaskView view = controller.getView();
        Scene scene = new Scene(view.getRoot(),600,400);

        stage.setTitle("Task Tracker - JavaFX");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
