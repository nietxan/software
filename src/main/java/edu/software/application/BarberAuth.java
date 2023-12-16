package edu.software.application;

import edu.software.database.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class BarberAuth {

    Database database = Database.getDatabase();

    @FXML
    private TextField barber;

    @FXML
    private PasswordField password;

    @FXML
    private TextArea note;

    @FXML
    private void singIn(ActionEvent event) throws IOException {

        switch (database.checkBarber(barber.getText(), password.getText())) {
            case 0: {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("barber_page.fxml"));
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
                stage.show();
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