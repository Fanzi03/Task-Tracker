package com.work.domain;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
public class Task implements Serializable {
    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty title = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final BooleanProperty completed = new SimpleBooleanProperty();


    public Task(String id, String title, String description, boolean completed) {
        this.id.set(id);
        this.title.set(title);
        this.description.set(description);
        this.completed.set(completed);
    }

    // ⚙️ Для JavaFX Property API

    public String getId() {
        return id.get();
    }
    public StringProperty idProperty() {
        return id;
    }
    public String getTitle() {
        return title.get();
    }
    public StringProperty titleProperty() {
        return title;
    }
    public String getDescription() {
        return description.get();
    }
    public StringProperty descriptionProperty() {
        return description;
    }
    public boolean isCompleted() {
        return completed.get();
    }
    public BooleanProperty completedProperty() {
        return completed;
    }
    public void setCompleted(boolean completed) {
        this.completed.set(completed);
    }
    public void setTitle(String title) {
        this.title.set(title);
    }
    public void setDescription(String description) {
        this.description.set(description);
    }
    public void setId(String id) {
        this.id.set(id);
    }
}
