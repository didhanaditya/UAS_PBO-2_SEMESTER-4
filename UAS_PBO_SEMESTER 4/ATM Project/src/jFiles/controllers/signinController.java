package jFiles.controllers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import resources.data.dataManager;
import resources.data.dataManip;

// Control center for Signing In


public class signinController {
    private dataManager database = new dataManager();

    @FXML
    TextField fullNameID = null;
    @FXML
    TextField customerID = null;
    @FXML
    TextField pinID = null;


    public void signInFromMenu(javafx.scene.input.MouseEvent mouseEvent) throws IOException {

        // Retreives sign in credentials from view

        String fullName = fullNameID.getText();
        String custNum = customerID.getText();
        String pin = pinID.getText();
        System.out.println(custNum);
        String verification = fullName + " " + custNum + " " + pin;


        // Sign In credentials validation

        if (signInVerified(verification) && (database.signInVerification(verification))) {

            Parent whichAccountRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("resources/views/whichAccount.fxml")));
            Scene whichAccount = new Scene(whichAccountRoot);

            Stage whichAccountWindow = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            whichAccountWindow.setScene(whichAccount);
            whichAccountWindow.show();
        }

    }

    // Takes user to the sign up view to create an account before signing in

    public void signUpFromMenu(MouseEvent mouseEvent) throws IOException {

        Parent newAccountRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("resources/views/createAccount.fxml")));
        Scene createAccount = new Scene(newAccountRoot);

        Stage signUpWindow = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        signUpWindow.setScene(createAccount);
        signUpWindow.show();

    }

    // Checks credentials

    private boolean signInVerified(String verification) throws IOException {
        dataManip data = new dataManip();
        return data.read(verification);
    }
}
