package com.work.domain;

import lombok.Data;

import java.util.List;

@Data
public class Project {
    private String id;
    private String name;
    private List<Task> tasks;
}

