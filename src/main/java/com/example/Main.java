package com.example;

import com.work.domain.Task;
import com.work.service.TaskManager;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();

        // Проверим, что было до
        System.out.println("До добавления:");
        manager.getAllTasks().forEach(System.out::println);

        // Добавим новую задачу
        Task task = new Task("1", "Тестовая задача", "Описание задачи", false);
        manager.addTask(task);

        // Проверим после добавления
        System.out.println("\nПосле добавления:");
        manager.getAllTasks().forEach(System.out::println);
    }
}
