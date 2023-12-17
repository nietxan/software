package edu.software.application;

import edu.software.database.Database;
import edu.software.order.*;
import edu.software.record.Barber;
import edu.software.record.Record;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class AddRecord {
    Database database = Database.getDatabase();

    public void initialize() {
        List<Record> recordList = database.getRecordList();

        date.setDayCellFactory(
                new Callback<>() {
                    @Override
                    public DateCell call(DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate date, boolean empty) {
                                super.updateItem(date, empty);

                                for (Record record : recordList) {
                                    if (date.isEqual(record.date().toLocalDate())) {
                                        setDisable(true);
                                        setStyle("-fx-background-color: #ffc0cb");
                                    }
                                }
                            }
                        };
                    }
                }
        );
    }

    private Order order;
    private Barber barber;

    @FXML
    private Button barber_choice;

    @FXML
    private DatePicker date;

    @FXML
    private void barberChoose() {
        Stage stage = new Stage();

        List<Barber> barberList = database.getBarberList();

        VBox vBox = new VBox();

        for (Barber barber : barberList) {
            Button button = new Button();
            button.setText(barber.name());
            button.setOnAction(event -> {
                barber_choice.setText(barber.name());
                this.barber = barber;
                stage.hide();
            });

            vBox.getChildren().add(button);
        }

        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void makeOrder() throws IOException {
        Stage stage = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("make_order.fxml"));
        Parent parent = loader.load();
        MakeOrder makeOrder = loader.getController();
        Button confirm = makeOrder.confirm;
        confirm.setOnAction(event -> {
            Order order = new BaseOrder(((TextField)parent.lookup("#description")).getText(), 0f);

            for (Node node : parent.getChildrenUnmodifiable()) {
                if (node instanceof CheckBox checkBox) {
                    if (checkBox.isSelected()) {
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
            }

            this.order = order;
            stage.hide();
        });

        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void confirm() {
        System.out.println(order.description());
    }
}
