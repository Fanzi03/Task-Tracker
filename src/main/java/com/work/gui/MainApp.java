package com.work.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        TaskView view = new TaskView();
        new TaskController(view); // ← подключаем контроллер

        Scene scene = new Scene(view.getRoot(),600,400);

        stage.setTitle("Task Tracker - JavaFX");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
