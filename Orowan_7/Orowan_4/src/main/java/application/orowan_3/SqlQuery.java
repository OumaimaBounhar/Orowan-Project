package application.orowan_3;

//The class "SqlQuery" is a class that controls the interface "SQL_Query". It's an interface for engineers
//which pops up when we click on the button "sql" from the interface "Graph_and_SQL".
//It allows us writing sql queries and printing results.

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SqlQuery implements Initializable{
    @FXML
    private Button coef; //Output
    @FXML
    private Button query;
    @FXML
    private Button button;
    @FXML
    private Button other;//Query
    @FXML
    private Button input;//Input
    @FXML
    private TextField sql;
    @FXML
    private ScrollPane result;
    Text text_result = new Text(" ");

    public void initialize(URL url, ResourceBundle rb) {
        result.setContent(text_result);
        //the "coef" button binds the text box query and calls the "DataBase" class to execute it
        coef.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                if ((sql.getText() != null && !sql.getText().isEmpty())) {
                    try {
                        DataBase data = DataBase.getInstance();

                        String listString = String.join(", ", data.sqlQuery(sql.getText()));
                        text_result.setText(listString);
                    } catch (SQLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }

                }
            }
        });
        //the "input" button binds the text box query and calls the "DataBase" class to execute it
        input.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                if ((sql.getText() != null && !sql.getText().isEmpty())) {
                    try {
                        DataBase data = DataBase.getInstance();

                        String listString = String.join(", ", data.sqlQueryInput(sql.getText()));
                        text_result.setText(listString);
                    } catch (SQLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }

                }
            }
        });
        //the "other" button binds the text box query and calls the "DataBase" class to execute it
        other.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                if ((sql.getText() != null && !sql.getText().isEmpty())) {
                    try {
                        DataBase data = DataBase.getInstance();

                        String listString = String.join(", ", data.otherSqlQueries(sql.getText()));
                        text_result.setText(listString);
                    } catch (SQLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }

                }
            }
        });
    }

}