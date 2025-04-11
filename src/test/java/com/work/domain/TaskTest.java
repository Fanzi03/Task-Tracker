package com.work.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void testLombokGeneratedMethods() {
        Task task = new Task("1", "Test", "Test description", false);

        // Проверка геттеров
        assertEquals("1", task.getId());
        assertEquals("Test", task.getTitle());
        assertEquals("Test description", task.getDescription());
        assertFalse(task.isCompleted());

        // Проверка сеттеров
        task.setCompleted(true);
        assertTrue(task.isCompleted());

        // Проверка toString
        String str = task.toString();
        assertTrue(str.contains("Task"));
        assertTrue(str.contains("Test"));

        // Проверка equals/hashCode
        Task sameTask = new Task("1", "Test", "Test description", true);
        sameTask.setCompleted(true);
        assertEquals(task, sameTask);
        assertEquals(task.hashCode(), sameTask.hashCode());
    }
}
