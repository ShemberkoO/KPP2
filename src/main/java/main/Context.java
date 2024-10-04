package main;

import controllers.BaseController;
import models.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Context {
    private DataModel dataModel;
    private Project currentProject;
    private Sprint currentSprint;
    private Task currentTask;

    private BaseController state;

    public void setState(BaseController state) {
        this.state = state;
    }

    public BaseController getState() {
       return state;
    }

    public void  deleteTask(Task task) {
        if(currentSprint != null) {
            currentSprint.getTasks().remove(task);
            currentSprint.getTasks().stream()
                    .filter(task_ -> task_.getClass() == SuperTask.class)
                    .map(task_ -> (SuperTask) task_)
                    .forEach(superTask -> superTask.getSubTasks().remove(task));
            if (task instanceof SuperTask) {
                SuperTask superTask = (SuperTask) task;
                superTask.getSubTasks().forEach(subTask -> deleteTask(subTask));
            }
        }
    }

    public Context() {
        readDataFromJson("src/main/resources/data.json");

//        Runtime.getRuntime().addShutdownHook(new Thread(this::save));
    }

    public void save(){
        writeDataToJson("result.json");
    }

    public Project getCurrentProject() {
        return currentProject;
    }
    public void setCurrentProject(Project currentProject) {
        this.currentProject = currentProject;
    }

    public Sprint getCurrentSprint() {
        return currentSprint;
    }
    public void setCurrentSprint(Sprint currentSprint) {
        this.currentSprint = currentSprint;
    }

    public Task getCurrentTask() {
        return currentTask;
    }
    public void setCurrentTask(Task currentTask) {
        this.currentTask = currentTask;
    }

    public DataModel getDataModel() {
        return dataModel;
    }

    public void readDataFromJson(String filePath) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        try (FileReader reader = new FileReader(filePath)) {
            DataModel data = gson.fromJson(reader, DataModel.class);
            // Перевіряємо, чи дані були завантажені
            if (data != null) {
                dataModel = data;
            } else {
                // Якщо дані не завантажено, створюємо новий DataModel
                dataModel = new DataModel();
            }
        } catch (IOException e) {
            // В разі помилки (файл не знайдено або інша проблема), створюємо новий DataModel
            dataModel = new DataModel();
            e.printStackTrace();
        }
    }


    public void writeDataToJson(String filePath) {
        Gson gson = new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(dataModel, writer);
        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
