package jFiles.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import resources.data.dataManager;

import java.io.IOException;
import java.util.Objects;

// View for the deposit prompt

public class depositPromptController {

    private dataManager database = new dataManager();
    @FXML
    public Label successLabel;
    @FXML
    public Button cancelButton;
    @FXML
    public Button depositButton;
    @FXML
    TextField depositValue = null;


    public void cancel(MouseEvent mouseEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    // Submits desired deposits

    public void deposit(MouseEvent mouseEvent) throws IOException {
        String val = depositValue.getText();
        float amount = Float.parseFloat(val);

        if (amount > 0) {
            database.deposit(amount);

            Parent successRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("resources/views/depositStatus.fxml")));
            Scene success = new Scene(successRoot);

            Stage successWindow = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            successWindow.setScene(success);
            successWindow.show();

        }
    }
}
