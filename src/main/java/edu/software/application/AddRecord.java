package edu.software.application;

import edu.software.database.Database;
import edu.software.order.*;
import edu.software.record.Barber;
import edu.software.record.Record;
import edu.software.record.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AddRecord {
    Database database = Database.getDatabase();

    private User user;
    private Barber barber;
    private Order order;
    private Timestamp date;

    public void initialize(User user) {
        this.user = user;
    }


    @FXML
    private DatePicker date_picker;

    @FXML
    private void pickTime() {
        LocalDate date = date_picker.getValue();

        if (date == null) {
            return;
        }

        Stage stage = new Stage();

        VBox vBox = new VBox();

        List<Integer> timeList = database.getRecordsTimeOfDay(date);

        HBox hBox = new HBox();

        for (int i = 10; i <= 21; i++) {
            Button button = new Button();
            button.setText(String.format("%d:00", i));
            button.setOnAction(event -> {
                this.date = Timestamp.valueOf(String.format("%s %s:00", date.format(DateTimeFormatter.ISO_DATE), button.getText()));
                stage.hide();
            });

            if (timeList.contains(i)) {
                button.setStyle("-fx-background-color: tomato");
                button.setDisable(true);
            }

            hBox.getChildren().add(button);

            if (hBox.getChildren().size() == 4) {
                vBox.getChildren().add(hBox);
                hBox = new HBox();
            }
        }

        stage.setScene(new Scene(vBox));
        stage.show();
    }

    @FXML
    private Button barber_choice;

    @FXML
    private void barberChoose() {
        Stage stage = new Stage();

        List<Barber> barberList = database.getBarberList();

        VBox vBox = new VBox();

        for (Barber barber : barberList) {
            Button button = new Button();
            button.setLayoutY(10);
            button.setPrefSize(80, 30);
            button.setText(barber.name());
            button.setOnAction(event -> {
                barber_choice.setText(barber.name());
                this.barber = barber;
                stage.hide();
            });

            vBox.getChildren().add(button);
        }

        vBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vBox, 200, 200);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void makeOrder() throws IOException {
        Stage stage = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("make_order.fxml"));
        Parent parent = loader.load();

        Button confirm = (Button) parent.lookup("#confirm");
        confirm.setOnAction(event -> {
            Order order = new BaseOrder(((TextField) parent.lookup("#description")).getText(), 200f);

            HBox hBox = (HBox) parent.lookup("#box");

            for (Node node : hBox.getChildrenUnmodifiable()) {
                if (node instanceof CheckBox checkBox && checkBox.isSelected()) {
                    if (checkBox.getText().equals("Hair")) {
                        order = new HairDecorator(order);
                    }

                    else if (checkBox.getText().equals("Beard")) {
                        order = new BeardDecorator(order);
                    }

                    else if (checkBox.getText().equals("Hair Care")) {
                        order = new HairCareDecorator(order);
                    }
                }
            }

            this.order = order;
            stage.hide();
        });

        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void confirm(ActionEvent event) throws IOException {
        Record record = new Record(
                database.getLastRecordId() + 1,
                this.user,
                this.barber,
                this.order,
                this.date
        );

        database.insertRecord(record);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("homepage.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
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
