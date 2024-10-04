package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SuperTaskTest {

    private SuperTask superTask;
    private Task subTask1;
    private Task subTask2;

    @BeforeEach
    void setUp() {
        superTask = new SuperTask("Main Task", "This is a super task",
                LocalDate.of(2024, 12, 31), Priority.HIGH);

        subTask1 = new Task("Subtask 1", "First subtask",
                LocalDate.of(2024, 12, 25), Priority.MEDIUM);
        subTask2 = new Task("Subtask 2", "Second subtask",
                LocalDate.of(2024, 12, 20), Priority.LOW);
    }

    @Test
    void testAddSubTask() {
        superTask.addSubTask(subTask1);
        superTask.addSubTask(subTask2);

        assertEquals(2, superTask.getSubTasks().size());
    }

    @Test
    void testGetSubTasks() {
        superTask.addSubTask(subTask1);
        superTask.addSubTask(subTask2);

        List<Task> subTasks = superTask.getSubTasks();
        assertTrue(subTasks.contains(subTask1));
        assertTrue(subTasks.contains(subTask2));
    }

    @Test
    void testTaskPriority() {
        superTask.setPriority(Priority.LOW);
        assertEquals("LOW", superTask.getPriority());
    }
}
