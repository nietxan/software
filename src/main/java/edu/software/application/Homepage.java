package edu.software.application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Homepage {
    @FXML
    private Button bookNow;

    public void onAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }
}
