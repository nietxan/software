module application {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.controlsfx.controls;

    opens edu.software.application to javafx.fxml;
    exports edu.software.application;
    exports edu.software.record;
    exports edu.software.order;
}