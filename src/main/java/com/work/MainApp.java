package com.work;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Button btn = new Button("Click Me");
        btn.setOnAction(e -> {
            System.out.println("Button clicked");
        });

        StackPane root = new StackPane(btn);
        Scene scene = new Scene(root,300,200);

        stage.setTitle("Task Tracker - JavaFX");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
