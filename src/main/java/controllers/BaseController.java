package controllers;

import main.Context;
import models.Project;
import models.Task;
import java.util.*;
import java.util.stream.Collectors;

public class BaseController {
    protected Context context;

    public BaseController(Context context) {
        this.context = context;
    }

    public void sendActionsList(){
        System.out.println("\nВиберіть дію:");
        System.out.println("1. Переглянути список проектів");
        System.out.println("2. Додати проект");
        System.out.println("3. Видалити проект");
        System.out.println("4. Вибрати проект");
        System.out.println("5. Переглянути завдання з найближчими дедлайнами");
        System.out.println("6. Записати в файл");
        System.out.println("exit  Вийти");
    }

    public void answerToInputAction(String input){
        switch (input) {
            case "1":
               var pr = context.getDataModel().getProjects();
               System.out.println(pr);
                break;
            case "2":
                CreateProjectWithInput();
                break;
            case "3":
                var projectToDel = selectProject();
                if(projectToDel.isPresent()){
                    context.getDataModel().removeProject(projectToDel.get());
                    System.out.println("\n ----project deleted---- \n");
                }else{
                    System.out.println("\n ----project was NOT found---- \n");
                }
                break;
            case "4":
               var res = selectProject();
               if (res.isPresent()){
                   context.setCurrentProject(res.get());
                   context.setState(new ProjectController(context));
                   System.out.println("\n ----project selected---- \n");
               }else{
                   System.out.println("\n ----project was NOT selected---- \n");
               }
                break;
            case "5":
                var tasks = ClosestDeadlineTasks();
                if(ClosestDeadlineTasks().size() > 1){
                    System.out.println(tasks);
                }else {
                    System.out.println("No Tasks in Add");
                }
                break;
            case "6":
               context.writeDataToJson("src/main/resources/data.json");
                break;
            default:
                System.out.println("Неправильний вибір. Спробуйте ще раз.");
        }
    }

    public Optional<Project> selectProject(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter project id:");
        int id =  scanner.nextInt();
        return context.getDataModel().getProjects().stream().filter(p -> p.getId() == id).findFirst();
    }

    protected List<Task> ClosestDeadlineTasks() {
        return context.getDataModel().getProjects().stream()
                .flatMap(project -> project.getSprints().stream())
                .flatMap(sprint -> sprint.getTasks().stream())
                .sorted(Comparator.comparing(Task::getDeadline))
                .limit(5)
                .collect(Collectors.toList());
    }

    public void CreateProjectWithInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter project name:");
        String name = scanner.nextLine();
        context.getDataModel().addProject(new Project(name));
        System.out.println("\n ----project created---- \n");
    }
}
