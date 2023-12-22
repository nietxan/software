package edu.software.application;

import edu.software.database.Database;
import edu.software.record.Receiver;
import edu.software.record.Record;
import edu.software.record.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UpdateRecord extends AddRecord {

    Database database = Database.getDatabase();

    private Record record;

    private final List<Receiver> receiverList = new ArrayList<>();

    public void initialize(User user, Record record) {
        super.user = record.user();
        super.barber = record.barber();
        super.order = record.order();
        super.date = record.date();

        super.barber_choice.setText(barber.name());
        super.date_picker.setValue(LocalDate.parse(
                record.date().toString().substring(0, 10),
                DateTimeFormatter.ISO_DATE
        ));

        super.initialize(user);

        this.user = user;
        this.record = record;
    }

    @Override
    protected void confirm(ActionEvent event) throws IOException {

        receiverList.add(database);

        Stage stage = new Stage();

        List<Stage> toHide = new ArrayList<>();

        for (Window window : Stage.getWindows()) {
            if (window instanceof Stage loaded && loaded.getTitle().equals(String.format("%s Page", this.record.barber().name()))) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("barber_page.fxml"));

                stage.setScene(new Scene(loader.load()));
                BarberPage page = loader.getController();

                receiverList.add(page);
                toHide.add(loaded);
            }
        }

        for (Stage loaded : toHide) {
            loaded.hide();
        }

        Record record = new Record(
                this.record.id(),
                super.user,
                super.barber,
                super.order,
                super.date
        );

        for (Receiver receiver : receiverList) {
            receiver.update(record);
        }

        if (stage.getScene() != null) {
            stage.setTitle(String.format("%s Page", record.barber().name()));
            stage.show();
        }

        Stage current = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("user_page.fxml"));
        Parent parent = loader.load();
        UserPage userPage = loader.getController();
        userPage.initialize(super.user);
        current.setScene(new Scene(parent));
        current.show();
    }

    @Override
    protected void home(ActionEvent event) throws IOException {
        super.home(event);
    }
}
