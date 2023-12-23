package edu.software.application;

import edu.software.database.Database;
import edu.software.record.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AddUser {

    Database database = Database.getDatabase();

    @FXML
    private TextField phoneNumber;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private void confirm(ActionEvent event) throws IOException {
        User user = new User(
                database.getLastUserId() + 1,
                username.getText(),
                phoneNumber.getText(),
                password.getText()
        );

        database.insertUser(user);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("user_page.fxml"));

        Parent root = loader.load();
        UserPage userPage = loader.getController();
        userPage.initialize(database.getUser(username.getText()));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
