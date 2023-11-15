package application.orowan_3;

//The class Password controls the interface "Login". It's an identification interface that requests a password.
//There is a page for each password ( engineer or worker).

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.scene.Node;

public class Password implements Initializable {
    int correctPwd = 0;
    @FXML
    private PasswordField password;
    @FXML
    private TextFlow txt;

    public void initialize(URL url, ResourceBundle rb) {

        password.setPromptText("Your password");
        Text text = new Text(" ");
        text.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
        txt.getChildren().add(text);

        password.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                User user = new User("worker", "engineer");

                //Open the "Graph" page if the password is "worker"
                if (password.getText().equals(user.getWorkerPwd())) {
                    text.setText("Password confirmed");
                    correctPwd = 1;
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("Graph.fxml"));
                        Scene scene = new Scene(root);
                        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();

                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }

                }
                //Open the "Graph_and_SQL" page if the password is "engineer"
                else if (password.getText().equals(user.getIngPwd())) {
                    text.setText("Password confirmed");
                    correctPwd = 2;

                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("Graph_and_SQL.fxml"));
                        Scene scene = new Scene(root);
                        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
                //show the following text if the password is neither "worker" nor "engineer"
                else {
                    text.setText("Incorrect password ! Try again.");
                }
            }
        });
    }
}