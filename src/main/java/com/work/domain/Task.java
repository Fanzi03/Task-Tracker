package com.work.domain;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.*;

public class Task implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private transient StringProperty id;
    private transient StringProperty title;
    private transient StringProperty description;
    private transient BooleanProperty completed;

    public Task(String id, String title, String description, boolean completed) {
        this.id = new SimpleStringProperty(id);
        this.title = new SimpleStringProperty(title);
        this.description = new SimpleStringProperty(description);
        this.completed = new SimpleBooleanProperty(completed);
    }

    public Task() {
        this("", "", "", false);
    }

    // Методы для сериализации
    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeUTF(getId());
        s.writeUTF(getTitle());
        s.writeUTF(getDescription());
        s.writeBoolean(isCompleted());
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        String idValue = s.readUTF();
        String titleValue = s.readUTF();
        String descriptionValue = s.readUTF();
        boolean completedValue = s.readBoolean();
        
        // Инициализируем свойства после десериализации
        this.id = new SimpleStringProperty(idValue);
        this.title = new SimpleStringProperty(titleValue);
        this.description = new SimpleStringProperty(descriptionValue);
        this.completed = new SimpleBooleanProperty(completedValue);
    }

    // Getters and setters for properties
    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
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

    @Override
    public String toString() {
        return "Task{" +
                "id='" + getId() + '\'' +
                ", title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", completed=" + isCompleted() +
                '}';
    }
}
