package edu.software.application;

import edu.software.database.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class BarberAuth {

    Database database = Database.getDatabase();

    @FXML
    private TextField barber_name;

    @FXML
    private PasswordField password;

    @FXML
    private TextArea note;

    @FXML
    private void singIn(ActionEvent event) throws IOException {
        switch (database.checkBarber(barber_name.getText(), password.getText())) {
            case 0: {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("barber_page.fxml"));
                Parent parent = loader.load();

                BarberPage page = loader.getController();
                page.initialize(database.getBarber(barber_name.getText()));

                stage.setScene(new Scene(parent));
                stage.setTitle("Barber Page");
                stage.show();

                Stage current = (Stage) ((Node) event.getSource()).getScene().getWindow();
                loader = new FXMLLoader(getClass().getResource("homepage.fxml"));
                current.setScene(new Scene(loader.load()));
                current.show();
                break;
            }

            case 1: {
                note.setText("Wrong password");
                break;
            }

            case -1: {
                note.setText("User not found");
                break;
            }
        }
    }
}
