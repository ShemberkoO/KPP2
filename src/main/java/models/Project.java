package models;

import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Project {
    private static int maxId;

    static {
        maxId = 0;
    }

    private int id;
    private String name;
    private List<Sprint> sprints;

    public Project() {
        this.id = ++maxId;
        sprints = new LinkedList<Sprint>();
    }



    public Project(String name) {
        this.id = ++maxId;
        sprints = new LinkedList<Sprint>();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
    public List<Sprint> getSprints() {
        return sprints;
    }
    public void setSprints(LinkedList<Sprint> sprints) {
        this.sprints = sprints;
    }

    public void addSprint(LocalDate startDate, LocalDate endDate) {
        int numberOfSprints = sprints.size();
        sprints.add(new Sprint(numberOfSprints, startDate, endDate));
    }

    public String toString() {
        return "{Project " + id + ", name=" + name + "\n"
                + sprints.stream().map(Objects::toString).collect(Collectors.joining("\n")) + "}";
    }
}
