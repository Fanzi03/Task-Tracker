package com.work.gui;

import com.work.domain.Task;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;
import java.util.UUID;

public class TaskController {

    private final TaskView view;

    public TaskController(TaskView view) {
        this.view = view;
        initListeners();

        // сортировка
        SortedList<Task> sortedList = new SortedList<>(view.getFilteredList());
        view.getTable().setItems(sortedList);

        view.getSortBox().valueProperty().addListener((obs, oldVal, newVal) -> {
            sortedList.setComparator((t1, t2) ->{
                return switch (newVal){
                    case "Title" -> t1.getTitle().compareToIgnoreCase(t2.getTitle());
                    case "Status" -> Boolean.compare(t1.isCompleted(), t2.isCompleted());
                    default -> 0;
                };
            });
        });
    }

    private void initListeners() {
        view.getAddBtn().setOnAction(e -> onAdd());
        view.getEditBtn().setOnAction(e -> onEdit());
        view.getDeleteBtn().setOnAction(e -> onDelete());

        view.getSearchField().textProperty().addListener((obs, oldVal, newVal) -> {
            view.getFilteredList().setPredicate(task ->{
                if(newVal == null || newVal.isEmpty()) return true;
                String lowerCase = newVal.toLowerCase();
                return task.getTitle().toLowerCase().contains(lowerCase);
            });
        });
    }

    private void onAdd() {
        TaskForm form = new TaskForm(null);
        Optional<Task> result = form.showAndWait();
        result.ifPresent(task -> {
            task.setId(UUID.randomUUID().toString());
            view.getTaskList().add(task);
        });
    }

    private void onEdit() {
        Task selected = view.getSelectedTask();
        if (selected == null) {
            alert("Choose the task for edition");
            return;
        }

        TaskForm form = new TaskForm(selected);
        Optional<Task> result = form.showAndWait();
        result.ifPresent(edited -> {
            selected.setTitle(edited.getTitle());
            selected.setDescription(edited.getDescription());
            selected.setCompleted(edited.isCompleted());
            view.refreshTable();
        });
    }
    private void onDelete() {
        Task selected = view.getSelectedTask();
        if(selected != null) {
            view.getTaskList().remove(selected);
        }else{
            alert("Choose the task for deletion");
        }
    }

    private void alert(String message) {
       new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK).showAndWait();
    }
}
