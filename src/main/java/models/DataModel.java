package models;

import java.util.ArrayList;
import java.util.List;

public class DataModel {

    private List<Project> projects;

    public DataModel() {
        projects = new ArrayList<>();
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public void ReadProjectsFromJson(String filename) {

    }

     public  void WriteProjectsToJson(String filename) {

     }

    public List<Project> getProjects() {
        return projects;
    }

    public void addProject(Project project) {
        projects.add(project);
    }

    public  void removeProject(Project project) {
        projects.remove(project);
    }
}
