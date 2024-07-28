package jFiles.controllers;

import javafx.scene.control.Label;
import resources.data.Customer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import resources.data.dataManager;

import java.io.IOException;
import java.util.Objects;

// Controller for account creation


public class createAccountController {
    private dataManager database = new dataManager();

    @FXML
    TextField firstNameID = null;
    @FXML
    TextField lastNameID = null;
    @FXML
    TextField pinID = null;
    @FXML
    TextField pinConfirmID = null;

    private boolean agreed = false;


    public void goToSignIn(MouseEvent mouseEvent) throws IOException {

        Parent signinRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("resources/views/signinform.fxml")));
        Scene signIn = new Scene(signinRoot);

        Stage signInWindow = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        signInWindow.setScene(signIn);
        signInWindow.show();
    }

    // Checks if terms and conditions are agreed to

    public void agreedToTerms(MouseEvent mouseEvent) {
        this.agreed = !this.agreed;
    }

    // Instructions when create account button is pressed
    // Checks if credentials are valid

    public void createAccount(MouseEvent mouseEvent) throws IOException {

        String firstName = firstNameID.getText();
        String lastName = lastNameID.getText();
        String pinChars = pinID.getText();

        boolean digitCheck = true;
        for (int i = 0; i < pinChars.length(); i++) {
            if (Character.isLetter(pinChars.charAt(i))) {
                digitCheck = false;
            }
        }

        // Validates the inputted credentials

        if ((pinID.getText().equals(pinConfirmID.getText())) && (pinID.getText().length() == 4) && (this.agreed) && (digitCheck)) {

            Parent signinRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("resources/views/signinform.fxml")));
            Scene signIn = new Scene(signinRoot);

            Stage signInWindow = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            signInWindow.setScene(signIn);
            signInWindow.show();
            System.out.println("Account Created");
            Customer tempCust = new Customer(firstName, lastName, pinChars);
            tempCust = null;

            // Pop Window for unique Customer Number

            String formattedCustNum = String.format("         Your Customer # is: %s", database.getCustomerNumber());
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Customer Number");
            database.getCustomerNumber();
            Label label = new Label(formattedCustNum);

            Scene scene = new Scene(label, 200, 100);
            primaryStage.setScene(scene);
            primaryStage.show();

        } else {
            System.out.println("Something went wrong :(");
        }
    }

}
