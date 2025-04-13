package com.work;

import com.work.gui.TaskView;
import com.work.service.TaskManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        TaskManager manager = new TaskManager();
        TaskView view = new TaskView(manager);

        Scene scene = new Scene(view, 600, 500);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        primaryStage.setTitle("Task Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
} 