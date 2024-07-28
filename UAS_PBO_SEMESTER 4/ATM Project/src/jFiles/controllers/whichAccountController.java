package jFiles.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import resources.data.dataManager;

import java.io.IOException;
import java.util.Objects;

// View for user to pick which account they want to go in

public class whichAccountController {

    private dataManager database = new dataManager();

    // Ability to sign out from this view

    public void signOut(MouseEvent mouseEvent) throws IOException {
        Parent signinRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("resources/views/signinform.fxml")));
        Scene signIn = new Scene(signinRoot);

        Stage signInWindow = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        signInWindow.setScene(signIn);
        signInWindow.show();
    }

    public void goToChecking(MouseEvent mouseEvent) throws IOException {

        database.writeAccount("checking");

        Parent dashboardRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("resources/views/AccountDashboard.fxml")));
        Scene dashboard = new Scene(dashboardRoot);

        Stage dashWindow = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        dashWindow.setScene(dashboard);
        dashWindow.show();
    }

    public void goToSavings(MouseEvent mouseEvent) throws IOException {

        database.writeAccount("savings");

        Parent dashboardRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("resources/views/AccountDashboard.fxml")));
        Scene dashboard = new Scene(dashboardRoot);

        Stage dashWindow = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        dashWindow.setScene(dashboard);
        dashWindow.show();
    }
}
