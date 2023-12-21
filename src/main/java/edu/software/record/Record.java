package edu.software.record;

import edu.software.application.UpdateRecord;
import edu.software.order.Order;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Timestamp;

public record Record(Integer id, User user, Barber barber, Order order, Timestamp date) {

    public String getUser() {
        return user.name();
    }

    public String getBarber() {
        return barber.name();
    }

    public String getDescription() {
        return order.description();
    }

    public Float getCost() {
        return order.cost();
    }

    public String getDate() {
        return date.toString();
    }

    public Button getButton() {
        Button button = new Button("update");

        button.setOnAction(event -> {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(UpdateRecord.class.getResource("update_record.fxml"));

            try {
                Parent root = loader.load();

                UpdateRecord updateRecord = loader.getController();
                updateRecord.initialize(this.user, this);

                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        });

        return button;
    }

    @Override
    public String toString() {
        return  barber.name() + ' ' +
                order.description() + ' ' +
                order.cost() + ' ' +
                date;
    }
}
