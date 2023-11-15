package application.orowan_3;

//The class SqlGraph is a class that controls the interface "Graph_and_SQL". It's an interface for engineers,
//that pop up if we enter the password "engineer". This class has a button sql, that calls an interface
// and which linked to sql queries and database about the different indicators.
//This class is identical the class Graph, the only difference is the button sql and the interface called.
//When we click on the button sql, the interface "sql_Query" pop up, it's used to let us type sql queries.

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class SqlGraph implements Initializable {
    @FXML
    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();
    NewFile file = new NewFile();
    int selectedReel = 0; //either the reel 2 or 3
    int categories_coef = 0;
    @FXML
    private LineChart lineChart;
    @FXML
    private TextFlow alertDisplay;
    @FXML
    private Button coef_de_friction;
    @FXML
    private Button sigma;
    @FXML
    private Button roll;
    @FXML
    private Button sql;
    @FXML
    private ChoiceBox F;

    @SuppressWarnings("unchecked")
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        XYChart.Series series = new XYChart.Series();
        //Choices of ChoiceBox :
        F.getItems().add("F3");
        F.getItems().add("F2");
        String value = (String) F.getValue();
        //We add the values that we have gotten from the documents generated by orowan on the graph
        lineChart.getData().add(series);
        for (int i = 0; i < 56; i++) {
            series.getData().add(new XYChart.Data<String, Number>(Integer.toString(i), 0));
        }
        series.setName("Friction coefficient");
        Text text = new Text(" ");
        Text textResult = new Text(" ");
        text.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
        //we configure the text zone which displays the messages concerning the states of the reel
        alertDisplay.getChildren().add(text);
        // choiceBox F has two options for 2 reels: F2 and F3
        F.setOnAction((event) -> {
            int selectedIndex = F.getSelectionModel().getSelectedIndex();
            Object selectedItem = F.getSelectionModel().getSelectedItem();
            if (selectedItem.equals("F3")) {
                try {
                    //We call the method F3, that rewrite the reel document so that it's correctly read by orowan.exe
                    file.F3();
                    selectedReel = 3;
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
            if (selectedItem.equals("F2")) {
                try {
                    //We call the method F2, that rewrite the reel document so that it's correctly read by orowan.exe
                    file.F2();
                    selectedReel = 2;
                } catch (Exception e2) {
                    // TODO Auto-generated catch block
                    e2.printStackTrace();
                }
            }
        });
        //the button "coef_de_friction" draw a graph using the data about the friction coefficient
        coef_de_friction.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                //If none of the reels is chosen, we show the following message
                if (selectedReel == 0) {
                    text.setText("Chose F2 or F3");
                } else {
                    //Clear the elements in the list
                    series.getData().clear();
                    categories_coef = 1; // ??
                    series.setName("Friction coefficient");
                    ArrayList<Float> coord = new ArrayList<Float>();
                    try {
                        coord = get_coef();
                        System.out.println(coord);
                        addCoord(series, text, coord);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        //Draw the concerned graph when we click on button "sigma".
        sigma.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (selectedReel == 0) {
                    text.setText("Chose F2 or F3");
                } else {
                    series.getData().clear();
                    categories_coef = 0;
                    series.setName("Sigma");
                    ArrayList<Float> coord = new ArrayList<Float>();
                    series.getData().clear();
                    try {
                        coord.clear();
                        coord = get_sigma();
                        addCoord(series, text, coord);
                    } catch (Exception e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            }
        });
        //Draw the concerned graph when we click on button "roll".
        roll.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (selectedReel == 0) {
                    text.setText("Chose F2 or F3");
                } else {
                    series.getData().clear();
                    categories_coef = 0;
                    series.setName("Roll speed");
                    ArrayList<Float> coord = new ArrayList<Float>();
                    series.getData().clear();
                    try {
                        coord.clear();
                        coord = get_roll();
                        addCoord(series, text, coord);
                    } catch (Exception e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            }
        });
        //We open a new page with the "sql" interface when we click on the button sql.
        sql.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Parent root;
                try {
                    root = FXMLLoader.load(getClass().getResource("SQL_Query.fxml"));
                    Scene scene = new Scene(root);
                    Stage primaryStage = new Stage();
                    primaryStage.setScene(scene);
                    primaryStage.show();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

            }
        });
    }

    //Methods to get the chosen indicators
    public ArrayList<Float> get_coef() throws Exception {
        SystProcess driver = new SystProcess();
        driver.syst_Process();
        //We add data to the list "coord" using the results of the method frictionCoeff()
        ArrayList<Float> coord = driver.frictionCoeff();
        //Add the coefficients that we got from the data base
        for (int i = 0; i < coord.size(); i++) {
            DataBase.getInstance().storeOutput(coord.get(i));
        }
        return coord;
    }

    public ArrayList<Float> get_roll() throws Exception {
        SystProcess driver = new SystProcess();
        driver.syst_Process();
        //We add data to the list "coord" using the results of the method roll()
        ArrayList<Float> coord = driver.roll();
        //Add the coefficients that we got from the data base
        for (int i = 0; i < coord.size(); i++) {
            DataBase.getInstance().storeOutput(coord.get(i));
        }
        return coord;
    }

    public ArrayList<Float> get_sigma() throws Exception {
        SystProcess driver = new SystProcess();
        driver.syst_Process();
        //We add data to the list "coord" using the results of the method sigma()
        ArrayList<Float> coord = driver.sigma();
        //Add the coefficients that we got from the data base
        for (int i = 0; i < coord.size(); i++) {
            DataBase.getInstance().storeOutput(coord.get(i));
        }
        return coord;
    }

    //Draw the graph using the list "coord" et show the message in the text zone depending on the conditions about values
    public void addCoord(XYChart.Series series, Text text, ArrayList<Float> coord) throws Exception {
        series.getData().clear();
        for (int i = 0; i < coord.size(); i++) {
            series.getData().add(new XYChart.Data<String, Number>(Integer.toString(i), coord.get(i)));
            if (coord.get(i) >= 0.6 && categories_coef == 1) {
                text.setText("Add lubricant");
            } else if (coord.get(i) >= 0.8 && categories_coef == 1) {
                text.setText("Damaged surface");
            } else if (coord.get(i) < 0.6 && categories_coef == 1) {
                text.setText("Friction is acceptable");
            }
        }
    }

}
