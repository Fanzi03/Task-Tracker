package com.work.gui;

import com.work.controller.TaskController;
import com.work.service.TaskControllerInitializer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        new TaskControllerInitializer().init(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
