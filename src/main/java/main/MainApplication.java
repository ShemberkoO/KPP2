package main;

import controllers.BaseController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("project-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        Context context = new Context();
        context.setState(new BaseController(context));

        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            context.getState().sendActionsList();
            input = scanner.nextLine();

            if ("exit".equals(input)) {
                Platform.exit(); // Закриває JavaFX
                return; // Завершує цикл
            }

            context.getState().answerToInputAction(input);
        }
    }

}
