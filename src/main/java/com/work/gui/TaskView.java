package com.work.gui;

import com.work.domain.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import lombok.Getter;

@Getter
public class TaskView {


    private final BorderPane root;
    private final TableView<Task> table;
    private final TextField searchField;
    private final ObservableList<Task> taskList;

    private final Button addBtn = new Button("Add");
    private final Button editBtn = new Button("Edit");
    private final Button deleteBtn = new Button("Delete");

    public TaskView() {
        root = new BorderPane();
        table = new TableView<>();
        searchField = new TextField();
        taskList = FXCollections.observableArrayList();

        setupTable();
        setupLayout();
    }

    private void setupTable() {
        TableColumn<Task, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTitle()));

        TableColumn<Task, String> descCol = new TableColumn<>("Description");
        descCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDescription()));

        TableColumn<Task, Boolean> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(data -> new javafx.beans.property.SimpleBooleanProperty(data.getValue().isCompleted()));
        statusCol.setCellFactory(tc -> new CheckBoxTableCell<>());

        table.getColumns().addAll(titleCol, descCol, statusCol);
        table.setItems(taskList);

        table.setPlaceholder(new Label("No tasks yet"));
    }

    private void setupLayout() {
        HBox topBar = new HBox(10, new Label("search"), searchField, addBtn, editBtn, deleteBtn);
        topBar.setPadding(new Insets(10));

        root.setTop(topBar);
        root.setCenter(table);
    }

    public Task getSelectedTask() {
        return table.getSelectionModel().getSelectedItem();
    }

    public void refreshTable(){
        table.refresh();
    }
}
