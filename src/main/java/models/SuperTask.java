package models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class SuperTask extends Task {
    private List<Task> subTasks;

    public SuperTask() {
        super();
        subTasks = new ArrayList<>();
    }
    public SuperTask(String title, String description, LocalDate deadline, Priority priority) {
        super(title, description, deadline, priority);
        subTasks = new ArrayList<>();
    }

    public void addSubTask(Task t) {subTasks.add(t);}
    public List<Task> getSubTasks() {return subTasks;}

    @Override
    public String toString() {
        var str = subTasks.stream().map(t ->  String.valueOf(t.getId()))
                .collect(Collectors.joining(", "));
        return  super.toString() + "\nSub Tasks: " + str;
    }
}

