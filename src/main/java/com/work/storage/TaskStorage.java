package com.work.storage;

import com.work.domain.Task;
import lombok.SneakyThrows;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class TaskStorage {

    private static final String FILE_NAME = "tasks.ser";

    private static void saveTasks(Map<String, Task> tasks) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(tasks);
            System.out.println("Tasks saved to " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("Error saving tasks to " + FILE_NAME);
        }
    }

    @SneakyThrows
    public static Map<String, Task> loadTasks() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (Map<String, Task>) ois.readObject();
        } catch (IOException e) {
            System.out.println("Error loading tasks from " + FILE_NAME + ", returning empty map");
            return new HashMap<>();

        }
    }
}
