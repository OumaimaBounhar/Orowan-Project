package application.orowan_3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.io.IOException;

public class Main extends Application {
    public static void main(String[] args) throws Exception {
        // Here, we are connected to the database
        //DataBase data = new DataBase();
        DataBase.getInstance().createDataFrame(); // Called only one time, in the beginning
        launch();
    }

    @Override

    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Scene scene = new Scene(root);
            Password pass = new Password();
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}