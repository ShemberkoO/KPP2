package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataModelTest {

    private DataModel dataModel;
    private Project project1;
    private Project project2;

    @BeforeEach
    void setUp() {
        dataModel = new DataModel();
        project1 = new Project("Project 1");
        project2 = new Project("Project 2");

        project1.addSprint(LocalDate.of(2024, 10, 1),
                LocalDate.of(2024, 10, 15));
        project2.addSprint(LocalDate.of(2024, 10, 16),
                LocalDate.of(2024, 10, 31));
    }

    @Test
    void testAddProject() {
        // Перевіряємо додавання проектів
        dataModel.addProject(project1);
        dataModel.addProject(project2);

        List<Project> projects = dataModel.getProjects();
        assertEquals(2, projects.size());
        assertTrue(projects.contains(project1));
        assertTrue(projects.contains(project2));
    }

    @Test
    void testRemoveProject() {
        dataModel.addProject(project1);
        dataModel.addProject(project2);

        dataModel.removeProject(project1);
        List<Project> projects = dataModel.getProjects();
        assertEquals(1, projects.size());
        assertFalse(projects.contains(project1));
        assertTrue(projects.contains(project2));
    }

    @Test
    void testSetProjects() {
        List<Project> newProjects = List.of(project1, project2);
        dataModel.setProjects(newProjects);

        assertEquals(newProjects, dataModel.getProjects());
    }
}
