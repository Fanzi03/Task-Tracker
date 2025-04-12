package com.work.gui;

import com.work.domain.Task;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Optional;

public class TaskForm {

    private final Stage window;
    private final TextField titleField;
    private final TextArea descField;
    private final CheckBox statusBox;
    private final Button saveBtn;
    private final Button cancelBtn;

    private Task taskResult;

    public TaskForm(Task taskToEdit){
       window = new Stage();
       window.initModality(Modality.APPLICATION_MODAL);
       window.setTitle(taskToEdit == null ? "Add Task" : "Edit Task");

       titleField = new TextField();
       descField = new TextArea();
       statusBox = new CheckBox("Completed");

       if(taskToEdit != null){
            titleField.setText(taskToEdit.getTitle());
            descField.setText(taskToEdit.getDescription());
            statusBox.setSelected(taskToEdit.isCompleted());
       }

       saveBtn = new Button("Save");
       cancelBtn = new Button("Cancel");

       saveBtn.setOnAction(event -> {
           String title = titleField.getText();
           String description = descField.getText();
           boolean completed = statusBox.isSelected();


           if (title.trim().isEmpty()) {
               showAlert("Title is required.");
               return;
           }

           taskResult = new Task(
                   taskToEdit != null ? taskToEdit.getId() : null,
                   title,
                   description,
                   completed
           );
           window.close();
       });
       cancelBtn.setOnAction(event -> {
           taskResult = null;
           window.close();
       });

       VBox layout = new VBox(10,
               new Label("Title:"), titleField,
               new Label("Description"), descField,
               statusBox,
               new VBox(5,saveBtn,cancelBtn)
       );

       layout.setPadding(new Insets(10));

       window.setScene(new Scene(layout, 300, 300));
    }

    public Optional<Task> showAndWait(){
        window.showAndWait();
        return Optional.ofNullable(taskResult);
    }

    private void showAlert(String message){
        new Alert(Alert.AlertType.WARNING, message, ButtonType.OK).showAndWait();
    }


}
