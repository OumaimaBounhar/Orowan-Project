package application.orowan_3;

//The class Graph is a class that controls the interface "Graph". It's an interface for workers,
//that pop up if we enter the password "worker".
//This class is identical the class SqlGraph, the only difference is the button sql.

//See comments in the class SqlGraph, it "includes" this class

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class Graph implements Initializable {
    @FXML
    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();
    NewFile file = new NewFile();
    int selectedReel = 0;
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
    private ChoiceBox F;

    @SuppressWarnings("unchecked")
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        XYChart.Series series = new XYChart.Series();
        F.getItems().add("F3");
        F.getItems().add("F2");
        String value = (String) F.getValue();

        lineChart.getData().add(series);
        for (int i = 0; i < 56; i++) {
            series.getData().add(new XYChart.Data<String, Number>(Integer.toString(i), 0));
        }

        //sql.setText("Enter sql query");
        series.setName("Friction coefficient");
        Text text = new Text(" ");
        Text text_result = new Text(" ");
        text.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
        alertDisplay.getChildren().add(text);


        F.setOnAction((event) -> {
            int selectedIndex = F.getSelectionModel().getSelectedIndex();
            Object selectedItem = F.getSelectionModel().getSelectedItem();
            if (selectedItem.equals("F3")) {
                try {
                    file.F3();
                    selectedReel = 3;
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
            if (selectedItem.equals("F2")) {
                try {
                    file.F2();
                    selectedReel = 2;
                } catch (Exception e2) {
                    // TODO Auto-generated catch block
                    e2.printStackTrace();
                }
            }
        });
        coef_de_friction.setOnAction(new EventHandler<ActionEvent>() {//friction coeffienct
            @Override
            public void handle(ActionEvent e) {
                if (selectedReel == 0) {
                    text.setText("Choose  F2 or F3");
                } else {
                    series.getData().clear();
                    categories_coef = 1;
                    series.setName("Friction coefficient");
                    ArrayList<Float> coord = new ArrayList<Float>();
                    series.getData().clear();
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
        sigma.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (selectedReel == 0) {
                    text.setText("Choose F2 or F3");
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

        roll.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (selectedReel == 0) {
                    text.setText("Choose F2 or F3");
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


    }

    public ArrayList<Float> get_coef() throws Exception {
        SystProcess driver = new SystProcess();
        driver.syst_Process();
        ArrayList<Float> coord = driver.frictionCoeff();
        for (int i = 0; i < coord.size(); i++) {
            DataBase.getInstance().storeOutput(coord.get(i));
        }
        return coord;
    }

    public ArrayList<Float> get_roll() throws Exception {
        SystProcess driver = new SystProcess();
        driver.syst_Process();
        ArrayList<Float> coord = driver.roll();
        for (int i = 0; i < coord.size(); i++) {
            DataBase.getInstance().storeOutput(coord.get(i));
        }
        return coord;
    }

    public ArrayList<Float> get_sigma() throws Exception {
        SystProcess lect = new SystProcess();
        lect.syst_Process();
        ArrayList<Float> coord = lect.sigma();
        for (int i = 0; i < coord.size(); i++) {
            DataBase.getInstance().storeOutput(coord.get(i));
        }
        return coord;
    }

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