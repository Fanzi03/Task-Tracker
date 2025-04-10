package com.work.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Task implements Serializable {
    private String id;
    private String name;
    private String description;
    private boolean completed;
}
