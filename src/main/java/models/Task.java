package models;

import main.Context;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Scanner;

public class Task {
    static int maxId;

    protected String title;
    protected String description;

    protected LocalDate deadline;
    protected Priority priority;
    protected Status status;
    protected int id;


    static {
        maxId = 0;
    }

    public Task() {
        this.id = ++maxId;
    }

    public Task(String title, String description, LocalDate deadline, Priority priority) {
        this.id = ++maxId;

        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
        this.status = Status.OPENED;
    }

    public SuperTask transformToSuperTask() {
        return new SuperTask(title, description, deadline, priority);
    }
    public void setStatus(Status status) {this.status = status;}
    public String getStatus() {return status.name();}

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public LocalDate getDeadline() {return deadline;}
    public void setDeadline(LocalDate deadline) {this.deadline = deadline;}

    public String getPriority() {return priority.name();}
    public void setPriority(Priority priority) {this.priority = priority;}

    public int getId() {return id;}

    public String toString() {
        return  "\nTask " + id +"\n"
                + "Title: " + title + "\n"
                + "Description: " + description + "\n"
                + "Deadline: " + deadline + "\n"
                + "Priority: " + priority + "\n"
                + "Status: " + status + "\n";
    }

    public static Task InputTask(Context context){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter task title:");
        String taskTitle = scanner.nextLine();
        System.out.println("Enter description:");
        String description = scanner.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        System.out.println("Enter deadline in format dd.MM.yyyy:");
        String input = scanner.nextLine();
        LocalDate  deadline;
        try {
            LocalDate date = LocalDate.parse(input, formatter);
            deadline = date;
            if (deadline.isAfter(context.getCurrentSprint().getEndDate())){
                deadline = context.getCurrentSprint().getEndDate();
            }
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format deadline set as Sprint end Date.");
            deadline = context.getCurrentSprint().getEndDate();
        }
        System.out.println("Enter priority(1-10):");
        Integer inp_prior = scanner.nextInt();
        Priority priority = Priority.MEDIUM;
        if(inp_prior <= 1 ){
            priority = Priority.LOW;
        }
        if(inp_prior >= 3){
            priority = Priority.HIGH;
        }

        System.out.println("Enter assignee:");
        String assignee = scanner.nextLine();
        return  new Task(taskTitle,description,deadline,priority);
    }
}


