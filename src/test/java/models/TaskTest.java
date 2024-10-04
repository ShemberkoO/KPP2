package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    private Task task;

    @BeforeEach
    public void setUp() {
        task = new Task("Test Task", "This is a test description", LocalDate.now(), Priority.MEDIUM);
    }

    @Test
    public void testTaskCreation() {
        assertEquals("Test Task", task.getTitle());
        assertEquals("This is a test description", task.getDescription());
        assertEquals("MEDIUM", task.getPriority());
        assertEquals("OPENED", task.getStatus());
    }

    @Test
    public void testSetTitle() {
        task.setTitle("Updated Task Title");
        assertEquals("Updated Task Title", task.getTitle());
    }

    @Test
    public void testSetDescription() {
        task.setDescription("Updated Description");
        assertEquals("Updated Description", task.getDescription());
    }

    @Test
    public void testSetPriority() {
        task.setPriority(Priority.HIGH);
        assertEquals("HIGH", task.getPriority());
    }

    @Test
    public void testSetStatus() {
        task.setStatus(Status.COMPLETED);
        assertEquals("COMPLETED", task.getStatus());
    }


    @Test
    public void testSetDeadline() {
        LocalDate newDeadline = LocalDate.of(2024, 10, 15);
        task.setDeadline(newDeadline);
        assertEquals(newDeadline, task.getDeadline());
    }

    @Test
    public void testToString() {
        String expected = "\nTask " + task.getId() + "\n"
                + "Title: Test Task\n"
                + "Description: This is a test description\n"
                + "Deadline: " + LocalDate.now() + "\n"
                + "Priority: MEDIUM\n"
                + "Status: OPENED\n";
        assertEquals(expected, task.toString());
    }

    @Test
    public void testTransformToSuperTask() {
        var superTask = task.transformToSuperTask();
        assertNotNull(superTask);
        assertEquals(task.getTitle(), superTask.getTitle());
        assertEquals(task.getDescription(), superTask.getDescription());
        assertEquals(task.getDeadline(), superTask.getDeadline());
        assertEquals(task.getPriority(), superTask.getPriority());
    }
}
