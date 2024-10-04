package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProjectTest {

    private Project project;

    @BeforeEach
    void setUp() {
        // Створення проекту перед кожним тестом
        project = new Project("Test Project");
    }

    @Test
    void testProjectCreation() {
        // Перевіряємо, що проект створюється з правильним іменем і порожнім списком спринтів
        assertEquals("Test Project", project.getName());
        assertNotNull(project.getSprints());
        assertTrue(project.getSprints().isEmpty());
    }

    @Test
    void testAddSprint() {
        // Додаємо два спринти до проекту
        project.addSprint(LocalDate.of(2024, 10, 1),
                LocalDate.of(2024, 10, 15));
        project.addSprint(LocalDate.of(2024, 10, 16),
                LocalDate.of(2024, 10, 31));

        // Перевіряємо, що кількість спринтів збільшилася
        List<Sprint> sprints = project.getSprints();
        assertEquals(2, sprints.size());

        // Перевіряємо перший спринт
        assertEquals(LocalDate.of(2024, 10, 1), sprints.get(0).getStartDate());
        assertEquals(LocalDate.of(2024, 10, 15), sprints.get(0).getEndDate());

        // Перевіряємо другий спринт
        assertEquals(LocalDate.of(2024, 10, 16), sprints.get(1).getStartDate());
        assertEquals(LocalDate.of(2024, 10, 31), sprints.get(1).getEndDate());
    }

    @Test
    void testToString() {
        // Додаємо спринти і перевіряємо метод toString
        project.addSprint(LocalDate.of(2024, 10, 1),
                LocalDate.of(2024, 10, 15));
        project.addSprint(LocalDate.of(2024, 10, 16),
                LocalDate.of(2024, 10, 31));

        String expected = "{Project " + project.getId() + ", name=Test Project\n" +
                "{Sprint 0 2024-10-01-2024-10-15\n" +
                "}\n{Sprint 1 2024-10-16-2024-10-31\n" +
                "}}";
        assertEquals(expected, project.toString());
    }
}
