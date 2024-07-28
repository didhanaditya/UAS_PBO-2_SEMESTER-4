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

public class withdrawPromptController {

    private dataManager database = new dataManager();
    @FXML
    public Label successLabel;
    @FXML
    public Button cancelButton;
    @FXML
    public Button withdrawButton;
    @FXML
    public TextField cashValue;

    // View for withdraw prompt and prevents a withdrawal if there are insufficient funds

    public void withdraw(MouseEvent mouseEvent) throws IOException {
        String val = cashValue.getText();
        float amount = Float.parseFloat(val);

        if (amount > 0) {
            if (amount < database.accountBalance()) {
                database.accountBalance();
                database.withdraw(amount);

                Parent successRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("resources/views/withdrawStatus.fxml")));
                Scene success = new Scene(successRoot);

                Stage successWindow = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
                successWindow.setScene(success);
                successWindow.show();
            } else {
                Parent failedRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("resources/views/withdrawFailed.fxml")));
                Scene failed = new Scene(failedRoot);

                Stage failedWindow = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
                failedWindow.setScene(failed);
                failedWindow.show();
            }
        }
    }
    public void cancel(MouseEvent mouseEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
