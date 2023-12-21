package edu.software.application;

import edu.software.database.Database;
import edu.software.record.Barber;
import edu.software.record.Record;
import edu.software.record.Receiver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import java.io.IOException;

public class BarberPage implements Receiver {

    Database database = Database.getDatabase();

    private Barber barber;

    public void initialize(Barber barber) {
        ObservableList<Record> list = FXCollections.observableList(database.getRecordList(barber));

        records.setItems(list);

        this.barber = barber;
    }

    @FXML
    private TableView<Record> records;

    @FXML
    private void home(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("homepage.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void update(Record record) {
        Notifications
                .create()
                .text("Record: " + record + " has changed!")
                .showInformation();

        ObservableList<Record> list = FXCollections.observableList(database.getRecordList(barber));

        records.setItems(list);
    }
}
