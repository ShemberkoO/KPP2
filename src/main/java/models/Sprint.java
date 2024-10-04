package models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Sprint {
    private int number;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<Task> tasks;

    public Sprint(int number, LocalDate startDate, LocalDate endDate) {
        this.tasks = new ArrayList<>();
        this.number = number;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Sprint(int number) {
        this.tasks = new ArrayList<>();
        this.number = number;
    }
    public int getNumber() {
        return number;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }
    public List<Task> getTasks() {
        return tasks;
    }
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public LocalDate getStartDate() {
        return startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public void setStartDate(LocalDate date) {
        this.startDate = date;
    }
    public void setEndDate(LocalDate date) {
        this.endDate = date;
    }

    public String toString() {
        return "{Sprint " + number + " " +startDate +"-"+ endDate + "\n"
                + tasks.stream().map(Objects::toString)
                .collect(Collectors.joining("\n\n")) + "}";
    }
}
