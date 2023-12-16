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
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class UserPage {
    Database database = Database.getDatabase();

    public void initialize(User user) {
        ObservableList<Button> list = FXCollections.observableList(new ArrayList<>());

        for (Record record : database.getRecordList(user)) {
            TextArea area = new TextArea();
            area.setPrefRowCount(1);
            area.setEditable(false);
            area.setText(String.valueOf(record));
            area.autosize();

            Button button = new Button();
            button.setText("Edit");
            button.setGraphic(area);
            button.setOnAction(event -> {
                try {
                    Stage stage = new Stage();

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("update_record.fxml"));
                    Parent parent = loader.load();

                    UpdateRecord updateRecord = loader.getController();
                    updateRecord.initialize(record);

                    Scene scene = new Scene(parent);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            list.add(button);
        }

        records.setItems(list);
    }

    @FXML
    private ListView<Button> records;

    @FXML
    private void addRecord(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("add_record.fxml"));
        Scene scene = new Scene(loader.load());
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
