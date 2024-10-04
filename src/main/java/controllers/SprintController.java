package controllers;

import main.Context;
import main.JavaFxWindowLoader;
import models.Priority;
import models.Project;
import models.Task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

public class SprintController extends BaseController {

    public SprintController(Context context) {
        super(context);
    }

    @Override
    public void sendActionsList() {
        super.sendActionsList();
        System.out.println("\n---------------");
        System.out.println("s1. Переглянути список завдань за пріоритетами");
        System.out.println("s2. Додати завдання");
        System.out.println("s3. Обрати  завдання");
        System.out.println("s4. Видалити завдання");
        System.out.println("s5. Відкрити інтерфейс з графіком");
        System.out.println("s6. Повернутись до спрінтів");
        System.out.println("s7. Повернутись до проектів");

    }

    @Override
    protected List<Task> ClosestDeadlineTasks() {
        return context.getCurrentSprint().getTasks().stream()
                .sorted(Comparator.comparing(Task::getDeadline))
                .limit(5)
                .collect(Collectors.toList());
    }

    @Override
    public void answerToInputAction(String input) {
        switch (input) {
            case "s1":
                var tasks = context.getCurrentSprint().getTasks().stream()
                    .sorted(Comparator.comparing(Task::getPriority))
                    .collect(Collectors.toList());
                if (tasks.isEmpty()) {
                    System.out.println("tasks is empty");
                }
                else {
                    System.out.println(tasks);
                }
                break;
            case "s2":
                context.getCurrentSprint().addTask(Task.InputTask(context));
                break;
            case "s3":
                var res = selectTask();
                if (res.isPresent()){
                    context.setCurrentTask(res.get());
                    context.setState(new TaskController(context));
                    System.out.println("\n ----task selected---- \n");
                }else{
                    System.out.println("\n ----task was NOT selected---- \n");
                }
                break;
            case "s4":
                var taskToDel = selectTask();
                if (taskToDel.isPresent()) {
                    System.out.println("\n ----task deleted---- \n");
                    context.deleteTask(taskToDel.get());
                }else{
                    System.out.println("\n ----task was NOT deleted---- \n");
                }
                break;
            case "s5":
                OpenGraphicsWindow();
                break;
            case "s6":
                context.setCurrentSprint(null);
                context.setState(new ProjectController(context));
                return;
            case "s7":
                context.setCurrentSprint(null);
                context.setCurrentProject(null);
                context.setState(new BaseController(context));
                return;
            default:
                super.answerToInputAction(input);
        }
    }

    private Optional<Task> selectTask(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter task number:");
        int number =  scanner.nextInt();
        return context.getCurrentSprint().getTasks().stream().filter(t -> t.getId() == number).findFirst();
    }

    private void OpenGraphicsWindow() {
        JavaFxWindowLoader.openNewWindow(context);
    }

}
