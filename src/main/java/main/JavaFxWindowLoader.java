package main;

import View.ProjectViewController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class JavaFxWindowLoader extends Application {

    private static boolean isLaunched = false;  // Контроль запуску JavaFX

    @Override
    public void start(Stage stage) throws IOException {

        Platform.setImplicitExit(false);
        isLaunched = true;
    }

    public static void openNewWindow(Context context) {
        if (!isLaunched) {
            // Якщо JavaFX ще не запущений, запускаємо
            new Thread(() -> Application.launch(JavaFxWindowLoader.class)).start();
        }

        // Відкриваємо нове вікно в JavaFX потоці
        Platform.runLater(() -> {
            Stage newStage = new Stage();
            try {
                FXMLLoader loader = new FXMLLoader(JavaFxWindowLoader.class.getResource("project-view.fxml"));
                Parent root = loader.load();

                ProjectViewController controller = loader.getController();
                controller.setContext(context);

                Scene scene = new Scene(root);
                newStage.setTitle("JavaFX Вікно з FXML");
                newStage.setScene(scene);
                newStage.setWidth(400);  // Ширина вікна
                newStage.setHeight(600);
                newStage.show();

                newStage.setOnCloseRequest(event -> {
                    System.out.println("JavaFX вікно закрите.");
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
