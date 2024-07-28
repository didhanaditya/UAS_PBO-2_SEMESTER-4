package jFiles.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class dashboardController {
    public Label balanceLabel;

    // Account Dashboard control center

    public void signOut(MouseEvent mouseEvent) throws IOException {
        Parent signinRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("resources/views/signinform.fxml")));
        Scene signIn = new Scene(signinRoot);

        Stage signInWindow = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        signInWindow.setScene(signIn);
        signInWindow.show();
    }

    public void backToAccounts(MouseEvent mouseEvent) throws IOException {
        Parent accountsRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("resources/views/whichAccount.fxml")));
        Scene accounts = new Scene(accountsRoot);

        Stage accountsWindow = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        accountsWindow.setScene(accounts);
        accountsWindow.show();
    }

    public void getCash(MouseEvent mouseEvent) throws IOException {

        Parent withdrawPromptRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("resources/views/withdrawPrompt.fxml")));
        Scene withdrawPrompt = new Scene(withdrawPromptRoot);

        Stage withdrawWindow = new Stage();

        withdrawWindow.setTitle("Withdraw Prompt");
        withdrawWindow.setScene(withdrawPrompt);
        withdrawWindow.show();
    }

    public void deposit(MouseEvent mouseEvent) throws IOException {

        Parent depoPromptRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("resources/views/depositPrompt.fxml")));
        Scene depoPrompt = new Scene(depoPromptRoot);

        Stage depositWindow = new Stage();

        depositWindow.setTitle("Deposit Prompt");
        depositWindow.setScene(depoPrompt);
        depositWindow.show();

    }
}
