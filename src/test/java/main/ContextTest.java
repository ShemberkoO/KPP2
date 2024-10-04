package main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ContextTest {

    private Context context;
    private DataModel dataModel;

    @BeforeEach
    void setUp() {
        context = new Context();
        dataModel = new DataModel();

        Project project = new Project("Test Project");
        project.addSprint(LocalDate.of(2024, 10, 1),
                LocalDate.of(2024, 10, 15));

        dataModel.addProject(project);
        context.getDataModel().setProjects(dataModel.getProjects());
    }

    @Test
    void testReadDataFromJson() {
        File tempFile;
        try {
            tempFile = File.createTempFile("test_read", ".json");
            context.writeDataToJson(tempFile.getPath());

            Context newContext = new Context();
            newContext.readDataFromJson(tempFile.getPath());

            List<Project> projects = newContext.getDataModel().getProjects();
            assertEquals(1, projects.size());
            assertEquals("Test Project", projects.get(0).getName());

            tempFile.delete();
        } catch (IOException e) {
            fail("Помилка при створенні тимчасового файлу");
        }
    }

    @Test
    void testWriteDataToJson() {
        File tempFile;
        try {
            tempFile = File.createTempFile("test_write", ".json");
            context.writeDataToJson(tempFile.getPath());

            FileReader reader = new FileReader(tempFile);
            assertNotNull(reader);

            Gson gson = new GsonBuilder().setPrettyPrinting()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
            DataModel dataFromFile = gson.fromJson(new FileReader(tempFile), DataModel.class);

            assertNotNull(dataFromFile);
            assertEquals(1, dataFromFile.getProjects().size());
            assertEquals("Test Project", dataFromFile.getProjects().get(0).getName());

            reader.close();
            tempFile.delete();
        } catch (IOException e) {
            fail("Помилка при створенні або запису у тимчасовий файл");
        }
    }

    @Test
    void testDeleteTask() {
        Project project = new Project("Project with Task");
        Sprint sprint = new Sprint(1, LocalDate.of(2024, 10, 1),
                LocalDate.of(2024, 10, 15));
        Task task = new Task("Task 1", "Description",
                LocalDate.of(2024, 10, 15), Priority.MEDIUM);
        sprint.addTask(task);
        project.getSprints().add(sprint);
        context.setCurrentProject(project);
        context.setCurrentSprint(sprint);

        context.deleteTask(task);

        assertFalse(context.getCurrentSprint().getTasks().contains(task));
    }
}
