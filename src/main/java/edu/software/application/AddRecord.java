package edu.software.application;

import edu.software.database.Database;
import edu.software.order.Order;
import edu.software.record.Barber;
import edu.software.record.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.util.List;

public class AddRecord {

    Database database = Database.getDatabase();

    private User user;

    public void initialize(User user) {
        this.user = user;
    }

    private Order order;
    private Barber barber;

    @FXML
    private Button barber_choice;

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
    private void confirm() {

    }
}
