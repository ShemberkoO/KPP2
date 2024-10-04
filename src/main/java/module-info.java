module second.task.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.google.gson;

    opens controllers  to javafx.fxml;
    exports controllers ;
    opens main  to javafx.fxml;
    exports main ;
    exports View; // Це дозволить доступ до класів у пакеті View
    opens View to javafx.fxml;

    opens models to com.google.gson; // Додаємо цю лінію
}