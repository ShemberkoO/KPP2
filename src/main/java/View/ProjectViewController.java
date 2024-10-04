package View;

import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.layout.GridPane;
import main.Context;
import models.Task; // Імпортуйте клас Task
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProjectViewController {

    private Context context;

    @FXML
    private GridPane calendarGrid;

    @FXML
    private BarChart<String, Number> taskGraph;

    @FXML
    private PieChart taskPieChart;
    @FXML
    private BarChart<String, Number> deadlineGraph;

    @FXML
    private CategoryAxis deadlineXAxis;

    @FXML
    private NumberAxis deadlineYAxis;


    @FXML
    public void initialize() {
        // Ініціалізація календаря
    }

    public void setContext(Context context) {
        this.context = context;
        displayDeadlineGraph();

        displayTaskGraph();
        displayTaskPieChart();
    }

    private void displayDeadlineGraph() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Кількість днів до дедлайну");

        // Отримайте список задач з поточного спринта
        List<Task> tasks = context.getCurrentSprint().getTasks();

        // Додайте дані до графіку
        for (Task task : tasks) {
            long daysToDeadline = ChronoUnit.DAYS.between(LocalDate.now(), task.getDeadline());
            series.getData().add(new XYChart.Data<>(task.getTitle(), daysToDeadline));
        }

        deadlineGraph.getData().add(series);
    }

    private void displayTaskGraph() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Завдання");

        // Отримайте список задач з поточного спринта
        List<Task> tasks = context.getCurrentSprint().getTasks();

        // Групування задач за пріоритетом
        Map<String, Long> priorityCounts = tasks.stream()
                .collect(Collectors.groupingBy(Task::getPriority, Collectors.counting()));

        // Додайте дані до графіку
        priorityCounts.forEach((priority, count) ->
                series.getData().add(new XYChart.Data<>(priority, count)));

        taskGraph.getData().add(series);
    }


    private void displayTaskPieChart() {
        // Отримайте список задач з поточного спринта
        List<Task> tasks = context.getCurrentSprint().getTasks();

        // Групування задач за статусом
        Map<String, Long> statusCounts = tasks.stream()
                .collect(Collectors.groupingBy(Task::getStatus, Collectors.counting()));

        // Додайте дані до кругової діаграми
        for (Map.Entry<String, Long> entry : statusCounts.entrySet()) {
            PieChart.Data slice = new PieChart.Data(entry.getKey(), entry.getValue());
            taskPieChart.getData().add(slice);
        }
    }
}
