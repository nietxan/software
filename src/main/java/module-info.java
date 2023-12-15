module application {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens edu.software.application to javafx.fxml;
    exports edu.software.application;
}