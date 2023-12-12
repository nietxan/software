package edu.software.Application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable  {
    @FXML
    private Pane homepage;

    @FXML
    private void handlePane() {
        System.out.println("yoo");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
