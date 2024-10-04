package main;

import controllers.BaseController;
import models.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                .registerTypeAdapter(Task.class, new TaskDeserializer())
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .setPrettyPrinting()
                .create();
        try (FileReader reader = new FileReader(filePath)) {
            DataModel data = gson.fromJson(reader, DataModel.class);
            if (data != null) {
                dataModel = data;
            } else {
                dataModel = new DataModel();
            }
            removeTaskDuplicates();
        } catch (IOException e) {
            dataModel = new DataModel();
            e.printStackTrace();
        }
    }

    private void removeTaskDuplicates() {
        Map<Integer, Task> uniqueTasksMap = new HashMap<>();

        for (Project project : dataModel.getProjects()) {
            for (Sprint sprint : project.getSprints()) {
                List<Task> tasks = sprint.getTasks();
                for (Task task : tasks) {
                    if (!uniqueTasksMap.containsKey(task.getId())) {
                        uniqueTasksMap.put(task.getId(), task);
                    }
                }

                for (Task task : tasks) {
                    if (task instanceof SuperTask) {
                        SuperTask superTask = (SuperTask) task;
                        List<Task> subTasks = superTask.getSubTasks();

                        for (int i = 0; i < subTasks.size(); i++) {
                            Task subTask = subTasks.get(i);
                            if (uniqueTasksMap.containsKey(subTask.getId())) {
                                subTasks.set(i, uniqueTasksMap.get(subTask.getId()));
                            }
                        }
                    }
                }
            }
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
