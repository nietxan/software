package edu.software.application;

import edu.software.database.Database;
import edu.software.record.Record;
import edu.software.record.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

public class UserPage {
    Database database = Database.getDatabase();

    private User user;

    public void initialize(User user) {
        ObservableList<Record> list = FXCollections.observableList(database.getRecordList(user));

        records.setItems(list);

        this.user = user;
    }

    @FXML
    private TableView<Record> records;

    @FXML
    private void addRecord(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("add_record.fxml"));
        Parent parent = loader.load();

        AddRecord addRecord = loader.getController();
        addRecord.initialize(user);

        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void home(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("homepage.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }
}
