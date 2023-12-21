package edu.software.application;

import edu.software.database.Database;
import edu.software.record.Receiver;
import edu.software.record.Record;
import edu.software.record.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
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

    private Record save;

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

        this.save = record;
        database.deleteRecord(record);
    }

    @Override
    protected void confirm(ActionEvent event) throws IOException {

        receiverList.add(database);

        for (Window window : Stage.getWindows()) if (window instanceof Stage stage) {
            if (stage.getTitle().equals("Barber Page")) {
                // need to add controller Barber Page
            }
        }

        for (Receiver receiver : receiverList) {
            receiver.update(new Record(
                    save.id(),
                    super.user,
                    super.barber,
                    super.order,
                    super.date
            ));
        }

        super.confirm(event);
    }

    @Override
    protected void home(ActionEvent event) throws IOException {
        database.insertRecord(save);
        super.home(event);
    }
}
