package controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class CashierMainFormController implements Initializable {
    public JFXButton BackLogincontext;
    public AnchorPane cashierMainFormcontext;
    public Label lblDate;
    public Label lblTime;
    public AnchorPane LineChartAncorPane;
    public Button settings;


    private double x=0,y=0;

    private Stage stage;
    @FXML
    private LineChart<?, ?> LineChart;

    @FXML
    private CategoryAxis LineX;

    @FXML
    private NumberAxis LineY;





    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TimeNow();

        XYChart.Series series1=new XYChart.Series();

        series1.getData().add(new XYChart.Data("Jan",5));
        series1.getData().add(new XYChart.Data("Feb",4));
        series1.getData().add(new XYChart.Data("Mar",6));
        series1.getData().add(new XYChart.Data("Apr",3));
        series1.getData().add(new XYChart.Data("June",10));







        XYChart.Series series3 =new XYChart.Series();
        series3.getData().add(new XYChart.Data("2020",1));
        series3.getData().add(new XYChart.Data("2021",4));
        series3.getData().add(new XYChart.Data("2022",9));









       LineChart.getXAxis().setLabel("Date");
        LineChart.getYAxis().setLabel("Daily Process");




        XYChart.Series series2= new XYChart.Series();

        series2.getData().add(new XYChart.Data("Drug",2));
        series2.getData().add(new XYChart.Data("Cus",2));
        series2.getData().add(new XYChart.Data("Suppli",5));


        LineChart.getData().addAll(series1,series2,series3);




    }







    private void TimeNow() {

        Timeline dateTime = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("hh:mm:ss a");
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            lblTime.setText(LocalDateTime.now().format(formatter1));
              lblDate.setText(LocalDateTime.now().format(formatter2));
        }), new KeyFrame(Duration.seconds(1)));
        dateTime.setCycleCount(Animation.INDEFINITE);
        dateTime.play();


    }




    public void backtoLoginFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("LoginPage");
    }


    private void setUi(String location) throws IOException {
     Stage stage =(Stage)cashierMainFormcontext.getScene().getWindow();
     stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../fxmlFiles/"+location+".fxml"))));

    }


    public void CashierBillingOnAction(ActionEvent actionEvent) throws IOException {
   setUi("CashierBillingDashboard");
    }

    public void btnCashierMyDetailsOnAction(ActionEvent actionEvent) throws IOException {
    setUi("CahierMyDetails");
    }

    public void btnCashierTransactionOnAction(ActionEvent actionEvent) throws IOException {
    setUi("CashierTransactionDetails");
    }


    public void settings(ActionEvent actionEvent) {
    }
}