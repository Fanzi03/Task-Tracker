package com.work.controller;

import com.work.domain.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;


public class TaskFormController {

    @FXML private TextField titleField;
    @FXML private TextArea descriptionField;
    @FXML private CheckBox completedCheckBox;

    private Task task;
    private boolean confirmed = false;

    public void setTask(Task task) {
        this.task = task;
        if(task != null) {
            titleField.setText(task.getTitle());
            descriptionField.setText(task.getDescription());
            completedCheckBox.setSelected(task.isCompleted());
        }
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public Task getResult(){
        if(task == null) {
            task = new Task();
        }
        task.setTitle(titleField.getText());
        task.setDescription(descriptionField.getText());
        task.setCompleted(completedCheckBox.isSelected());
        return task;
    }

    @FXML
    private void onOk(){
        confirmed = true;
        ((Stage) titleField.getScene().getWindow()).close();
    }

    @FXML
    private void onCancel(){
        ((Stage) titleField.getScene().getWindow()).close();
    }

}

