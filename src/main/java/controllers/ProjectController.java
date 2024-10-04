package controllers;

import main.Context;
import main.JavaFxWindowLoader;
import models.Sprint;
import models.Task;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ProjectController extends BaseController {
    public ProjectController(Context context) {
        super(context);
    }

    @Override
    public void sendActionsList() {
        super.sendActionsList();
        System.out.println("\n---------------");
        System.out.println("p1. Переглянути список спрінтів");
        System.out.println("p2. Додати спрінт");
        System.out.println("p3. Вибрати спрінт");
        System.out.println("p4. Повернутись до проектів");

    }

    @Override
    protected List<Task> ClosestDeadlineTasks() {
        return context.getCurrentProject().getSprints().stream()
                .flatMap(sprint -> sprint.getTasks().stream())
                .sorted(Comparator.comparing(Task::getDeadline))
                .limit(5)
                .collect(Collectors.toList());
    }

    @Override
    public void answerToInputAction(String input) {
        switch (input) {
            case "p1":
                System.out.println(context.getCurrentProject().getSprints());
                break;
            case "p2":
                AddSprintToCurrentProject();
                System.out.println(context.getCurrentProject().getSprints());
                break;
            case "p3":
                var res = selectSprint();
                if (res.isPresent()){
                    context.setCurrentSprint(res.get());
                    context.setState(new SprintController(context));
                    System.out.println("\n ----sprint selected---- \n");
                }else{
                    System.out.println("\n ----sprint was NOT selected---- \n");
                }
                break;
            case "p4":
                context.setCurrentProject(null);
                context.setState(new BaseController(context));
                break;
            default:
                super.answerToInputAction(input);
        }
    }

    private void AddSprintToCurrentProject() {
        LocalDate startDate =context.getCurrentProject().getSprints().isEmpty() ? LocalDate.now() :
                context.getCurrentProject().getSprints().getLast().getEndDate();

        LocalDate endDate = startDate.plusDays(14);

        context.getCurrentProject().addSprint(startDate, endDate);
    }

    public Optional<Sprint> selectSprint(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter sprint number:");
        int number =  scanner.nextInt();
        return context.getCurrentProject().getSprints().stream().filter(p -> p.getNumber() == number).findFirst();
    }

}
