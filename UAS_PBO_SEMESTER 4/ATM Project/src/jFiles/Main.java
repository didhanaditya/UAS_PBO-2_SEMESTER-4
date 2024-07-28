package jFiles;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import resources.data.dataManip;
import resources.data.dataManager;

import java.util.Objects;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {        // Landing Page (Sign In Prompt)

        Parent signinRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("resources/views/signinform.fxml")));
        Scene signIn = new Scene(signinRoot);


        primaryStage.setTitle("Jeff's Fargo");
        primaryStage.setScene(signIn);
        primaryStage.show();

    }


    @Override
    public void stop() throws Exception {       // Clears text file data and Postgres table data when app is closed
        dataManip data = new dataManip();
        data.clear();
        dataManager database = new dataManager();
        database.truncate();
        database.idReset();
        System.out.println("You have been signed out.");
        System.out.println("Thank you for banking with Jeff's Fargo!");

    }

    public static void main(String[] args) {
        launch(args);

    }

}
