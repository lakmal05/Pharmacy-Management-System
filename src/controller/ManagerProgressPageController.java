package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

public class ManagerProgressPageController implements Initializable {
    @FXML
    public AnchorPane ProgressContext;
    @FXML
    public ProgressBar ManagerProgressbar;
    @FXML
    public JFXButton ProgressBtnContext;
    public Label MyLable;


    BigDecimal progress=new BigDecimal(String.format("%.2f",0.0)) ;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       ManagerProgressbar.setStyle("-fx-alignment:#a4b0be;");

    }

    private void setUi(String location) throws IOException {
        Stage stage =(Stage)ProgressContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../fxmlFiles/"+location+".fxml"))));
        stage.centerOnScreen();
    }

















    public void btnOnAction(ActionEvent actionEvent) throws IOException {

        if (progress.doubleValue()<1) {

            progress = new BigDecimal(String.format("%.2f",progress.doubleValue()+0.5));
            System.out.println(progress.doubleValue());
            ManagerProgressbar.setProgress(progress.doubleValue());
            MyLable.setText(Double.toString((int) Math.round(progress .doubleValue()* 100)) + "%  Completed");

        }
        setUi("ManagerMainForm");


    }
}
