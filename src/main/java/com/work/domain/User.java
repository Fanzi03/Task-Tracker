package com.work.domain;


import lombok.Data;


import java.util.ArrayList;
import java.util.List;

@Data
public class User {
    private String id;
    private String name;
    private String email;
    private String password;
    private List<Project> projects;


    public User(String id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.projects = new ArrayList<Project>();
    }

    public void addProject(Project project){
        this.projects.add(project);
    }

    public void removeProject(Project project){
        this.projects.remove(project);
    }

}
