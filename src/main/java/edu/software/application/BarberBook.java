package edu.software.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BarberBook extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(BarberBook.class.getResource("index.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setTitle("BarberBook");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
