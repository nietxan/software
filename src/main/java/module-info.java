module edu.software.final_project {
    requires javafx.controls;
    requires javafx.fxml;


    opens edu.software.final_project to javafx.fxml;
    exports edu.software.final_project;
}