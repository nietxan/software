module edu.software.Application {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.jetbrains.annotations;

    opens edu.software.Application to javafx.fxml;
    exports edu.software.Application;
}