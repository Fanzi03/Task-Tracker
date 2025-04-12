package com.work.service;


import com.work.controller.TaskController;
import com.work.gui.TaskView;


public class TaskControllerInitializer {

    public TaskController createController() {
        // Создаём менеджер задач
        TaskManager manager = new TaskManager();

        // Создаём View и сразу загружаем в него задачи
        TaskView view = new TaskView();
        view.getTaskList().setAll(manager.getAllTasks());

        // Создаём контроллер с уже готовыми зависимостями
        return new TaskController(view, manager);
    }
}

