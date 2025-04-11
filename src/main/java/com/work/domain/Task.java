package com.work.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Task implements Serializable {
    private String id;
    private String title;
    private String description;
    private boolean completed;

    public Task(String number, String testTask, String description, boolean b) {
        this.id = number;
        this.title = testTask;
        this.description = description;
        this.completed = b;
    }
}
