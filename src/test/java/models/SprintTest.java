package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SprintTest {

    private Sprint sprint;
    private Task task1;
    private Task task2;

    @BeforeEach
    void setUp() {
        // Створення Sprint та задач
        sprint = new Sprint(1, LocalDate.of(2024, 10, 1),
                LocalDate.of(2024, 10, 31));

        task1 = new Task("Task 1", "Description 1",
                LocalDate.of(2024, 10, 15), Priority.HIGH);
        task2 = new Task("Task 2", "Description 2",
                LocalDate.of(2024, 10, 25), Priority.MEDIUM);
    }

    @Test
    void testAddTask() {
        // Додавання задач у спринт
        sprint.addTask(task1);
        sprint.addTask(task2);

        // Перевіряємо, що кількість задач у спринті правильна
        assertEquals(2, sprint.getTasks().size());
    }

    @Test
    void testGetTasks() {
        // Додавання задач
        sprint.addTask(task1);
        sprint.addTask(task2);

        // Перевірка, що повернутий список задач містить додані задачі
        List<Task> tasks = sprint.getTasks();
        assertTrue(tasks.contains(task1));
        assertTrue(tasks.contains(task2));
    }

    @Test
    void testGetStartDate() {
        // Перевірка правильності стартової дати спринта
        assertEquals(LocalDate.of(2024, 10, 1), sprint.getStartDate());
    }

    @Test
    void testSetStartDate() {
        // Встановлення нової дати початку спринта
        sprint.setStartDate(LocalDate.of(2024, 9, 25));

        // Перевірка нової дати
        assertEquals(LocalDate.of(2024, 9, 25), sprint.getStartDate());
    }

    @Test
    void testGetEndDate() {
        // Перевірка кінцевої дати спринта
        assertEquals(LocalDate.of(2024, 10, 31), sprint.getEndDate());
    }

    @Test
    void testSetEndDate() {
        // Встановлення нової кінцевої дати спринта
        sprint.setEndDate(LocalDate.of(2024, 11, 15));

        // Перевірка нової дати
        assertEquals(LocalDate.of(2024, 11, 15), sprint.getEndDate());
    }

    @Test
    void testToString() {
        // Додавання задач
        sprint.addTask(task1);
        sprint.addTask(task2);

        // Перевірка форматування рядка toString
        String expected = "{Sprint 1 2024-10-01-2024-10-31\n" +
                task1.toString() + "\n\n" +
                task2.toString() + "}";
        assertEquals(expected, sprint.toString());
    }
}
