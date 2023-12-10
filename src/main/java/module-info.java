module edu.software.Application {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens edu.software.Application to javafx.fxml;
    exports edu.software.Application;
}