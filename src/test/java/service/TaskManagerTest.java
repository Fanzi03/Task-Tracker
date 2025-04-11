package service;

import com.work.domain.Task;
import com.work.service.TaskManager;
import com.work.storage.TaskStorage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TaskManagerTest {
    private final String FILE_NAME = "test_tasks.ser";
    private TaskManager manager;

    @BeforeEach
    void testSetUp(){

        //Before Clean
        TaskStorage.setFileName(FILE_NAME);

        //After create
        manager = new TaskManager();
    }

    @AfterEach
    void testTearDown(){
        File file = new File(FILE_NAME);
        if(file.exists()){
            file.delete();
        }
    }

    @Test
    void testAddTask_shouldAddCorrectly(){
        Task task = new Task("1", "Test Task", "Description", false);
        manager.addTask(task);

        assertEquals(1, manager.getAllTasks().size());
        assertEquals("Test Task", manager.getTask("1").getTitle());
    }

    @Test
    void testGetTask_shouldReturnNullForEmptyId(){
        Task result = manager.getTask("");
        assertNull(result);
    }

    @Test
    void testGetTask_shouldReturnNullForNullId(){
        Task result = manager.getTask(null);
        assertNull(result);
    }

    @Test
    void testRemoveTask_shouldDoNothingForWrongId(){
        manager.removeTask(null);
        manager.removeTask("");
    }

    @Test
    void testUpdateTask_shouldNotFailWithWrongId(){
        Task newTask = new Task("1", "Updated", "Description", false);
        manager.updateTask("nonexistent", newTask);
        assertTrue(manager.getAllTasks().isEmpty());
    }

    @Test
    void testGetAllTasks_shouldBeEmptyInitially(){
        List<Task> tasks = manager.getAllTasks();
        assertNotNull(tasks);
        assertTrue(tasks.isEmpty());

    }

}
