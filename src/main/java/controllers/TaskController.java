package controllers;

import main.Context;
import models.Status;
import models.SuperTask;
import models.Task;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TaskController extends BaseController{


    public TaskController(Context context) {
        super(context);
    }

//    public void CreateSubtask(){
//        if(context.getCurrentTask().getClass() == Task.class){
//            var currentTask = context.getCurrentTask();
//            SuperTask superTask = currentTask.transformToSuperTask();
//            context.getCurrentSprint();
//            context.setCurrentTask(superTask);
//            context.getCurrentSprint()
//                    .getTasks()
//                    .replaceAll(task -> task.equals(currentTask) ? superTask : task);
//        }
//    }

    @Override
    protected List<Task> ClosestDeadlineTasks() {
        return context.getCurrentSprint().getTasks().stream()
                .sorted(Comparator.comparing(Task::getDeadline))
                .limit(5)
                .collect(Collectors.toList());
    }

    @Override
    public void sendActionsList() {
        super.sendActionsList();
        System.out.println("t1. Додати підзавдання");
        System.out.println("t2. Видалити підзавдання");
        System.out.println("t3. Переглянути SubTasks");
        System.out.println("t4. Переглянути SuperTasks");
        System.out.println("t5. Змінити статус");
        System.out.println("t6. Повернутись до завдань");
        System.out.println("t7. Повернутись до спринтів");
        System.out.println("t8. Повернутись до проектів");
    }

    @Override
    public void answerToInputAction(String input) {
        switch (input) {
            case "t1":
                AddSubtask();
                return;
            case "t2":
                deleteSubtask();
                return;
            case "t3":
                watchSubtasks();
                return;
            case "t4":
                watchSupertask();
                return;
            case "t5":
                changeStatus();
                return;
            case "t6":
                context.setCurrentTask(null);
                context.setState(new SprintController(context));
                return;
            case "t7":
                context.setCurrentTask(null);
                context.setCurrentSprint(null);
                context.setState(new ProjectController(context));
                return;
            case "t8":
                context.setCurrentTask(null);
                context.setCurrentSprint(null);
                context.setCurrentProject(null);
                context.setState(new BaseController(context));
                return;
            default:
                super.answerToInputAction(input);
        }
    }

    private void deleteSubtask() {
        var taskToDel = selectSubtask();
        if(taskToDel.isPresent()){
           context.deleteTask(taskToDel.get());
        }else {
            System.out.println("Завдання не знайдено");
        }
    }

    private void watchSubtasks() {
        var tasksToWatch = context.getCurrentTask();
        if(context.getCurrentTask().getClass() == SuperTask.class){
            System.out.println(((SuperTask)context.getCurrentTask()).getSubTasks());
        }else {
            System.out.println("Немає підзавдань");
        }
        return;
    }

    private void watchSupertask() {
        var res = findSuperTask();
        if(res.isPresent()){
            System.out.println(res.get());
        }else {
            System.out.println("Завдання не є SubTask");
        }
    }

    private void changeStatus() {
        System.out.println("\nEnter new status\n");
        System.out.println(" OPENED, TODO, IN_PROGRESS, COMPLETED, BLOCKED, REOPENED\n");
        Scanner scanner = new Scanner(System.in);
        String inputSt =  scanner.nextLine().toUpperCase();
        try {
            Status newStatus = Status.valueOf(inputSt);
            System.out.println("New status: " + newStatus);
            context.getCurrentTask().setStatus(newStatus);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid status entered. Please enter a valid status.");
        }
    }

    private Optional<Task> selectSubtask(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter task id:");
        int number =  scanner.nextInt();
        if (context.getCurrentTask().getClass() == SuperTask.class) {
            return ((SuperTask)context.getCurrentTask())
                    .getSubTasks().stream().filter(t -> t.getId() == number).findFirst();
        }
        return Optional.empty();
    }

    private Optional<SuperTask> findSuperTask(){
            var res = context.getCurrentSprint().getTasks().stream()
                    .filter(task_ -> task_.getClass() == SuperTask.class)
                    .map(task_ -> (SuperTask) task_)
                    .filter(superTask -> superTask.getSubTasks().contains(context.getCurrentTask()))
                    .findFirst();
            return res;
    }

    private void AddSubtask() {
        if (context.getCurrentTask().getClass() == Task.class) {
            SuperTask superTask = context.getCurrentTask().transformToSuperTask();

            List<Task> tasks = context.getCurrentSprint().getTasks();

            int index = tasks.indexOf(context.getCurrentTask());

            if (index != -1) {
                tasks.set(index, superTask);
                context.setCurrentTask(superTask);
            }
        }
        var task = Task.InputTask(context);
        ((SuperTask) context.getCurrentTask()).addSubTask(task);
        context.getCurrentSprint().addTask(task);
    }
}
